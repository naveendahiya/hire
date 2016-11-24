package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.EeoEthnicType;
import com.datasolution.hire.repository.EeoEthnicTypeRepository;

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
 * Test class for the EeoEthnicTypeResource REST controller.
 *
 * @see EeoEthnicTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class EeoEthnicTypeResourceIntTest {

    private static final Integer DEFAULT_EEO_ETHNIC_TYPE_ID = 1;
    private static final Integer UPDATED_EEO_ETHNIC_TYPE_ID = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Inject
    private EeoEthnicTypeRepository eeoEthnicTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEeoEthnicTypeMockMvc;

    private EeoEthnicType eeoEthnicType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EeoEthnicTypeResource eeoEthnicTypeResource = new EeoEthnicTypeResource();
        ReflectionTestUtils.setField(eeoEthnicTypeResource, "eeoEthnicTypeRepository", eeoEthnicTypeRepository);
        this.restEeoEthnicTypeMockMvc = MockMvcBuilders.standaloneSetup(eeoEthnicTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EeoEthnicType createEntity(EntityManager em) {
        EeoEthnicType eeoEthnicType = new EeoEthnicType()
                .eeoEthnicTypeId(DEFAULT_EEO_ETHNIC_TYPE_ID)
                .type(DEFAULT_TYPE);
        return eeoEthnicType;
    }

    @Before
    public void initTest() {
        eeoEthnicType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEeoEthnicType() throws Exception {
        int databaseSizeBeforeCreate = eeoEthnicTypeRepository.findAll().size();

        // Create the EeoEthnicType

        restEeoEthnicTypeMockMvc.perform(post("/api/eeo-ethnic-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eeoEthnicType)))
                .andExpect(status().isCreated());

        // Validate the EeoEthnicType in the database
        List<EeoEthnicType> eeoEthnicTypes = eeoEthnicTypeRepository.findAll();
        assertThat(eeoEthnicTypes).hasSize(databaseSizeBeforeCreate + 1);
        EeoEthnicType testEeoEthnicType = eeoEthnicTypes.get(eeoEthnicTypes.size() - 1);
        assertThat(testEeoEthnicType.getEeoEthnicTypeId()).isEqualTo(DEFAULT_EEO_ETHNIC_TYPE_ID);
        assertThat(testEeoEthnicType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEeoEthnicTypes() throws Exception {
        // Initialize the database
        eeoEthnicTypeRepository.saveAndFlush(eeoEthnicType);

        // Get all the eeoEthnicTypes
        restEeoEthnicTypeMockMvc.perform(get("/api/eeo-ethnic-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eeoEthnicType.getId().intValue())))
                .andExpect(jsonPath("$.[*].eeoEthnicTypeId").value(hasItem(DEFAULT_EEO_ETHNIC_TYPE_ID)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEeoEthnicType() throws Exception {
        // Initialize the database
        eeoEthnicTypeRepository.saveAndFlush(eeoEthnicType);

        // Get the eeoEthnicType
        restEeoEthnicTypeMockMvc.perform(get("/api/eeo-ethnic-types/{id}", eeoEthnicType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eeoEthnicType.getId().intValue()))
            .andExpect(jsonPath("$.eeoEthnicTypeId").value(DEFAULT_EEO_ETHNIC_TYPE_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEeoEthnicType() throws Exception {
        // Get the eeoEthnicType
        restEeoEthnicTypeMockMvc.perform(get("/api/eeo-ethnic-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEeoEthnicType() throws Exception {
        // Initialize the database
        eeoEthnicTypeRepository.saveAndFlush(eeoEthnicType);
        int databaseSizeBeforeUpdate = eeoEthnicTypeRepository.findAll().size();

        // Update the eeoEthnicType
        EeoEthnicType updatedEeoEthnicType = eeoEthnicTypeRepository.findOne(eeoEthnicType.getId());
        updatedEeoEthnicType
                .eeoEthnicTypeId(UPDATED_EEO_ETHNIC_TYPE_ID)
                .type(UPDATED_TYPE);

        restEeoEthnicTypeMockMvc.perform(put("/api/eeo-ethnic-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEeoEthnicType)))
                .andExpect(status().isOk());

        // Validate the EeoEthnicType in the database
        List<EeoEthnicType> eeoEthnicTypes = eeoEthnicTypeRepository.findAll();
        assertThat(eeoEthnicTypes).hasSize(databaseSizeBeforeUpdate);
        EeoEthnicType testEeoEthnicType = eeoEthnicTypes.get(eeoEthnicTypes.size() - 1);
        assertThat(testEeoEthnicType.getEeoEthnicTypeId()).isEqualTo(UPDATED_EEO_ETHNIC_TYPE_ID);
        assertThat(testEeoEthnicType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteEeoEthnicType() throws Exception {
        // Initialize the database
        eeoEthnicTypeRepository.saveAndFlush(eeoEthnicType);
        int databaseSizeBeforeDelete = eeoEthnicTypeRepository.findAll().size();

        // Get the eeoEthnicType
        restEeoEthnicTypeMockMvc.perform(delete("/api/eeo-ethnic-types/{id}", eeoEthnicType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EeoEthnicType> eeoEthnicTypes = eeoEthnicTypeRepository.findAll();
        assertThat(eeoEthnicTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
