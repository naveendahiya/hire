package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.AccessLevel;
import com.datasolution.hire.repository.AccessLevelRepository;

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
 * Test class for the AccessLevelResource REST controller.
 *
 * @see AccessLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class AccessLevelResourceIntTest {

    private static final Integer DEFAULT_ACCESS_LEVEL_ID = 1;
    private static final Integer UPDATED_ACCESS_LEVEL_ID = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private AccessLevelRepository accessLevelRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAccessLevelMockMvc;

    private AccessLevel accessLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccessLevelResource accessLevelResource = new AccessLevelResource();
        ReflectionTestUtils.setField(accessLevelResource, "accessLevelRepository", accessLevelRepository);
        this.restAccessLevelMockMvc = MockMvcBuilders.standaloneSetup(accessLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccessLevel createEntity(EntityManager em) {
        AccessLevel accessLevel = new AccessLevel()
                .accessLevelId(DEFAULT_ACCESS_LEVEL_ID)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .longDescription(DEFAULT_LONG_DESCRIPTION);
        return accessLevel;
    }

    @Before
    public void initTest() {
        accessLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccessLevel() throws Exception {
        int databaseSizeBeforeCreate = accessLevelRepository.findAll().size();

        // Create the AccessLevel

        restAccessLevelMockMvc.perform(post("/api/access-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accessLevel)))
                .andExpect(status().isCreated());

        // Validate the AccessLevel in the database
        List<AccessLevel> accessLevels = accessLevelRepository.findAll();
        assertThat(accessLevels).hasSize(databaseSizeBeforeCreate + 1);
        AccessLevel testAccessLevel = accessLevels.get(accessLevels.size() - 1);
        assertThat(testAccessLevel.getAccessLevelId()).isEqualTo(DEFAULT_ACCESS_LEVEL_ID);
        assertThat(testAccessLevel.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testAccessLevel.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAccessLevels() throws Exception {
        // Initialize the database
        accessLevelRepository.saveAndFlush(accessLevel);

        // Get all the accessLevels
        restAccessLevelMockMvc.perform(get("/api/access-levels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accessLevel.getId().intValue())))
                .andExpect(jsonPath("$.[*].accessLevelId").value(hasItem(DEFAULT_ACCESS_LEVEL_ID)))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAccessLevel() throws Exception {
        // Initialize the database
        accessLevelRepository.saveAndFlush(accessLevel);

        // Get the accessLevel
        restAccessLevelMockMvc.perform(get("/api/access-levels/{id}", accessLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accessLevel.getId().intValue()))
            .andExpect(jsonPath("$.accessLevelId").value(DEFAULT_ACCESS_LEVEL_ID))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccessLevel() throws Exception {
        // Get the accessLevel
        restAccessLevelMockMvc.perform(get("/api/access-levels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccessLevel() throws Exception {
        // Initialize the database
        accessLevelRepository.saveAndFlush(accessLevel);
        int databaseSizeBeforeUpdate = accessLevelRepository.findAll().size();

        // Update the accessLevel
        AccessLevel updatedAccessLevel = accessLevelRepository.findOne(accessLevel.getId());
        updatedAccessLevel
                .accessLevelId(UPDATED_ACCESS_LEVEL_ID)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .longDescription(UPDATED_LONG_DESCRIPTION);

        restAccessLevelMockMvc.perform(put("/api/access-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAccessLevel)))
                .andExpect(status().isOk());

        // Validate the AccessLevel in the database
        List<AccessLevel> accessLevels = accessLevelRepository.findAll();
        assertThat(accessLevels).hasSize(databaseSizeBeforeUpdate);
        AccessLevel testAccessLevel = accessLevels.get(accessLevels.size() - 1);
        assertThat(testAccessLevel.getAccessLevelId()).isEqualTo(UPDATED_ACCESS_LEVEL_ID);
        assertThat(testAccessLevel.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testAccessLevel.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteAccessLevel() throws Exception {
        // Initialize the database
        accessLevelRepository.saveAndFlush(accessLevel);
        int databaseSizeBeforeDelete = accessLevelRepository.findAll().size();

        // Get the accessLevel
        restAccessLevelMockMvc.perform(delete("/api/access-levels/{id}", accessLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AccessLevel> accessLevels = accessLevelRepository.findAll();
        assertThat(accessLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
