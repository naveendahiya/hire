package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CandidateSource;
import com.datasolution.hire.repository.CandidateSourceRepository;

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
 * Test class for the CandidateSourceResource REST controller.
 *
 * @see CandidateSourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateSourceResourceIntTest {

    private static final Integer DEFAULT_SOURCE_ID = 1;
    private static final Integer UPDATED_SOURCE_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    @Inject
    private CandidateSourceRepository candidateSourceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateSourceMockMvc;

    private CandidateSource candidateSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateSourceResource candidateSourceResource = new CandidateSourceResource();
        ReflectionTestUtils.setField(candidateSourceResource, "candidateSourceRepository", candidateSourceRepository);
        this.restCandidateSourceMockMvc = MockMvcBuilders.standaloneSetup(candidateSourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateSource createEntity(EntityManager em) {
        CandidateSource candidateSource = new CandidateSource()
                .sourceId(DEFAULT_SOURCE_ID)
                .name(DEFAULT_NAME)
                .siteId(DEFAULT_SITE_ID)
                .dateCreated(DEFAULT_DATE_CREATED);
        return candidateSource;
    }

    @Before
    public void initTest() {
        candidateSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateSource() throws Exception {
        int databaseSizeBeforeCreate = candidateSourceRepository.findAll().size();

        // Create the CandidateSource

        restCandidateSourceMockMvc.perform(post("/api/candidate-sources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateSource)))
                .andExpect(status().isCreated());

        // Validate the CandidateSource in the database
        List<CandidateSource> candidateSources = candidateSourceRepository.findAll();
        assertThat(candidateSources).hasSize(databaseSizeBeforeCreate + 1);
        CandidateSource testCandidateSource = candidateSources.get(candidateSources.size() - 1);
        assertThat(testCandidateSource.getSourceId()).isEqualTo(DEFAULT_SOURCE_ID);
        assertThat(testCandidateSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCandidateSource.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCandidateSource.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllCandidateSources() throws Exception {
        // Initialize the database
        candidateSourceRepository.saveAndFlush(candidateSource);

        // Get all the candidateSources
        restCandidateSourceMockMvc.perform(get("/api/candidate-sources?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidateSource.getId().intValue())))
                .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getCandidateSource() throws Exception {
        // Initialize the database
        candidateSourceRepository.saveAndFlush(candidateSource);

        // Get the candidateSource
        restCandidateSourceMockMvc.perform(get("/api/candidate-sources/{id}", candidateSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateSource.getId().intValue()))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateSource() throws Exception {
        // Get the candidateSource
        restCandidateSourceMockMvc.perform(get("/api/candidate-sources/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateSource() throws Exception {
        // Initialize the database
        candidateSourceRepository.saveAndFlush(candidateSource);
        int databaseSizeBeforeUpdate = candidateSourceRepository.findAll().size();

        // Update the candidateSource
        CandidateSource updatedCandidateSource = candidateSourceRepository.findOne(candidateSource.getId());
        updatedCandidateSource
                .sourceId(UPDATED_SOURCE_ID)
                .name(UPDATED_NAME)
                .siteId(UPDATED_SITE_ID)
                .dateCreated(UPDATED_DATE_CREATED);

        restCandidateSourceMockMvc.perform(put("/api/candidate-sources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidateSource)))
                .andExpect(status().isOk());

        // Validate the CandidateSource in the database
        List<CandidateSource> candidateSources = candidateSourceRepository.findAll();
        assertThat(candidateSources).hasSize(databaseSizeBeforeUpdate);
        CandidateSource testCandidateSource = candidateSources.get(candidateSources.size() - 1);
        assertThat(testCandidateSource.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testCandidateSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCandidateSource.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCandidateSource.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void deleteCandidateSource() throws Exception {
        // Initialize the database
        candidateSourceRepository.saveAndFlush(candidateSource);
        int databaseSizeBeforeDelete = candidateSourceRepository.findAll().size();

        // Get the candidateSource
        restCandidateSourceMockMvc.perform(delete("/api/candidate-sources/{id}", candidateSource.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateSource> candidateSources = candidateSourceRepository.findAll();
        assertThat(candidateSources).hasSize(databaseSizeBeforeDelete - 1);
    }
}
