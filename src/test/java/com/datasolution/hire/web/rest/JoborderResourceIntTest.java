package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Joborder;
import com.datasolution.hire.repository.JoborderRepository;

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
 * Test class for the JoborderResource REST controller.
 *
 * @see JoborderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class JoborderResourceIntTest {

    private static final Integer DEFAULT_JOBORDER_ID = 1;
    private static final Integer UPDATED_JOBORDER_ID = 2;

    private static final Integer DEFAULT_RECRUITER = 1;
    private static final Integer UPDATED_RECRUITER = 2;

    private static final Integer DEFAULT_CONTACT_ID = 1;
    private static final Integer UPDATED_CONTACT_ID = 2;

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_ENTERED_BY = 1;
    private static final Integer UPDATED_ENTERED_BY = 2;

    private static final Integer DEFAULT_OWNER = 1;
    private static final Integer UPDATED_OWNER = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_CLIENT_JOB_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_JOB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_RATE_MAX = "AAAAAAAAAA";
    private static final String UPDATED_RATE_MAX = "BBBBBBBBBB";

    private static final String DEFAULT_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_HOT = 1;
    private static final Integer UPDATED_IS_HOT = 2;

    private static final Integer DEFAULT_OPENINGS = 1;
    private static final Integer UPDATED_OPENINGS = 2;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    private static final Integer DEFAULT_PUBLIC_V = 1;
    private static final Integer UPDATED_PUBLIC_V = 2;

    private static final Integer DEFAULT_COMPANY_DEPARTMENT_ID = 1;
    private static final Integer UPDATED_COMPANY_DEPARTMENT_ID = 2;

    private static final Integer DEFAULT_IS_ADMIN_HIDDEN = 1;
    private static final Integer UPDATED_IS_ADMIN_HIDDEN = 2;

    private static final Integer DEFAULT_OPENINGS_AVAILABLE = 1;
    private static final Integer UPDATED_OPENINGS_AVAILABLE = 2;

    private static final Integer DEFAULT_QUESTIONNAIRE_ID = 1;
    private static final Integer UPDATED_QUESTIONNAIRE_ID = 2;

    @Inject
    private JoborderRepository joborderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJoborderMockMvc;

    private Joborder joborder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JoborderResource joborderResource = new JoborderResource();
        ReflectionTestUtils.setField(joborderResource, "joborderRepository", joborderRepository);
        this.restJoborderMockMvc = MockMvcBuilders.standaloneSetup(joborderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Joborder createEntity(EntityManager em) {
        Joborder joborder = new Joborder()
                .joborderId(DEFAULT_JOBORDER_ID)
                .recruiter(DEFAULT_RECRUITER)
                .contactId(DEFAULT_CONTACT_ID)
                .companyId(DEFAULT_COMPANY_ID)
                .enteredBy(DEFAULT_ENTERED_BY)
                .owner(DEFAULT_OWNER)
                .siteId(DEFAULT_SITE_ID)
                .clientJobId(DEFAULT_CLIENT_JOB_ID)
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .notes(DEFAULT_NOTES)
                .type(DEFAULT_TYPE)
                .duration(DEFAULT_DURATION)
                .rateMax(DEFAULT_RATE_MAX)
                .salary(DEFAULT_SALARY)
                .status(DEFAULT_STATUS)
                .isHot(DEFAULT_IS_HOT)
                .openings(DEFAULT_OPENINGS)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .startDate(DEFAULT_START_DATE)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .publicV(DEFAULT_PUBLIC_V)
                .companyDepartmentId(DEFAULT_COMPANY_DEPARTMENT_ID)
                .isAdminHidden(DEFAULT_IS_ADMIN_HIDDEN)
                .openingsAvailable(DEFAULT_OPENINGS_AVAILABLE)
                .questionnaireId(DEFAULT_QUESTIONNAIRE_ID);
        return joborder;
    }

    @Before
    public void initTest() {
        joborder = createEntity(em);
    }

    @Test
    @Transactional
    public void createJoborder() throws Exception {
        int databaseSizeBeforeCreate = joborderRepository.findAll().size();

        // Create the Joborder

        restJoborderMockMvc.perform(post("/api/joborders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(joborder)))
                .andExpect(status().isCreated());

        // Validate the Joborder in the database
        List<Joborder> joborders = joborderRepository.findAll();
        assertThat(joborders).hasSize(databaseSizeBeforeCreate + 1);
        Joborder testJoborder = joborders.get(joborders.size() - 1);
        assertThat(testJoborder.getJoborderId()).isEqualTo(DEFAULT_JOBORDER_ID);
        assertThat(testJoborder.getRecruiter()).isEqualTo(DEFAULT_RECRUITER);
        assertThat(testJoborder.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testJoborder.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testJoborder.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testJoborder.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testJoborder.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testJoborder.getClientJobId()).isEqualTo(DEFAULT_CLIENT_JOB_ID);
        assertThat(testJoborder.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJoborder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJoborder.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testJoborder.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJoborder.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testJoborder.getRateMax()).isEqualTo(DEFAULT_RATE_MAX);
        assertThat(testJoborder.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testJoborder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testJoborder.getIsHot()).isEqualTo(DEFAULT_IS_HOT);
        assertThat(testJoborder.getOpenings()).isEqualTo(DEFAULT_OPENINGS);
        assertThat(testJoborder.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testJoborder.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testJoborder.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJoborder.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testJoborder.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testJoborder.getPublicV()).isEqualTo(DEFAULT_PUBLIC_V);
        assertThat(testJoborder.getCompanyDepartmentId()).isEqualTo(DEFAULT_COMPANY_DEPARTMENT_ID);
        assertThat(testJoborder.getIsAdminHidden()).isEqualTo(DEFAULT_IS_ADMIN_HIDDEN);
        assertThat(testJoborder.getOpeningsAvailable()).isEqualTo(DEFAULT_OPENINGS_AVAILABLE);
        assertThat(testJoborder.getQuestionnaireId()).isEqualTo(DEFAULT_QUESTIONNAIRE_ID);
    }

    @Test
    @Transactional
    public void getAllJoborders() throws Exception {
        // Initialize the database
        joborderRepository.saveAndFlush(joborder);

        // Get all the joborders
        restJoborderMockMvc.perform(get("/api/joborders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(joborder.getId().intValue())))
                .andExpect(jsonPath("$.[*].joborderId").value(hasItem(DEFAULT_JOBORDER_ID)))
                .andExpect(jsonPath("$.[*].recruiter").value(hasItem(DEFAULT_RECRUITER)))
                .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID)))
                .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].clientJobId").value(hasItem(DEFAULT_CLIENT_JOB_ID.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
                .andExpect(jsonPath("$.[*].rateMax").value(hasItem(DEFAULT_RATE_MAX.toString())))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].isHot").value(hasItem(DEFAULT_IS_HOT)))
                .andExpect(jsonPath("$.[*].openings").value(hasItem(DEFAULT_OPENINGS)))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].publicV").value(hasItem(DEFAULT_PUBLIC_V)))
                .andExpect(jsonPath("$.[*].companyDepartmentId").value(hasItem(DEFAULT_COMPANY_DEPARTMENT_ID)))
                .andExpect(jsonPath("$.[*].isAdminHidden").value(hasItem(DEFAULT_IS_ADMIN_HIDDEN)))
                .andExpect(jsonPath("$.[*].openingsAvailable").value(hasItem(DEFAULT_OPENINGS_AVAILABLE)))
                .andExpect(jsonPath("$.[*].questionnaireId").value(hasItem(DEFAULT_QUESTIONNAIRE_ID)));
    }

    @Test
    @Transactional
    public void getJoborder() throws Exception {
        // Initialize the database
        joborderRepository.saveAndFlush(joborder);

        // Get the joborder
        restJoborderMockMvc.perform(get("/api/joborders/{id}", joborder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(joborder.getId().intValue()))
            .andExpect(jsonPath("$.joborderId").value(DEFAULT_JOBORDER_ID))
            .andExpect(jsonPath("$.recruiter").value(DEFAULT_RECRUITER))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.clientJobId").value(DEFAULT_CLIENT_JOB_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.rateMax").value(DEFAULT_RATE_MAX.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isHot").value(DEFAULT_IS_HOT))
            .andExpect(jsonPath("$.openings").value(DEFAULT_OPENINGS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.publicV").value(DEFAULT_PUBLIC_V))
            .andExpect(jsonPath("$.companyDepartmentId").value(DEFAULT_COMPANY_DEPARTMENT_ID))
            .andExpect(jsonPath("$.isAdminHidden").value(DEFAULT_IS_ADMIN_HIDDEN))
            .andExpect(jsonPath("$.openingsAvailable").value(DEFAULT_OPENINGS_AVAILABLE))
            .andExpect(jsonPath("$.questionnaireId").value(DEFAULT_QUESTIONNAIRE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingJoborder() throws Exception {
        // Get the joborder
        restJoborderMockMvc.perform(get("/api/joborders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJoborder() throws Exception {
        // Initialize the database
        joborderRepository.saveAndFlush(joborder);
        int databaseSizeBeforeUpdate = joborderRepository.findAll().size();

        // Update the joborder
        Joborder updatedJoborder = joborderRepository.findOne(joborder.getId());
        updatedJoborder
                .joborderId(UPDATED_JOBORDER_ID)
                .recruiter(UPDATED_RECRUITER)
                .contactId(UPDATED_CONTACT_ID)
                .companyId(UPDATED_COMPANY_ID)
                .enteredBy(UPDATED_ENTERED_BY)
                .owner(UPDATED_OWNER)
                .siteId(UPDATED_SITE_ID)
                .clientJobId(UPDATED_CLIENT_JOB_ID)
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .notes(UPDATED_NOTES)
                .type(UPDATED_TYPE)
                .duration(UPDATED_DURATION)
                .rateMax(UPDATED_RATE_MAX)
                .salary(UPDATED_SALARY)
                .status(UPDATED_STATUS)
                .isHot(UPDATED_IS_HOT)
                .openings(UPDATED_OPENINGS)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .startDate(UPDATED_START_DATE)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .publicV(UPDATED_PUBLIC_V)
                .companyDepartmentId(UPDATED_COMPANY_DEPARTMENT_ID)
                .isAdminHidden(UPDATED_IS_ADMIN_HIDDEN)
                .openingsAvailable(UPDATED_OPENINGS_AVAILABLE)
                .questionnaireId(UPDATED_QUESTIONNAIRE_ID);

        restJoborderMockMvc.perform(put("/api/joborders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedJoborder)))
                .andExpect(status().isOk());

        // Validate the Joborder in the database
        List<Joborder> joborders = joborderRepository.findAll();
        assertThat(joborders).hasSize(databaseSizeBeforeUpdate);
        Joborder testJoborder = joborders.get(joborders.size() - 1);
        assertThat(testJoborder.getJoborderId()).isEqualTo(UPDATED_JOBORDER_ID);
        assertThat(testJoborder.getRecruiter()).isEqualTo(UPDATED_RECRUITER);
        assertThat(testJoborder.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testJoborder.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testJoborder.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testJoborder.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testJoborder.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testJoborder.getClientJobId()).isEqualTo(UPDATED_CLIENT_JOB_ID);
        assertThat(testJoborder.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJoborder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJoborder.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testJoborder.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJoborder.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testJoborder.getRateMax()).isEqualTo(UPDATED_RATE_MAX);
        assertThat(testJoborder.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testJoborder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testJoborder.getIsHot()).isEqualTo(UPDATED_IS_HOT);
        assertThat(testJoborder.getOpenings()).isEqualTo(UPDATED_OPENINGS);
        assertThat(testJoborder.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testJoborder.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testJoborder.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJoborder.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testJoborder.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testJoborder.getPublicV()).isEqualTo(UPDATED_PUBLIC_V);
        assertThat(testJoborder.getCompanyDepartmentId()).isEqualTo(UPDATED_COMPANY_DEPARTMENT_ID);
        assertThat(testJoborder.getIsAdminHidden()).isEqualTo(UPDATED_IS_ADMIN_HIDDEN);
        assertThat(testJoborder.getOpeningsAvailable()).isEqualTo(UPDATED_OPENINGS_AVAILABLE);
        assertThat(testJoborder.getQuestionnaireId()).isEqualTo(UPDATED_QUESTIONNAIRE_ID);
    }

    @Test
    @Transactional
    public void deleteJoborder() throws Exception {
        // Initialize the database
        joborderRepository.saveAndFlush(joborder);
        int databaseSizeBeforeDelete = joborderRepository.findAll().size();

        // Get the joborder
        restJoborderMockMvc.perform(delete("/api/joborders/{id}", joborder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Joborder> joborders = joborderRepository.findAll();
        assertThat(joborders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
