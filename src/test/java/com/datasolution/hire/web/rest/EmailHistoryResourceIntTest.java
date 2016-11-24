package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.EmailHistory;
import com.datasolution.hire.repository.EmailHistoryRepository;

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
 * Test class for the EmailHistoryResource REST controller.
 *
 * @see EmailHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class EmailHistoryResourceIntTest {

    private static final Integer DEFAULT_EMAIL_HISTORY_ID = 1;
    private static final Integer UPDATED_EMAIL_HISTORY_ID = 2;

    private static final String DEFAULT_FROM_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FROM_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPIENTS = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENTS = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE);

    @Inject
    private EmailHistoryRepository emailHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEmailHistoryMockMvc;

    private EmailHistory emailHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailHistoryResource emailHistoryResource = new EmailHistoryResource();
        ReflectionTestUtils.setField(emailHistoryResource, "emailHistoryRepository", emailHistoryRepository);
        this.restEmailHistoryMockMvc = MockMvcBuilders.standaloneSetup(emailHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailHistory createEntity(EntityManager em) {
        EmailHistory emailHistory = new EmailHistory()
                .emailHistoryId(DEFAULT_EMAIL_HISTORY_ID)
                .fromAddress(DEFAULT_FROM_ADDRESS)
                .recipients(DEFAULT_RECIPIENTS)
                .text(DEFAULT_TEXT)
                .userId(DEFAULT_USER_ID)
                .siteId(DEFAULT_SITE_ID)
                .date(DEFAULT_DATE);
        return emailHistory;
    }

    @Before
    public void initTest() {
        emailHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailHistory() throws Exception {
        int databaseSizeBeforeCreate = emailHistoryRepository.findAll().size();

        // Create the EmailHistory

        restEmailHistoryMockMvc.perform(post("/api/email-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(emailHistory)))
                .andExpect(status().isCreated());

        // Validate the EmailHistory in the database
        List<EmailHistory> emailHistories = emailHistoryRepository.findAll();
        assertThat(emailHistories).hasSize(databaseSizeBeforeCreate + 1);
        EmailHistory testEmailHistory = emailHistories.get(emailHistories.size() - 1);
        assertThat(testEmailHistory.getEmailHistoryId()).isEqualTo(DEFAULT_EMAIL_HISTORY_ID);
        assertThat(testEmailHistory.getFromAddress()).isEqualTo(DEFAULT_FROM_ADDRESS);
        assertThat(testEmailHistory.getRecipients()).isEqualTo(DEFAULT_RECIPIENTS);
        assertThat(testEmailHistory.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testEmailHistory.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmailHistory.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testEmailHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllEmailHistories() throws Exception {
        // Initialize the database
        emailHistoryRepository.saveAndFlush(emailHistory);

        // Get all the emailHistories
        restEmailHistoryMockMvc.perform(get("/api/email-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(emailHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].emailHistoryId").value(hasItem(DEFAULT_EMAIL_HISTORY_ID)))
                .andExpect(jsonPath("$.[*].fromAddress").value(hasItem(DEFAULT_FROM_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].recipients").value(hasItem(DEFAULT_RECIPIENTS.toString())))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getEmailHistory() throws Exception {
        // Initialize the database
        emailHistoryRepository.saveAndFlush(emailHistory);

        // Get the emailHistory
        restEmailHistoryMockMvc.perform(get("/api/email-histories/{id}", emailHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailHistory.getId().intValue()))
            .andExpect(jsonPath("$.emailHistoryId").value(DEFAULT_EMAIL_HISTORY_ID))
            .andExpect(jsonPath("$.fromAddress").value(DEFAULT_FROM_ADDRESS.toString()))
            .andExpect(jsonPath("$.recipients").value(DEFAULT_RECIPIENTS.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEmailHistory() throws Exception {
        // Get the emailHistory
        restEmailHistoryMockMvc.perform(get("/api/email-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailHistory() throws Exception {
        // Initialize the database
        emailHistoryRepository.saveAndFlush(emailHistory);
        int databaseSizeBeforeUpdate = emailHistoryRepository.findAll().size();

        // Update the emailHistory
        EmailHistory updatedEmailHistory = emailHistoryRepository.findOne(emailHistory.getId());
        updatedEmailHistory
                .emailHistoryId(UPDATED_EMAIL_HISTORY_ID)
                .fromAddress(UPDATED_FROM_ADDRESS)
                .recipients(UPDATED_RECIPIENTS)
                .text(UPDATED_TEXT)
                .userId(UPDATED_USER_ID)
                .siteId(UPDATED_SITE_ID)
                .date(UPDATED_DATE);

        restEmailHistoryMockMvc.perform(put("/api/email-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEmailHistory)))
                .andExpect(status().isOk());

        // Validate the EmailHistory in the database
        List<EmailHistory> emailHistories = emailHistoryRepository.findAll();
        assertThat(emailHistories).hasSize(databaseSizeBeforeUpdate);
        EmailHistory testEmailHistory = emailHistories.get(emailHistories.size() - 1);
        assertThat(testEmailHistory.getEmailHistoryId()).isEqualTo(UPDATED_EMAIL_HISTORY_ID);
        assertThat(testEmailHistory.getFromAddress()).isEqualTo(UPDATED_FROM_ADDRESS);
        assertThat(testEmailHistory.getRecipients()).isEqualTo(UPDATED_RECIPIENTS);
        assertThat(testEmailHistory.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEmailHistory.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmailHistory.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testEmailHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteEmailHistory() throws Exception {
        // Initialize the database
        emailHistoryRepository.saveAndFlush(emailHistory);
        int databaseSizeBeforeDelete = emailHistoryRepository.findAll().size();

        // Get the emailHistory
        restEmailHistoryMockMvc.perform(delete("/api/email-histories/{id}", emailHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailHistory> emailHistories = emailHistoryRepository.findAll();
        assertThat(emailHistories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
