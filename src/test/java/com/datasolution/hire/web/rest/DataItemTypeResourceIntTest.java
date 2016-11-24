package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.DataItemType;
import com.datasolution.hire.repository.DataItemTypeRepository;

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
 * Test class for the DataItemTypeResource REST controller.
 *
 * @see DataItemTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class DataItemTypeResourceIntTest {

    private static final Integer DEFAULT_DATA_ITEM_TYPE_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE_ID = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private DataItemTypeRepository dataItemTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDataItemTypeMockMvc;

    private DataItemType dataItemType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DataItemTypeResource dataItemTypeResource = new DataItemTypeResource();
        ReflectionTestUtils.setField(dataItemTypeResource, "dataItemTypeRepository", dataItemTypeRepository);
        this.restDataItemTypeMockMvc = MockMvcBuilders.standaloneSetup(dataItemTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataItemType createEntity(EntityManager em) {
        DataItemType dataItemType = new DataItemType()
                .dataItemTypeId(DEFAULT_DATA_ITEM_TYPE_ID)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION);
        return dataItemType;
    }

    @Before
    public void initTest() {
        dataItemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataItemType() throws Exception {
        int databaseSizeBeforeCreate = dataItemTypeRepository.findAll().size();

        // Create the DataItemType

        restDataItemTypeMockMvc.perform(post("/api/data-item-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dataItemType)))
                .andExpect(status().isCreated());

        // Validate the DataItemType in the database
        List<DataItemType> dataItemTypes = dataItemTypeRepository.findAll();
        assertThat(dataItemTypes).hasSize(databaseSizeBeforeCreate + 1);
        DataItemType testDataItemType = dataItemTypes.get(dataItemTypes.size() - 1);
        assertThat(testDataItemType.getDataItemTypeId()).isEqualTo(DEFAULT_DATA_ITEM_TYPE_ID);
        assertThat(testDataItemType.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDataItemTypes() throws Exception {
        // Initialize the database
        dataItemTypeRepository.saveAndFlush(dataItemType);

        // Get all the dataItemTypes
        restDataItemTypeMockMvc.perform(get("/api/data-item-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dataItemType.getId().intValue())))
                .andExpect(jsonPath("$.[*].dataItemTypeId").value(hasItem(DEFAULT_DATA_ITEM_TYPE_ID)))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDataItemType() throws Exception {
        // Initialize the database
        dataItemTypeRepository.saveAndFlush(dataItemType);

        // Get the dataItemType
        restDataItemTypeMockMvc.perform(get("/api/data-item-types/{id}", dataItemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataItemType.getId().intValue()))
            .andExpect(jsonPath("$.dataItemTypeId").value(DEFAULT_DATA_ITEM_TYPE_ID))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataItemType() throws Exception {
        // Get the dataItemType
        restDataItemTypeMockMvc.perform(get("/api/data-item-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataItemType() throws Exception {
        // Initialize the database
        dataItemTypeRepository.saveAndFlush(dataItemType);
        int databaseSizeBeforeUpdate = dataItemTypeRepository.findAll().size();

        // Update the dataItemType
        DataItemType updatedDataItemType = dataItemTypeRepository.findOne(dataItemType.getId());
        updatedDataItemType
                .dataItemTypeId(UPDATED_DATA_ITEM_TYPE_ID)
                .shortDescription(UPDATED_SHORT_DESCRIPTION);

        restDataItemTypeMockMvc.perform(put("/api/data-item-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDataItemType)))
                .andExpect(status().isOk());

        // Validate the DataItemType in the database
        List<DataItemType> dataItemTypes = dataItemTypeRepository.findAll();
        assertThat(dataItemTypes).hasSize(databaseSizeBeforeUpdate);
        DataItemType testDataItemType = dataItemTypes.get(dataItemTypes.size() - 1);
        assertThat(testDataItemType.getDataItemTypeId()).isEqualTo(UPDATED_DATA_ITEM_TYPE_ID);
        assertThat(testDataItemType.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteDataItemType() throws Exception {
        // Initialize the database
        dataItemTypeRepository.saveAndFlush(dataItemType);
        int databaseSizeBeforeDelete = dataItemTypeRepository.findAll().size();

        // Get the dataItemType
        restDataItemTypeMockMvc.perform(delete("/api/data-item-types/{id}", dataItemType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DataItemType> dataItemTypes = dataItemTypeRepository.findAll();
        assertThat(dataItemTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
