package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.SavedSearch;
import com.datasolution.hire.repository.SavedSearchRepository;

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
 * Test class for the SavedSearchResource REST controller.
 *
 * @see SavedSearchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class SavedSearchResourceIntTest {

    private static final Integer DEFAULT_SEARCH_ID = 1;
    private static final Integer UPDATED_SEARCH_ID = 2;

    private static final String DEFAULT_DATA_ITEM_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ITEM_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_CUSTOM = 1;
    private static final Integer UPDATED_IS_CUSTOM = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    @Inject
    private SavedSearchRepository savedSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSavedSearchMockMvc;

    private SavedSearch savedSearch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SavedSearchResource savedSearchResource = new SavedSearchResource();
        ReflectionTestUtils.setField(savedSearchResource, "savedSearchRepository", savedSearchRepository);
        this.restSavedSearchMockMvc = MockMvcBuilders.standaloneSetup(savedSearchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedSearch createEntity(EntityManager em) {
        SavedSearch savedSearch = new SavedSearch()
                .searchId(DEFAULT_SEARCH_ID)
                .dataItemText(DEFAULT_DATA_ITEM_TEXT)
                .url(DEFAULT_URL)
                .isCustom(DEFAULT_IS_CUSTOM)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .userId(DEFAULT_USER_ID)
                .siteId(DEFAULT_SITE_ID)
                .dateCreated(DEFAULT_DATE_CREATED);
        return savedSearch;
    }

    @Before
    public void initTest() {
        savedSearch = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedSearch() throws Exception {
        int databaseSizeBeforeCreate = savedSearchRepository.findAll().size();

        // Create the SavedSearch

        restSavedSearchMockMvc.perform(post("/api/saved-searches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(savedSearch)))
                .andExpect(status().isCreated());

        // Validate the SavedSearch in the database
        List<SavedSearch> savedSearches = savedSearchRepository.findAll();
        assertThat(savedSearches).hasSize(databaseSizeBeforeCreate + 1);
        SavedSearch testSavedSearch = savedSearches.get(savedSearches.size() - 1);
        assertThat(testSavedSearch.getSearchId()).isEqualTo(DEFAULT_SEARCH_ID);
        assertThat(testSavedSearch.getDataItemText()).isEqualTo(DEFAULT_DATA_ITEM_TEXT);
        assertThat(testSavedSearch.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSavedSearch.getIsCustom()).isEqualTo(DEFAULT_IS_CUSTOM);
        assertThat(testSavedSearch.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testSavedSearch.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSavedSearch.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testSavedSearch.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllSavedSearches() throws Exception {
        // Initialize the database
        savedSearchRepository.saveAndFlush(savedSearch);

        // Get all the savedSearches
        restSavedSearchMockMvc.perform(get("/api/saved-searches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(savedSearch.getId().intValue())))
                .andExpect(jsonPath("$.[*].searchId").value(hasItem(DEFAULT_SEARCH_ID)))
                .andExpect(jsonPath("$.[*].dataItemText").value(hasItem(DEFAULT_DATA_ITEM_TEXT.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].isCustom").value(hasItem(DEFAULT_IS_CUSTOM)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getSavedSearch() throws Exception {
        // Initialize the database
        savedSearchRepository.saveAndFlush(savedSearch);

        // Get the savedSearch
        restSavedSearchMockMvc.perform(get("/api/saved-searches/{id}", savedSearch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(savedSearch.getId().intValue()))
            .andExpect(jsonPath("$.searchId").value(DEFAULT_SEARCH_ID))
            .andExpect(jsonPath("$.dataItemText").value(DEFAULT_DATA_ITEM_TEXT.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.isCustom").value(DEFAULT_IS_CUSTOM))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSavedSearch() throws Exception {
        // Get the savedSearch
        restSavedSearchMockMvc.perform(get("/api/saved-searches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedSearch() throws Exception {
        // Initialize the database
        savedSearchRepository.saveAndFlush(savedSearch);
        int databaseSizeBeforeUpdate = savedSearchRepository.findAll().size();

        // Update the savedSearch
        SavedSearch updatedSavedSearch = savedSearchRepository.findOne(savedSearch.getId());
        updatedSavedSearch
                .searchId(UPDATED_SEARCH_ID)
                .dataItemText(UPDATED_DATA_ITEM_TEXT)
                .url(UPDATED_URL)
                .isCustom(UPDATED_IS_CUSTOM)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .userId(UPDATED_USER_ID)
                .siteId(UPDATED_SITE_ID)
                .dateCreated(UPDATED_DATE_CREATED);

        restSavedSearchMockMvc.perform(put("/api/saved-searches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSavedSearch)))
                .andExpect(status().isOk());

        // Validate the SavedSearch in the database
        List<SavedSearch> savedSearches = savedSearchRepository.findAll();
        assertThat(savedSearches).hasSize(databaseSizeBeforeUpdate);
        SavedSearch testSavedSearch = savedSearches.get(savedSearches.size() - 1);
        assertThat(testSavedSearch.getSearchId()).isEqualTo(UPDATED_SEARCH_ID);
        assertThat(testSavedSearch.getDataItemText()).isEqualTo(UPDATED_DATA_ITEM_TEXT);
        assertThat(testSavedSearch.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSavedSearch.getIsCustom()).isEqualTo(UPDATED_IS_CUSTOM);
        assertThat(testSavedSearch.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testSavedSearch.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSavedSearch.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testSavedSearch.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void deleteSavedSearch() throws Exception {
        // Initialize the database
        savedSearchRepository.saveAndFlush(savedSearch);
        int databaseSizeBeforeDelete = savedSearchRepository.findAll().size();

        // Get the savedSearch
        restSavedSearchMockMvc.perform(delete("/api/saved-searches/{id}", savedSearch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SavedSearch> savedSearches = savedSearchRepository.findAll();
        assertThat(savedSearches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
