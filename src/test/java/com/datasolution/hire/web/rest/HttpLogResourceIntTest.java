package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.HttpLog;
import com.datasolution.hire.repository.HttpLogRepository;

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
 * Test class for the HttpLogResource REST controller.
 *
 * @see HttpLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class HttpLogResourceIntTest {

    private static final Integer DEFAULT_LOG_ID = 1;
    private static final Integer UPDATED_LOG_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_REMOTE_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_ADDR = "BBBBBBBBBB";

    private static final String DEFAULT_HTTP_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_HTTP_USER_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_SCRIPT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY_STRING = "AAAAAAAAAA";
    private static final String UPDATED_QUERY_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_URI = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_URI = "BBBBBBBBBB";

    private static final String DEFAULT_SCRIPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOG_TYPE = 1;
    private static final Integer UPDATED_LOG_TYPE = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE);

    @Inject
    private HttpLogRepository httpLogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHttpLogMockMvc;

    private HttpLog httpLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HttpLogResource httpLogResource = new HttpLogResource();
        ReflectionTestUtils.setField(httpLogResource, "httpLogRepository", httpLogRepository);
        this.restHttpLogMockMvc = MockMvcBuilders.standaloneSetup(httpLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HttpLog createEntity(EntityManager em) {
        HttpLog httpLog = new HttpLog()
                .logId(DEFAULT_LOG_ID)
                .siteId(DEFAULT_SITE_ID)
                .remoteAddr(DEFAULT_REMOTE_ADDR)
                .httpUserAgent(DEFAULT_HTTP_USER_AGENT)
                .scriptFilename(DEFAULT_SCRIPT_FILENAME)
                .requestMethod(DEFAULT_REQUEST_METHOD)
                .queryString(DEFAULT_QUERY_STRING)
                .requestUri(DEFAULT_REQUEST_URI)
                .scriptName(DEFAULT_SCRIPT_NAME)
                .logType(DEFAULT_LOG_TYPE)
                .date(DEFAULT_DATE);
        return httpLog;
    }

    @Before
    public void initTest() {
        httpLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createHttpLog() throws Exception {
        int databaseSizeBeforeCreate = httpLogRepository.findAll().size();

        // Create the HttpLog

        restHttpLogMockMvc.perform(post("/api/http-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(httpLog)))
                .andExpect(status().isCreated());

        // Validate the HttpLog in the database
        List<HttpLog> httpLogs = httpLogRepository.findAll();
        assertThat(httpLogs).hasSize(databaseSizeBeforeCreate + 1);
        HttpLog testHttpLog = httpLogs.get(httpLogs.size() - 1);
        assertThat(testHttpLog.getLogId()).isEqualTo(DEFAULT_LOG_ID);
        assertThat(testHttpLog.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testHttpLog.getRemoteAddr()).isEqualTo(DEFAULT_REMOTE_ADDR);
        assertThat(testHttpLog.getHttpUserAgent()).isEqualTo(DEFAULT_HTTP_USER_AGENT);
        assertThat(testHttpLog.getScriptFilename()).isEqualTo(DEFAULT_SCRIPT_FILENAME);
        assertThat(testHttpLog.getRequestMethod()).isEqualTo(DEFAULT_REQUEST_METHOD);
        assertThat(testHttpLog.getQueryString()).isEqualTo(DEFAULT_QUERY_STRING);
        assertThat(testHttpLog.getRequestUri()).isEqualTo(DEFAULT_REQUEST_URI);
        assertThat(testHttpLog.getScriptName()).isEqualTo(DEFAULT_SCRIPT_NAME);
        assertThat(testHttpLog.getLogType()).isEqualTo(DEFAULT_LOG_TYPE);
        assertThat(testHttpLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllHttpLogs() throws Exception {
        // Initialize the database
        httpLogRepository.saveAndFlush(httpLog);

        // Get all the httpLogs
        restHttpLogMockMvc.perform(get("/api/http-logs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(httpLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].logId").value(hasItem(DEFAULT_LOG_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].remoteAddr").value(hasItem(DEFAULT_REMOTE_ADDR.toString())))
                .andExpect(jsonPath("$.[*].httpUserAgent").value(hasItem(DEFAULT_HTTP_USER_AGENT.toString())))
                .andExpect(jsonPath("$.[*].scriptFilename").value(hasItem(DEFAULT_SCRIPT_FILENAME.toString())))
                .andExpect(jsonPath("$.[*].requestMethod").value(hasItem(DEFAULT_REQUEST_METHOD.toString())))
                .andExpect(jsonPath("$.[*].queryString").value(hasItem(DEFAULT_QUERY_STRING.toString())))
                .andExpect(jsonPath("$.[*].requestUri").value(hasItem(DEFAULT_REQUEST_URI.toString())))
                .andExpect(jsonPath("$.[*].scriptName").value(hasItem(DEFAULT_SCRIPT_NAME.toString())))
                .andExpect(jsonPath("$.[*].logType").value(hasItem(DEFAULT_LOG_TYPE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getHttpLog() throws Exception {
        // Initialize the database
        httpLogRepository.saveAndFlush(httpLog);

        // Get the httpLog
        restHttpLogMockMvc.perform(get("/api/http-logs/{id}", httpLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(httpLog.getId().intValue()))
            .andExpect(jsonPath("$.logId").value(DEFAULT_LOG_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.remoteAddr").value(DEFAULT_REMOTE_ADDR.toString()))
            .andExpect(jsonPath("$.httpUserAgent").value(DEFAULT_HTTP_USER_AGENT.toString()))
            .andExpect(jsonPath("$.scriptFilename").value(DEFAULT_SCRIPT_FILENAME.toString()))
            .andExpect(jsonPath("$.requestMethod").value(DEFAULT_REQUEST_METHOD.toString()))
            .andExpect(jsonPath("$.queryString").value(DEFAULT_QUERY_STRING.toString()))
            .andExpect(jsonPath("$.requestUri").value(DEFAULT_REQUEST_URI.toString()))
            .andExpect(jsonPath("$.scriptName").value(DEFAULT_SCRIPT_NAME.toString()))
            .andExpect(jsonPath("$.logType").value(DEFAULT_LOG_TYPE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingHttpLog() throws Exception {
        // Get the httpLog
        restHttpLogMockMvc.perform(get("/api/http-logs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHttpLog() throws Exception {
        // Initialize the database
        httpLogRepository.saveAndFlush(httpLog);
        int databaseSizeBeforeUpdate = httpLogRepository.findAll().size();

        // Update the httpLog
        HttpLog updatedHttpLog = httpLogRepository.findOne(httpLog.getId());
        updatedHttpLog
                .logId(UPDATED_LOG_ID)
                .siteId(UPDATED_SITE_ID)
                .remoteAddr(UPDATED_REMOTE_ADDR)
                .httpUserAgent(UPDATED_HTTP_USER_AGENT)
                .scriptFilename(UPDATED_SCRIPT_FILENAME)
                .requestMethod(UPDATED_REQUEST_METHOD)
                .queryString(UPDATED_QUERY_STRING)
                .requestUri(UPDATED_REQUEST_URI)
                .scriptName(UPDATED_SCRIPT_NAME)
                .logType(UPDATED_LOG_TYPE)
                .date(UPDATED_DATE);

        restHttpLogMockMvc.perform(put("/api/http-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHttpLog)))
                .andExpect(status().isOk());

        // Validate the HttpLog in the database
        List<HttpLog> httpLogs = httpLogRepository.findAll();
        assertThat(httpLogs).hasSize(databaseSizeBeforeUpdate);
        HttpLog testHttpLog = httpLogs.get(httpLogs.size() - 1);
        assertThat(testHttpLog.getLogId()).isEqualTo(UPDATED_LOG_ID);
        assertThat(testHttpLog.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testHttpLog.getRemoteAddr()).isEqualTo(UPDATED_REMOTE_ADDR);
        assertThat(testHttpLog.getHttpUserAgent()).isEqualTo(UPDATED_HTTP_USER_AGENT);
        assertThat(testHttpLog.getScriptFilename()).isEqualTo(UPDATED_SCRIPT_FILENAME);
        assertThat(testHttpLog.getRequestMethod()).isEqualTo(UPDATED_REQUEST_METHOD);
        assertThat(testHttpLog.getQueryString()).isEqualTo(UPDATED_QUERY_STRING);
        assertThat(testHttpLog.getRequestUri()).isEqualTo(UPDATED_REQUEST_URI);
        assertThat(testHttpLog.getScriptName()).isEqualTo(UPDATED_SCRIPT_NAME);
        assertThat(testHttpLog.getLogType()).isEqualTo(UPDATED_LOG_TYPE);
        assertThat(testHttpLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteHttpLog() throws Exception {
        // Initialize the database
        httpLogRepository.saveAndFlush(httpLog);
        int databaseSizeBeforeDelete = httpLogRepository.findAll().size();

        // Get the httpLog
        restHttpLogMockMvc.perform(delete("/api/http-logs/{id}", httpLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HttpLog> httpLogs = httpLogRepository.findAll();
        assertThat(httpLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
