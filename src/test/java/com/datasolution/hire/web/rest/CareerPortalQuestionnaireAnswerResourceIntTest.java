package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CareerPortalQuestionnaireAnswer;
import com.datasolution.hire.repository.CareerPortalQuestionnaireAnswerRepository;

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
 * Test class for the CareerPortalQuestionnaireAnswerResource REST controller.
 *
 * @see CareerPortalQuestionnaireAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CareerPortalQuestionnaireAnswerResourceIntTest {

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID = 2;

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID = 2;

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTION_IS_HOT = false;
    private static final Boolean UPDATED_ACTION_IS_HOT = true;

    private static final Boolean DEFAULT_ACTION_IS_ACTIVE = false;
    private static final Boolean UPDATED_ACTION_IS_ACTIVE = true;

    private static final Boolean DEFAULT_ACTION_CAN_RELOCATE = false;
    private static final Boolean UPDATED_ACTION_CAN_RELOCATE = true;

    private static final String DEFAULT_ACTION_KEY_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_KEY_SKILLS = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    @Inject
    private CareerPortalQuestionnaireAnswerRepository careerPortalQuestionnaireAnswerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCareerPortalQuestionnaireAnswerMockMvc;

    private CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerPortalQuestionnaireAnswerResource careerPortalQuestionnaireAnswerResource = new CareerPortalQuestionnaireAnswerResource();
        ReflectionTestUtils.setField(careerPortalQuestionnaireAnswerResource, "careerPortalQuestionnaireAnswerRepository", careerPortalQuestionnaireAnswerRepository);
        this.restCareerPortalQuestionnaireAnswerMockMvc = MockMvcBuilders.standaloneSetup(careerPortalQuestionnaireAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPortalQuestionnaireAnswer createEntity(EntityManager em) {
        CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswer = new CareerPortalQuestionnaireAnswer()
                .careerPortalQuestionnaireAnswerId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID)
                .careerPortalQuestionnaireQuestionId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID)
                .careerPortalQuestionnaireId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID)
                .text(DEFAULT_TEXT)
                .actionSource(DEFAULT_ACTION_SOURCE)
                .actionNotes(DEFAULT_ACTION_NOTES)
                .actionIsHot(DEFAULT_ACTION_IS_HOT)
                .actionIsActive(DEFAULT_ACTION_IS_ACTIVE)
                .actionCanRelocate(DEFAULT_ACTION_CAN_RELOCATE)
                .actionKeySkills(DEFAULT_ACTION_KEY_SKILLS)
                .position(DEFAULT_POSITION)
                .siteId(DEFAULT_SITE_ID);
        return careerPortalQuestionnaireAnswer;
    }

    @Before
    public void initTest() {
        careerPortalQuestionnaireAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPortalQuestionnaireAnswer() throws Exception {
        int databaseSizeBeforeCreate = careerPortalQuestionnaireAnswerRepository.findAll().size();

        // Create the CareerPortalQuestionnaireAnswer

        restCareerPortalQuestionnaireAnswerMockMvc.perform(post("/api/career-portal-questionnaire-answers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerPortalQuestionnaireAnswer)))
                .andExpect(status().isCreated());

        // Validate the CareerPortalQuestionnaireAnswer in the database
        List<CareerPortalQuestionnaireAnswer> careerPortalQuestionnaireAnswers = careerPortalQuestionnaireAnswerRepository.findAll();
        assertThat(careerPortalQuestionnaireAnswers).hasSize(databaseSizeBeforeCreate + 1);
        CareerPortalQuestionnaireAnswer testCareerPortalQuestionnaireAnswer = careerPortalQuestionnaireAnswers.get(careerPortalQuestionnaireAnswers.size() - 1);
        assertThat(testCareerPortalQuestionnaireAnswer.getCareerPortalQuestionnaireAnswerId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID);
        assertThat(testCareerPortalQuestionnaireAnswer.getCareerPortalQuestionnaireQuestionId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID);
        assertThat(testCareerPortalQuestionnaireAnswer.getCareerPortalQuestionnaireId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID);
        assertThat(testCareerPortalQuestionnaireAnswer.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testCareerPortalQuestionnaireAnswer.getActionSource()).isEqualTo(DEFAULT_ACTION_SOURCE);
        assertThat(testCareerPortalQuestionnaireAnswer.getActionNotes()).isEqualTo(DEFAULT_ACTION_NOTES);
        assertThat(testCareerPortalQuestionnaireAnswer.isActionIsHot()).isEqualTo(DEFAULT_ACTION_IS_HOT);
        assertThat(testCareerPortalQuestionnaireAnswer.isActionIsActive()).isEqualTo(DEFAULT_ACTION_IS_ACTIVE);
        assertThat(testCareerPortalQuestionnaireAnswer.isActionCanRelocate()).isEqualTo(DEFAULT_ACTION_CAN_RELOCATE);
        assertThat(testCareerPortalQuestionnaireAnswer.getActionKeySkills()).isEqualTo(DEFAULT_ACTION_KEY_SKILLS);
        assertThat(testCareerPortalQuestionnaireAnswer.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCareerPortalQuestionnaireAnswer.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllCareerPortalQuestionnaireAnswers() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireAnswerRepository.saveAndFlush(careerPortalQuestionnaireAnswer);

        // Get all the careerPortalQuestionnaireAnswers
        restCareerPortalQuestionnaireAnswerMockMvc.perform(get("/api/career-portal-questionnaire-answers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerPortalQuestionnaireAnswer.getId().intValue())))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireAnswerId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID)))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireQuestionId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID)))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID)))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].actionSource").value(hasItem(DEFAULT_ACTION_SOURCE.toString())))
                .andExpect(jsonPath("$.[*].actionNotes").value(hasItem(DEFAULT_ACTION_NOTES.toString())))
                .andExpect(jsonPath("$.[*].actionIsHot").value(hasItem(DEFAULT_ACTION_IS_HOT.booleanValue())))
                .andExpect(jsonPath("$.[*].actionIsActive").value(hasItem(DEFAULT_ACTION_IS_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].actionCanRelocate").value(hasItem(DEFAULT_ACTION_CAN_RELOCATE.booleanValue())))
                .andExpect(jsonPath("$.[*].actionKeySkills").value(hasItem(DEFAULT_ACTION_KEY_SKILLS.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)));
    }

    @Test
    @Transactional
    public void getCareerPortalQuestionnaireAnswer() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireAnswerRepository.saveAndFlush(careerPortalQuestionnaireAnswer);

        // Get the careerPortalQuestionnaireAnswer
        restCareerPortalQuestionnaireAnswerMockMvc.perform(get("/api/career-portal-questionnaire-answers/{id}", careerPortalQuestionnaireAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPortalQuestionnaireAnswer.getId().intValue()))
            .andExpect(jsonPath("$.careerPortalQuestionnaireAnswerId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID))
            .andExpect(jsonPath("$.careerPortalQuestionnaireQuestionId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID))
            .andExpect(jsonPath("$.careerPortalQuestionnaireId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.actionSource").value(DEFAULT_ACTION_SOURCE.toString()))
            .andExpect(jsonPath("$.actionNotes").value(DEFAULT_ACTION_NOTES.toString()))
            .andExpect(jsonPath("$.actionIsHot").value(DEFAULT_ACTION_IS_HOT.booleanValue()))
            .andExpect(jsonPath("$.actionIsActive").value(DEFAULT_ACTION_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.actionCanRelocate").value(DEFAULT_ACTION_CAN_RELOCATE.booleanValue()))
            .andExpect(jsonPath("$.actionKeySkills").value(DEFAULT_ACTION_KEY_SKILLS.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPortalQuestionnaireAnswer() throws Exception {
        // Get the careerPortalQuestionnaireAnswer
        restCareerPortalQuestionnaireAnswerMockMvc.perform(get("/api/career-portal-questionnaire-answers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPortalQuestionnaireAnswer() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireAnswerRepository.saveAndFlush(careerPortalQuestionnaireAnswer);
        int databaseSizeBeforeUpdate = careerPortalQuestionnaireAnswerRepository.findAll().size();

        // Update the careerPortalQuestionnaireAnswer
        CareerPortalQuestionnaireAnswer updatedCareerPortalQuestionnaireAnswer = careerPortalQuestionnaireAnswerRepository.findOne(careerPortalQuestionnaireAnswer.getId());
        updatedCareerPortalQuestionnaireAnswer
                .careerPortalQuestionnaireAnswerId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID)
                .careerPortalQuestionnaireQuestionId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID)
                .careerPortalQuestionnaireId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID)
                .text(UPDATED_TEXT)
                .actionSource(UPDATED_ACTION_SOURCE)
                .actionNotes(UPDATED_ACTION_NOTES)
                .actionIsHot(UPDATED_ACTION_IS_HOT)
                .actionIsActive(UPDATED_ACTION_IS_ACTIVE)
                .actionCanRelocate(UPDATED_ACTION_CAN_RELOCATE)
                .actionKeySkills(UPDATED_ACTION_KEY_SKILLS)
                .position(UPDATED_POSITION)
                .siteId(UPDATED_SITE_ID);

        restCareerPortalQuestionnaireAnswerMockMvc.perform(put("/api/career-portal-questionnaire-answers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCareerPortalQuestionnaireAnswer)))
                .andExpect(status().isOk());

        // Validate the CareerPortalQuestionnaireAnswer in the database
        List<CareerPortalQuestionnaireAnswer> careerPortalQuestionnaireAnswers = careerPortalQuestionnaireAnswerRepository.findAll();
        assertThat(careerPortalQuestionnaireAnswers).hasSize(databaseSizeBeforeUpdate);
        CareerPortalQuestionnaireAnswer testCareerPortalQuestionnaireAnswer = careerPortalQuestionnaireAnswers.get(careerPortalQuestionnaireAnswers.size() - 1);
        assertThat(testCareerPortalQuestionnaireAnswer.getCareerPortalQuestionnaireAnswerId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ANSWER_ID);
        assertThat(testCareerPortalQuestionnaireAnswer.getCareerPortalQuestionnaireQuestionId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_QUESTION_ID);
        assertThat(testCareerPortalQuestionnaireAnswer.getCareerPortalQuestionnaireId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_ID);
        assertThat(testCareerPortalQuestionnaireAnswer.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testCareerPortalQuestionnaireAnswer.getActionSource()).isEqualTo(UPDATED_ACTION_SOURCE);
        assertThat(testCareerPortalQuestionnaireAnswer.getActionNotes()).isEqualTo(UPDATED_ACTION_NOTES);
        assertThat(testCareerPortalQuestionnaireAnswer.isActionIsHot()).isEqualTo(UPDATED_ACTION_IS_HOT);
        assertThat(testCareerPortalQuestionnaireAnswer.isActionIsActive()).isEqualTo(UPDATED_ACTION_IS_ACTIVE);
        assertThat(testCareerPortalQuestionnaireAnswer.isActionCanRelocate()).isEqualTo(UPDATED_ACTION_CAN_RELOCATE);
        assertThat(testCareerPortalQuestionnaireAnswer.getActionKeySkills()).isEqualTo(UPDATED_ACTION_KEY_SKILLS);
        assertThat(testCareerPortalQuestionnaireAnswer.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCareerPortalQuestionnaireAnswer.getSiteId()).isEqualTo(UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void deleteCareerPortalQuestionnaireAnswer() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireAnswerRepository.saveAndFlush(careerPortalQuestionnaireAnswer);
        int databaseSizeBeforeDelete = careerPortalQuestionnaireAnswerRepository.findAll().size();

        // Get the careerPortalQuestionnaireAnswer
        restCareerPortalQuestionnaireAnswerMockMvc.perform(delete("/api/career-portal-questionnaire-answers/{id}", careerPortalQuestionnaireAnswer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPortalQuestionnaireAnswer> careerPortalQuestionnaireAnswers = careerPortalQuestionnaireAnswerRepository.findAll();
        assertThat(careerPortalQuestionnaireAnswers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
