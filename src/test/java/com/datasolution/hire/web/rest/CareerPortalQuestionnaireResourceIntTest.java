package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CareerPortalQuestionnaire;
import com.datasolution.hire.repository.CareerPortalQuestionnaireRepository;

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
 * Test class for the CareerPortalQuestionnaireResource REST controller.
 *
 * @see CareerPortalQuestionnaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CareerPortalQuestionnaireResourceIntTest {

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private CareerPortalQuestionnaireRepository careerPortalQuestionnaireRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCareerPortalQuestionnaireMockMvc;

    private CareerPortalQuestionnaire careerPortalQuestionnaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerPortalQuestionnaireResource careerPortalQuestionnaireResource = new CareerPortalQuestionnaireResource();
        ReflectionTestUtils.setField(careerPortalQuestionnaireResource, "careerPortalQuestionnaireRepository", careerPortalQuestionnaireRepository);
        this.restCareerPortalQuestionnaireMockMvc = MockMvcBuilders.standaloneSetup(careerPortalQuestionnaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPortalQuestionnaire createEntity(EntityManager em) {
        CareerPortalQuestionnaire careerPortalQuestionnaire = new CareerPortalQuestionnaire()
                .careerPortalQuestionnaireId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID)
                .title(DEFAULT_TITLE)
                .siteId(DEFAULT_SITE_ID)
                .description(DEFAULT_DESCRIPTION)
                .isActive(DEFAULT_IS_ACTIVE);
        return careerPortalQuestionnaire;
    }

    @Before
    public void initTest() {
        careerPortalQuestionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPortalQuestionnaire() throws Exception {
        int databaseSizeBeforeCreate = careerPortalQuestionnaireRepository.findAll().size();

        // Create the CareerPortalQuestionnaire

        restCareerPortalQuestionnaireMockMvc.perform(post("/api/career-portal-questionnaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerPortalQuestionnaire)))
                .andExpect(status().isCreated());

        // Validate the CareerPortalQuestionnaire in the database
        List<CareerPortalQuestionnaire> careerPortalQuestionnaires = careerPortalQuestionnaireRepository.findAll();
        assertThat(careerPortalQuestionnaires).hasSize(databaseSizeBeforeCreate + 1);
        CareerPortalQuestionnaire testCareerPortalQuestionnaire = careerPortalQuestionnaires.get(careerPortalQuestionnaires.size() - 1);
        assertThat(testCareerPortalQuestionnaire.getCareerPortalQuestionnaireId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID);
        assertThat(testCareerPortalQuestionnaire.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCareerPortalQuestionnaire.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCareerPortalQuestionnaire.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCareerPortalQuestionnaire.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCareerPortalQuestionnaires() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireRepository.saveAndFlush(careerPortalQuestionnaire);

        // Get all the careerPortalQuestionnaires
        restCareerPortalQuestionnaireMockMvc.perform(get("/api/career-portal-questionnaires?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerPortalQuestionnaire.getId().intValue())))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCareerPortalQuestionnaire() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireRepository.saveAndFlush(careerPortalQuestionnaire);

        // Get the careerPortalQuestionnaire
        restCareerPortalQuestionnaireMockMvc.perform(get("/api/career-portal-questionnaires/{id}", careerPortalQuestionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPortalQuestionnaire.getId().intValue()))
            .andExpect(jsonPath("$.careerPortalQuestionnaireId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPortalQuestionnaire() throws Exception {
        // Get the careerPortalQuestionnaire
        restCareerPortalQuestionnaireMockMvc.perform(get("/api/career-portal-questionnaires/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPortalQuestionnaire() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireRepository.saveAndFlush(careerPortalQuestionnaire);
        int databaseSizeBeforeUpdate = careerPortalQuestionnaireRepository.findAll().size();

        // Update the careerPortalQuestionnaire
        CareerPortalQuestionnaire updatedCareerPortalQuestionnaire = careerPortalQuestionnaireRepository.findOne(careerPortalQuestionnaire.getId());
        updatedCareerPortalQuestionnaire
                .careerPortalQuestionnaireId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID)
                .title(UPDATED_TITLE)
                .siteId(UPDATED_SITE_ID)
                .description(UPDATED_DESCRIPTION)
                .isActive(UPDATED_IS_ACTIVE);

        restCareerPortalQuestionnaireMockMvc.perform(put("/api/career-portal-questionnaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCareerPortalQuestionnaire)))
                .andExpect(status().isOk());

        // Validate the CareerPortalQuestionnaire in the database
        List<CareerPortalQuestionnaire> careerPortalQuestionnaires = careerPortalQuestionnaireRepository.findAll();
        assertThat(careerPortalQuestionnaires).hasSize(databaseSizeBeforeUpdate);
        CareerPortalQuestionnaire testCareerPortalQuestionnaire = careerPortalQuestionnaires.get(careerPortalQuestionnaires.size() - 1);
        assertThat(testCareerPortalQuestionnaire.getCareerPortalQuestionnaireId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID);
        assertThat(testCareerPortalQuestionnaire.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCareerPortalQuestionnaire.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCareerPortalQuestionnaire.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCareerPortalQuestionnaire.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteCareerPortalQuestionnaire() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireRepository.saveAndFlush(careerPortalQuestionnaire);
        int databaseSizeBeforeDelete = careerPortalQuestionnaireRepository.findAll().size();

        // Get the careerPortalQuestionnaire
        restCareerPortalQuestionnaireMockMvc.perform(delete("/api/career-portal-questionnaires/{id}", careerPortalQuestionnaire.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPortalQuestionnaire> careerPortalQuestionnaires = careerPortalQuestionnaireRepository.findAll();
        assertThat(careerPortalQuestionnaires).hasSize(databaseSizeBeforeDelete - 1);
    }
}
