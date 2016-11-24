package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CalendarEventType;
import com.datasolution.hire.repository.CalendarEventTypeRepository;

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
 * Test class for the CalendarEventTypeResource REST controller.
 *
 * @see CalendarEventTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CalendarEventTypeResourceIntTest {

    private static final Integer DEFAULT_CALENDAR_EVENT_TYPE_ID = 1;
    private static final Integer UPDATED_CALENDAR_EVENT_TYPE_ID = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_ICON_IMAGE = "BBBBBBBBBB";

    @Inject
    private CalendarEventTypeRepository calendarEventTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCalendarEventTypeMockMvc;

    private CalendarEventType calendarEventType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalendarEventTypeResource calendarEventTypeResource = new CalendarEventTypeResource();
        ReflectionTestUtils.setField(calendarEventTypeResource, "calendarEventTypeRepository", calendarEventTypeRepository);
        this.restCalendarEventTypeMockMvc = MockMvcBuilders.standaloneSetup(calendarEventTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarEventType createEntity(EntityManager em) {
        CalendarEventType calendarEventType = new CalendarEventType()
                .calendarEventTypeId(DEFAULT_CALENDAR_EVENT_TYPE_ID)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .iconImage(DEFAULT_ICON_IMAGE);
        return calendarEventType;
    }

    @Before
    public void initTest() {
        calendarEventType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarEventType() throws Exception {
        int databaseSizeBeforeCreate = calendarEventTypeRepository.findAll().size();

        // Create the CalendarEventType

        restCalendarEventTypeMockMvc.perform(post("/api/calendar-event-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calendarEventType)))
                .andExpect(status().isCreated());

        // Validate the CalendarEventType in the database
        List<CalendarEventType> calendarEventTypes = calendarEventTypeRepository.findAll();
        assertThat(calendarEventTypes).hasSize(databaseSizeBeforeCreate + 1);
        CalendarEventType testCalendarEventType = calendarEventTypes.get(calendarEventTypes.size() - 1);
        assertThat(testCalendarEventType.getCalendarEventTypeId()).isEqualTo(DEFAULT_CALENDAR_EVENT_TYPE_ID);
        assertThat(testCalendarEventType.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testCalendarEventType.getIconImage()).isEqualTo(DEFAULT_ICON_IMAGE);
    }

    @Test
    @Transactional
    public void getAllCalendarEventTypes() throws Exception {
        // Initialize the database
        calendarEventTypeRepository.saveAndFlush(calendarEventType);

        // Get all the calendarEventTypes
        restCalendarEventTypeMockMvc.perform(get("/api/calendar-event-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(calendarEventType.getId().intValue())))
                .andExpect(jsonPath("$.[*].calendarEventTypeId").value(hasItem(DEFAULT_CALENDAR_EVENT_TYPE_ID)))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].iconImage").value(hasItem(DEFAULT_ICON_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getCalendarEventType() throws Exception {
        // Initialize the database
        calendarEventTypeRepository.saveAndFlush(calendarEventType);

        // Get the calendarEventType
        restCalendarEventTypeMockMvc.perform(get("/api/calendar-event-types/{id}", calendarEventType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendarEventType.getId().intValue()))
            .andExpect(jsonPath("$.calendarEventTypeId").value(DEFAULT_CALENDAR_EVENT_TYPE_ID))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.iconImage").value(DEFAULT_ICON_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalendarEventType() throws Exception {
        // Get the calendarEventType
        restCalendarEventTypeMockMvc.perform(get("/api/calendar-event-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarEventType() throws Exception {
        // Initialize the database
        calendarEventTypeRepository.saveAndFlush(calendarEventType);
        int databaseSizeBeforeUpdate = calendarEventTypeRepository.findAll().size();

        // Update the calendarEventType
        CalendarEventType updatedCalendarEventType = calendarEventTypeRepository.findOne(calendarEventType.getId());
        updatedCalendarEventType
                .calendarEventTypeId(UPDATED_CALENDAR_EVENT_TYPE_ID)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .iconImage(UPDATED_ICON_IMAGE);

        restCalendarEventTypeMockMvc.perform(put("/api/calendar-event-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCalendarEventType)))
                .andExpect(status().isOk());

        // Validate the CalendarEventType in the database
        List<CalendarEventType> calendarEventTypes = calendarEventTypeRepository.findAll();
        assertThat(calendarEventTypes).hasSize(databaseSizeBeforeUpdate);
        CalendarEventType testCalendarEventType = calendarEventTypes.get(calendarEventTypes.size() - 1);
        assertThat(testCalendarEventType.getCalendarEventTypeId()).isEqualTo(UPDATED_CALENDAR_EVENT_TYPE_ID);
        assertThat(testCalendarEventType.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testCalendarEventType.getIconImage()).isEqualTo(UPDATED_ICON_IMAGE);
    }

    @Test
    @Transactional
    public void deleteCalendarEventType() throws Exception {
        // Initialize the database
        calendarEventTypeRepository.saveAndFlush(calendarEventType);
        int databaseSizeBeforeDelete = calendarEventTypeRepository.findAll().size();

        // Get the calendarEventType
        restCalendarEventTypeMockMvc.perform(delete("/api/calendar-event-types/{id}", calendarEventType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CalendarEventType> calendarEventTypes = calendarEventTypeRepository.findAll();
        assertThat(calendarEventTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
