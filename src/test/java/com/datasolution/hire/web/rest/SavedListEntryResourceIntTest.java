package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.SavedListEntry;
import com.datasolution.hire.repository.SavedListEntryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SavedListEntryResource REST controller.
 *
 * @see SavedListEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class SavedListEntryResourceIntTest {

    private static final Integer DEFAULT_SAVED_LIST_ENTRY_ID = 1;
    private static final Integer UPDATED_SAVED_LIST_ENTRY_ID = 2;

    private static final Integer DEFAULT_SAVED_LIST_ID = 1;
    private static final Integer UPDATED_SAVED_LIST_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_DATA_ITEM_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    @Inject
    private SavedListEntryRepository savedListEntryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSavedListEntryMockMvc;

    private SavedListEntry savedListEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SavedListEntryResource savedListEntryResource = new SavedListEntryResource();
        ReflectionTestUtils.setField(savedListEntryResource, "savedListEntryRepository", savedListEntryRepository);
        this.restSavedListEntryMockMvc = MockMvcBuilders.standaloneSetup(savedListEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedListEntry createEntity(EntityManager em) {
        SavedListEntry savedListEntry = new SavedListEntry()
                .savedListEntryId(DEFAULT_SAVED_LIST_ENTRY_ID)
                .savedListId(DEFAULT_SAVED_LIST_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .dataItemId(DEFAULT_DATA_ITEM_ID)
                .siteId(DEFAULT_SITE_ID)
                .dateCreated(DEFAULT_DATE_CREATED);
        return savedListEntry;
    }

    @Before
    public void initTest() {
        savedListEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedListEntry() throws Exception {
        int databaseSizeBeforeCreate = savedListEntryRepository.findAll().size();

        // Create the SavedListEntry

        restSavedListEntryMockMvc.perform(post("/api/saved-list-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(savedListEntry)))
                .andExpect(status().isCreated());

        // Validate the SavedListEntry in the database
        List<SavedListEntry> savedListEntries = savedListEntryRepository.findAll();
        assertThat(savedListEntries).hasSize(databaseSizeBeforeCreate + 1);
        SavedListEntry testSavedListEntry = savedListEntries.get(savedListEntries.size() - 1);
        assertThat(testSavedListEntry.getSavedListEntryId()).isEqualTo(DEFAULT_SAVED_LIST_ENTRY_ID);
        assertThat(testSavedListEntry.getSavedListId()).isEqualTo(DEFAULT_SAVED_LIST_ID);
        assertThat(testSavedListEntry.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testSavedListEntry.getDataItemId()).isEqualTo(DEFAULT_DATA_ITEM_ID);
        assertThat(testSavedListEntry.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testSavedListEntry.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllSavedListEntries() throws Exception {
        // Initialize the database
        savedListEntryRepository.saveAndFlush(savedListEntry);

        // Get all the savedListEntries
        restSavedListEntryMockMvc.perform(get("/api/saved-list-entries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(savedListEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].savedListEntryId").value(hasItem(DEFAULT_SAVED_LIST_ENTRY_ID)))
                .andExpect(jsonPath("$.[*].savedListId").value(hasItem(DEFAULT_SAVED_LIST_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].dataItemId").value(hasItem(DEFAULT_DATA_ITEM_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getSavedListEntry() throws Exception {
        // Initialize the database
        savedListEntryRepository.saveAndFlush(savedListEntry);

        // Get the savedListEntry
        restSavedListEntryMockMvc.perform(get("/api/saved-list-entries/{id}", savedListEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(savedListEntry.getId().intValue()))
            .andExpect(jsonPath("$.savedListEntryId").value(DEFAULT_SAVED_LIST_ENTRY_ID))
            .andExpect(jsonPath("$.savedListId").value(DEFAULT_SAVED_LIST_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.dataItemId").value(DEFAULT_DATA_ITEM_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSavedListEntry() throws Exception {
        // Get the savedListEntry
        restSavedListEntryMockMvc.perform(get("/api/saved-list-entries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedListEntry() throws Exception {
        // Initialize the database
        savedListEntryRepository.saveAndFlush(savedListEntry);
        int databaseSizeBeforeUpdate = savedListEntryRepository.findAll().size();

        // Update the savedListEntry
        SavedListEntry updatedSavedListEntry = savedListEntryRepository.findOne(savedListEntry.getId());
        updatedSavedListEntry
                .savedListEntryId(UPDATED_SAVED_LIST_ENTRY_ID)
                .savedListId(UPDATED_SAVED_LIST_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .dataItemId(UPDATED_DATA_ITEM_ID)
                .siteId(UPDATED_SITE_ID)
                .dateCreated(UPDATED_DATE_CREATED);

        restSavedListEntryMockMvc.perform(put("/api/saved-list-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSavedListEntry)))
                .andExpect(status().isOk());

        // Validate the SavedListEntry in the database
        List<SavedListEntry> savedListEntries = savedListEntryRepository.findAll();
        assertThat(savedListEntries).hasSize(databaseSizeBeforeUpdate);
        SavedListEntry testSavedListEntry = savedListEntries.get(savedListEntries.size() - 1);
        assertThat(testSavedListEntry.getSavedListEntryId()).isEqualTo(UPDATED_SAVED_LIST_ENTRY_ID);
        assertThat(testSavedListEntry.getSavedListId()).isEqualTo(UPDATED_SAVED_LIST_ID);
        assertThat(testSavedListEntry.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testSavedListEntry.getDataItemId()).isEqualTo(UPDATED_DATA_ITEM_ID);
        assertThat(testSavedListEntry.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testSavedListEntry.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void deleteSavedListEntry() throws Exception {
        // Initialize the database
        savedListEntryRepository.saveAndFlush(savedListEntry);
        int databaseSizeBeforeDelete = savedListEntryRepository.findAll().size();

        // Get the savedListEntry
        restSavedListEntryMockMvc.perform(delete("/api/saved-list-entries/{id}", savedListEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SavedListEntry> savedListEntries = savedListEntryRepository.findAll();
        assertThat(savedListEntries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
