package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Activity;
import com.datasolution.hire.repository.ActivityRepository;

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
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ActivityResourceIntTest {

    private static final Integer DEFAULT_ACTIVITY_ID = 1;
    private static final Integer UPDATED_ACTIVITY_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_JOBORDER_ID = 1;
    private static final Integer UPDATED_JOBORDER_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_ENTERED_BY = 1;
    private static final Integer UPDATED_ENTERED_BY = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    @Inject
    private ActivityRepository activityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivityResource activityResource = new ActivityResource();
        ReflectionTestUtils.setField(activityResource, "activityRepository", activityRepository);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
                .activityId(DEFAULT_ACTIVITY_ID)
                .dataItemId(DEFAULT_DATA_ITEM_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .joborderId(DEFAULT_JOBORDER_ID)
                .siteId(DEFAULT_SITE_ID)
                .enteredBy(DEFAULT_ENTERED_BY)
                .dateCreated(DEFAULT_DATE_CREATED)
                .type(DEFAULT_TYPE)
                .notes(DEFAULT_NOTES)
                .dateModified(DEFAULT_DATE_MODIFIED);
        return activity;
    }

    @Before
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity

        restActivityMockMvc.perform(post("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activity)))
                .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activities = activityRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activities.get(activities.size() - 1);
        assertThat(testActivity.getActivityId()).isEqualTo(DEFAULT_ACTIVITY_ID);
        assertThat(testActivity.getDataItemId()).isEqualTo(DEFAULT_DATA_ITEM_ID);
        assertThat(testActivity.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testActivity.getJoborderId()).isEqualTo(DEFAULT_JOBORDER_ID);
        assertThat(testActivity.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testActivity.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testActivity.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testActivity.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testActivity.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testActivity.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activities
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
                .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID)))
                .andExpect(jsonPath("$.[*].dataItemId").value(hasItem(DEFAULT_DATA_ITEM_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].joborderId").value(hasItem(DEFAULT_JOBORDER_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)));
    }

    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.activityId").value(DEFAULT_ACTIVITY_ID))
            .andExpect(jsonPath("$.dataItemId").value(DEFAULT_DATA_ITEM_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.joborderId").value(DEFAULT_JOBORDER_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activity.getId());
        updatedActivity
                .activityId(UPDATED_ACTIVITY_ID)
                .dataItemId(UPDATED_DATA_ITEM_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .joborderId(UPDATED_JOBORDER_ID)
                .siteId(UPDATED_SITE_ID)
                .enteredBy(UPDATED_ENTERED_BY)
                .dateCreated(UPDATED_DATE_CREATED)
                .type(UPDATED_TYPE)
                .notes(UPDATED_NOTES)
                .dateModified(UPDATED_DATE_MODIFIED);

        restActivityMockMvc.perform(put("/api/activities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedActivity)))
                .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activities = activityRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activities.get(activities.size() - 1);
        assertThat(testActivity.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testActivity.getDataItemId()).isEqualTo(UPDATED_DATA_ITEM_ID);
        assertThat(testActivity.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testActivity.getJoborderId()).isEqualTo(UPDATED_JOBORDER_ID);
        assertThat(testActivity.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testActivity.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testActivity.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testActivity.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testActivity.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Activity> activities = activityRepository.findAll();
        assertThat(activities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
