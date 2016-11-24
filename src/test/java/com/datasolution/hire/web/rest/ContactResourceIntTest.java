package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Contact;
import com.datasolution.hire.repository.ContactRepository;

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
 * Test class for the ContactResource REST controller.
 *
 * @see ContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ContactResourceIntTest {

    private static final Integer DEFAULT_CONTACT_ID = 1;
    private static final Integer UPDATED_CONTACT_ID = 2;

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_WORK = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_CELL = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_CELL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_OTHER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_HOT = 1;
    private static final Integer UPDATED_IS_HOT = 2;

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

    private static final Integer DEFAULT_LEFT_COMPANY = 1;
    private static final Integer UPDATED_LEFT_COMPANY = 2;

    private static final Integer DEFAULT_IMPORTED_ID = 1;
    private static final Integer UPDATED_IMPORTED_ID = 2;

    private static final Integer DEFAULT_COMPANY_DEPARTMENT_ID = 1;
    private static final Integer UPDATED_COMPANY_DEPARTMENT_ID = 2;

    private static final Integer DEFAULT_REPORTS_TO = 1;
    private static final Integer UPDATED_REPORTS_TO = 2;

    @Inject
    private ContactRepository contactRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restContactMockMvc;

    private Contact contact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactResource contactResource = new ContactResource();
        ReflectionTestUtils.setField(contactResource, "contactRepository", contactRepository);
        this.restContactMockMvc = MockMvcBuilders.standaloneSetup(contactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
                .contactId(DEFAULT_CONTACT_ID)
                .companyId(DEFAULT_COMPANY_ID)
                .siteId(DEFAULT_SITE_ID)
                .lastName(DEFAULT_LAST_NAME)
                .firstName(DEFAULT_FIRST_NAME)
                .title(DEFAULT_TITLE)
                .email1(DEFAULT_EMAIL_1)
                .email2(DEFAULT_EMAIL_2)
                .phoneWork(DEFAULT_PHONE_WORK)
                .phoneCell(DEFAULT_PHONE_CELL)
                .phoneOther(DEFAULT_PHONE_OTHER)
                .address(DEFAULT_ADDRESS)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .zip(DEFAULT_ZIP)
                .isHot(DEFAULT_IS_HOT)
                .notes(DEFAULT_NOTES)
                .enteredBy(DEFAULT_ENTERED_BY)
                .owner(DEFAULT_OWNER)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .leftCompany(DEFAULT_LEFT_COMPANY)
                .importedId(DEFAULT_IMPORTED_ID)
                .companyDepartmentId(DEFAULT_COMPANY_DEPARTMENT_ID)
                .reportsTo(DEFAULT_REPORTS_TO);
        return contact;
    }

    @Before
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact

        restContactMockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
                .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contacts.get(contacts.size() - 1);
        assertThat(testContact.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testContact.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testContact.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContact.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testContact.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testContact.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testContact.getPhoneWork()).isEqualTo(DEFAULT_PHONE_WORK);
        assertThat(testContact.getPhoneCell()).isEqualTo(DEFAULT_PHONE_CELL);
        assertThat(testContact.getPhoneOther()).isEqualTo(DEFAULT_PHONE_OTHER);
        assertThat(testContact.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testContact.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContact.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testContact.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testContact.getIsHot()).isEqualTo(DEFAULT_IS_HOT);
        assertThat(testContact.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testContact.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testContact.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testContact.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testContact.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testContact.getLeftCompany()).isEqualTo(DEFAULT_LEFT_COMPANY);
        assertThat(testContact.getImportedId()).isEqualTo(DEFAULT_IMPORTED_ID);
        assertThat(testContact.getCompanyDepartmentId()).isEqualTo(DEFAULT_COMPANY_DEPARTMENT_ID);
        assertThat(testContact.getReportsTo()).isEqualTo(DEFAULT_REPORTS_TO);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contacts
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
                .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID)))
                .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
                .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
                .andExpect(jsonPath("$.[*].phoneWork").value(hasItem(DEFAULT_PHONE_WORK.toString())))
                .andExpect(jsonPath("$.[*].phoneCell").value(hasItem(DEFAULT_PHONE_CELL.toString())))
                .andExpect(jsonPath("$.[*].phoneOther").value(hasItem(DEFAULT_PHONE_OTHER.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
                .andExpect(jsonPath("$.[*].isHot").value(hasItem(DEFAULT_IS_HOT)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].leftCompany").value(hasItem(DEFAULT_LEFT_COMPANY)))
                .andExpect(jsonPath("$.[*].importedId").value(hasItem(DEFAULT_IMPORTED_ID)))
                .andExpect(jsonPath("$.[*].companyDepartmentId").value(hasItem(DEFAULT_COMPANY_DEPARTMENT_ID)))
                .andExpect(jsonPath("$.[*].reportsTo").value(hasItem(DEFAULT_REPORTS_TO)));
    }

    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.phoneWork").value(DEFAULT_PHONE_WORK.toString()))
            .andExpect(jsonPath("$.phoneCell").value(DEFAULT_PHONE_CELL.toString()))
            .andExpect(jsonPath("$.phoneOther").value(DEFAULT_PHONE_OTHER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.isHot").value(DEFAULT_IS_HOT))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.leftCompany").value(DEFAULT_LEFT_COMPANY))
            .andExpect(jsonPath("$.importedId").value(DEFAULT_IMPORTED_ID))
            .andExpect(jsonPath("$.companyDepartmentId").value(DEFAULT_COMPANY_DEPARTMENT_ID))
            .andExpect(jsonPath("$.reportsTo").value(DEFAULT_REPORTS_TO));
    }

    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findOne(contact.getId());
        updatedContact
                .contactId(UPDATED_CONTACT_ID)
                .companyId(UPDATED_COMPANY_ID)
                .siteId(UPDATED_SITE_ID)
                .lastName(UPDATED_LAST_NAME)
                .firstName(UPDATED_FIRST_NAME)
                .title(UPDATED_TITLE)
                .email1(UPDATED_EMAIL_1)
                .email2(UPDATED_EMAIL_2)
                .phoneWork(UPDATED_PHONE_WORK)
                .phoneCell(UPDATED_PHONE_CELL)
                .phoneOther(UPDATED_PHONE_OTHER)
                .address(UPDATED_ADDRESS)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .zip(UPDATED_ZIP)
                .isHot(UPDATED_IS_HOT)
                .notes(UPDATED_NOTES)
                .enteredBy(UPDATED_ENTERED_BY)
                .owner(UPDATED_OWNER)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .leftCompany(UPDATED_LEFT_COMPANY)
                .importedId(UPDATED_IMPORTED_ID)
                .companyDepartmentId(UPDATED_COMPANY_DEPARTMENT_ID)
                .reportsTo(UPDATED_REPORTS_TO);

        restContactMockMvc.perform(put("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedContact)))
                .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contacts.get(contacts.size() - 1);
        assertThat(testContact.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testContact.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testContact.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContact.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testContact.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testContact.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testContact.getPhoneWork()).isEqualTo(UPDATED_PHONE_WORK);
        assertThat(testContact.getPhoneCell()).isEqualTo(UPDATED_PHONE_CELL);
        assertThat(testContact.getPhoneOther()).isEqualTo(UPDATED_PHONE_OTHER);
        assertThat(testContact.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContact.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContact.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testContact.getIsHot()).isEqualTo(UPDATED_IS_HOT);
        assertThat(testContact.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testContact.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testContact.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testContact.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testContact.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testContact.getLeftCompany()).isEqualTo(UPDATED_LEFT_COMPANY);
        assertThat(testContact.getImportedId()).isEqualTo(UPDATED_IMPORTED_ID);
        assertThat(testContact.getCompanyDepartmentId()).isEqualTo(UPDATED_COMPANY_DEPARTMENT_ID);
        assertThat(testContact.getReportsTo()).isEqualTo(UPDATED_REPORTS_TO);
    }

    @Test
    @Transactional
    public void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Get the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contact.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
