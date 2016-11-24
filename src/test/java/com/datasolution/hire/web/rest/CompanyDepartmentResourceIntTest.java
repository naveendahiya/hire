package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CompanyDepartment;
import com.datasolution.hire.repository.CompanyDepartmentRepository;

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
 * Test class for the CompanyDepartmentResource REST controller.
 *
 * @see CompanyDepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CompanyDepartmentResourceIntTest {

    private static final Integer DEFAULT_COMPANY_DEPARTMENT_ID = 1;
    private static final Integer UPDATED_COMPANY_DEPARTMENT_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    @Inject
    private CompanyDepartmentRepository companyDepartmentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCompanyDepartmentMockMvc;

    private CompanyDepartment companyDepartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyDepartmentResource companyDepartmentResource = new CompanyDepartmentResource();
        ReflectionTestUtils.setField(companyDepartmentResource, "companyDepartmentRepository", companyDepartmentRepository);
        this.restCompanyDepartmentMockMvc = MockMvcBuilders.standaloneSetup(companyDepartmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyDepartment createEntity(EntityManager em) {
        CompanyDepartment companyDepartment = new CompanyDepartment()
                .companyDepartmentId(DEFAULT_COMPANY_DEPARTMENT_ID)
                .name(DEFAULT_NAME)
                .companyId(DEFAULT_COMPANY_ID)
                .siteId(DEFAULT_SITE_ID)
                .dateCreated(DEFAULT_DATE_CREATED)
                .createdBy(DEFAULT_CREATED_BY);
        return companyDepartment;
    }

    @Before
    public void initTest() {
        companyDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDepartment() throws Exception {
        int databaseSizeBeforeCreate = companyDepartmentRepository.findAll().size();

        // Create the CompanyDepartment

        restCompanyDepartmentMockMvc.perform(post("/api/company-departments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyDepartment)))
                .andExpect(status().isCreated());

        // Validate the CompanyDepartment in the database
        List<CompanyDepartment> companyDepartments = companyDepartmentRepository.findAll();
        assertThat(companyDepartments).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDepartment testCompanyDepartment = companyDepartments.get(companyDepartments.size() - 1);
        assertThat(testCompanyDepartment.getCompanyDepartmentId()).isEqualTo(DEFAULT_COMPANY_DEPARTMENT_ID);
        assertThat(testCompanyDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyDepartment.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompanyDepartment.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCompanyDepartment.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCompanyDepartment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCompanyDepartments() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);

        // Get all the companyDepartments
        restCompanyDepartmentMockMvc.perform(get("/api/company-departments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(companyDepartment.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyDepartmentId").value(hasItem(DEFAULT_COMPANY_DEPARTMENT_ID)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    public void getCompanyDepartment() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);

        // Get the companyDepartment
        restCompanyDepartmentMockMvc.perform(get("/api/company-departments/{id}", companyDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDepartment.getId().intValue()))
            .andExpect(jsonPath("$.companyDepartmentId").value(DEFAULT_COMPANY_DEPARTMENT_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDepartment() throws Exception {
        // Get the companyDepartment
        restCompanyDepartmentMockMvc.perform(get("/api/company-departments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDepartment() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);
        int databaseSizeBeforeUpdate = companyDepartmentRepository.findAll().size();

        // Update the companyDepartment
        CompanyDepartment updatedCompanyDepartment = companyDepartmentRepository.findOne(companyDepartment.getId());
        updatedCompanyDepartment
                .companyDepartmentId(UPDATED_COMPANY_DEPARTMENT_ID)
                .name(UPDATED_NAME)
                .companyId(UPDATED_COMPANY_ID)
                .siteId(UPDATED_SITE_ID)
                .dateCreated(UPDATED_DATE_CREATED)
                .createdBy(UPDATED_CREATED_BY);

        restCompanyDepartmentMockMvc.perform(put("/api/company-departments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCompanyDepartment)))
                .andExpect(status().isOk());

        // Validate the CompanyDepartment in the database
        List<CompanyDepartment> companyDepartments = companyDepartmentRepository.findAll();
        assertThat(companyDepartments).hasSize(databaseSizeBeforeUpdate);
        CompanyDepartment testCompanyDepartment = companyDepartments.get(companyDepartments.size() - 1);
        assertThat(testCompanyDepartment.getCompanyDepartmentId()).isEqualTo(UPDATED_COMPANY_DEPARTMENT_ID);
        assertThat(testCompanyDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyDepartment.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompanyDepartment.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCompanyDepartment.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCompanyDepartment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void deleteCompanyDepartment() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);
        int databaseSizeBeforeDelete = companyDepartmentRepository.findAll().size();

        // Get the companyDepartment
        restCompanyDepartmentMockMvc.perform(delete("/api/company-departments/{id}", companyDepartment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDepartment> companyDepartments = companyDepartmentRepository.findAll();
        assertThat(companyDepartments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
