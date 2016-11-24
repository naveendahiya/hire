package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.HttpLogTypes;
import com.datasolution.hire.repository.HttpLogTypesRepository;

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
 * Test class for the HttpLogTypesResource REST controller.
 *
 * @see HttpLogTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class HttpLogTypesResourceIntTest {

    private static final Integer DEFAULT_LOG_TYPE_ID = 1;
    private static final Integer UPDATED_LOG_TYPE_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_LOG_TYPE = false;
    private static final Boolean UPDATED_DEFAULT_LOG_TYPE = true;

    @Inject
    private HttpLogTypesRepository httpLogTypesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHttpLogTypesMockMvc;

    private HttpLogTypes httpLogTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HttpLogTypesResource httpLogTypesResource = new HttpLogTypesResource();
        ReflectionTestUtils.setField(httpLogTypesResource, "httpLogTypesRepository", httpLogTypesRepository);
        this.restHttpLogTypesMockMvc = MockMvcBuilders.standaloneSetup(httpLogTypesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HttpLogTypes createEntity(EntityManager em) {
        HttpLogTypes httpLogTypes = new HttpLogTypes()
                .logTypeId(DEFAULT_LOG_TYPE_ID)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .defaultLogType(DEFAULT_DEFAULT_LOG_TYPE);
        return httpLogTypes;
    }

    @Before
    public void initTest() {
        httpLogTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createHttpLogTypes() throws Exception {
        int databaseSizeBeforeCreate = httpLogTypesRepository.findAll().size();

        // Create the HttpLogTypes

        restHttpLogTypesMockMvc.perform(post("/api/http-log-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(httpLogTypes)))
                .andExpect(status().isCreated());

        // Validate the HttpLogTypes in the database
        List<HttpLogTypes> httpLogTypes = httpLogTypesRepository.findAll();
        assertThat(httpLogTypes).hasSize(databaseSizeBeforeCreate + 1);
        HttpLogTypes testHttpLogTypes = httpLogTypes.get(httpLogTypes.size() - 1);
        assertThat(testHttpLogTypes.getLogTypeId()).isEqualTo(DEFAULT_LOG_TYPE_ID);
        assertThat(testHttpLogTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHttpLogTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHttpLogTypes.isDefaultLogType()).isEqualTo(DEFAULT_DEFAULT_LOG_TYPE);
    }

    @Test
    @Transactional
    public void getAllHttpLogTypes() throws Exception {
        // Initialize the database
        httpLogTypesRepository.saveAndFlush(httpLogTypes);

        // Get all the httpLogTypes
        restHttpLogTypesMockMvc.perform(get("/api/http-log-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(httpLogTypes.getId().intValue())))
                .andExpect(jsonPath("$.[*].logTypeId").value(hasItem(DEFAULT_LOG_TYPE_ID)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].defaultLogType").value(hasItem(DEFAULT_DEFAULT_LOG_TYPE.booleanValue())));
    }

    @Test
    @Transactional
    public void getHttpLogTypes() throws Exception {
        // Initialize the database
        httpLogTypesRepository.saveAndFlush(httpLogTypes);

        // Get the httpLogTypes
        restHttpLogTypesMockMvc.perform(get("/api/http-log-types/{id}", httpLogTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(httpLogTypes.getId().intValue()))
            .andExpect(jsonPath("$.logTypeId").value(DEFAULT_LOG_TYPE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.defaultLogType").value(DEFAULT_DEFAULT_LOG_TYPE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHttpLogTypes() throws Exception {
        // Get the httpLogTypes
        restHttpLogTypesMockMvc.perform(get("/api/http-log-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHttpLogTypes() throws Exception {
        // Initialize the database
        httpLogTypesRepository.saveAndFlush(httpLogTypes);
        int databaseSizeBeforeUpdate = httpLogTypesRepository.findAll().size();

        // Update the httpLogTypes
        HttpLogTypes updatedHttpLogTypes = httpLogTypesRepository.findOne(httpLogTypes.getId());
        updatedHttpLogTypes
                .logTypeId(UPDATED_LOG_TYPE_ID)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .defaultLogType(UPDATED_DEFAULT_LOG_TYPE);

        restHttpLogTypesMockMvc.perform(put("/api/http-log-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHttpLogTypes)))
                .andExpect(status().isOk());

        // Validate the HttpLogTypes in the database
        List<HttpLogTypes> httpLogTypes = httpLogTypesRepository.findAll();
        assertThat(httpLogTypes).hasSize(databaseSizeBeforeUpdate);
        HttpLogTypes testHttpLogTypes = httpLogTypes.get(httpLogTypes.size() - 1);
        assertThat(testHttpLogTypes.getLogTypeId()).isEqualTo(UPDATED_LOG_TYPE_ID);
        assertThat(testHttpLogTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHttpLogTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHttpLogTypes.isDefaultLogType()).isEqualTo(UPDATED_DEFAULT_LOG_TYPE);
    }

    @Test
    @Transactional
    public void deleteHttpLogTypes() throws Exception {
        // Initialize the database
        httpLogTypesRepository.saveAndFlush(httpLogTypes);
        int databaseSizeBeforeDelete = httpLogTypesRepository.findAll().size();

        // Get the httpLogTypes
        restHttpLogTypesMockMvc.perform(delete("/api/http-log-types/{id}", httpLogTypes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HttpLogTypes> httpLogTypes = httpLogTypesRepository.findAll();
        assertThat(httpLogTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
