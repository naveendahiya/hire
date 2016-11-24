package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.EmailTemplate;
import com.datasolution.hire.repository.EmailTemplateRepository;

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
 * Test class for the EmailTemplateResource REST controller.
 *
 * @see EmailTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class EmailTemplateResourceIntTest {

    private static final Integer DEFAULT_EMAIL_TEMPLATE_ID = 1;
    private static final Integer UPDATED_EMAIL_TEMPLATE_ID = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALLOW_SUBSTITUTION = 1;
    private static final Integer UPDATED_ALLOW_SUBSTITUTION = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_POSSIBLE_VARIABLES = "AAAAAAAAAA";
    private static final String UPDATED_POSSIBLE_VARIABLES = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISABLED = 1;
    private static final Integer UPDATED_DISABLED = 2;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEmailTemplateMockMvc;

    private EmailTemplate emailTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailTemplateResource emailTemplateResource = new EmailTemplateResource();
        ReflectionTestUtils.setField(emailTemplateResource, "emailTemplateRepository", emailTemplateRepository);
        this.restEmailTemplateMockMvc = MockMvcBuilders.standaloneSetup(emailTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTemplate createEntity(EntityManager em) {
        EmailTemplate emailTemplate = new EmailTemplate()
                .emailTemplateId(DEFAULT_EMAIL_TEMPLATE_ID)
                .text(DEFAULT_TEXT)
                .allowSubstitution(DEFAULT_ALLOW_SUBSTITUTION)
                .siteId(DEFAULT_SITE_ID)
                .tag(DEFAULT_TAG)
                .title(DEFAULT_TITLE)
                .possibleVariables(DEFAULT_POSSIBLE_VARIABLES)
                .disabled(DEFAULT_DISABLED);
        return emailTemplate;
    }

    @Before
    public void initTest() {
        emailTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTemplate() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate

        restEmailTemplateMockMvc.perform(post("/api/email-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(emailTemplate)))
                .andExpect(status().isCreated());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
        assertThat(emailTemplates).hasSize(databaseSizeBeforeCreate + 1);
        EmailTemplate testEmailTemplate = emailTemplates.get(emailTemplates.size() - 1);
        assertThat(testEmailTemplate.getEmailTemplateId()).isEqualTo(DEFAULT_EMAIL_TEMPLATE_ID);
        assertThat(testEmailTemplate.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testEmailTemplate.getAllowSubstitution()).isEqualTo(DEFAULT_ALLOW_SUBSTITUTION);
        assertThat(testEmailTemplate.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testEmailTemplate.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testEmailTemplate.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmailTemplate.getPossibleVariables()).isEqualTo(DEFAULT_POSSIBLE_VARIABLES);
        assertThat(testEmailTemplate.getDisabled()).isEqualTo(DEFAULT_DISABLED);
    }

    @Test
    @Transactional
    public void getAllEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        // Get all the emailTemplates
        restEmailTemplateMockMvc.perform(get("/api/email-templates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplate.getId().intValue())))
                .andExpect(jsonPath("$.[*].emailTemplateId").value(hasItem(DEFAULT_EMAIL_TEMPLATE_ID)))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].allowSubstitution").value(hasItem(DEFAULT_ALLOW_SUBSTITUTION)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].possibleVariables").value(hasItem(DEFAULT_POSSIBLE_VARIABLES.toString())))
                .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED)));
    }

    @Test
    @Transactional
    public void getEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", emailTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailTemplate.getId().intValue()))
            .andExpect(jsonPath("$.emailTemplateId").value(DEFAULT_EMAIL_TEMPLATE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.allowSubstitution").value(DEFAULT_ALLOW_SUBSTITUTION))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.possibleVariables").value(DEFAULT_POSSIBLE_VARIABLES.toString()))
            .andExpect(jsonPath("$.disabled").value(DEFAULT_DISABLED));
    }

    @Test
    @Transactional
    public void getNonExistingEmailTemplate() throws Exception {
        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);
        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Update the emailTemplate
        EmailTemplate updatedEmailTemplate = emailTemplateRepository.findOne(emailTemplate.getId());
        updatedEmailTemplate
                .emailTemplateId(UPDATED_EMAIL_TEMPLATE_ID)
                .text(UPDATED_TEXT)
                .allowSubstitution(UPDATED_ALLOW_SUBSTITUTION)
                .siteId(UPDATED_SITE_ID)
                .tag(UPDATED_TAG)
                .title(UPDATED_TITLE)
                .possibleVariables(UPDATED_POSSIBLE_VARIABLES)
                .disabled(UPDATED_DISABLED);

        restEmailTemplateMockMvc.perform(put("/api/email-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEmailTemplate)))
                .andExpect(status().isOk());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
        assertThat(emailTemplates).hasSize(databaseSizeBeforeUpdate);
        EmailTemplate testEmailTemplate = emailTemplates.get(emailTemplates.size() - 1);
        assertThat(testEmailTemplate.getEmailTemplateId()).isEqualTo(UPDATED_EMAIL_TEMPLATE_ID);
        assertThat(testEmailTemplate.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEmailTemplate.getAllowSubstitution()).isEqualTo(UPDATED_ALLOW_SUBSTITUTION);
        assertThat(testEmailTemplate.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testEmailTemplate.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testEmailTemplate.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmailTemplate.getPossibleVariables()).isEqualTo(UPDATED_POSSIBLE_VARIABLES);
        assertThat(testEmailTemplate.getDisabled()).isEqualTo(UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void deleteEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);
        int databaseSizeBeforeDelete = emailTemplateRepository.findAll().size();

        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(delete("/api/email-templates/{id}", emailTemplate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
        assertThat(emailTemplates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
