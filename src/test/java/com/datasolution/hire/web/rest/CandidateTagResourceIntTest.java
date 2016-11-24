package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CandidateTag;
import com.datasolution.hire.repository.CandidateTagRepository;

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
 * Test class for the CandidateTagResource REST controller.
 *
 * @see CandidateTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateTagResourceIntTest {

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_CANDIDATE_ID = 1;
    private static final Integer UPDATED_CANDIDATE_ID = 2;

    private static final Integer DEFAULT_TAG_ID = 1;
    private static final Integer UPDATED_TAG_ID = 2;

    @Inject
    private CandidateTagRepository candidateTagRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateTagMockMvc;

    private CandidateTag candidateTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateTagResource candidateTagResource = new CandidateTagResource();
        ReflectionTestUtils.setField(candidateTagResource, "candidateTagRepository", candidateTagRepository);
        this.restCandidateTagMockMvc = MockMvcBuilders.standaloneSetup(candidateTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateTag createEntity(EntityManager em) {
        CandidateTag candidateTag = new CandidateTag()
                .siteId(DEFAULT_SITE_ID)
                .candidateId(DEFAULT_CANDIDATE_ID)
                .tagId(DEFAULT_TAG_ID);
        return candidateTag;
    }

    @Before
    public void initTest() {
        candidateTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateTag() throws Exception {
        int databaseSizeBeforeCreate = candidateTagRepository.findAll().size();

        // Create the CandidateTag

        restCandidateTagMockMvc.perform(post("/api/candidate-tags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidateTag)))
                .andExpect(status().isCreated());

        // Validate the CandidateTag in the database
        List<CandidateTag> candidateTags = candidateTagRepository.findAll();
        assertThat(candidateTags).hasSize(databaseSizeBeforeCreate + 1);
        CandidateTag testCandidateTag = candidateTags.get(candidateTags.size() - 1);
        assertThat(testCandidateTag.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCandidateTag.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCandidateTag.getTagId()).isEqualTo(DEFAULT_TAG_ID);
    }

    @Test
    @Transactional
    public void getAllCandidateTags() throws Exception {
        // Initialize the database
        candidateTagRepository.saveAndFlush(candidateTag);

        // Get all the candidateTags
        restCandidateTagMockMvc.perform(get("/api/candidate-tags?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidateTag.getId().intValue())))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID)))
                .andExpect(jsonPath("$.[*].tagId").value(hasItem(DEFAULT_TAG_ID)));
    }

    @Test
    @Transactional
    public void getCandidateTag() throws Exception {
        // Initialize the database
        candidateTagRepository.saveAndFlush(candidateTag);

        // Get the candidateTag
        restCandidateTagMockMvc.perform(get("/api/candidate-tags/{id}", candidateTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateTag.getId().intValue()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID))
            .andExpect(jsonPath("$.tagId").value(DEFAULT_TAG_ID));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateTag() throws Exception {
        // Get the candidateTag
        restCandidateTagMockMvc.perform(get("/api/candidate-tags/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateTag() throws Exception {
        // Initialize the database
        candidateTagRepository.saveAndFlush(candidateTag);
        int databaseSizeBeforeUpdate = candidateTagRepository.findAll().size();

        // Update the candidateTag
        CandidateTag updatedCandidateTag = candidateTagRepository.findOne(candidateTag.getId());
        updatedCandidateTag
                .siteId(UPDATED_SITE_ID)
                .candidateId(UPDATED_CANDIDATE_ID)
                .tagId(UPDATED_TAG_ID);

        restCandidateTagMockMvc.perform(put("/api/candidate-tags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidateTag)))
                .andExpect(status().isOk());

        // Validate the CandidateTag in the database
        List<CandidateTag> candidateTags = candidateTagRepository.findAll();
        assertThat(candidateTags).hasSize(databaseSizeBeforeUpdate);
        CandidateTag testCandidateTag = candidateTags.get(candidateTags.size() - 1);
        assertThat(testCandidateTag.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCandidateTag.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateTag.getTagId()).isEqualTo(UPDATED_TAG_ID);
    }

    @Test
    @Transactional
    public void deleteCandidateTag() throws Exception {
        // Initialize the database
        candidateTagRepository.saveAndFlush(candidateTag);
        int databaseSizeBeforeDelete = candidateTagRepository.findAll().size();

        // Get the candidateTag
        restCandidateTagMockMvc.perform(delete("/api/candidate-tags/{id}", candidateTag.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateTag> candidateTags = candidateTagRepository.findAll();
        assertThat(candidateTags).hasSize(databaseSizeBeforeDelete - 1);
    }
}
