package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CareerPortalQuestionnaireQuestion;
import com.datasolution.hire.repository.CareerPortalQuestionnaireQuestionRepository;

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
 * Test class for the CareerPortalQuestionnaireQuestionResource REST controller.
 *
 * @see CareerPortalQuestionnaireQuestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CareerPortalQuestionnaireQuestionResourceIntTest {

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID = 2;

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MINIMUM_LENGTH = 1;
    private static final Integer UPDATED_MINIMUM_LENGTH = 2;

    private static final Integer DEFAULT_MAXIMUM_LENGTH = 1;
    private static final Integer UPDATED_MAXIMUM_LENGTH = 2;

    private static final Boolean DEFAULT_REQUIR = false;
    private static final Boolean UPDATED_REQUIR = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    @Inject
    private CareerPortalQuestionnaireQuestionRepository careerPortalQuestionnaireQuestionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCareerPortalQuestionnaireQuestionMockMvc;

    private CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerPortalQuestionnaireQuestionResource careerPortalQuestionnaireQuestionResource = new CareerPortalQuestionnaireQuestionResource();
        ReflectionTestUtils.setField(careerPortalQuestionnaireQuestionResource, "careerPortalQuestionnaireQuestionRepository", careerPortalQuestionnaireQuestionRepository);
        this.restCareerPortalQuestionnaireQuestionMockMvc = MockMvcBuilders.standaloneSetup(careerPortalQuestionnaireQuestionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPortalQuestionnaireQuestion createEntity(EntityManager em) {
        CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestion = new CareerPortalQuestionnaireQuestion()
                .careerPortalQuestionnaireQuestionId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID)
                .careerPortalQuestionnaireId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID)
                .text(DEFAULT_TEXT)
                .minimumLength(DEFAULT_MINIMUM_LENGTH)
                .maximumLength(DEFAULT_MAXIMUM_LENGTH)
                .requir(DEFAULT_REQUIR)
                .position(DEFAULT_POSITION)
                .siteId(DEFAULT_SITE_ID)
                .type(DEFAULT_TYPE);
        return careerPortalQuestionnaireQuestion;
    }

    @Before
    public void initTest() {
        careerPortalQuestionnaireQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPortalQuestionnaireQuestion() throws Exception {
        int databaseSizeBeforeCreate = careerPortalQuestionnaireQuestionRepository.findAll().size();

        // Create the CareerPortalQuestionnaireQuestion

        restCareerPortalQuestionnaireQuestionMockMvc.perform(post("/api/career-portal-questionnaire-questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerPortalQuestionnaireQuestion)))
                .andExpect(status().isCreated());

        // Validate the CareerPortalQuestionnaireQuestion in the database
        List<CareerPortalQuestionnaireQuestion> careerPortalQuestionnaireQuestions = careerPortalQuestionnaireQuestionRepository.findAll();
        assertThat(careerPortalQuestionnaireQuestions).hasSize(databaseSizeBeforeCreate + 1);
        CareerPortalQuestionnaireQuestion testCareerPortalQuestionnaireQuestion = careerPortalQuestionnaireQuestions.get(careerPortalQuestionnaireQuestions.size() - 1);
        assertThat(testCareerPortalQuestionnaireQuestion.getCareerPortalQuestionnaireQuestionId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID);
        assertThat(testCareerPortalQuestionnaireQuestion.getCareerPortalQuestionnaireId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID);
        assertThat(testCareerPortalQuestionnaireQuestion.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testCareerPortalQuestionnaireQuestion.getMinimumLength()).isEqualTo(DEFAULT_MINIMUM_LENGTH);
        assertThat(testCareerPortalQuestionnaireQuestion.getMaximumLength()).isEqualTo(DEFAULT_MAXIMUM_LENGTH);
        assertThat(testCareerPortalQuestionnaireQuestion.isRequir()).isEqualTo(DEFAULT_REQUIR);
        assertThat(testCareerPortalQuestionnaireQuestion.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCareerPortalQuestionnaireQuestion.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCareerPortalQuestionnaireQuestion.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCareerPortalQuestionnaireQuestions() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireQuestionRepository.saveAndFlush(careerPortalQuestionnaireQuestion);

        // Get all the careerPortalQuestionnaireQuestions
        restCareerPortalQuestionnaireQuestionMockMvc.perform(get("/api/career-portal-questionnaire-questions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerPortalQuestionnaireQuestion.getId().intValue())))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireQuestionId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID)))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID)))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].minimumLength").value(hasItem(DEFAULT_MINIMUM_LENGTH)))
                .andExpect(jsonPath("$.[*].maximumLength").value(hasItem(DEFAULT_MAXIMUM_LENGTH)))
                .andExpect(jsonPath("$.[*].requir").value(hasItem(DEFAULT_REQUIR.booleanValue())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    public void getCareerPortalQuestionnaireQuestion() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireQuestionRepository.saveAndFlush(careerPortalQuestionnaireQuestion);

        // Get the careerPortalQuestionnaireQuestion
        restCareerPortalQuestionnaireQuestionMockMvc.perform(get("/api/career-portal-questionnaire-questions/{id}", careerPortalQuestionnaireQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPortalQuestionnaireQuestion.getId().intValue()))
            .andExpect(jsonPath("$.careerPortalQuestionnaireQuestionId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID))
            .andExpect(jsonPath("$.careerPortalQuestionnaireId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.minimumLength").value(DEFAULT_MINIMUM_LENGTH))
            .andExpect(jsonPath("$.maximumLength").value(DEFAULT_MAXIMUM_LENGTH))
            .andExpect(jsonPath("$.requir").value(DEFAULT_REQUIR.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPortalQuestionnaireQuestion() throws Exception {
        // Get the careerPortalQuestionnaireQuestion
        restCareerPortalQuestionnaireQuestionMockMvc.perform(get("/api/career-portal-questionnaire-questions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPortalQuestionnaireQuestion() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireQuestionRepository.saveAndFlush(careerPortalQuestionnaireQuestion);
        int databaseSizeBeforeUpdate = careerPortalQuestionnaireQuestionRepository.findAll().size();

        // Update the careerPortalQuestionnaireQuestion
        CareerPortalQuestionnaireQuestion updatedCareerPortalQuestionnaireQuestion = careerPortalQuestionnaireQuestionRepository.findOne(careerPortalQuestionnaireQuestion.getId());
        updatedCareerPortalQuestionnaireQuestion
                .careerPortalQuestionnaireQuestionId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID)
                .careerPortalQuestionnaireId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID)
                .text(UPDATED_TEXT)
                .minimumLength(UPDATED_MINIMUM_LENGTH)
                .maximumLength(UPDATED_MAXIMUM_LENGTH)
                .requir(UPDATED_REQUIR)
                .position(UPDATED_POSITION)
                .siteId(UPDATED_SITE_ID)
                .type(UPDATED_TYPE);

        restCareerPortalQuestionnaireQuestionMockMvc.perform(put("/api/career-portal-questionnaire-questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCareerPortalQuestionnaireQuestion)))
                .andExpect(status().isOk());

        // Validate the CareerPortalQuestionnaireQuestion in the database
        List<CareerPortalQuestionnaireQuestion> careerPortalQuestionnaireQuestions = careerPortalQuestionnaireQuestionRepository.findAll();
        assertThat(careerPortalQuestionnaireQuestions).hasSize(databaseSizeBeforeUpdate);
        CareerPortalQuestionnaireQuestion testCareerPortalQuestionnaireQuestion = careerPortalQuestionnaireQuestions.get(careerPortalQuestionnaireQuestions.size() - 1);
        assertThat(testCareerPortalQuestionnaireQuestion.getCareerPortalQuestionnaireQuestionId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID);
        assertThat(testCareerPortalQuestionnaireQuestion.getCareerPortalQuestionnaireId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID);
        assertThat(testCareerPortalQuestionnaireQuestion.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testCareerPortalQuestionnaireQuestion.getMinimumLength()).isEqualTo(UPDATED_MINIMUM_LENGTH);
        assertThat(testCareerPortalQuestionnaireQuestion.getMaximumLength()).isEqualTo(UPDATED_MAXIMUM_LENGTH);
        assertThat(testCareerPortalQuestionnaireQuestion.isRequir()).isEqualTo(UPDATED_REQUIR);
        assertThat(testCareerPortalQuestionnaireQuestion.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCareerPortalQuestionnaireQuestion.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCareerPortalQuestionnaireQuestion.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteCareerPortalQuestionnaireQuestion() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireQuestionRepository.saveAndFlush(careerPortalQuestionnaireQuestion);
        int databaseSizeBeforeDelete = careerPortalQuestionnaireQuestionRepository.findAll().size();

        // Get the careerPortalQuestionnaireQuestion
        restCareerPortalQuestionnaireQuestionMockMvc.perform(delete("/api/career-portal-questionnaire-questions/{id}", careerPortalQuestionnaireQuestion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPortalQuestionnaireQuestion> careerPortalQuestionnaireQuestions = careerPortalQuestionnaireQuestionRepository.findAll();
        assertThat(careerPortalQuestionnaireQuestions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
