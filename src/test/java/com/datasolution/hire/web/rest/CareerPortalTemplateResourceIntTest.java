package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CareerPortalTemplate;
import com.datasolution.hire.repository.CareerPortalTemplateRepository;

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
 * Test class for the CareerPortalTemplateResource REST controller.
 *
 * @see CareerPortalTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CareerPortalTemplateResourceIntTest {

    private static final Integer DEFAULT_CAREER_PORTAL_TEMPLATE_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_TEMPLATE_ID = 2;

    private static final String DEFAULT_CAREER_PORTAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAREER_PORTAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SETTING = "AAAAAAAAAA";
    private static final String UPDATED_SETTING = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Inject
    private CareerPortalTemplateRepository careerPortalTemplateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCareerPortalTemplateMockMvc;

    private CareerPortalTemplate careerPortalTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerPortalTemplateResource careerPortalTemplateResource = new CareerPortalTemplateResource();
        ReflectionTestUtils.setField(careerPortalTemplateResource, "careerPortalTemplateRepository", careerPortalTemplateRepository);
        this.restCareerPortalTemplateMockMvc = MockMvcBuilders.standaloneSetup(careerPortalTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPortalTemplate createEntity(EntityManager em) {
        CareerPortalTemplate careerPortalTemplate = new CareerPortalTemplate()
                .careerPortalTemplateId(DEFAULT_CAREER_PORTAL_TEMPLATE_ID)
                .careerPortalName(DEFAULT_CAREER_PORTAL_NAME)
                .setting(DEFAULT_SETTING)
                .value(DEFAULT_VALUE);
        return careerPortalTemplate;
    }

    @Before
    public void initTest() {
        careerPortalTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPortalTemplate() throws Exception {
        int databaseSizeBeforeCreate = careerPortalTemplateRepository.findAll().size();

        // Create the CareerPortalTemplate

        restCareerPortalTemplateMockMvc.perform(post("/api/career-portal-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerPortalTemplate)))
                .andExpect(status().isCreated());

        // Validate the CareerPortalTemplate in the database
        List<CareerPortalTemplate> careerPortalTemplates = careerPortalTemplateRepository.findAll();
        assertThat(careerPortalTemplates).hasSize(databaseSizeBeforeCreate + 1);
        CareerPortalTemplate testCareerPortalTemplate = careerPortalTemplates.get(careerPortalTemplates.size() - 1);
        assertThat(testCareerPortalTemplate.getCareerPortalTemplateId()).isEqualTo(DEFAULT_CAREER_PORTAL_TEMPLATE_ID);
        assertThat(testCareerPortalTemplate.getCareerPortalName()).isEqualTo(DEFAULT_CAREER_PORTAL_NAME);
        assertThat(testCareerPortalTemplate.getSetting()).isEqualTo(DEFAULT_SETTING);
        assertThat(testCareerPortalTemplate.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCareerPortalTemplates() throws Exception {
        // Initialize the database
        careerPortalTemplateRepository.saveAndFlush(careerPortalTemplate);

        // Get all the careerPortalTemplates
        restCareerPortalTemplateMockMvc.perform(get("/api/career-portal-templates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerPortalTemplate.getId().intValue())))
                .andExpect(jsonPath("$.[*].careerPortalTemplateId").value(hasItem(DEFAULT_CAREER_PORTAL_TEMPLATE_ID)))
                .andExpect(jsonPath("$.[*].careerPortalName").value(hasItem(DEFAULT_CAREER_PORTAL_NAME.toString())))
                .andExpect(jsonPath("$.[*].setting").value(hasItem(DEFAULT_SETTING.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCareerPortalTemplate() throws Exception {
        // Initialize the database
        careerPortalTemplateRepository.saveAndFlush(careerPortalTemplate);

        // Get the careerPortalTemplate
        restCareerPortalTemplateMockMvc.perform(get("/api/career-portal-templates/{id}", careerPortalTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPortalTemplate.getId().intValue()))
            .andExpect(jsonPath("$.careerPortalTemplateId").value(DEFAULT_CAREER_PORTAL_TEMPLATE_ID))
            .andExpect(jsonPath("$.careerPortalName").value(DEFAULT_CAREER_PORTAL_NAME.toString()))
            .andExpect(jsonPath("$.setting").value(DEFAULT_SETTING.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPortalTemplate() throws Exception {
        // Get the careerPortalTemplate
        restCareerPortalTemplateMockMvc.perform(get("/api/career-portal-templates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPortalTemplate() throws Exception {
        // Initialize the database
        careerPortalTemplateRepository.saveAndFlush(careerPortalTemplate);
        int databaseSizeBeforeUpdate = careerPortalTemplateRepository.findAll().size();

        // Update the careerPortalTemplate
        CareerPortalTemplate updatedCareerPortalTemplate = careerPortalTemplateRepository.findOne(careerPortalTemplate.getId());
        updatedCareerPortalTemplate
                .careerPortalTemplateId(UPDATED_CAREER_PORTAL_TEMPLATE_ID)
                .careerPortalName(UPDATED_CAREER_PORTAL_NAME)
                .setting(UPDATED_SETTING)
                .value(UPDATED_VALUE);

        restCareerPortalTemplateMockMvc.perform(put("/api/career-portal-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCareerPortalTemplate)))
                .andExpect(status().isOk());

        // Validate the CareerPortalTemplate in the database
        List<CareerPortalTemplate> careerPortalTemplates = careerPortalTemplateRepository.findAll();
        assertThat(careerPortalTemplates).hasSize(databaseSizeBeforeUpdate);
        CareerPortalTemplate testCareerPortalTemplate = careerPortalTemplates.get(careerPortalTemplates.size() - 1);
        assertThat(testCareerPortalTemplate.getCareerPortalTemplateId()).isEqualTo(UPDATED_CAREER_PORTAL_TEMPLATE_ID);
        assertThat(testCareerPortalTemplate.getCareerPortalName()).isEqualTo(UPDATED_CAREER_PORTAL_NAME);
        assertThat(testCareerPortalTemplate.getSetting()).isEqualTo(UPDATED_SETTING);
        assertThat(testCareerPortalTemplate.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCareerPortalTemplate() throws Exception {
        // Initialize the database
        careerPortalTemplateRepository.saveAndFlush(careerPortalTemplate);
        int databaseSizeBeforeDelete = careerPortalTemplateRepository.findAll().size();

        // Get the careerPortalTemplate
        restCareerPortalTemplateMockMvc.perform(delete("/api/career-portal-templates/{id}", careerPortalTemplate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPortalTemplate> careerPortalTemplates = careerPortalTemplateRepository.findAll();
        assertThat(careerPortalTemplates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
