package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.CalendarEvent;
import com.datasolution.hire.repository.CalendarEventRepository;

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
 * Test class for the CalendarEventResource REST controller.
 *
 * @see CalendarEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class CalendarEventResourceIntTest {

    private static final Integer DEFAULT_CALENDAR_EVENT_ID = 1;
    private static final Integer UPDATED_CALENDAR_EVENT_ID = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALL_DAY = 1;
    private static final Integer UPDATED_ALL_DAY = 2;

    private static final Integer DEFAULT_DATA_ITEM_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_ENTERED_BY = 1;
    private static final Integer UPDATED_ENTERED_BY = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_JOBORDER_ID = 1;
    private static final Integer UPDATED_JOBORDER_ID = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_REMINDER_ENABLED = 1;
    private static final Integer UPDATED_REMINDER_ENABLED = 2;

    private static final String DEFAULT_REMINDER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_REMINDER_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_REMINDER_TIME = 1;
    private static final Integer UPDATED_REMINDER_TIME = 2;

    private static final Integer DEFAULT_PUBLIC_V = 1;
    private static final Integer UPDATED_PUBLIC_V = 2;

    @Inject
    private CalendarEventRepository calendarEventRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCalendarEventMockMvc;

    private CalendarEvent calendarEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalendarEventResource calendarEventResource = new CalendarEventResource();
        ReflectionTestUtils.setField(calendarEventResource, "calendarEventRepository", calendarEventRepository);
        this.restCalendarEventMockMvc = MockMvcBuilders.standaloneSetup(calendarEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarEvent createEntity(EntityManager em) {
        CalendarEvent calendarEvent = new CalendarEvent()
                .calendarEventId(DEFAULT_CALENDAR_EVENT_ID)
                .type(DEFAULT_TYPE)
                .date(DEFAULT_DATE)
                .title(DEFAULT_TITLE)
                .allDay(DEFAULT_ALL_DAY)
                .dataItemId(DEFAULT_DATA_ITEM_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .enteredBy(DEFAULT_ENTERED_BY)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .siteId(DEFAULT_SITE_ID)
                .joborderId(DEFAULT_JOBORDER_ID)
                .description(DEFAULT_DESCRIPTION)
                .duration(DEFAULT_DURATION)
                .reminderEnabled(DEFAULT_REMINDER_ENABLED)
                .reminderEmail(DEFAULT_REMINDER_EMAIL)
                .reminderTime(DEFAULT_REMINDER_TIME)
                .publicV(DEFAULT_PUBLIC_V);
        return calendarEvent;
    }

    @Before
    public void initTest() {
        calendarEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarEvent() throws Exception {
        int databaseSizeBeforeCreate = calendarEventRepository.findAll().size();

        // Create the CalendarEvent

        restCalendarEventMockMvc.perform(post("/api/calendar-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calendarEvent)))
                .andExpect(status().isCreated());

        // Validate the CalendarEvent in the database
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();
        assertThat(calendarEvents).hasSize(databaseSizeBeforeCreate + 1);
        CalendarEvent testCalendarEvent = calendarEvents.get(calendarEvents.size() - 1);
        assertThat(testCalendarEvent.getCalendarEventId()).isEqualTo(DEFAULT_CALENDAR_EVENT_ID);
        assertThat(testCalendarEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCalendarEvent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCalendarEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCalendarEvent.getAllDay()).isEqualTo(DEFAULT_ALL_DAY);
        assertThat(testCalendarEvent.getDataItemId()).isEqualTo(DEFAULT_DATA_ITEM_ID);
        assertThat(testCalendarEvent.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testCalendarEvent.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testCalendarEvent.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCalendarEvent.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCalendarEvent.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testCalendarEvent.getJoborderId()).isEqualTo(DEFAULT_JOBORDER_ID);
        assertThat(testCalendarEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCalendarEvent.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCalendarEvent.getReminderEnabled()).isEqualTo(DEFAULT_REMINDER_ENABLED);
        assertThat(testCalendarEvent.getReminderEmail()).isEqualTo(DEFAULT_REMINDER_EMAIL);
        assertThat(testCalendarEvent.getReminderTime()).isEqualTo(DEFAULT_REMINDER_TIME);
        assertThat(testCalendarEvent.getPublicV()).isEqualTo(DEFAULT_PUBLIC_V);
    }

    @Test
    @Transactional
    public void getAllCalendarEvents() throws Exception {
        // Initialize the database
        calendarEventRepository.saveAndFlush(calendarEvent);

        // Get all the calendarEvents
        restCalendarEventMockMvc.perform(get("/api/calendar-events?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(calendarEvent.getId().intValue())))
                .andExpect(jsonPath("$.[*].calendarEventId").value(hasItem(DEFAULT_CALENDAR_EVENT_ID)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].allDay").value(hasItem(DEFAULT_ALL_DAY)))
                .andExpect(jsonPath("$.[*].dataItemId").value(hasItem(DEFAULT_DATA_ITEM_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].joborderId").value(hasItem(DEFAULT_JOBORDER_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
                .andExpect(jsonPath("$.[*].reminderEnabled").value(hasItem(DEFAULT_REMINDER_ENABLED)))
                .andExpect(jsonPath("$.[*].reminderEmail").value(hasItem(DEFAULT_REMINDER_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].reminderTime").value(hasItem(DEFAULT_REMINDER_TIME)))
                .andExpect(jsonPath("$.[*].publicV").value(hasItem(DEFAULT_PUBLIC_V)));
    }

    @Test
    @Transactional
    public void getCalendarEvent() throws Exception {
        // Initialize the database
        calendarEventRepository.saveAndFlush(calendarEvent);

        // Get the calendarEvent
        restCalendarEventMockMvc.perform(get("/api/calendar-events/{id}", calendarEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendarEvent.getId().intValue()))
            .andExpect(jsonPath("$.calendarEventId").value(DEFAULT_CALENDAR_EVENT_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.allDay").value(DEFAULT_ALL_DAY))
            .andExpect(jsonPath("$.dataItemId").value(DEFAULT_DATA_ITEM_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.joborderId").value(DEFAULT_JOBORDER_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.reminderEnabled").value(DEFAULT_REMINDER_ENABLED))
            .andExpect(jsonPath("$.reminderEmail").value(DEFAULT_REMINDER_EMAIL.toString()))
            .andExpect(jsonPath("$.reminderTime").value(DEFAULT_REMINDER_TIME))
            .andExpect(jsonPath("$.publicV").value(DEFAULT_PUBLIC_V));
    }

    @Test
    @Transactional
    public void getNonExistingCalendarEvent() throws Exception {
        // Get the calendarEvent
        restCalendarEventMockMvc.perform(get("/api/calendar-events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarEvent() throws Exception {
        // Initialize the database
        calendarEventRepository.saveAndFlush(calendarEvent);
        int databaseSizeBeforeUpdate = calendarEventRepository.findAll().size();

        // Update the calendarEvent
        CalendarEvent updatedCalendarEvent = calendarEventRepository.findOne(calendarEvent.getId());
        updatedCalendarEvent
                .calendarEventId(UPDATED_CALENDAR_EVENT_ID)
                .type(UPDATED_TYPE)
                .date(UPDATED_DATE)
                .title(UPDATED_TITLE)
                .allDay(UPDATED_ALL_DAY)
                .dataItemId(UPDATED_DATA_ITEM_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .enteredBy(UPDATED_ENTERED_BY)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .siteId(UPDATED_SITE_ID)
                .joborderId(UPDATED_JOBORDER_ID)
                .description(UPDATED_DESCRIPTION)
                .duration(UPDATED_DURATION)
                .reminderEnabled(UPDATED_REMINDER_ENABLED)
                .reminderEmail(UPDATED_REMINDER_EMAIL)
                .reminderTime(UPDATED_REMINDER_TIME)
                .publicV(UPDATED_PUBLIC_V);

        restCalendarEventMockMvc.perform(put("/api/calendar-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCalendarEvent)))
                .andExpect(status().isOk());

        // Validate the CalendarEvent in the database
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();
        assertThat(calendarEvents).hasSize(databaseSizeBeforeUpdate);
        CalendarEvent testCalendarEvent = calendarEvents.get(calendarEvents.size() - 1);
        assertThat(testCalendarEvent.getCalendarEventId()).isEqualTo(UPDATED_CALENDAR_EVENT_ID);
        assertThat(testCalendarEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCalendarEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCalendarEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCalendarEvent.getAllDay()).isEqualTo(UPDATED_ALL_DAY);
        assertThat(testCalendarEvent.getDataItemId()).isEqualTo(UPDATED_DATA_ITEM_ID);
        assertThat(testCalendarEvent.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testCalendarEvent.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testCalendarEvent.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCalendarEvent.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCalendarEvent.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testCalendarEvent.getJoborderId()).isEqualTo(UPDATED_JOBORDER_ID);
        assertThat(testCalendarEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCalendarEvent.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCalendarEvent.getReminderEnabled()).isEqualTo(UPDATED_REMINDER_ENABLED);
        assertThat(testCalendarEvent.getReminderEmail()).isEqualTo(UPDATED_REMINDER_EMAIL);
        assertThat(testCalendarEvent.getReminderTime()).isEqualTo(UPDATED_REMINDER_TIME);
        assertThat(testCalendarEvent.getPublicV()).isEqualTo(UPDATED_PUBLIC_V);
    }

    @Test
    @Transactional
    public void deleteCalendarEvent() throws Exception {
        // Initialize the database
        calendarEventRepository.saveAndFlush(calendarEvent);
        int databaseSizeBeforeDelete = calendarEventRepository.findAll().size();

        // Get the calendarEvent
        restCalendarEventMockMvc.perform(delete("/api/calendar-events/{id}", calendarEvent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();
        assertThat(calendarEvents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
