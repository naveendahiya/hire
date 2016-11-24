package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.ModuleSchema;
import com.datasolution.hire.repository.ModuleSchemaRepository;

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
 * Test class for the ModuleSchemaResource REST controller.
 *
 * @see ModuleSchemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ModuleSchemaResourceIntTest {

    private static final Integer DEFAULT_MODULE_SCHEMA_ID = 1;
    private static final Integer UPDATED_MODULE_SCHEMA_ID = 2;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private ModuleSchemaRepository moduleSchemaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restModuleSchemaMockMvc;

    private ModuleSchema moduleSchema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuleSchemaResource moduleSchemaResource = new ModuleSchemaResource();
        ReflectionTestUtils.setField(moduleSchemaResource, "moduleSchemaRepository", moduleSchemaRepository);
        this.restModuleSchemaMockMvc = MockMvcBuilders.standaloneSetup(moduleSchemaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleSchema createEntity(EntityManager em) {
        ModuleSchema moduleSchema = new ModuleSchema()
                .moduleSchemaId(DEFAULT_MODULE_SCHEMA_ID)
                .version(DEFAULT_VERSION)
                .name(DEFAULT_NAME);
        return moduleSchema;
    }

    @Before
    public void initTest() {
        moduleSchema = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleSchema() throws Exception {
        int databaseSizeBeforeCreate = moduleSchemaRepository.findAll().size();

        // Create the ModuleSchema

        restModuleSchemaMockMvc.perform(post("/api/module-schemas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(moduleSchema)))
                .andExpect(status().isCreated());

        // Validate the ModuleSchema in the database
        List<ModuleSchema> moduleSchemas = moduleSchemaRepository.findAll();
        assertThat(moduleSchemas).hasSize(databaseSizeBeforeCreate + 1);
        ModuleSchema testModuleSchema = moduleSchemas.get(moduleSchemas.size() - 1);
        assertThat(testModuleSchema.getModuleSchemaId()).isEqualTo(DEFAULT_MODULE_SCHEMA_ID);
        assertThat(testModuleSchema.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testModuleSchema.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllModuleSchemas() throws Exception {
        // Initialize the database
        moduleSchemaRepository.saveAndFlush(moduleSchema);

        // Get all the moduleSchemas
        restModuleSchemaMockMvc.perform(get("/api/module-schemas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(moduleSchema.getId().intValue())))
                .andExpect(jsonPath("$.[*].moduleSchemaId").value(hasItem(DEFAULT_MODULE_SCHEMA_ID)))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getModuleSchema() throws Exception {
        // Initialize the database
        moduleSchemaRepository.saveAndFlush(moduleSchema);

        // Get the moduleSchema
        restModuleSchemaMockMvc.perform(get("/api/module-schemas/{id}", moduleSchema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moduleSchema.getId().intValue()))
            .andExpect(jsonPath("$.moduleSchemaId").value(DEFAULT_MODULE_SCHEMA_ID))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModuleSchema() throws Exception {
        // Get the moduleSchema
        restModuleSchemaMockMvc.perform(get("/api/module-schemas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleSchema() throws Exception {
        // Initialize the database
        moduleSchemaRepository.saveAndFlush(moduleSchema);
        int databaseSizeBeforeUpdate = moduleSchemaRepository.findAll().size();

        // Update the moduleSchema
        ModuleSchema updatedModuleSchema = moduleSchemaRepository.findOne(moduleSchema.getId());
        updatedModuleSchema
                .moduleSchemaId(UPDATED_MODULE_SCHEMA_ID)
                .version(UPDATED_VERSION)
                .name(UPDATED_NAME);

        restModuleSchemaMockMvc.perform(put("/api/module-schemas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedModuleSchema)))
                .andExpect(status().isOk());

        // Validate the ModuleSchema in the database
        List<ModuleSchema> moduleSchemas = moduleSchemaRepository.findAll();
        assertThat(moduleSchemas).hasSize(databaseSizeBeforeUpdate);
        ModuleSchema testModuleSchema = moduleSchemas.get(moduleSchemas.size() - 1);
        assertThat(testModuleSchema.getModuleSchemaId()).isEqualTo(UPDATED_MODULE_SCHEMA_ID);
        assertThat(testModuleSchema.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testModuleSchema.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteModuleSchema() throws Exception {
        // Initialize the database
        moduleSchemaRepository.saveAndFlush(moduleSchema);
        int databaseSizeBeforeDelete = moduleSchemaRepository.findAll().size();

        // Get the moduleSchema
        restModuleSchemaMockMvc.perform(delete("/api/module-schemas/{id}", moduleSchema.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ModuleSchema> moduleSchemas = moduleSchemaRepository.findAll();
        assertThat(moduleSchemas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
