package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CareerPortalTemplateSite;
import com.datasolution.hire.repository.CareerPortalTemplateSiteRepository;

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
 * Test class for the CareerPortalTemplateSiteResource REST controller.
 *
 * @see CareerPortalTemplateSiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CareerPortalTemplateSiteResourceIntTest {

    private static final Integer DEFAULT_CAREER_PORTAL_TEMPLATE_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_TEMPLATE_ID = 2;

    private static final String DEFAULT_CAREER_PORTAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAREER_PORTAL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_SETTING = "AAAAAAAAAA";
    private static final String UPDATED_SETTING = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Inject
    private CareerPortalTemplateSiteRepository careerPortalTemplateSiteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCareerPortalTemplateSiteMockMvc;

    private CareerPortalTemplateSite careerPortalTemplateSite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerPortalTemplateSiteResource careerPortalTemplateSiteResource = new CareerPortalTemplateSiteResource();
        ReflectionTestUtils.setField(careerPortalTemplateSiteResource, "careerPortalTemplateSiteRepository", careerPortalTemplateSiteRepository);
        this.restCareerPortalTemplateSiteMockMvc = MockMvcBuilders.standaloneSetup(careerPortalTemplateSiteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPortalTemplateSite createEntity(EntityManager em) {
        CareerPortalTemplateSite careerPortalTemplateSite = new CareerPortalTemplateSite()
                .careerPortalTemplateId(DEFAULT_CAREER_PORTAL_TEMPLATE_ID)
                .careerPortalName(DEFAULT_CAREER_PORTAL_NAME)
                .siteId(DEFAULT_SITE_ID)
                .setting(DEFAULT_SETTING)
                .value(DEFAULT_VALUE);
        return careerPortalTemplateSite;
    }

    @Before
    public void initTest() {
        careerPortalTemplateSite = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPortalTemplateSite() throws Exception {
        int databaseSizeBeforeCreate = careerPortalTemplateSiteRepository.findAll().size();

        // Create the CareerPortalTemplateSite

        restCareerPortalTemplateSiteMockMvc.perform(post("/api/career-portal-template-sites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerPortalTemplateSite)))
                .andExpect(status().isCreated());

        // Validate the CareerPortalTemplateSite in the database
        List<CareerPortalTemplateSite> careerPortalTemplateSites = careerPortalTemplateSiteRepository.findAll();
        assertThat(careerPortalTemplateSites).hasSize(databaseSizeBeforeCreate + 1);
        CareerPortalTemplateSite testCareerPortalTemplateSite = careerPortalTemplateSites.get(careerPortalTemplateSites.size() - 1);
        assertThat(testCareerPortalTemplateSite.getCareerPortalTemplateId()).isEqualTo(DEFAULT_CAREER_PORTAL_TEMPLATE_ID);
        assertThat(testCareerPortalTemplateSite.getCareerPortalName()).isEqualTo(DEFAULT_CAREER_PORTAL_NAME);
        assertThat(testCareerPortalTemplateSite.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCareerPortalTemplateSite.getSetting()).isEqualTo(DEFAULT_SETTING);
        assertThat(testCareerPortalTemplateSite.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCareerPortalTemplateSites() throws Exception {
        // Initialize the database
        careerPortalTemplateSiteRepository.saveAndFlush(careerPortalTemplateSite);

        // Get all the careerPortalTemplateSites
        restCareerPortalTemplateSiteMockMvc.perform(get("/api/career-portal-template-sites?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerPortalTemplateSite.getId().intValue())))
                .andExpect(jsonPath("$.[*].careerPortalTemplateId").value(hasItem(DEFAULT_CAREER_PORTAL_TEMPLATE_ID)))
                .andExpect(jsonPath("$.[*].careerPortalName").value(hasItem(DEFAULT_CAREER_PORTAL_NAME.toString())))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].setting").value(hasItem(DEFAULT_SETTING.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCareerPortalTemplateSite() throws Exception {
        // Initialize the database
        careerPortalTemplateSiteRepository.saveAndFlush(careerPortalTemplateSite);

        // Get the careerPortalTemplateSite
        restCareerPortalTemplateSiteMockMvc.perform(get("/api/career-portal-template-sites/{id}", careerPortalTemplateSite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPortalTemplateSite.getId().intValue()))
            .andExpect(jsonPath("$.careerPortalTemplateId").value(DEFAULT_CAREER_PORTAL_TEMPLATE_ID))
            .andExpect(jsonPath("$.careerPortalName").value(DEFAULT_CAREER_PORTAL_NAME.toString()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.setting").value(DEFAULT_SETTING.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPortalTemplateSite() throws Exception {
        // Get the careerPortalTemplateSite
        restCareerPortalTemplateSiteMockMvc.perform(get("/api/career-portal-template-sites/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPortalTemplateSite() throws Exception {
        // Initialize the database
        careerPortalTemplateSiteRepository.saveAndFlush(careerPortalTemplateSite);
        int databaseSizeBeforeUpdate = careerPortalTemplateSiteRepository.findAll().size();

        // Update the careerPortalTemplateSite
        CareerPortalTemplateSite updatedCareerPortalTemplateSite = careerPortalTemplateSiteRepository.findOne(careerPortalTemplateSite.getId());
        updatedCareerPortalTemplateSite
                .careerPortalTemplateId(UPDATED_CAREER_PORTAL_TEMPLATE_ID)
                .careerPortalName(UPDATED_CAREER_PORTAL_NAME)
                .siteId(UPDATED_SITE_ID)
                .setting(UPDATED_SETTING)
                .value(UPDATED_VALUE);

        restCareerPortalTemplateSiteMockMvc.perform(put("/api/career-portal-template-sites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCareerPortalTemplateSite)))
                .andExpect(status().isOk());

        // Validate the CareerPortalTemplateSite in the database
        List<CareerPortalTemplateSite> careerPortalTemplateSites = careerPortalTemplateSiteRepository.findAll();
        assertThat(careerPortalTemplateSites).hasSize(databaseSizeBeforeUpdate);
        CareerPortalTemplateSite testCareerPortalTemplateSite = careerPortalTemplateSites.get(careerPortalTemplateSites.size() - 1);
        assertThat(testCareerPortalTemplateSite.getCareerPortalTemplateId()).isEqualTo(UPDATED_CAREER_PORTAL_TEMPLATE_ID);
        assertThat(testCareerPortalTemplateSite.getCareerPortalName()).isEqualTo(UPDATED_CAREER_PORTAL_NAME);
        assertThat(testCareerPortalTemplateSite.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCareerPortalTemplateSite.getSetting()).isEqualTo(UPDATED_SETTING);
        assertThat(testCareerPortalTemplateSite.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCareerPortalTemplateSite() throws Exception {
        // Initialize the database
        careerPortalTemplateSiteRepository.saveAndFlush(careerPortalTemplateSite);
        int databaseSizeBeforeDelete = careerPortalTemplateSiteRepository.findAll().size();

        // Get the careerPortalTemplateSite
        restCareerPortalTemplateSiteMockMvc.perform(delete("/api/career-portal-template-sites/{id}", careerPortalTemplateSite.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPortalTemplateSite> careerPortalTemplateSites = careerPortalTemplateSiteRepository.findAll();
        assertThat(careerPortalTemplateSites).hasSize(databaseSizeBeforeDelete - 1);
    }
}
