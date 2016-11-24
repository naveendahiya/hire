package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.ExtraFieldSettings;
import com.datasolution.hire.repository.ExtraFieldSettingsRepository;

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
 * Test class for the ExtraFieldSettingsResource REST controller.
 *
 * @see ExtraFieldSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ExtraFieldSettingsResourceIntTest {

    private static final Integer DEFAULT_EXTRA_FIELD_SETTINGS_ID = 1;
    private static final Integer UPDATED_EXTRA_FIELD_SETTINGS_ID = 2;

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMPORTED_ID = 1;
    private static final Integer UPDATED_IMPORTED_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_EXTRA_FIELD_TYPE = 1;
    private static final Integer UPDATED_EXTRA_FIELD_TYPE = 2;

    private static final String DEFAULT_EXTRA_FIELD_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELD_OPTIONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    @Inject
    private ExtraFieldSettingsRepository extraFieldSettingsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExtraFieldSettingsMockMvc;

    private ExtraFieldSettings extraFieldSettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtraFieldSettingsResource extraFieldSettingsResource = new ExtraFieldSettingsResource();
        ReflectionTestUtils.setField(extraFieldSettingsResource, "extraFieldSettingsRepository", extraFieldSettingsRepository);
        this.restExtraFieldSettingsMockMvc = MockMvcBuilders.standaloneSetup(extraFieldSettingsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraFieldSettings createEntity(EntityManager em) {
        ExtraFieldSettings extraFieldSettings = new ExtraFieldSettings()
                .extraFieldSettingsId(DEFAULT_EXTRA_FIELD_SETTINGS_ID)
                .fieldName(DEFAULT_FIELD_NAME)
                .importedId(DEFAULT_IMPORTED_ID)
                .siteId(DEFAULT_SITE_ID)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .extraFieldType(DEFAULT_EXTRA_FIELD_TYPE)
                .extraFieldOptions(DEFAULT_EXTRA_FIELD_OPTIONS)
                .position(DEFAULT_POSITION);
        return extraFieldSettings;
    }

    @Before
    public void initTest() {
        extraFieldSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraFieldSettings() throws Exception {
        int databaseSizeBeforeCreate = extraFieldSettingsRepository.findAll().size();

        // Create the ExtraFieldSettings

        restExtraFieldSettingsMockMvc.perform(post("/api/extra-field-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(extraFieldSettings)))
                .andExpect(status().isCreated());

        // Validate the ExtraFieldSettings in the database
        List<ExtraFieldSettings> extraFieldSettings = extraFieldSettingsRepository.findAll();
        assertThat(extraFieldSettings).hasSize(databaseSizeBeforeCreate + 1);
        ExtraFieldSettings testExtraFieldSettings = extraFieldSettings.get(extraFieldSettings.size() - 1);
        assertThat(testExtraFieldSettings.getExtraFieldSettingsId()).isEqualTo(DEFAULT_EXTRA_FIELD_SETTINGS_ID);
        assertThat(testExtraFieldSettings.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testExtraFieldSettings.getImportedId()).isEqualTo(DEFAULT_IMPORTED_ID);
        assertThat(testExtraFieldSettings.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testExtraFieldSettings.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testExtraFieldSettings.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testExtraFieldSettings.getExtraFieldType()).isEqualTo(DEFAULT_EXTRA_FIELD_TYPE);
        assertThat(testExtraFieldSettings.getExtraFieldOptions()).isEqualTo(DEFAULT_EXTRA_FIELD_OPTIONS);
        assertThat(testExtraFieldSettings.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void getAllExtraFieldSettings() throws Exception {
        // Initialize the database
        extraFieldSettingsRepository.saveAndFlush(extraFieldSettings);

        // Get all the extraFieldSettings
        restExtraFieldSettingsMockMvc.perform(get("/api/extra-field-settings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(extraFieldSettings.getId().intValue())))
                .andExpect(jsonPath("$.[*].extraFieldSettingsId").value(hasItem(DEFAULT_EXTRA_FIELD_SETTINGS_ID)))
                .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
                .andExpect(jsonPath("$.[*].importedId").value(hasItem(DEFAULT_IMPORTED_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].extraFieldType").value(hasItem(DEFAULT_EXTRA_FIELD_TYPE)))
                .andExpect(jsonPath("$.[*].extraFieldOptions").value(hasItem(DEFAULT_EXTRA_FIELD_OPTIONS.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    public void getExtraFieldSettings() throws Exception {
        // Initialize the database
        extraFieldSettingsRepository.saveAndFlush(extraFieldSettings);

        // Get the extraFieldSettings
        restExtraFieldSettingsMockMvc.perform(get("/api/extra-field-settings/{id}", extraFieldSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extraFieldSettings.getId().intValue()))
            .andExpect(jsonPath("$.extraFieldSettingsId").value(DEFAULT_EXTRA_FIELD_SETTINGS_ID))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.importedId").value(DEFAULT_IMPORTED_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.extraFieldType").value(DEFAULT_EXTRA_FIELD_TYPE))
            .andExpect(jsonPath("$.extraFieldOptions").value(DEFAULT_EXTRA_FIELD_OPTIONS.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    public void getNonExistingExtraFieldSettings() throws Exception {
        // Get the extraFieldSettings
        restExtraFieldSettingsMockMvc.perform(get("/api/extra-field-settings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraFieldSettings() throws Exception {
        // Initialize the database
        extraFieldSettingsRepository.saveAndFlush(extraFieldSettings);
        int databaseSizeBeforeUpdate = extraFieldSettingsRepository.findAll().size();

        // Update the extraFieldSettings
        ExtraFieldSettings updatedExtraFieldSettings = extraFieldSettingsRepository.findOne(extraFieldSettings.getId());
        updatedExtraFieldSettings
                .extraFieldSettingsId(UPDATED_EXTRA_FIELD_SETTINGS_ID)
                .fieldName(UPDATED_FIELD_NAME)
                .importedId(UPDATED_IMPORTED_ID)
                .siteId(UPDATED_SITE_ID)
                .dateCreated(UPDATED_DATE_CREATED)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .extraFieldType(UPDATED_EXTRA_FIELD_TYPE)
                .extraFieldOptions(UPDATED_EXTRA_FIELD_OPTIONS)
                .position(UPDATED_POSITION);

        restExtraFieldSettingsMockMvc.perform(put("/api/extra-field-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExtraFieldSettings)))
                .andExpect(status().isOk());

        // Validate the ExtraFieldSettings in the database
        List<ExtraFieldSettings> extraFieldSettings = extraFieldSettingsRepository.findAll();
        assertThat(extraFieldSettings).hasSize(databaseSizeBeforeUpdate);
        ExtraFieldSettings testExtraFieldSettings = extraFieldSettings.get(extraFieldSettings.size() - 1);
        assertThat(testExtraFieldSettings.getExtraFieldSettingsId()).isEqualTo(UPDATED_EXTRA_FIELD_SETTINGS_ID);
        assertThat(testExtraFieldSettings.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testExtraFieldSettings.getImportedId()).isEqualTo(UPDATED_IMPORTED_ID);
        assertThat(testExtraFieldSettings.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testExtraFieldSettings.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testExtraFieldSettings.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testExtraFieldSettings.getExtraFieldType()).isEqualTo(UPDATED_EXTRA_FIELD_TYPE);
        assertThat(testExtraFieldSettings.getExtraFieldOptions()).isEqualTo(UPDATED_EXTRA_FIELD_OPTIONS);
        assertThat(testExtraFieldSettings.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void deleteExtraFieldSettings() throws Exception {
        // Initialize the database
        extraFieldSettingsRepository.saveAndFlush(extraFieldSettings);
        int databaseSizeBeforeDelete = extraFieldSettingsRepository.findAll().size();

        // Get the extraFieldSettings
        restExtraFieldSettingsMockMvc.perform(delete("/api/extra-field-settings/{id}", extraFieldSettings.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtraFieldSettings> extraFieldSettings = extraFieldSettingsRepository.findAll();
        assertThat(extraFieldSettings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
