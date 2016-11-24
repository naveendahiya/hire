package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.ExtraField;
import com.datasolution.hire.repository.ExtraFieldRepository;

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
 * Test class for the ExtraFieldResource REST controller.
 *
 * @see ExtraFieldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ExtraFieldResourceIntTest {

    private static final Integer DEFAULT_EXTRA_FIELD_ID = 1;
    private static final Integer UPDATED_EXTRA_FIELD_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_ID = 2;

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMPORTEDED_ID = 1;
    private static final Integer UPDATED_IMPORTEDED_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    @Inject
    private ExtraFieldRepository extraFieldRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExtraFieldMockMvc;

    private ExtraField extraField;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtraFieldResource extraFieldResource = new ExtraFieldResource();
        ReflectionTestUtils.setField(extraFieldResource, "extraFieldRepository", extraFieldRepository);
        this.restExtraFieldMockMvc = MockMvcBuilders.standaloneSetup(extraFieldResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtraField createEntity(EntityManager em) {
        ExtraField extraField = new ExtraField()
                .extraFieldId(DEFAULT_EXTRA_FIELD_ID)
                .dataItemId(DEFAULT_DATA_ITEM_ID)
                .fieldName(DEFAULT_FIELD_NAME)
                .value(DEFAULT_VALUE)
                .importededId(DEFAULT_IMPORTEDED_ID)
                .siteId(DEFAULT_SITE_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE);
        return extraField;
    }

    @Before
    public void initTest() {
        extraField = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraField() throws Exception {
        int databaseSizeBeforeCreate = extraFieldRepository.findAll().size();

        // Create the ExtraField

        restExtraFieldMockMvc.perform(post("/api/extra-fields")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(extraField)))
                .andExpect(status().isCreated());

        // Validate the ExtraField in the database
        List<ExtraField> extraFields = extraFieldRepository.findAll();
        assertThat(extraFields).hasSize(databaseSizeBeforeCreate + 1);
        ExtraField testExtraField = extraFields.get(extraFields.size() - 1);
        assertThat(testExtraField.getExtraFieldId()).isEqualTo(DEFAULT_EXTRA_FIELD_ID);
        assertThat(testExtraField.getDataItemId()).isEqualTo(DEFAULT_DATA_ITEM_ID);
        assertThat(testExtraField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testExtraField.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testExtraField.getImportededId()).isEqualTo(DEFAULT_IMPORTEDED_ID);
        assertThat(testExtraField.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testExtraField.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
    }

    @Test
    @Transactional
    public void getAllExtraFields() throws Exception {
        // Initialize the database
        extraFieldRepository.saveAndFlush(extraField);

        // Get all the extraFields
        restExtraFieldMockMvc.perform(get("/api/extra-fields?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(extraField.getId().intValue())))
                .andExpect(jsonPath("$.[*].extraFieldId").value(hasItem(DEFAULT_EXTRA_FIELD_ID)))
                .andExpect(jsonPath("$.[*].dataItemId").value(hasItem(DEFAULT_DATA_ITEM_ID)))
                .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].importededId").value(hasItem(DEFAULT_IMPORTEDED_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)));
    }

    @Test
    @Transactional
    public void getExtraField() throws Exception {
        // Initialize the database
        extraFieldRepository.saveAndFlush(extraField);

        // Get the extraField
        restExtraFieldMockMvc.perform(get("/api/extra-fields/{id}", extraField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extraField.getId().intValue()))
            .andExpect(jsonPath("$.extraFieldId").value(DEFAULT_EXTRA_FIELD_ID))
            .andExpect(jsonPath("$.dataItemId").value(DEFAULT_DATA_ITEM_ID))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.importededId").value(DEFAULT_IMPORTEDED_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingExtraField() throws Exception {
        // Get the extraField
        restExtraFieldMockMvc.perform(get("/api/extra-fields/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraField() throws Exception {
        // Initialize the database
        extraFieldRepository.saveAndFlush(extraField);
        int databaseSizeBeforeUpdate = extraFieldRepository.findAll().size();

        // Update the extraField
        ExtraField updatedExtraField = extraFieldRepository.findOne(extraField.getId());
        updatedExtraField
                .extraFieldId(UPDATED_EXTRA_FIELD_ID)
                .dataItemId(UPDATED_DATA_ITEM_ID)
                .fieldName(UPDATED_FIELD_NAME)
                .value(UPDATED_VALUE)
                .importededId(UPDATED_IMPORTEDED_ID)
                .siteId(UPDATED_SITE_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE);

        restExtraFieldMockMvc.perform(put("/api/extra-fields")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExtraField)))
                .andExpect(status().isOk());

        // Validate the ExtraField in the database
        List<ExtraField> extraFields = extraFieldRepository.findAll();
        assertThat(extraFields).hasSize(databaseSizeBeforeUpdate);
        ExtraField testExtraField = extraFields.get(extraFields.size() - 1);
        assertThat(testExtraField.getExtraFieldId()).isEqualTo(UPDATED_EXTRA_FIELD_ID);
        assertThat(testExtraField.getDataItemId()).isEqualTo(UPDATED_DATA_ITEM_ID);
        assertThat(testExtraField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testExtraField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testExtraField.getImportededId()).isEqualTo(UPDATED_IMPORTEDED_ID);
        assertThat(testExtraField.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testExtraField.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
    }

    @Test
    @Transactional
    public void deleteExtraField() throws Exception {
        // Initialize the database
        extraFieldRepository.saveAndFlush(extraField);
        int databaseSizeBeforeDelete = extraFieldRepository.findAll().size();

        // Get the extraField
        restExtraFieldMockMvc.perform(delete("/api/extra-fields/{id}", extraField.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtraField> extraFields = extraFieldRepository.findAll();
        assertThat(extraFields).hasSize(databaseSizeBeforeDelete - 1);
    }
}
