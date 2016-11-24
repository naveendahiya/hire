package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Imported;
import com.datasolution.hire.repository.ImportedRepository;

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
 * Test class for the ImportedResource REST controller.
 *
 * @see ImportedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ImportedResourceIntTest {

    private static final Integer DEFAULT_IMPORTED_ID = 1;
    private static final Integer UPDATED_IMPORTED_ID = 2;

    private static final String DEFAULT_MODULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODULE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_REVERTED = 1;
    private static final Integer UPDATED_REVERTED = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_IMPORTED_ERRORS = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTED_ERRORS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ADDED_LINES = 1;
    private static final Integer UPDATED_ADDED_LINES = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    @Inject
    private ImportedRepository importedRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restImportedMockMvc;

    private Imported imported;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImportedResource importedResource = new ImportedResource();
        ReflectionTestUtils.setField(importedResource, "importedRepository", importedRepository);
        this.restImportedMockMvc = MockMvcBuilders.standaloneSetup(importedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imported createEntity(EntityManager em) {
        Imported imported = new Imported()
                .importedId(DEFAULT_IMPORTED_ID)
                .moduleName(DEFAULT_MODULE_NAME)
                .reverted(DEFAULT_REVERTED)
                .siteId(DEFAULT_SITE_ID)
                .importedErrors(DEFAULT_IMPORTED_ERRORS)
                .addedLines(DEFAULT_ADDED_LINES)
                .dateCreated(DEFAULT_DATE_CREATED);
        return imported;
    }

    @Before
    public void initTest() {
        imported = createEntity(em);
    }

    @Test
    @Transactional
    public void createImported() throws Exception {
        int databaseSizeBeforeCreate = importedRepository.findAll().size();

        // Create the Imported

        restImportedMockMvc.perform(post("/api/importeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imported)))
                .andExpect(status().isCreated());

        // Validate the Imported in the database
        List<Imported> importeds = importedRepository.findAll();
        assertThat(importeds).hasSize(databaseSizeBeforeCreate + 1);
        Imported testImported = importeds.get(importeds.size() - 1);
        assertThat(testImported.getImportedId()).isEqualTo(DEFAULT_IMPORTED_ID);
        assertThat(testImported.getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(testImported.getReverted()).isEqualTo(DEFAULT_REVERTED);
        assertThat(testImported.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testImported.getImportedErrors()).isEqualTo(DEFAULT_IMPORTED_ERRORS);
        assertThat(testImported.getAddedLines()).isEqualTo(DEFAULT_ADDED_LINES);
        assertThat(testImported.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllImporteds() throws Exception {
        // Initialize the database
        importedRepository.saveAndFlush(imported);

        // Get all the importeds
        restImportedMockMvc.perform(get("/api/importeds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(imported.getId().intValue())))
                .andExpect(jsonPath("$.[*].importedId").value(hasItem(DEFAULT_IMPORTED_ID)))
                .andExpect(jsonPath("$.[*].moduleName").value(hasItem(DEFAULT_MODULE_NAME.toString())))
                .andExpect(jsonPath("$.[*].reverted").value(hasItem(DEFAULT_REVERTED)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].importedErrors").value(hasItem(DEFAULT_IMPORTED_ERRORS.toString())))
                .andExpect(jsonPath("$.[*].addedLines").value(hasItem(DEFAULT_ADDED_LINES)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getImported() throws Exception {
        // Initialize the database
        importedRepository.saveAndFlush(imported);

        // Get the imported
        restImportedMockMvc.perform(get("/api/importeds/{id}", imported.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imported.getId().intValue()))
            .andExpect(jsonPath("$.importedId").value(DEFAULT_IMPORTED_ID))
            .andExpect(jsonPath("$.moduleName").value(DEFAULT_MODULE_NAME.toString()))
            .andExpect(jsonPath("$.reverted").value(DEFAULT_REVERTED))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.importedErrors").value(DEFAULT_IMPORTED_ERRORS.toString()))
            .andExpect(jsonPath("$.addedLines").value(DEFAULT_ADDED_LINES))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingImported() throws Exception {
        // Get the imported
        restImportedMockMvc.perform(get("/api/importeds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImported() throws Exception {
        // Initialize the database
        importedRepository.saveAndFlush(imported);
        int databaseSizeBeforeUpdate = importedRepository.findAll().size();

        // Update the imported
        Imported updatedImported = importedRepository.findOne(imported.getId());
        updatedImported
                .importedId(UPDATED_IMPORTED_ID)
                .moduleName(UPDATED_MODULE_NAME)
                .reverted(UPDATED_REVERTED)
                .siteId(UPDATED_SITE_ID)
                .importedErrors(UPDATED_IMPORTED_ERRORS)
                .addedLines(UPDATED_ADDED_LINES)
                .dateCreated(UPDATED_DATE_CREATED);

        restImportedMockMvc.perform(put("/api/importeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedImported)))
                .andExpect(status().isOk());

        // Validate the Imported in the database
        List<Imported> importeds = importedRepository.findAll();
        assertThat(importeds).hasSize(databaseSizeBeforeUpdate);
        Imported testImported = importeds.get(importeds.size() - 1);
        assertThat(testImported.getImportedId()).isEqualTo(UPDATED_IMPORTED_ID);
        assertThat(testImported.getModuleName()).isEqualTo(UPDATED_MODULE_NAME);
        assertThat(testImported.getReverted()).isEqualTo(UPDATED_REVERTED);
        assertThat(testImported.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testImported.getImportedErrors()).isEqualTo(UPDATED_IMPORTED_ERRORS);
        assertThat(testImported.getAddedLines()).isEqualTo(UPDATED_ADDED_LINES);
        assertThat(testImported.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void deleteImported() throws Exception {
        // Initialize the database
        importedRepository.saveAndFlush(imported);
        int databaseSizeBeforeDelete = importedRepository.findAll().size();

        // Get the imported
        restImportedMockMvc.perform(delete("/api/importeds/{id}", imported.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Imported> importeds = importedRepository.findAll();
        assertThat(importeds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
