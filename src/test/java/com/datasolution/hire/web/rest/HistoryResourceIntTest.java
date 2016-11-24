package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.History;
import com.datasolution.hire.repository.HistoryRepository;

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
 * Test class for the HistoryResource REST controller.
 *
 * @see HistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class HistoryResourceIntTest {

    private static final Integer DEFAULT_HISTORY_ID = 1;
    private static final Integer UPDATED_HISTORY_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_DATA_ITEM_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_ID = 2;

    private static final String DEFAULT_THE_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_THE_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUS_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SET_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SET_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SET_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_SET_DATE);

    private static final Integer DEFAULT_ENTERED_BY = 1;
    private static final Integer UPDATED_ENTERED_BY = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    @Inject
    private HistoryRepository historyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHistoryMockMvc;

    private History history;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistoryResource historyResource = new HistoryResource();
        ReflectionTestUtils.setField(historyResource, "historyRepository", historyRepository);
        this.restHistoryMockMvc = MockMvcBuilders.standaloneSetup(historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createEntity(EntityManager em) {
        History history = new History()
                .historyId(DEFAULT_HISTORY_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .dataItemId(DEFAULT_DATA_ITEM_ID)
                .theField(DEFAULT_THE_FIELD)
                .previousValue(DEFAULT_PREVIOUS_VALUE)
                .newValue(DEFAULT_NEW_VALUE)
                .description(DEFAULT_DESCRIPTION)
                .setDate(DEFAULT_SET_DATE)
                .enteredBy(DEFAULT_ENTERED_BY)
                .siteId(DEFAULT_SITE_ID);
        return history;
    }

    @Before
    public void initTest() {
        history = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistory() throws Exception {
        int databaseSizeBeforeCreate = historyRepository.findAll().size();

        // Create the History

        restHistoryMockMvc.perform(post("/api/histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(history)))
                .andExpect(status().isCreated());

        // Validate the History in the database
        List<History> histories = historyRepository.findAll();
        assertThat(histories).hasSize(databaseSizeBeforeCreate + 1);
        History testHistory = histories.get(histories.size() - 1);
        assertThat(testHistory.getHistoryId()).isEqualTo(DEFAULT_HISTORY_ID);
        assertThat(testHistory.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testHistory.getDataItemId()).isEqualTo(DEFAULT_DATA_ITEM_ID);
        assertThat(testHistory.getTheField()).isEqualTo(DEFAULT_THE_FIELD);
        assertThat(testHistory.getPreviousValue()).isEqualTo(DEFAULT_PREVIOUS_VALUE);
        assertThat(testHistory.getNewValue()).isEqualTo(DEFAULT_NEW_VALUE);
        assertThat(testHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHistory.getSetDate()).isEqualTo(DEFAULT_SET_DATE);
        assertThat(testHistory.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testHistory.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllHistories() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get all the histories
        restHistoryMockMvc.perform(get("/api/histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(history.getId().intValue())))
                .andExpect(jsonPath("$.[*].historyId").value(hasItem(DEFAULT_HISTORY_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].dataItemId").value(hasItem(DEFAULT_DATA_ITEM_ID)))
                .andExpect(jsonPath("$.[*].theField").value(hasItem(DEFAULT_THE_FIELD.toString())))
                .andExpect(jsonPath("$.[*].previousValue").value(hasItem(DEFAULT_PREVIOUS_VALUE.toString())))
                .andExpect(jsonPath("$.[*].newValue").value(hasItem(DEFAULT_NEW_VALUE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].setDate").value(hasItem(DEFAULT_SET_DATE_STR)))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)));
    }

    @Test
    @Transactional
    public void getHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get the history
        restHistoryMockMvc.perform(get("/api/histories/{id}", history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(history.getId().intValue()))
            .andExpect(jsonPath("$.historyId").value(DEFAULT_HISTORY_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.dataItemId").value(DEFAULT_DATA_ITEM_ID))
            .andExpect(jsonPath("$.theField").value(DEFAULT_THE_FIELD.toString()))
            .andExpect(jsonPath("$.previousValue").value(DEFAULT_PREVIOUS_VALUE.toString()))
            .andExpect(jsonPath("$.newValue").value(DEFAULT_NEW_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.setDate").value(DEFAULT_SET_DATE_STR))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingHistory() throws Exception {
        // Get the history
        restHistoryMockMvc.perform(get("/api/histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Update the history
        History updatedHistory = historyRepository.findOne(history.getId());
        updatedHistory
                .historyId(UPDATED_HISTORY_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .dataItemId(UPDATED_DATA_ITEM_ID)
                .theField(UPDATED_THE_FIELD)
                .previousValue(UPDATED_PREVIOUS_VALUE)
                .newValue(UPDATED_NEW_VALUE)
                .description(UPDATED_DESCRIPTION)
                .setDate(UPDATED_SET_DATE)
                .enteredBy(UPDATED_ENTERED_BY)
                .siteId(UPDATED_SITE_ID);

        restHistoryMockMvc.perform(put("/api/histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHistory)))
                .andExpect(status().isOk());

        // Validate the History in the database
        List<History> histories = historyRepository.findAll();
        assertThat(histories).hasSize(databaseSizeBeforeUpdate);
        History testHistory = histories.get(histories.size() - 1);
        assertThat(testHistory.getHistoryId()).isEqualTo(UPDATED_HISTORY_ID);
        assertThat(testHistory.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testHistory.getDataItemId()).isEqualTo(UPDATED_DATA_ITEM_ID);
        assertThat(testHistory.getTheField()).isEqualTo(UPDATED_THE_FIELD);
        assertThat(testHistory.getPreviousValue()).isEqualTo(UPDATED_PREVIOUS_VALUE);
        assertThat(testHistory.getNewValue()).isEqualTo(UPDATED_NEW_VALUE);
        assertThat(testHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistory.getSetDate()).isEqualTo(UPDATED_SET_DATE);
        assertThat(testHistory.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testHistory.getSiteId()).isEqualTo(UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void deleteHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);
        int databaseSizeBeforeDelete = historyRepository.findAll().size();

        // Get the history
        restHistoryMockMvc.perform(delete("/api/histories/{id}", history.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<History> histories = historyRepository.findAll();
        assertThat(histories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
