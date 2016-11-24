package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Candidate;
import com.datasolution.hire.repository.CandidateRepository;

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
 * Test class for the CandidateResource REST controller.
 *
 * @see CandidateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CandidateResourceIntTest {

    private static final Integer DEFAULT_CANDIDATE_ID = 1;
    private static final Integer UPDATED_CANDIDATE_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_HOME = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_HOME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_CELL = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_CELL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_WORK = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_AVAILABLE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AVAILABLE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_AVAILABLE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_AVAILABLE);

    private static final Integer DEFAULT_CAN_RELOCATE = 1;
    private static final Integer UPDATED_CAN_RELOCATE = 2;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_KEY_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_EMPLOYER = "BBBBBBBBBB";

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

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMPORTED_ID = 1;
    private static final Integer UPDATED_IMPORTED_ID = 2;

    private static final Integer DEFAULT_IS_HOT = 1;
    private static final Integer UPDATED_IS_HOT = 2;

    private static final Integer DEFAULT_EEO_ETHNIC_TYPE_ID = 1;
    private static final Integer UPDATED_EEO_ETHNIC_TYPE_ID = 2;

    private static final Integer DEFAULT_EEO_VETERAN_TYPE_ID = 1;
    private static final Integer UPDATED_EEO_VETERAN_TYPE_ID = 2;

    private static final String DEFAULT_EEO_DISABILITY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EEO_DISABILITY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EEO_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_EEO_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_DESIRED_PAY = "AAAAAAAAAA";
    private static final String UPDATED_DESIRED_PAY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PAY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PAY = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    private static final Integer DEFAULT_IS_ADMIN_HIDDEN = 1;
    private static final Integer UPDATED_IS_ADMIN_HIDDEN = 2;

    private static final String DEFAULT_BEST_TIME_TO_CALL = "AAAAAAAAAA";
    private static final String UPDATED_BEST_TIME_TO_CALL = "BBBBBBBBBB";

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateResource candidateResource = new CandidateResource();
        ReflectionTestUtils.setField(candidateResource, "candidateRepository", candidateRepository);
        this.restCandidateMockMvc = MockMvcBuilders.standaloneSetup(candidateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
                .candidateId(DEFAULT_CANDIDATE_ID)
                .siteId(DEFAULT_SITE_ID)
                .lastName(DEFAULT_LAST_NAME)
                .firstName(DEFAULT_FIRST_NAME)
                .middleName(DEFAULT_MIDDLE_NAME)
                .phoneHome(DEFAULT_PHONE_HOME)
                .phoneCell(DEFAULT_PHONE_CELL)
                .phoneWork(DEFAULT_PHONE_WORK)
                .address(DEFAULT_ADDRESS)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .zip(DEFAULT_ZIP)
                .source(DEFAULT_SOURCE)
                .dateAvailable(DEFAULT_DATE_AVAILABLE)
                .canRelocate(DEFAULT_CAN_RELOCATE)
                .notes(DEFAULT_NOTES)
                .keySkills(DEFAULT_KEY_SKILLS)
                .currentEmployer(DEFAULT_CURRENT_EMPLOYER)
                .enteredBy(DEFAULT_ENTERED_BY)
                .owner(DEFAULT_OWNER)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .email1(DEFAULT_EMAIL_1)
                .email2(DEFAULT_EMAIL_2)
                .webSite(DEFAULT_WEB_SITE)
                .importedId(DEFAULT_IMPORTED_ID)
                .isHot(DEFAULT_IS_HOT)
                .eeoEthnicTypeId(DEFAULT_EEO_ETHNIC_TYPE_ID)
                .eeoVeteranTypeId(DEFAULT_EEO_VETERAN_TYPE_ID)
                .eeoDisabilityStatus(DEFAULT_EEO_DISABILITY_STATUS)
                .eeoGender(DEFAULT_EEO_GENDER)
                .desiredPay(DEFAULT_DESIRED_PAY)
                .currentPay(DEFAULT_CURRENT_PAY)
                .isActive(DEFAULT_IS_ACTIVE)
                .isAdminHidden(DEFAULT_IS_ADMIN_HIDDEN)
                .bestTimeToCall(DEFAULT_BEST_TIME_TO_CALL);
        return candidate;
    }

    @Before
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // Create the Candidate

        restCandidateMockMvc.perform(post("/api/candidates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidate)))
                .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidates.get(candidates.size() - 1);
        assertThat(testCandidate.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCandidate.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCandidate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCandidate.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCandidate.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCandidate.getPhoneHome()).isEqualTo(DEFAULT_PHONE_HOME);
        assertThat(testCandidate.getPhoneCell()).isEqualTo(DEFAULT_PHONE_CELL);
        assertThat(testCandidate.getPhoneWork()).isEqualTo(DEFAULT_PHONE_WORK);
        assertThat(testCandidate.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCandidate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCandidate.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCandidate.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testCandidate.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCandidate.getDateAvailable()).isEqualTo(DEFAULT_DATE_AVAILABLE);
        assertThat(testCandidate.getCanRelocate()).isEqualTo(DEFAULT_CAN_RELOCATE);
        assertThat(testCandidate.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCandidate.getKeySkills()).isEqualTo(DEFAULT_KEY_SKILLS);
        assertThat(testCandidate.getCurrentEmployer()).isEqualTo(DEFAULT_CURRENT_EMPLOYER);
        assertThat(testCandidate.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testCandidate.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testCandidate.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCandidate.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCandidate.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testCandidate.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testCandidate.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testCandidate.getImportedId()).isEqualTo(DEFAULT_IMPORTED_ID);
        assertThat(testCandidate.getIsHot()).isEqualTo(DEFAULT_IS_HOT);
        assertThat(testCandidate.getEeoEthnicTypeId()).isEqualTo(DEFAULT_EEO_ETHNIC_TYPE_ID);
        assertThat(testCandidate.getEeoVeteranTypeId()).isEqualTo(DEFAULT_EEO_VETERAN_TYPE_ID);
        assertThat(testCandidate.getEeoDisabilityStatus()).isEqualTo(DEFAULT_EEO_DISABILITY_STATUS);
        assertThat(testCandidate.getEeoGender()).isEqualTo(DEFAULT_EEO_GENDER);
        assertThat(testCandidate.getDesiredPay()).isEqualTo(DEFAULT_DESIRED_PAY);
        assertThat(testCandidate.getCurrentPay()).isEqualTo(DEFAULT_CURRENT_PAY);
        assertThat(testCandidate.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCandidate.getIsAdminHidden()).isEqualTo(DEFAULT_IS_ADMIN_HIDDEN);
        assertThat(testCandidate.getBestTimeToCall()).isEqualTo(DEFAULT_BEST_TIME_TO_CALL);
    }

    @Test
    @Transactional
    public void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidates
        restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
                .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].phoneHome").value(hasItem(DEFAULT_PHONE_HOME.toString())))
                .andExpect(jsonPath("$.[*].phoneCell").value(hasItem(DEFAULT_PHONE_CELL.toString())))
                .andExpect(jsonPath("$.[*].phoneWork").value(hasItem(DEFAULT_PHONE_WORK.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
                .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
                .andExpect(jsonPath("$.[*].dateAvailable").value(hasItem(DEFAULT_DATE_AVAILABLE_STR)))
                .andExpect(jsonPath("$.[*].canRelocate").value(hasItem(DEFAULT_CAN_RELOCATE)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].keySkills").value(hasItem(DEFAULT_KEY_SKILLS.toString())))
                .andExpect(jsonPath("$.[*].currentEmployer").value(hasItem(DEFAULT_CURRENT_EMPLOYER.toString())))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
                .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
                .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE.toString())))
                .andExpect(jsonPath("$.[*].importedId").value(hasItem(DEFAULT_IMPORTED_ID)))
                .andExpect(jsonPath("$.[*].isHot").value(hasItem(DEFAULT_IS_HOT)))
                .andExpect(jsonPath("$.[*].eeoEthnicTypeId").value(hasItem(DEFAULT_EEO_ETHNIC_TYPE_ID)))
                .andExpect(jsonPath("$.[*].eeoVeteranTypeId").value(hasItem(DEFAULT_EEO_VETERAN_TYPE_ID)))
                .andExpect(jsonPath("$.[*].eeoDisabilityStatus").value(hasItem(DEFAULT_EEO_DISABILITY_STATUS.toString())))
                .andExpect(jsonPath("$.[*].eeoGender").value(hasItem(DEFAULT_EEO_GENDER.toString())))
                .andExpect(jsonPath("$.[*].desiredPay").value(hasItem(DEFAULT_DESIRED_PAY.toString())))
                .andExpect(jsonPath("$.[*].currentPay").value(hasItem(DEFAULT_CURRENT_PAY.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
                .andExpect(jsonPath("$.[*].isAdminHidden").value(hasItem(DEFAULT_IS_ADMIN_HIDDEN)))
                .andExpect(jsonPath("$.[*].bestTimeToCall").value(hasItem(DEFAULT_BEST_TIME_TO_CALL.toString())));
    }

    @Test
    @Transactional
    public void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.phoneHome").value(DEFAULT_PHONE_HOME.toString()))
            .andExpect(jsonPath("$.phoneCell").value(DEFAULT_PHONE_CELL.toString()))
            .andExpect(jsonPath("$.phoneWork").value(DEFAULT_PHONE_WORK.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.dateAvailable").value(DEFAULT_DATE_AVAILABLE_STR))
            .andExpect(jsonPath("$.canRelocate").value(DEFAULT_CAN_RELOCATE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.keySkills").value(DEFAULT_KEY_SKILLS.toString()))
            .andExpect(jsonPath("$.currentEmployer").value(DEFAULT_CURRENT_EMPLOYER.toString()))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE.toString()))
            .andExpect(jsonPath("$.importedId").value(DEFAULT_IMPORTED_ID))
            .andExpect(jsonPath("$.isHot").value(DEFAULT_IS_HOT))
            .andExpect(jsonPath("$.eeoEthnicTypeId").value(DEFAULT_EEO_ETHNIC_TYPE_ID))
            .andExpect(jsonPath("$.eeoVeteranTypeId").value(DEFAULT_EEO_VETERAN_TYPE_ID))
            .andExpect(jsonPath("$.eeoDisabilityStatus").value(DEFAULT_EEO_DISABILITY_STATUS.toString()))
            .andExpect(jsonPath("$.eeoGender").value(DEFAULT_EEO_GENDER.toString()))
            .andExpect(jsonPath("$.desiredPay").value(DEFAULT_DESIRED_PAY.toString()))
            .andExpect(jsonPath("$.currentPay").value(DEFAULT_CURRENT_PAY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.isAdminHidden").value(DEFAULT_IS_ADMIN_HIDDEN))
            .andExpect(jsonPath("$.bestTimeToCall").value(DEFAULT_BEST_TIME_TO_CALL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findOne(candidate.getId());
        updatedCandidate
                .candidateId(UPDATED_CANDIDATE_ID)
                .siteId(UPDATED_SITE_ID)
                .lastName(UPDATED_LAST_NAME)
                .firstName(UPDATED_FIRST_NAME)
                .middleName(UPDATED_MIDDLE_NAME)
                .phoneHome(UPDATED_PHONE_HOME)
                .phoneCell(UPDATED_PHONE_CELL)
                .phoneWork(UPDATED_PHONE_WORK)
                .address(UPDATED_ADDRESS)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .zip(UPDATED_ZIP)
                .source(UPDATED_SOURCE)
                .dateAvailable(UPDATED_DATE_AVAILABLE)
                .canRelocate(UPDATED_CAN_RELOCATE)
                .notes(UPDATED_NOTES)
                .keySkills(UPDATED_KEY_SKILLS)
                .currentEmployer(UPDATED_CURRENT_EMPLOYER)
                .enteredBy(UPDATED_ENTERED_BY)
                .owner(UPDATED_OWNER)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .email1(UPDATED_EMAIL_1)
                .email2(UPDATED_EMAIL_2)
                .webSite(UPDATED_WEB_SITE)
                .importedId(UPDATED_IMPORTED_ID)
                .isHot(UPDATED_IS_HOT)
                .eeoEthnicTypeId(UPDATED_EEO_ETHNIC_TYPE_ID)
                .eeoVeteranTypeId(UPDATED_EEO_VETERAN_TYPE_ID)
                .eeoDisabilityStatus(UPDATED_EEO_DISABILITY_STATUS)
                .eeoGender(UPDATED_EEO_GENDER)
                .desiredPay(UPDATED_DESIRED_PAY)
                .currentPay(UPDATED_CURRENT_PAY)
                .isActive(UPDATED_IS_ACTIVE)
                .isAdminHidden(UPDATED_IS_ADMIN_HIDDEN)
                .bestTimeToCall(UPDATED_BEST_TIME_TO_CALL);

        restCandidateMockMvc.perform(put("/api/candidates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCandidate)))
                .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidates.get(candidates.size() - 1);
        assertThat(testCandidate.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidate.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCandidate.getPhoneHome()).isEqualTo(UPDATED_PHONE_HOME);
        assertThat(testCandidate.getPhoneCell()).isEqualTo(UPDATED_PHONE_CELL);
        assertThat(testCandidate.getPhoneWork()).isEqualTo(UPDATED_PHONE_WORK);
        assertThat(testCandidate.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCandidate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCandidate.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidate.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testCandidate.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCandidate.getDateAvailable()).isEqualTo(UPDATED_DATE_AVAILABLE);
        assertThat(testCandidate.getCanRelocate()).isEqualTo(UPDATED_CAN_RELOCATE);
        assertThat(testCandidate.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCandidate.getKeySkills()).isEqualTo(UPDATED_KEY_SKILLS);
        assertThat(testCandidate.getCurrentEmployer()).isEqualTo(UPDATED_CURRENT_EMPLOYER);
        assertThat(testCandidate.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testCandidate.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testCandidate.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCandidate.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCandidate.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testCandidate.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testCandidate.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testCandidate.getImportedId()).isEqualTo(UPDATED_IMPORTED_ID);
        assertThat(testCandidate.getIsHot()).isEqualTo(UPDATED_IS_HOT);
        assertThat(testCandidate.getEeoEthnicTypeId()).isEqualTo(UPDATED_EEO_ETHNIC_TYPE_ID);
        assertThat(testCandidate.getEeoVeteranTypeId()).isEqualTo(UPDATED_EEO_VETERAN_TYPE_ID);
        assertThat(testCandidate.getEeoDisabilityStatus()).isEqualTo(UPDATED_EEO_DISABILITY_STATUS);
        assertThat(testCandidate.getEeoGender()).isEqualTo(UPDATED_EEO_GENDER);
        assertThat(testCandidate.getDesiredPay()).isEqualTo(UPDATED_DESIRED_PAY);
        assertThat(testCandidate.getCurrentPay()).isEqualTo(UPDATED_CURRENT_PAY);
        assertThat(testCandidate.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCandidate.getIsAdminHidden()).isEqualTo(UPDATED_IS_ADMIN_HIDDEN);
        assertThat(testCandidate.getBestTimeToCall()).isEqualTo(UPDATED_BEST_TIME_TO_CALL);
    }

    @Test
    @Transactional
    public void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);
        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Get the candidate
        restCandidateMockMvc.perform(delete("/api/candidates/{id}", candidate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
