package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Feedback;
import com.datasolution.hire.repository.FeedbackRepository;

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
 * Test class for the FeedbackResource REST controller.
 *
 * @see FeedbackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class FeedbackResourceIntTest {

    private static final Integer DEFAULT_FEEDBACK_ID = 1;
    private static final Integer UPDATED_FEEDBACK_ID = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_REPLY_TO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_TO_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_REPLY_TO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_TO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final Integer DEFAULT_ARCHIVED = 1;
    private static final Integer UPDATED_ARCHIVED = 2;

    @Inject
    private FeedbackRepository feedbackRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeedbackResource feedbackResource = new FeedbackResource();
        ReflectionTestUtils.setField(feedbackResource, "feedbackRepository", feedbackRepository);
        this.restFeedbackMockMvc = MockMvcBuilders.standaloneSetup(feedbackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
                .feedbackId(DEFAULT_FEEDBACK_ID)
                .userId(DEFAULT_USER_ID)
                .siteId(DEFAULT_SITE_ID)
                .dateCreated(DEFAULT_DATE_CREATED)
                .subject(DEFAULT_SUBJECT)
                .replyToAddress(DEFAULT_REPLY_TO_ADDRESS)
                .replyToName(DEFAULT_REPLY_TO_NAME)
                .feedback(DEFAULT_FEEDBACK)
                .archived(DEFAULT_ARCHIVED);
        return feedback;
    }

    @Before
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbacks.get(feedbacks.size() - 1);
        assertThat(testFeedback.getFeedbackId()).isEqualTo(DEFAULT_FEEDBACK_ID);
        assertThat(testFeedback.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testFeedback.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testFeedback.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFeedback.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testFeedback.getReplyToAddress()).isEqualTo(DEFAULT_REPLY_TO_ADDRESS);
        assertThat(testFeedback.getReplyToName()).isEqualTo(DEFAULT_REPLY_TO_NAME);
        assertThat(testFeedback.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testFeedback.getArchived()).isEqualTo(DEFAULT_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbacks
        restFeedbackMockMvc.perform(get("/api/feedbacks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
                .andExpect(jsonPath("$.[*].feedbackId").value(hasItem(DEFAULT_FEEDBACK_ID)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].replyToAddress").value(hasItem(DEFAULT_REPLY_TO_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].replyToName").value(hasItem(DEFAULT_REPLY_TO_NAME.toString())))
                .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK.toString())))
                .andExpect(jsonPath("$.[*].archived").value(hasItem(DEFAULT_ARCHIVED)));
    }

    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.feedbackId").value(DEFAULT_FEEDBACK_ID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.replyToAddress").value(DEFAULT_REPLY_TO_ADDRESS.toString()))
            .andExpect(jsonPath("$.replyToName").value(DEFAULT_REPLY_TO_NAME.toString()))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK.toString()))
            .andExpect(jsonPath("$.archived").value(DEFAULT_ARCHIVED));
    }

    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findOne(feedback.getId());
        updatedFeedback
                .feedbackId(UPDATED_FEEDBACK_ID)
                .userId(UPDATED_USER_ID)
                .siteId(UPDATED_SITE_ID)
                .dateCreated(UPDATED_DATE_CREATED)
                .subject(UPDATED_SUBJECT)
                .replyToAddress(UPDATED_REPLY_TO_ADDRESS)
                .replyToName(UPDATED_REPLY_TO_NAME)
                .feedback(UPDATED_FEEDBACK)
                .archived(UPDATED_ARCHIVED);

        restFeedbackMockMvc.perform(put("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFeedback)))
                .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbacks.get(feedbacks.size() - 1);
        assertThat(testFeedback.getFeedbackId()).isEqualTo(UPDATED_FEEDBACK_ID);
        assertThat(testFeedback.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFeedback.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testFeedback.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFeedback.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testFeedback.getReplyToAddress()).isEqualTo(UPDATED_REPLY_TO_ADDRESS);
        assertThat(testFeedback.getReplyToName()).isEqualTo(UPDATED_REPLY_TO_NAME);
        assertThat(testFeedback.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testFeedback.getArchived()).isEqualTo(UPDATED_ARCHIVED);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);
        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Get the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
