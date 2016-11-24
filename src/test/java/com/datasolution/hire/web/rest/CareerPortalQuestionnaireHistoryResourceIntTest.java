package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CareerPortalQuestionnaireHistory;
import com.datasolution.hire.repository.CareerPortalQuestionnaireHistoryRepository;

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
 * Test class for the CareerPortalQuestionnaireHistoryResource REST controller.
 *
 * @see CareerPortalQuestionnaireHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CareerPortalQuestionnaireHistoryResourceIntTest {

    private static final Integer DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID = 1;
    private static final Integer UPDATED_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_CANDIDATE_ID = 1;
    private static final Integer UPDATED_CANDIDATE_ID = 2;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTIONNAIRE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_QUESTIONNAIRE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTIONNAIRE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTIONNAIRE_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE);

    @Inject
    private CareerPortalQuestionnaireHistoryRepository careerPortalQuestionnaireHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCareerPortalQuestionnaireHistoryMockMvc;

    private CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CareerPortalQuestionnaireHistoryResource careerPortalQuestionnaireHistoryResource = new CareerPortalQuestionnaireHistoryResource();
        ReflectionTestUtils.setField(careerPortalQuestionnaireHistoryResource, "careerPortalQuestionnaireHistoryRepository", careerPortalQuestionnaireHistoryRepository);
        this.restCareerPortalQuestionnaireHistoryMockMvc = MockMvcBuilders.standaloneSetup(careerPortalQuestionnaireHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPortalQuestionnaireHistory createEntity(EntityManager em) {
        CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistory = new CareerPortalQuestionnaireHistory()
                .careerPortalQuestionnaireHistoryId(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID)
                .siteId(DEFAULT_SITE_ID)
                .candidateId(DEFAULT_CANDIDATE_ID)
                .question(DEFAULT_QUESTION)
                .answer(DEFAULT_ANSWER)
                .questionnaireTitle(DEFAULT_QUESTIONNAIRE_TITLE)
                .questionnaireDescription(DEFAULT_QUESTIONNAIRE_DESCRIPTION)
                .date(DEFAULT_DATE);
        return careerPortalQuestionnaireHistory;
    }

    @Before
    public void initTest() {
        careerPortalQuestionnaireHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPortalQuestionnaireHistory() throws Exception {
        int databaseSizeBeforeCreate = careerPortalQuestionnaireHistoryRepository.findAll().size();

        // Create the CareerPortalQuestionnaireHistory

        restCareerPortalQuestionnaireHistoryMockMvc.perform(post("/api/career-portal-questionnaire-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(careerPortalQuestionnaireHistory)))
                .andExpect(status().isCreated());

        // Validate the CareerPortalQuestionnaireHistory in the database
        List<CareerPortalQuestionnaireHistory> careerPortalQuestionnaireHistories = careerPortalQuestionnaireHistoryRepository.findAll();
        assertThat(careerPortalQuestionnaireHistories).hasSize(databaseSizeBeforeCreate + 1);
        CareerPortalQuestionnaireHistory testCareerPortalQuestionnaireHistory = careerPortalQuestionnaireHistories.get(careerPortalQuestionnaireHistories.size() - 1);
        assertThat(testCareerPortalQuestionnaireHistory.getCareerPortalQuestionnaireHistoryId()).isEqualTo(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID);
        assertThat(testCareerPortalQuestionnaireHistory.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCareerPortalQuestionnaireHistory.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCareerPortalQuestionnaireHistory.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testCareerPortalQuestionnaireHistory.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testCareerPortalQuestionnaireHistory.getQuestionnaireTitle()).isEqualTo(DEFAULT_QUESTIONNAIRE_TITLE);
        assertThat(testCareerPortalQuestionnaireHistory.getQuestionnaireDescription()).isEqualTo(DEFAULT_QUESTIONNAIRE_DESCRIPTION);
        assertThat(testCareerPortalQuestionnaireHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllCareerPortalQuestionnaireHistories() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireHistoryRepository.saveAndFlush(careerPortalQuestionnaireHistory);

        // Get all the careerPortalQuestionnaireHistories
        restCareerPortalQuestionnaireHistoryMockMvc.perform(get("/api/career-portal-questionnaire-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(careerPortalQuestionnaireHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].careerPortalQuestionnaireHistoryId").value(hasItem(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID)))
                .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
                .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
                .andExpect(jsonPath("$.[*].questionnaireTitle").value(hasItem(DEFAULT_QUESTIONNAIRE_TITLE.toString())))
                .andExpect(jsonPath("$.[*].questionnaireDescription").value(hasItem(DEFAULT_QUESTIONNAIRE_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getCareerPortalQuestionnaireHistory() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireHistoryRepository.saveAndFlush(careerPortalQuestionnaireHistory);

        // Get the careerPortalQuestionnaireHistory
        restCareerPortalQuestionnaireHistoryMockMvc.perform(get("/api/career-portal-questionnaire-histories/{id}", careerPortalQuestionnaireHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPortalQuestionnaireHistory.getId().intValue()))
            .andExpect(jsonPath("$.careerPortalQuestionnaireHistoryId").value(DEFAULT_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()))
            .andExpect(jsonPath("$.questionnaireTitle").value(DEFAULT_QUESTIONNAIRE_TITLE.toString()))
            .andExpect(jsonPath("$.questionnaireDescription").value(DEFAULT_QUESTIONNAIRE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPortalQuestionnaireHistory() throws Exception {
        // Get the careerPortalQuestionnaireHistory
        restCareerPortalQuestionnaireHistoryMockMvc.perform(get("/api/career-portal-questionnaire-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPortalQuestionnaireHistory() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireHistoryRepository.saveAndFlush(careerPortalQuestionnaireHistory);
        int databaseSizeBeforeUpdate = careerPortalQuestionnaireHistoryRepository.findAll().size();

        // Update the careerPortalQuestionnaireHistory
        CareerPortalQuestionnaireHistory updatedCareerPortalQuestionnaireHistory = careerPortalQuestionnaireHistoryRepository.findOne(careerPortalQuestionnaireHistory.getId());
        updatedCareerPortalQuestionnaireHistory
                .careerPortalQuestionnaireHistoryId(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID)
                .siteId(UPDATED_SITE_ID)
                .candidateId(UPDATED_CANDIDATE_ID)
                .question(UPDATED_QUESTION)
                .answer(UPDATED_ANSWER)
                .questionnaireTitle(UPDATED_QUESTIONNAIRE_TITLE)
                .questionnaireDescription(UPDATED_QUESTIONNAIRE_DESCRIPTION)
                .date(UPDATED_DATE);

        restCareerPortalQuestionnaireHistoryMockMvc.perform(put("/api/career-portal-questionnaire-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCareerPortalQuestionnaireHistory)))
                .andExpect(status().isOk());

        // Validate the CareerPortalQuestionnaireHistory in the database
        List<CareerPortalQuestionnaireHistory> careerPortalQuestionnaireHistories = careerPortalQuestionnaireHistoryRepository.findAll();
        assertThat(careerPortalQuestionnaireHistories).hasSize(databaseSizeBeforeUpdate);
        CareerPortalQuestionnaireHistory testCareerPortalQuestionnaireHistory = careerPortalQuestionnaireHistories.get(careerPortalQuestionnaireHistories.size() - 1);
        assertThat(testCareerPortalQuestionnaireHistory.getCareerPortalQuestionnaireHistoryId()).isEqualTo(UPDATED_CAREER_PORTAL_QUESTIONNAIRE_HISTORY_ID);
        assertThat(testCareerPortalQuestionnaireHistory.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCareerPortalQuestionnaireHistory.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCareerPortalQuestionnaireHistory.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testCareerPortalQuestionnaireHistory.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testCareerPortalQuestionnaireHistory.getQuestionnaireTitle()).isEqualTo(UPDATED_QUESTIONNAIRE_TITLE);
        assertThat(testCareerPortalQuestionnaireHistory.getQuestionnaireDescription()).isEqualTo(UPDATED_QUESTIONNAIRE_DESCRIPTION);
        assertThat(testCareerPortalQuestionnaireHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteCareerPortalQuestionnaireHistory() throws Exception {
        // Initialize the database
        careerPortalQuestionnaireHistoryRepository.saveAndFlush(careerPortalQuestionnaireHistory);
        int databaseSizeBeforeDelete = careerPortalQuestionnaireHistoryRepository.findAll().size();

        // Get the careerPortalQuestionnaireHistory
        restCareerPortalQuestionnaireHistoryMockMvc.perform(delete("/api/career-portal-questionnaire-histories/{id}", careerPortalQuestionnaireHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPortalQuestionnaireHistory> careerPortalQuestionnaireHistories = careerPortalQuestionnaireHistoryRepository.findAll();
        assertThat(careerPortalQuestionnaireHistories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
