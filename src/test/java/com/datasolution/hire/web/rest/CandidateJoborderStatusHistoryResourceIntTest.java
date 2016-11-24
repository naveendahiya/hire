package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CandidateJoborderStatusHistory;
import com.datasolution.hire.repository.CandidateJoborderStatusHistoryRepository;

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
 * Test class for the CandidateJoborderStatusHistoryResource REST controller.
 *
 * @see CandidateJoborderStatusHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateJoborderStatusHistoryResourceIntTest {

    private static final Integer DEFAULT_CANDIDATE_JOBORDER_STATUS_HISTORY_ID = 1;
    private static final Integer UPDATED_CANDIDATE_JOBORDER_STATUS_HISTORY_ID = 2;

    private static final Integer DEFAULT_CANDIDATE_ID = 1;
    private static final Integer UPDATED_CANDIDATE_ID = 2;

    private static final Integer DEFAULT_JOBORDER_ID = 1;
    private static final Integer UPDATED_JOBORDER_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE);

    private static final Integer DEFAULT_STATUS_FROM = 1;
    private static final Integer UPDATED_STATUS_FROM = 2;

    private static final Integer DEFAULT_STATUS_TO = 1;
    private static final Integer UPDATED_STATUS_TO = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    @Inject
    private CandidateJoborderStatusHistoryRepository candidateJoborderStatusHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateJoborderStatusHistoryMockMvc;

    private CandidateJoborderStatusHistory candidateJoborderStatusHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateJoborderStatusHistoryResource candidateJoborderStatusHistoryResource = new CandidateJoborderStatusHistoryResource();
        ReflectionTestUtils.setField(candidateJoborderStatusHistoryResource, "candidateJoborderStatusHistoryRepository", candidateJoborderStatusHistoryRepository);
        this.restCandidateJoborderStatusHistoryMockMvc = MockMvcBuilders.standaloneSetup(candidateJoborderStatusHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateJoborderStatusHistory createEntity(EntityManager em) {
        CandidateJoborderStatusHistory candidateJoborderStatusHistory = new CandidateJoborderStatusHistory()
                .candidateJoborderStatusHistoryId(DEFAULT_CANDIDATE_JOBORDER_STATUS_HISTORY_ID)
                .candidateId(DEFAULT_CANDIDATE_ID)
                .joborderId(DEFAULT_JOBORDER_ID)
                .date(DEFAULT_DATE)
                .statusFrom(DEFAULT_STATUS_FROM)
                .statusTo(DEFAULT_STATUS_TO)
                .siteId(DEFAULT_SITE_ID);
        return candidateJoborderStatusHistory;
    }

    @Before
    public void initTest() {
        candidateJoborderStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateJoborderStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = candidateJoborderStatusHistoryRepository.findAll().size();

        // Create the CandidateJoborderStatusHistory

        restCandidateJoborderStatusHistoryMockMvc.perform(post("/api/candidate-joborder-status-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateJoborderStatusHistory)))
                .andExpect(status().isCreated());

        // Validate the CandidateJoborderStatusHistory in the database
        List<CandidateJoborderStatusHistory> candidateJoborderStatusHistories = candidateJoborderStatusHistoryRepository.findAll();
        assertThat(candidateJoborderStatusHistories).hasSize(databaseSizeBeforeCreate + 1);
        CandidateJoborderStatusHistory testCandidateJoborderStatusHistory = candidateJoborderStatusHistories.get(candidateJoborderStatusHistories.size() - 1);
        assertThat(testCandidateJoborderStatusHistory.getCandidateJoborderStatusHistoryId()).isEqualTo(DEFAULT_CANDIDATE_JOBORDER_STATUS_HISTORY_ID);
        assertThat(testCandidateJoborderStatusHistory.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCandidateJoborderStatusHistory.getJoborderId()).isEqualTo(DEFAULT_JOBORDER_ID);
        assertThat(testCandidateJoborderStatusHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCandidateJoborderStatusHistory.getStatusFrom()).isEqualTo(DEFAULT_STATUS_FROM);
        assertThat(testCandidateJoborderStatusHistory.getStatusTo()).isEqualTo(DEFAULT_STATUS_TO);
        assertThat(testCandidateJoborderStatusHistory.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllCandidateJoborderStatusHistories() throws Exception {
        // Initialize the database
        candidateJoborderStatusHistoryRepository.saveAndFlush(candidateJoborderStatusHistory);

        // Get all the candidateJoborderStatusHistories
        restCandidateJoborderStatusHistoryMockMvc.perform(get("/api/candidate-joborder-status-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidateJoborderStatusHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].candidateJoborderStatusHistoryId").value(hasItem(DEFAULT_CANDIDATE_JOBORDER_STATUS_HISTORY_ID)))
                .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID)))
                .andExpect(jsonPath("$.[*].joborderId").value(hasItem(DEFAULT_JOBORDER_ID)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].statusFrom").value(hasItem(DEFAULT_STATUS_FROM)))
                .andExpect(jsonPath("$.[*].statusTo").value(hasItem(DEFAULT_STATUS_TO)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)));
    }

    @Test
    @Transactional
    public void getCandidateJoborderStatusHistory() throws Exception {
        // Initialize the database
        candidateJoborderStatusHistoryRepository.saveAndFlush(candidateJoborderStatusHistory);

        // Get the candidateJoborderStatusHistory
        restCandidateJoborderStatusHistoryMockMvc.perform(get("/api/candidate-joborder-status-histories/{id}", candidateJoborderStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateJoborderStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.candidateJoborderStatusHistoryId").value(DEFAULT_CANDIDATE_JOBORDER_STATUS_HISTORY_ID))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID))
            .andExpect(jsonPath("$.joborderId").value(DEFAULT_JOBORDER_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.statusFrom").value(DEFAULT_STATUS_FROM))
            .andExpect(jsonPath("$.statusTo").value(DEFAULT_STATUS_TO))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateJoborderStatusHistory() throws Exception {
        // Get the candidateJoborderStatusHistory
        restCandidateJoborderStatusHistoryMockMvc.perform(get("/api/candidate-joborder-status-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateJoborderStatusHistory() throws Exception {
        // Initialize the database
        candidateJoborderStatusHistoryRepository.saveAndFlush(candidateJoborderStatusHistory);
        int databaseSizeBeforeUpdate = candidateJoborderStatusHistoryRepository.findAll().size();

        // Update the candidateJoborderStatusHistory
        CandidateJoborderStatusHistory updatedCandidateJoborderStatusHistory = candidateJoborderStatusHistoryRepository.findOne(candidateJoborderStatusHistory.getId());
        updatedCandidateJoborderStatusHistory
                .candidateJoborderStatusHistoryId(UPDATED_CANDIDATE_JOBORDER_STATUS_HISTORY_ID)
                .candidateId(UPDATED_CANDIDATE_ID)
                .joborderId(UPDATED_JOBORDER_ID)
                .date(UPDATED_DATE)
                .statusFrom(UPDATED_STATUS_FROM)
                .statusTo(UPDATED_STATUS_TO)
                .siteId(UPDATED_SITE_ID);

        restCandidateJoborderStatusHistoryMockMvc.perform(put("/api/candidate-joborder-status-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidateJoborderStatusHistory)))
                .andExpect(status().isOk());

        // Validate the CandidateJoborderStatusHistory in the database
        List<CandidateJoborderStatusHistory> candidateJoborderStatusHistories = candidateJoborderStatusHistoryRepository.findAll();
        assertThat(candidateJoborderStatusHistories).hasSize(databaseSizeBeforeUpdate);
        CandidateJoborderStatusHistory testCandidateJoborderStatusHistory = candidateJoborderStatusHistories.get(candidateJoborderStatusHistories.size() - 1);
        assertThat(testCandidateJoborderStatusHistory.getCandidateJoborderStatusHistoryId()).isEqualTo(UPDATED_CANDIDATE_JOBORDER_STATUS_HISTORY_ID);
        assertThat(testCandidateJoborderStatusHistory.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateJoborderStatusHistory.getJoborderId()).isEqualTo(UPDATED_JOBORDER_ID);
        assertThat(testCandidateJoborderStatusHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCandidateJoborderStatusHistory.getStatusFrom()).isEqualTo(UPDATED_STATUS_FROM);
        assertThat(testCandidateJoborderStatusHistory.getStatusTo()).isEqualTo(UPDATED_STATUS_TO);
        assertThat(testCandidateJoborderStatusHistory.getSiteId()).isEqualTo(UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void deleteCandidateJoborderStatusHistory() throws Exception {
        // Initialize the database
        candidateJoborderStatusHistoryRepository.saveAndFlush(candidateJoborderStatusHistory);
        int databaseSizeBeforeDelete = candidateJoborderStatusHistoryRepository.findAll().size();

        // Get the candidateJoborderStatusHistory
        restCandidateJoborderStatusHistoryMockMvc.perform(delete("/api/candidate-joborder-status-histories/{id}", candidateJoborderStatusHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateJoborderStatusHistory> candidateJoborderStatusHistories = candidateJoborderStatusHistoryRepository.findAll();
        assertThat(candidateJoborderStatusHistories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
