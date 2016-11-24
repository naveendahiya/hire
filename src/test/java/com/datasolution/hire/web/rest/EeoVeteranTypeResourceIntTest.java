package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.EeoVeteranType;
import com.datasolution.hire.repository.EeoVeteranTypeRepository;

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
 * Test class for the EeoVeteranTypeResource REST controller.
 *
 * @see EeoVeteranTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class EeoVeteranTypeResourceIntTest {

    private static final Integer DEFAULT_EEO_VETERAN_TYPE_ID = 1;
    private static final Integer UPDATED_EEO_VETERAN_TYPE_ID = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Inject
    private EeoVeteranTypeRepository eeoVeteranTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEeoVeteranTypeMockMvc;

    private EeoVeteranType eeoVeteranType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EeoVeteranTypeResource eeoVeteranTypeResource = new EeoVeteranTypeResource();
        ReflectionTestUtils.setField(eeoVeteranTypeResource, "eeoVeteranTypeRepository", eeoVeteranTypeRepository);
        this.restEeoVeteranTypeMockMvc = MockMvcBuilders.standaloneSetup(eeoVeteranTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EeoVeteranType createEntity(EntityManager em) {
        EeoVeteranType eeoVeteranType = new EeoVeteranType()
                .eeoVeteranTypeId(DEFAULT_EEO_VETERAN_TYPE_ID)
                .type(DEFAULT_TYPE);
        return eeoVeteranType;
    }

    @Before
    public void initTest() {
        eeoVeteranType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEeoVeteranType() throws Exception {
        int databaseSizeBeforeCreate = eeoVeteranTypeRepository.findAll().size();

        // Create the EeoVeteranType

        restEeoVeteranTypeMockMvc.perform(post("/api/eeo-veteran-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eeoVeteranType)))
                .andExpect(status().isCreated());

        // Validate the EeoVeteranType in the database
        List<EeoVeteranType> eeoVeteranTypes = eeoVeteranTypeRepository.findAll();
        assertThat(eeoVeteranTypes).hasSize(databaseSizeBeforeCreate + 1);
        EeoVeteranType testEeoVeteranType = eeoVeteranTypes.get(eeoVeteranTypes.size() - 1);
        assertThat(testEeoVeteranType.getEeoVeteranTypeId()).isEqualTo(DEFAULT_EEO_VETERAN_TYPE_ID);
        assertThat(testEeoVeteranType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEeoVeteranTypes() throws Exception {
        // Initialize the database
        eeoVeteranTypeRepository.saveAndFlush(eeoVeteranType);

        // Get all the eeoVeteranTypes
        restEeoVeteranTypeMockMvc.perform(get("/api/eeo-veteran-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eeoVeteranType.getId().intValue())))
                .andExpect(jsonPath("$.[*].eeoVeteranTypeId").value(hasItem(DEFAULT_EEO_VETERAN_TYPE_ID)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEeoVeteranType() throws Exception {
        // Initialize the database
        eeoVeteranTypeRepository.saveAndFlush(eeoVeteranType);

        // Get the eeoVeteranType
        restEeoVeteranTypeMockMvc.perform(get("/api/eeo-veteran-types/{id}", eeoVeteranType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eeoVeteranType.getId().intValue()))
            .andExpect(jsonPath("$.eeoVeteranTypeId").value(DEFAULT_EEO_VETERAN_TYPE_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEeoVeteranType() throws Exception {
        // Get the eeoVeteranType
        restEeoVeteranTypeMockMvc.perform(get("/api/eeo-veteran-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEeoVeteranType() throws Exception {
        // Initialize the database
        eeoVeteranTypeRepository.saveAndFlush(eeoVeteranType);
        int databaseSizeBeforeUpdate = eeoVeteranTypeRepository.findAll().size();

        // Update the eeoVeteranType
        EeoVeteranType updatedEeoVeteranType = eeoVeteranTypeRepository.findOne(eeoVeteranType.getId());
        updatedEeoVeteranType
                .eeoVeteranTypeId(UPDATED_EEO_VETERAN_TYPE_ID)
                .type(UPDATED_TYPE);

        restEeoVeteranTypeMockMvc.perform(put("/api/eeo-veteran-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEeoVeteranType)))
                .andExpect(status().isOk());

        // Validate the EeoVeteranType in the database
        List<EeoVeteranType> eeoVeteranTypes = eeoVeteranTypeRepository.findAll();
        assertThat(eeoVeteranTypes).hasSize(databaseSizeBeforeUpdate);
        EeoVeteranType testEeoVeteranType = eeoVeteranTypes.get(eeoVeteranTypes.size() - 1);
        assertThat(testEeoVeteranType.getEeoVeteranTypeId()).isEqualTo(UPDATED_EEO_VETERAN_TYPE_ID);
        assertThat(testEeoVeteranType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteEeoVeteranType() throws Exception {
        // Initialize the database
        eeoVeteranTypeRepository.saveAndFlush(eeoVeteranType);
        int databaseSizeBeforeDelete = eeoVeteranTypeRepository.findAll().size();

        // Get the eeoVeteranType
        restEeoVeteranTypeMockMvc.perform(delete("/api/eeo-veteran-types/{id}", eeoVeteranType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EeoVeteranType> eeoVeteranTypes = eeoVeteranTypeRepository.findAll();
        assertThat(eeoVeteranTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
