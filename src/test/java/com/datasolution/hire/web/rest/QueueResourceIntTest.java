package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Queue;
import com.datasolution.hire.repository.QueueRepository;

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
 * Test class for the QueueResource REST controller.
 *
 * @see QueueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class QueueResourceIntTest {

    private static final Integer DEFAULT_QUEUE_ID = 1;
    private static final Integer UPDATED_QUEUE_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_ARGS = "AAAAAAAAAA";
    private static final String UPDATED_ARGS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_TIMEOUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIMEOUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_TIMEOUT_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_TIMEOUT);

    private static final ZonedDateTime DEFAULT_DATE_COMPLETED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_COMPLETED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_COMPLETED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_COMPLETED);

    private static final Boolean DEFAULT_LOCKED = false;
    private static final Boolean UPDATED_LOCKED = true;

    private static final Boolean DEFAULT_ERROR = false;
    private static final Boolean UPDATED_ERROR = true;

    private static final String DEFAULT_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE = "BBBBBBBBBB";

    @Inject
    private QueueRepository queueRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQueueMockMvc;

    private Queue queue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QueueResource queueResource = new QueueResource();
        ReflectionTestUtils.setField(queueResource, "queueRepository", queueRepository);
        this.restQueueMockMvc = MockMvcBuilders.standaloneSetup(queueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Queue createEntity(EntityManager em) {
        Queue queue = new Queue()
                .queueId(DEFAULT_QUEUE_ID)
                .siteId(DEFAULT_SITE_ID)
                .task(DEFAULT_TASK)
                .args(DEFAULT_ARGS)
                .priority(DEFAULT_PRIORITY)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateTimeout(DEFAULT_DATE_TIMEOUT)
                .dateCompleted(DEFAULT_DATE_COMPLETED)
                .locked(DEFAULT_LOCKED)
                .error(DEFAULT_ERROR)
                .response(DEFAULT_RESPONSE);
        return queue;
    }

    @Before
    public void initTest() {
        queue = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueue() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue

        restQueueMockMvc.perform(post("/api/queues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(queue)))
                .andExpect(status().isCreated());

        // Validate the Queue in the database
        List<Queue> queues = queueRepository.findAll();
        assertThat(queues).hasSize(databaseSizeBeforeCreate + 1);
        Queue testQueue = queues.get(queues.size() - 1);
        assertThat(testQueue.getQueueId()).isEqualTo(DEFAULT_QUEUE_ID);
        assertThat(testQueue.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testQueue.getTask()).isEqualTo(DEFAULT_TASK);
        assertThat(testQueue.getArgs()).isEqualTo(DEFAULT_ARGS);
        assertThat(testQueue.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testQueue.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testQueue.getDateTimeout()).isEqualTo(DEFAULT_DATE_TIMEOUT);
        assertThat(testQueue.getDateCompleted()).isEqualTo(DEFAULT_DATE_COMPLETED);
        assertThat(testQueue.isLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testQueue.isError()).isEqualTo(DEFAULT_ERROR);
        assertThat(testQueue.getResponse()).isEqualTo(DEFAULT_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllQueues() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get all the queues
        restQueueMockMvc.perform(get("/api/queues?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(queue.getId().intValue())))
                .andExpect(jsonPath("$.[*].queueId").value(hasItem(DEFAULT_QUEUE_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].task").value(hasItem(DEFAULT_TASK.toString())))
                .andExpect(jsonPath("$.[*].args").value(hasItem(DEFAULT_ARGS.toString())))
                .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateTimeout").value(hasItem(DEFAULT_DATE_TIMEOUT_STR)))
                .andExpect(jsonPath("$.[*].dateCompleted").value(hasItem(DEFAULT_DATE_COMPLETED_STR)))
                .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
                .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR.booleanValue())))
                .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE.toString())));
    }

    @Test
    @Transactional
    public void getQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", queue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queue.getId().intValue()))
            .andExpect(jsonPath("$.queueId").value(DEFAULT_QUEUE_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.task").value(DEFAULT_TASK.toString()))
            .andExpect(jsonPath("$.args").value(DEFAULT_ARGS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateTimeout").value(DEFAULT_DATE_TIMEOUT_STR))
            .andExpect(jsonPath("$.dateCompleted").value(DEFAULT_DATE_COMPLETED_STR))
            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR.booleanValue()))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQueue() throws Exception {
        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);
        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Update the queue
        Queue updatedQueue = queueRepository.findOne(queue.getId());
        updatedQueue
                .queueId(UPDATED_QUEUE_ID)
                .siteId(UPDATED_SITE_ID)
                .task(UPDATED_TASK)
                .args(UPDATED_ARGS)
                .priority(UPDATED_PRIORITY)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateTimeout(UPDATED_DATE_TIMEOUT)
                .dateCompleted(UPDATED_DATE_COMPLETED)
                .locked(UPDATED_LOCKED)
                .error(UPDATED_ERROR)
                .response(UPDATED_RESPONSE);

        restQueueMockMvc.perform(put("/api/queues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedQueue)))
                .andExpect(status().isOk());

        // Validate the Queue in the database
        List<Queue> queues = queueRepository.findAll();
        assertThat(queues).hasSize(databaseSizeBeforeUpdate);
        Queue testQueue = queues.get(queues.size() - 1);
        assertThat(testQueue.getQueueId()).isEqualTo(UPDATED_QUEUE_ID);
        assertThat(testQueue.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testQueue.getTask()).isEqualTo(UPDATED_TASK);
        assertThat(testQueue.getArgs()).isEqualTo(UPDATED_ARGS);
        assertThat(testQueue.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testQueue.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testQueue.getDateTimeout()).isEqualTo(UPDATED_DATE_TIMEOUT);
        assertThat(testQueue.getDateCompleted()).isEqualTo(UPDATED_DATE_COMPLETED);
        assertThat(testQueue.isLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testQueue.isError()).isEqualTo(UPDATED_ERROR);
        assertThat(testQueue.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    public void deleteQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);
        int databaseSizeBeforeDelete = queueRepository.findAll().size();

        // Get the queue
        restQueueMockMvc.perform(delete("/api/queues/{id}", queue.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Queue> queues = queueRepository.findAll();
        assertThat(queues).hasSize(databaseSizeBeforeDelete - 1);
    }
}
