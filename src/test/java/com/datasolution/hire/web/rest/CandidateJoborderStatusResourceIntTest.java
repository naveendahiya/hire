package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CandidateJoborderStatus;
import com.datasolution.hire.repository.CandidateJoborderStatusRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidateJoborderStatusResource REST controller.
 *
 * @see CandidateJoborderStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateJoborderStatusResourceIntTest {

    private static final Integer DEFAULT_CANDIDATE_JOBORDER_STATUS_ID = 1;
    private static final Integer UPDATED_CANDIDATE_JOBORDER_STATUS_ID = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAN_BE_SCHEDULED = 1;
    private static final Integer UPDATED_CAN_BE_SCHEDULED = 2;

    private static final Integer DEFAULT_TRIGGERS_EMAIL = 1;
    private static final Integer UPDATED_TRIGGERS_EMAIL = 2;

    private static final Integer DEFAULT_IS_ENABLED = 1;
    private static final Integer UPDATED_IS_ENABLED = 2;

    @Inject
    private CandidateJoborderStatusRepository candidateJoborderStatusRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateJoborderStatusMockMvc;

    private CandidateJoborderStatus candidateJoborderStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateJoborderStatusResource candidateJoborderStatusResource = new CandidateJoborderStatusResource();
        ReflectionTestUtils.setField(candidateJoborderStatusResource, "candidateJoborderStatusRepository", candidateJoborderStatusRepository);
        this.restCandidateJoborderStatusMockMvc = MockMvcBuilders.standaloneSetup(candidateJoborderStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateJoborderStatus createEntity(EntityManager em) {
        CandidateJoborderStatus candidateJoborderStatus = new CandidateJoborderStatus()
                .candidateJoborderStatusId(DEFAULT_CANDIDATE_JOBORDER_STATUS_ID)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .canBeScheduled(DEFAULT_CAN_BE_SCHEDULED)
                .triggersEmail(DEFAULT_TRIGGERS_EMAIL)
                .isEnabled(DEFAULT_IS_ENABLED);
        return candidateJoborderStatus;
    }

    @Before
    public void initTest() {
        candidateJoborderStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateJoborderStatus() throws Exception {
        int databaseSizeBeforeCreate = candidateJoborderStatusRepository.findAll().size();

        // Create the CandidateJoborderStatus

        restCandidateJoborderStatusMockMvc.perform(post("/api/candidate-joborder-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateJoborderStatus)))
                .andExpect(status().isCreated());

        // Validate the CandidateJoborderStatus in the database
        List<CandidateJoborderStatus> candidateJoborderStatuses = candidateJoborderStatusRepository.findAll();
        assertThat(candidateJoborderStatuses).hasSize(databaseSizeBeforeCreate + 1);
        CandidateJoborderStatus testCandidateJoborderStatus = candidateJoborderStatuses.get(candidateJoborderStatuses.size() - 1);
        assertThat(testCandidateJoborderStatus.getCandidateJoborderStatusId()).isEqualTo(DEFAULT_CANDIDATE_JOBORDER_STATUS_ID);
        assertThat(testCandidateJoborderStatus.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testCandidateJoborderStatus.getCanBeScheduled()).isEqualTo(DEFAULT_CAN_BE_SCHEDULED);
        assertThat(testCandidateJoborderStatus.getTriggersEmail()).isEqualTo(DEFAULT_TRIGGERS_EMAIL);
        assertThat(testCandidateJoborderStatus.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCandidateJoborderStatuses() throws Exception {
        // Initialize the database
        candidateJoborderStatusRepository.saveAndFlush(candidateJoborderStatus);

        // Get all the candidateJoborderStatuses
        restCandidateJoborderStatusMockMvc.perform(get("/api/candidate-joborder-statuses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidateJoborderStatus.getId().intValue())))
                .andExpect(jsonPath("$.[*].candidateJoborderStatusId").value(hasItem(DEFAULT_CANDIDATE_JOBORDER_STATUS_ID)))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].canBeScheduled").value(hasItem(DEFAULT_CAN_BE_SCHEDULED)))
                .andExpect(jsonPath("$.[*].triggersEmail").value(hasItem(DEFAULT_TRIGGERS_EMAIL)))
                .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED)));
    }

    @Test
    @Transactional
    public void getCandidateJoborderStatus() throws Exception {
        // Initialize the database
        candidateJoborderStatusRepository.saveAndFlush(candidateJoborderStatus);

        // Get the candidateJoborderStatus
        restCandidateJoborderStatusMockMvc.perform(get("/api/candidate-joborder-statuses/{id}", candidateJoborderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateJoborderStatus.getId().intValue()))
            .andExpect(jsonPath("$.candidateJoborderStatusId").value(DEFAULT_CANDIDATE_JOBORDER_STATUS_ID))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.canBeScheduled").value(DEFAULT_CAN_BE_SCHEDULED))
            .andExpect(jsonPath("$.triggersEmail").value(DEFAULT_TRIGGERS_EMAIL))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateJoborderStatus() throws Exception {
        // Get the candidateJoborderStatus
        restCandidateJoborderStatusMockMvc.perform(get("/api/candidate-joborder-statuses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateJoborderStatus() throws Exception {
        // Initialize the database
        candidateJoborderStatusRepository.saveAndFlush(candidateJoborderStatus);
        int databaseSizeBeforeUpdate = candidateJoborderStatusRepository.findAll().size();

        // Update the candidateJoborderStatus
        CandidateJoborderStatus updatedCandidateJoborderStatus = candidateJoborderStatusRepository.findOne(candidateJoborderStatus.getId());
        updatedCandidateJoborderStatus
                .candidateJoborderStatusId(UPDATED_CANDIDATE_JOBORDER_STATUS_ID)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .canBeScheduled(UPDATED_CAN_BE_SCHEDULED)
                .triggersEmail(UPDATED_TRIGGERS_EMAIL)
                .isEnabled(UPDATED_IS_ENABLED);

        restCandidateJoborderStatusMockMvc.perform(put("/api/candidate-joborder-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidateJoborderStatus)))
                .andExpect(status().isOk());

        // Validate the CandidateJoborderStatus in the database
        List<CandidateJoborderStatus> candidateJoborderStatuses = candidateJoborderStatusRepository.findAll();
        assertThat(candidateJoborderStatuses).hasSize(databaseSizeBeforeUpdate);
        CandidateJoborderStatus testCandidateJoborderStatus = candidateJoborderStatuses.get(candidateJoborderStatuses.size() - 1);
        assertThat(testCandidateJoborderStatus.getCandidateJoborderStatusId()).isEqualTo(UPDATED_CANDIDATE_JOBORDER_STATUS_ID);
        assertThat(testCandidateJoborderStatus.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testCandidateJoborderStatus.getCanBeScheduled()).isEqualTo(UPDATED_CAN_BE_SCHEDULED);
        assertThat(testCandidateJoborderStatus.getTriggersEmail()).isEqualTo(UPDATED_TRIGGERS_EMAIL);
        assertThat(testCandidateJoborderStatus.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void deleteCandidateJoborderStatus() throws Exception {
        // Initialize the database
        candidateJoborderStatusRepository.saveAndFlush(candidateJoborderStatus);
        int databaseSizeBeforeDelete = candidateJoborderStatusRepository.findAll().size();

        // Get the candidateJoborderStatus
        restCandidateJoborderStatusMockMvc.perform(delete("/api/candidate-joborder-statuses/{id}", candidateJoborderStatus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateJoborderStatus> candidateJoborderStatuses = candidateJoborderStatusRepository.findAll();
        assertThat(candidateJoborderStatuses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
