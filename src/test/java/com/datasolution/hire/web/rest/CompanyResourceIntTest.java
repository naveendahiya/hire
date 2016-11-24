package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Company;
import com.datasolution.hire.repository.CompanyRepository;

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
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CompanyResourceIntTest {

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_BILLING_CONTACT = 1;
    private static final Integer UPDATED_BILLING_CONTACT = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_TECHNOLOGIES = "AAAAAAAAAA";
    private static final String UPDATED_KEY_TECHNOLOGIES = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENTERED_BY = 1;
    private static final Integer UPDATED_ENTERED_BY = 2;

    private static final Integer DEFAULT_OWNER = 1;
    private static final Integer UPDATED_OWNER = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    private static final Integer DEFAULT_IS_HOT = 1;
    private static final Integer UPDATED_IS_HOT = 2;

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMPORTED_ID = 1;
    private static final Integer UPDATED_IMPORTED_ID = 2;

    private static final Integer DEFAULT_DEFAULT_COMPANY = 1;
    private static final Integer UPDATED_DEFAULT_COMPANY = 2;

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCompanyMockMvc;

    private Company company;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyResource companyResource = new CompanyResource();
        ReflectionTestUtils.setField(companyResource, "companyRepository", companyRepository);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
                .companyId(DEFAULT_COMPANY_ID)
                .siteId(DEFAULT_SITE_ID)
                .billingContact(DEFAULT_BILLING_CONTACT)
                .name(DEFAULT_NAME)
                .address(DEFAULT_ADDRESS)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .zip(DEFAULT_ZIP)
                .phone1(DEFAULT_PHONE_1)
                .phone2(DEFAULT_PHONE_2)
                .url(DEFAULT_URL)
                .keyTechnologies(DEFAULT_KEY_TECHNOLOGIES)
                .notes(DEFAULT_NOTES)
                .enteredBy(DEFAULT_ENTERED_BY)
                .owner(DEFAULT_OWNER)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .isHot(DEFAULT_IS_HOT)
                .faxNumber(DEFAULT_FAX_NUMBER)
                .importedId(DEFAULT_IMPORTED_ID)
                .defaultCompany(DEFAULT_DEFAULT_COMPANY);
        return company;
    }

    @Before
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company

        restCompanyMockMvc.perform(post("/api/companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companies.get(companies.size() - 1);
        assertThat(testCompany.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompany.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCompany.getBillingContact()).isEqualTo(DEFAULT_BILLING_CONTACT);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCompany.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCompany.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCompany.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testCompany.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testCompany.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testCompany.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCompany.getKeyTechnologies()).isEqualTo(DEFAULT_KEY_TECHNOLOGIES);
        assertThat(testCompany.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCompany.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testCompany.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testCompany.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCompany.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCompany.getIsHot()).isEqualTo(DEFAULT_IS_HOT);
        assertThat(testCompany.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testCompany.getImportedId()).isEqualTo(DEFAULT_IMPORTED_ID);
        assertThat(testCompany.getDefaultCompany()).isEqualTo(DEFAULT_DEFAULT_COMPANY);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companies
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].billingContact").value(hasItem(DEFAULT_BILLING_CONTACT)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
                .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
                .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].keyTechnologies").value(hasItem(DEFAULT_KEY_TECHNOLOGIES.toString())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].isHot").value(hasItem(DEFAULT_IS_HOT)))
                .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].importedId").value(hasItem(DEFAULT_IMPORTED_ID)))
                .andExpect(jsonPath("$.[*].defaultCompany").value(hasItem(DEFAULT_DEFAULT_COMPANY)));
    }

    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.billingContact").value(DEFAULT_BILLING_CONTACT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.keyTechnologies").value(DEFAULT_KEY_TECHNOLOGIES.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.isHot").value(DEFAULT_IS_HOT))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.toString()))
            .andExpect(jsonPath("$.importedId").value(DEFAULT_IMPORTED_ID))
            .andExpect(jsonPath("$.defaultCompany").value(DEFAULT_DEFAULT_COMPANY));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findOne(company.getId());
        updatedCompany
                .companyId(UPDATED_COMPANY_ID)
                .siteId(UPDATED_SITE_ID)
                .billingContact(UPDATED_BILLING_CONTACT)
                .name(UPDATED_NAME)
                .address(UPDATED_ADDRESS)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .zip(UPDATED_ZIP)
                .phone1(UPDATED_PHONE_1)
                .phone2(UPDATED_PHONE_2)
                .url(UPDATED_URL)
                .keyTechnologies(UPDATED_KEY_TECHNOLOGIES)
                .notes(UPDATED_NOTES)
                .enteredBy(UPDATED_ENTERED_BY)
                .owner(UPDATED_OWNER)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .isHot(UPDATED_IS_HOT)
                .faxNumber(UPDATED_FAX_NUMBER)
                .importedId(UPDATED_IMPORTED_ID)
                .defaultCompany(UPDATED_DEFAULT_COMPANY);

        restCompanyMockMvc.perform(put("/api/companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
                .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companies.get(companies.size() - 1);
        assertThat(testCompany.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompany.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCompany.getBillingContact()).isEqualTo(UPDATED_BILLING_CONTACT);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompany.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCompany.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompany.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testCompany.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testCompany.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testCompany.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCompany.getKeyTechnologies()).isEqualTo(UPDATED_KEY_TECHNOLOGIES);
        assertThat(testCompany.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCompany.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testCompany.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testCompany.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCompany.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCompany.getIsHot()).isEqualTo(UPDATED_IS_HOT);
        assertThat(testCompany.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCompany.getImportedId()).isEqualTo(UPDATED_IMPORTED_ID);
        assertThat(testCompany.getDefaultCompany()).isEqualTo(UPDATED_DEFAULT_COMPANY);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Get the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
