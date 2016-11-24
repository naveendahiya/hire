package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CandidateJobordrerStatusType;
import com.datasolution.hire.repository.CandidateJobordrerStatusTypeRepository;

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
 * Test class for the CandidateJobordrerStatusTypeResource REST controller.
 *
 * @see CandidateJobordrerStatusTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateJobordrerStatusTypeResourceIntTest {

    private static final Integer DEFAULT_CANDIDATE_STATUS_TYPE_ID = 1;
    private static final Integer UPDATED_CANDIDATE_STATUS_TYPE_ID = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAN_BE_SCHEDULED = 1;
    private static final Integer UPDATED_CAN_BE_SCHEDULED = 2;

    @Inject
    private CandidateJobordrerStatusTypeRepository candidateJobordrerStatusTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateJobordrerStatusTypeMockMvc;

    private CandidateJobordrerStatusType candidateJobordrerStatusType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateJobordrerStatusTypeResource candidateJobordrerStatusTypeResource = new CandidateJobordrerStatusTypeResource();
        ReflectionTestUtils.setField(candidateJobordrerStatusTypeResource, "candidateJobordrerStatusTypeRepository", candidateJobordrerStatusTypeRepository);
        this.restCandidateJobordrerStatusTypeMockMvc = MockMvcBuilders.standaloneSetup(candidateJobordrerStatusTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateJobordrerStatusType createEntity(EntityManager em) {
        CandidateJobordrerStatusType candidateJobordrerStatusType = new CandidateJobordrerStatusType()
                .candidateStatusTypeId(DEFAULT_CANDIDATE_STATUS_TYPE_ID)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .canBeScheduled(DEFAULT_CAN_BE_SCHEDULED);
        return candidateJobordrerStatusType;
    }

    @Before
    public void initTest() {
        candidateJobordrerStatusType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateJobordrerStatusType() throws Exception {
        int databaseSizeBeforeCreate = candidateJobordrerStatusTypeRepository.findAll().size();

        // Create the CandidateJobordrerStatusType

        restCandidateJobordrerStatusTypeMockMvc.perform(post("/api/candidate-jobordrer-status-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateJobordrerStatusType)))
                .andExpect(status().isCreated());

        // Validate the CandidateJobordrerStatusType in the database
        List<CandidateJobordrerStatusType> candidateJobordrerStatusTypes = candidateJobordrerStatusTypeRepository.findAll();
        assertThat(candidateJobordrerStatusTypes).hasSize(databaseSizeBeforeCreate + 1);
        CandidateJobordrerStatusType testCandidateJobordrerStatusType = candidateJobordrerStatusTypes.get(candidateJobordrerStatusTypes.size() - 1);
        assertThat(testCandidateJobordrerStatusType.getCandidateStatusTypeId()).isEqualTo(DEFAULT_CANDIDATE_STATUS_TYPE_ID);
        assertThat(testCandidateJobordrerStatusType.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testCandidateJobordrerStatusType.getCanBeScheduled()).isEqualTo(DEFAULT_CAN_BE_SCHEDULED);
    }

    @Test
    @Transactional
    public void getAllCandidateJobordrerStatusTypes() throws Exception {
        // Initialize the database
        candidateJobordrerStatusTypeRepository.saveAndFlush(candidateJobordrerStatusType);

        // Get all the candidateJobordrerStatusTypes
        restCandidateJobordrerStatusTypeMockMvc.perform(get("/api/candidate-jobordrer-status-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidateJobordrerStatusType.getId().intValue())))
                .andExpect(jsonPath("$.[*].candidateStatusTypeId").value(hasItem(DEFAULT_CANDIDATE_STATUS_TYPE_ID)))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].canBeScheduled").value(hasItem(DEFAULT_CAN_BE_SCHEDULED)));
    }

    @Test
    @Transactional
    public void getCandidateJobordrerStatusType() throws Exception {
        // Initialize the database
        candidateJobordrerStatusTypeRepository.saveAndFlush(candidateJobordrerStatusType);

        // Get the candidateJobordrerStatusType
        restCandidateJobordrerStatusTypeMockMvc.perform(get("/api/candidate-jobordrer-status-types/{id}", candidateJobordrerStatusType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateJobordrerStatusType.getId().intValue()))
            .andExpect(jsonPath("$.candidateStatusTypeId").value(DEFAULT_CANDIDATE_STATUS_TYPE_ID))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.canBeScheduled").value(DEFAULT_CAN_BE_SCHEDULED));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateJobordrerStatusType() throws Exception {
        // Get the candidateJobordrerStatusType
        restCandidateJobordrerStatusTypeMockMvc.perform(get("/api/candidate-jobordrer-status-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateJobordrerStatusType() throws Exception {
        // Initialize the database
        candidateJobordrerStatusTypeRepository.saveAndFlush(candidateJobordrerStatusType);
        int databaseSizeBeforeUpdate = candidateJobordrerStatusTypeRepository.findAll().size();

        // Update the candidateJobordrerStatusType
        CandidateJobordrerStatusType updatedCandidateJobordrerStatusType = candidateJobordrerStatusTypeRepository.findOne(candidateJobordrerStatusType.getId());
        updatedCandidateJobordrerStatusType
                .candidateStatusTypeId(UPDATED_CANDIDATE_STATUS_TYPE_ID)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .canBeScheduled(UPDATED_CAN_BE_SCHEDULED);

        restCandidateJobordrerStatusTypeMockMvc.perform(put("/api/candidate-jobordrer-status-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidateJobordrerStatusType)))
                .andExpect(status().isOk());

        // Validate the CandidateJobordrerStatusType in the database
        List<CandidateJobordrerStatusType> candidateJobordrerStatusTypes = candidateJobordrerStatusTypeRepository.findAll();
        assertThat(candidateJobordrerStatusTypes).hasSize(databaseSizeBeforeUpdate);
        CandidateJobordrerStatusType testCandidateJobordrerStatusType = candidateJobordrerStatusTypes.get(candidateJobordrerStatusTypes.size() - 1);
        assertThat(testCandidateJobordrerStatusType.getCandidateStatusTypeId()).isEqualTo(UPDATED_CANDIDATE_STATUS_TYPE_ID);
        assertThat(testCandidateJobordrerStatusType.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testCandidateJobordrerStatusType.getCanBeScheduled()).isEqualTo(UPDATED_CAN_BE_SCHEDULED);
    }

    @Test
    @Transactional
    public void deleteCandidateJobordrerStatusType() throws Exception {
        // Initialize the database
        candidateJobordrerStatusTypeRepository.saveAndFlush(candidateJobordrerStatusType);
        int databaseSizeBeforeDelete = candidateJobordrerStatusTypeRepository.findAll().size();

        // Get the candidateJobordrerStatusType
        restCandidateJobordrerStatusTypeMockMvc.perform(delete("/api/candidate-jobordrer-status-types/{id}", candidateJobordrerStatusType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateJobordrerStatusType> candidateJobordrerStatusTypes = candidateJobordrerStatusTypeRepository.findAll();
        assertThat(candidateJobordrerStatusTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
