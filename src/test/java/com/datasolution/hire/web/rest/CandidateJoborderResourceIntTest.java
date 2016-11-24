package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CandidateJoborder;
import com.datasolution.hire.repository.CandidateJoborderRepository;

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
 * Test class for the CandidateJoborderResource REST controller.
 *
 * @see CandidateJoborderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateJoborderResourceIntTest {

    private static final Integer DEFAULT_CANDIDATE_JOBORDER_ID = 1;
    private static final Integer UPDATED_CANDIDATE_JOBORDER_ID = 2;

    private static final Integer DEFAULT_CANDIDATE_ID = 1;
    private static final Integer UPDATED_CANDIDATE_ID = 2;

    private static final Integer DEFAULT_JOBORDER_ID = 1;
    private static final Integer UPDATED_JOBORDER_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final ZonedDateTime DEFAULT_DATE_SUBMITTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_SUBMITTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_SUBMITTED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_SUBMITTED);

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    private static final Integer DEFAULT_RATING_VALUE = 1;
    private static final Integer UPDATED_RATING_VALUE = 2;

    private static final Integer DEFAULT_ADDED_BY = 1;
    private static final Integer UPDATED_ADDED_BY = 2;

    @Inject
    private CandidateJoborderRepository candidateJoborderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateJoborderMockMvc;

    private CandidateJoborder candidateJoborder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateJoborderResource candidateJoborderResource = new CandidateJoborderResource();
        ReflectionTestUtils.setField(candidateJoborderResource, "candidateJoborderRepository", candidateJoborderRepository);
        this.restCandidateJoborderMockMvc = MockMvcBuilders.standaloneSetup(candidateJoborderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateJoborder createEntity(EntityManager em) {
        CandidateJoborder candidateJoborder = new CandidateJoborder()
                .candidateJoborderId(DEFAULT_CANDIDATE_JOBORDER_ID)
                .candidateId(DEFAULT_CANDIDATE_ID)
                .joborderId(DEFAULT_JOBORDER_ID)
                .siteId(DEFAULT_SITE_ID)
                .status(DEFAULT_STATUS)
                .dateSubmitted(DEFAULT_DATE_SUBMITTED)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .ratingValue(DEFAULT_RATING_VALUE)
                .addedBy(DEFAULT_ADDED_BY);
        return candidateJoborder;
    }

    @Before
    public void initTest() {
        candidateJoborder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateJoborder() throws Exception {
        int databaseSizeBeforeCreate = candidateJoborderRepository.findAll().size();

        // Create the CandidateJoborder

        restCandidateJoborderMockMvc.perform(post("/api/candidate-joborders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateJoborder)))
                .andExpect(status().isCreated());

        // Validate the CandidateJoborder in the database
        List<CandidateJoborder> candidateJoborders = candidateJoborderRepository.findAll();
        assertThat(candidateJoborders).hasSize(databaseSizeBeforeCreate + 1);
        CandidateJoborder testCandidateJoborder = candidateJoborders.get(candidateJoborders.size() - 1);
        assertThat(testCandidateJoborder.getCandidateJoborderId()).isEqualTo(DEFAULT_CANDIDATE_JOBORDER_ID);
        assertThat(testCandidateJoborder.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCandidateJoborder.getJoborderId()).isEqualTo(DEFAULT_JOBORDER_ID);
        assertThat(testCandidateJoborder.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCandidateJoborder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCandidateJoborder.getDateSubmitted()).isEqualTo(DEFAULT_DATE_SUBMITTED);
        assertThat(testCandidateJoborder.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCandidateJoborder.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCandidateJoborder.getRatingValue()).isEqualTo(DEFAULT_RATING_VALUE);
        assertThat(testCandidateJoborder.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
    }

    @Test
    @Transactional
    public void getAllCandidateJoborders() throws Exception {
        // Initialize the database
        candidateJoborderRepository.saveAndFlush(candidateJoborder);

        // Get all the candidateJoborders
        restCandidateJoborderMockMvc.perform(get("/api/candidate-joborders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidateJoborder.getId().intValue())))
                .andExpect(jsonPath("$.[*].candidateJoborderId").value(hasItem(DEFAULT_CANDIDATE_JOBORDER_ID)))
                .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID)))
                .andExpect(jsonPath("$.[*].joborderId").value(hasItem(DEFAULT_JOBORDER_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].dateSubmitted").value(hasItem(DEFAULT_DATE_SUBMITTED_STR)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].ratingValue").value(hasItem(DEFAULT_RATING_VALUE)))
                .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY)));
    }

    @Test
    @Transactional
    public void getCandidateJoborder() throws Exception {
        // Initialize the database
        candidateJoborderRepository.saveAndFlush(candidateJoborder);

        // Get the candidateJoborder
        restCandidateJoborderMockMvc.perform(get("/api/candidate-joborders/{id}", candidateJoborder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateJoborder.getId().intValue()))
            .andExpect(jsonPath("$.candidateJoborderId").value(DEFAULT_CANDIDATE_JOBORDER_ID))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID))
            .andExpect(jsonPath("$.joborderId").value(DEFAULT_JOBORDER_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dateSubmitted").value(DEFAULT_DATE_SUBMITTED_STR))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.ratingValue").value(DEFAULT_RATING_VALUE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateJoborder() throws Exception {
        // Get the candidateJoborder
        restCandidateJoborderMockMvc.perform(get("/api/candidate-joborders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateJoborder() throws Exception {
        // Initialize the database
        candidateJoborderRepository.saveAndFlush(candidateJoborder);
        int databaseSizeBeforeUpdate = candidateJoborderRepository.findAll().size();

        // Update the candidateJoborder
        CandidateJoborder updatedCandidateJoborder = candidateJoborderRepository.findOne(candidateJoborder.getId());
        updatedCandidateJoborder
                .candidateJoborderId(UPDATED_CANDIDATE_JOBORDER_ID)
                .candidateId(UPDATED_CANDIDATE_ID)
                .joborderId(UPDATED_JOBORDER_ID)
                .siteId(UPDATED_SITE_ID)
                .status(UPDATED_STATUS)
                .dateSubmitted(UPDATED_DATE_SUBMITTED)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .ratingValue(UPDATED_RATING_VALUE)
                .addedBy(UPDATED_ADDED_BY);

        restCandidateJoborderMockMvc.perform(put("/api/candidate-joborders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidateJoborder)))
                .andExpect(status().isOk());

        // Validate the CandidateJoborder in the database
        List<CandidateJoborder> candidateJoborders = candidateJoborderRepository.findAll();
        assertThat(candidateJoborders).hasSize(databaseSizeBeforeUpdate);
        CandidateJoborder testCandidateJoborder = candidateJoborders.get(candidateJoborders.size() - 1);
        assertThat(testCandidateJoborder.getCandidateJoborderId()).isEqualTo(UPDATED_CANDIDATE_JOBORDER_ID);
        assertThat(testCandidateJoborder.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateJoborder.getJoborderId()).isEqualTo(UPDATED_JOBORDER_ID);
        assertThat(testCandidateJoborder.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCandidateJoborder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCandidateJoborder.getDateSubmitted()).isEqualTo(UPDATED_DATE_SUBMITTED);
        assertThat(testCandidateJoborder.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCandidateJoborder.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCandidateJoborder.getRatingValue()).isEqualTo(UPDATED_RATING_VALUE);
        assertThat(testCandidateJoborder.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
    }

    @Test
    @Transactional
    public void deleteCandidateJoborder() throws Exception {
        // Initialize the database
        candidateJoborderRepository.saveAndFlush(candidateJoborder);
        int databaseSizeBeforeDelete = candidateJoborderRepository.findAll().size();

        // Get the candidateJoborder
        restCandidateJoborderMockMvc.perform(delete("/api/candidate-joborders/{id}", candidateJoborder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateJoborder> candidateJoborders = candidateJoborderRepository.findAll();
        assertThat(candidateJoborders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
