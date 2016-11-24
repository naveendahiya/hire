package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.ActivityType;
import com.datasolution.hire.repository.ActivityTypeRepository;

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
 * Test class for the ActivityTypeResource REST controller.
 *
 * @see ActivityTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ActivityTypeResourceIntTest {

    private static final Integer DEFAULT_ACTIVITY_TYPE_ID = 1;
    private static final Integer UPDATED_ACTIVITY_TYPE_ID = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private ActivityTypeRepository activityTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restActivityTypeMockMvc;

    private ActivityType activityType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivityTypeResource activityTypeResource = new ActivityTypeResource();
        ReflectionTestUtils.setField(activityTypeResource, "activityTypeRepository", activityTypeRepository);
        this.restActivityTypeMockMvc = MockMvcBuilders.standaloneSetup(activityTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityType createEntity(EntityManager em) {
        ActivityType activityType = new ActivityType()
                .activityTypeId(DEFAULT_ACTIVITY_TYPE_ID)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION);
        return activityType;
    }

    @Before
    public void initTest() {
        activityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityType() throws Exception {
        int databaseSizeBeforeCreate = activityTypeRepository.findAll().size();

        // Create the ActivityType

        restActivityTypeMockMvc.perform(post("/api/activity-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activityType)))
                .andExpect(status().isCreated());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypes = activityTypeRepository.findAll();
        assertThat(activityTypes).hasSize(databaseSizeBeforeCreate + 1);
        ActivityType testActivityType = activityTypes.get(activityTypes.size() - 1);
        assertThat(testActivityType.getActivityTypeId()).isEqualTo(DEFAULT_ACTIVITY_TYPE_ID);
        assertThat(testActivityType.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllActivityTypes() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypes
        restActivityTypeMockMvc.perform(get("/api/activity-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(activityType.getId().intValue())))
                .andExpect(jsonPath("$.[*].activityTypeId").value(hasItem(DEFAULT_ACTIVITY_TYPE_ID)))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get the activityType
        restActivityTypeMockMvc.perform(get("/api/activity-types/{id}", activityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityType.getId().intValue()))
            .andExpect(jsonPath("$.activityTypeId").value(DEFAULT_ACTIVITY_TYPE_ID))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityType() throws Exception {
        // Get the activityType
        restActivityTypeMockMvc.perform(get("/api/activity-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType
        ActivityType updatedActivityType = activityTypeRepository.findOne(activityType.getId());
        updatedActivityType
                .activityTypeId(UPDATED_ACTIVITY_TYPE_ID)
                .shortDescription(UPDATED_SHORT_DESCRIPTION);

        restActivityTypeMockMvc.perform(put("/api/activity-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedActivityType)))
                .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypes = activityTypeRepository.findAll();
        assertThat(activityTypes).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypes.get(activityTypes.size() - 1);
        assertThat(testActivityType.getActivityTypeId()).isEqualTo(UPDATED_ACTIVITY_TYPE_ID);
        assertThat(testActivityType.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);
        int databaseSizeBeforeDelete = activityTypeRepository.findAll().size();

        // Get the activityType
        restActivityTypeMockMvc.perform(delete("/api/activity-types/{id}", activityType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ActivityType> activityTypes = activityTypeRepository.findAll();
        assertThat(activityTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
