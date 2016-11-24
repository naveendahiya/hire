package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.ExtensionStatistics;
import com.datasolution.hire.repository.ExtensionStatisticsRepository;

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
 * Test class for the ExtensionStatisticsResource REST controller.
 *
 * @see ExtensionStatisticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class ExtensionStatisticsResourceIntTest {

    private static final Integer DEFAULT_EXTENSION_STATISTICS_ID = 1;
    private static final Integer UPDATED_EXTENSION_STATISTICS_ID = 2;

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE);

    @Inject
    private ExtensionStatisticsRepository extensionStatisticsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExtensionStatisticsMockMvc;

    private ExtensionStatistics extensionStatistics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtensionStatisticsResource extensionStatisticsResource = new ExtensionStatisticsResource();
        ReflectionTestUtils.setField(extensionStatisticsResource, "extensionStatisticsRepository", extensionStatisticsRepository);
        this.restExtensionStatisticsMockMvc = MockMvcBuilders.standaloneSetup(extensionStatisticsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtensionStatistics createEntity(EntityManager em) {
        ExtensionStatistics extensionStatistics = new ExtensionStatistics()
                .extensionStatisticsId(DEFAULT_EXTENSION_STATISTICS_ID)
                .extension(DEFAULT_EXTENSION)
                .action(DEFAULT_ACTION)
                .user(DEFAULT_USER)
                .date(DEFAULT_DATE);
        return extensionStatistics;
    }

    @Before
    public void initTest() {
        extensionStatistics = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtensionStatistics() throws Exception {
        int databaseSizeBeforeCreate = extensionStatisticsRepository.findAll().size();

        // Create the ExtensionStatistics

        restExtensionStatisticsMockMvc.perform(post("/api/extension-statistics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(extensionStatistics)))
                .andExpect(status().isCreated());

        // Validate the ExtensionStatistics in the database
        List<ExtensionStatistics> extensionStatistics = extensionStatisticsRepository.findAll();
        assertThat(extensionStatistics).hasSize(databaseSizeBeforeCreate + 1);
        ExtensionStatistics testExtensionStatistics = extensionStatistics.get(extensionStatistics.size() - 1);
        assertThat(testExtensionStatistics.getExtensionStatisticsId()).isEqualTo(DEFAULT_EXTENSION_STATISTICS_ID);
        assertThat(testExtensionStatistics.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testExtensionStatistics.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testExtensionStatistics.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testExtensionStatistics.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllExtensionStatistics() throws Exception {
        // Initialize the database
        extensionStatisticsRepository.saveAndFlush(extensionStatistics);

        // Get all the extensionStatistics
        restExtensionStatisticsMockMvc.perform(get("/api/extension-statistics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(extensionStatistics.getId().intValue())))
                .andExpect(jsonPath("$.[*].extensionStatisticsId").value(hasItem(DEFAULT_EXTENSION_STATISTICS_ID)))
                .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION.toString())))
                .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
                .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getExtensionStatistics() throws Exception {
        // Initialize the database
        extensionStatisticsRepository.saveAndFlush(extensionStatistics);

        // Get the extensionStatistics
        restExtensionStatisticsMockMvc.perform(get("/api/extension-statistics/{id}", extensionStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extensionStatistics.getId().intValue()))
            .andExpect(jsonPath("$.extensionStatisticsId").value(DEFAULT_EXTENSION_STATISTICS_ID))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingExtensionStatistics() throws Exception {
        // Get the extensionStatistics
        restExtensionStatisticsMockMvc.perform(get("/api/extension-statistics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtensionStatistics() throws Exception {
        // Initialize the database
        extensionStatisticsRepository.saveAndFlush(extensionStatistics);
        int databaseSizeBeforeUpdate = extensionStatisticsRepository.findAll().size();

        // Update the extensionStatistics
        ExtensionStatistics updatedExtensionStatistics = extensionStatisticsRepository.findOne(extensionStatistics.getId());
        updatedExtensionStatistics
                .extensionStatisticsId(UPDATED_EXTENSION_STATISTICS_ID)
                .extension(UPDATED_EXTENSION)
                .action(UPDATED_ACTION)
                .user(UPDATED_USER)
                .date(UPDATED_DATE);

        restExtensionStatisticsMockMvc.perform(put("/api/extension-statistics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExtensionStatistics)))
                .andExpect(status().isOk());

        // Validate the ExtensionStatistics in the database
        List<ExtensionStatistics> extensionStatistics = extensionStatisticsRepository.findAll();
        assertThat(extensionStatistics).hasSize(databaseSizeBeforeUpdate);
        ExtensionStatistics testExtensionStatistics = extensionStatistics.get(extensionStatistics.size() - 1);
        assertThat(testExtensionStatistics.getExtensionStatisticsId()).isEqualTo(UPDATED_EXTENSION_STATISTICS_ID);
        assertThat(testExtensionStatistics.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testExtensionStatistics.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testExtensionStatistics.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testExtensionStatistics.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteExtensionStatistics() throws Exception {
        // Initialize the database
        extensionStatisticsRepository.saveAndFlush(extensionStatistics);
        int databaseSizeBeforeDelete = extensionStatisticsRepository.findAll().size();

        // Get the extensionStatistics
        restExtensionStatisticsMockMvc.perform(delete("/api/extension-statistics/{id}", extensionStatistics.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtensionStatistics> extensionStatistics = extensionStatisticsRepository.findAll();
        assertThat(extensionStatistics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
