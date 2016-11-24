package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.SavedList;
import com.datasolution.hire.repository.SavedListRepository;

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
 * Test class for the SavedListResource REST controller.
 *
 * @see SavedListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class SavedListResourceIntTest {

    private static final Integer DEFAULT_SAVED_LIST_ID = 1;
    private static final Integer UPDATED_SAVED_LIST_ID = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_IS_DYNAMIC = 1;
    private static final Integer UPDATED_IS_DYNAMIC = 2;

    private static final String DEFAULT_DATAGRID_INSTANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATAGRID_INSTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETERS = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETERS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_NUMBER_ENTRIES = 1;
    private static final Integer UPDATED_NUMBER_ENTRIES = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    @Inject
    private SavedListRepository savedListRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSavedListMockMvc;

    private SavedList savedList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SavedListResource savedListResource = new SavedListResource();
        ReflectionTestUtils.setField(savedListResource, "savedListRepository", savedListRepository);
        this.restSavedListMockMvc = MockMvcBuilders.standaloneSetup(savedListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedList createEntity(EntityManager em) {
        SavedList savedList = new SavedList()
                .savedListId(DEFAULT_SAVED_LIST_ID)
                .description(DEFAULT_DESCRIPTION)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .siteId(DEFAULT_SITE_ID)
                .isDynamic(DEFAULT_IS_DYNAMIC)
                .datagridInstance(DEFAULT_DATAGRID_INSTANCE)
                .parameters(DEFAULT_PARAMETERS)
                .createdBy(DEFAULT_CREATED_BY)
                .numberEntries(DEFAULT_NUMBER_ENTRIES)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED);
        return savedList;
    }

    @Before
    public void initTest() {
        savedList = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedList() throws Exception {
        int databaseSizeBeforeCreate = savedListRepository.findAll().size();

        // Create the SavedList

        restSavedListMockMvc.perform(post("/api/saved-lists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(savedList)))
                .andExpect(status().isCreated());

        // Validate the SavedList in the database
        List<SavedList> savedLists = savedListRepository.findAll();
        assertThat(savedLists).hasSize(databaseSizeBeforeCreate + 1);
        SavedList testSavedList = savedLists.get(savedLists.size() - 1);
        assertThat(testSavedList.getSavedListId()).isEqualTo(DEFAULT_SAVED_LIST_ID);
        assertThat(testSavedList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSavedList.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testSavedList.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testSavedList.getIsDynamic()).isEqualTo(DEFAULT_IS_DYNAMIC);
        assertThat(testSavedList.getDatagridInstance()).isEqualTo(DEFAULT_DATAGRID_INSTANCE);
        assertThat(testSavedList.getParameters()).isEqualTo(DEFAULT_PARAMETERS);
        assertThat(testSavedList.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSavedList.getNumberEntries()).isEqualTo(DEFAULT_NUMBER_ENTRIES);
        assertThat(testSavedList.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testSavedList.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSavedLists() throws Exception {
        // Initialize the database
        savedListRepository.saveAndFlush(savedList);

        // Get all the savedLists
        restSavedListMockMvc.perform(get("/api/saved-lists?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(savedList.getId().intValue())))
                .andExpect(jsonPath("$.[*].savedListId").value(hasItem(DEFAULT_SAVED_LIST_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].isDynamic").value(hasItem(DEFAULT_IS_DYNAMIC)))
                .andExpect(jsonPath("$.[*].datagridInstance").value(hasItem(DEFAULT_DATAGRID_INSTANCE.toString())))
                .andExpect(jsonPath("$.[*].parameters").value(hasItem(DEFAULT_PARAMETERS.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].numberEntries").value(hasItem(DEFAULT_NUMBER_ENTRIES)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)));
    }

    @Test
    @Transactional
    public void getSavedList() throws Exception {
        // Initialize the database
        savedListRepository.saveAndFlush(savedList);

        // Get the savedList
        restSavedListMockMvc.perform(get("/api/saved-lists/{id}", savedList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(savedList.getId().intValue()))
            .andExpect(jsonPath("$.savedListId").value(DEFAULT_SAVED_LIST_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.isDynamic").value(DEFAULT_IS_DYNAMIC))
            .andExpect(jsonPath("$.datagridInstance").value(DEFAULT_DATAGRID_INSTANCE.toString()))
            .andExpect(jsonPath("$.parameters").value(DEFAULT_PARAMETERS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.numberEntries").value(DEFAULT_NUMBER_ENTRIES))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSavedList() throws Exception {
        // Get the savedList
        restSavedListMockMvc.perform(get("/api/saved-lists/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedList() throws Exception {
        // Initialize the database
        savedListRepository.saveAndFlush(savedList);
        int databaseSizeBeforeUpdate = savedListRepository.findAll().size();

        // Update the savedList
        SavedList updatedSavedList = savedListRepository.findOne(savedList.getId());
        updatedSavedList
                .savedListId(UPDATED_SAVED_LIST_ID)
                .description(UPDATED_DESCRIPTION)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .siteId(UPDATED_SITE_ID)
                .isDynamic(UPDATED_IS_DYNAMIC)
                .datagridInstance(UPDATED_DATAGRID_INSTANCE)
                .parameters(UPDATED_PARAMETERS)
                .createdBy(UPDATED_CREATED_BY)
                .numberEntries(UPDATED_NUMBER_ENTRIES)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED);

        restSavedListMockMvc.perform(put("/api/saved-lists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSavedList)))
                .andExpect(status().isOk());

        // Validate the SavedList in the database
        List<SavedList> savedLists = savedListRepository.findAll();
        assertThat(savedLists).hasSize(databaseSizeBeforeUpdate);
        SavedList testSavedList = savedLists.get(savedLists.size() - 1);
        assertThat(testSavedList.getSavedListId()).isEqualTo(UPDATED_SAVED_LIST_ID);
        assertThat(testSavedList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSavedList.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testSavedList.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testSavedList.getIsDynamic()).isEqualTo(UPDATED_IS_DYNAMIC);
        assertThat(testSavedList.getDatagridInstance()).isEqualTo(UPDATED_DATAGRID_INSTANCE);
        assertThat(testSavedList.getParameters()).isEqualTo(UPDATED_PARAMETERS);
        assertThat(testSavedList.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSavedList.getNumberEntries()).isEqualTo(UPDATED_NUMBER_ENTRIES);
        assertThat(testSavedList.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testSavedList.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void deleteSavedList() throws Exception {
        // Initialize the database
        savedListRepository.saveAndFlush(savedList);
        int databaseSizeBeforeDelete = savedListRepository.findAll().size();

        // Get the savedList
        restSavedListMockMvc.perform(delete("/api/saved-lists/{id}", savedList.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SavedList> savedLists = savedListRepository.findAll();
        assertThat(savedLists).hasSize(databaseSizeBeforeDelete - 1);
    }
}
