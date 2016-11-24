package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Mru;
import com.datasolution.hire.repository.MruRepository;

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
 * Test class for the MruResource REST controller.
 *
 * @see MruResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class MruResourceIntTest {

    private static final Integer DEFAULT_MRU_ID = 1;
    private static final Integer UPDATED_MRU_ID = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final String DEFAULT_DATA_ITEM_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ITEM_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    @Inject
    private MruRepository mruRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMruMockMvc;

    private Mru mru;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MruResource mruResource = new MruResource();
        ReflectionTestUtils.setField(mruResource, "mruRepository", mruRepository);
        this.restMruMockMvc = MockMvcBuilders.standaloneSetup(mruResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mru createEntity(EntityManager em) {
        Mru mru = new Mru()
                .mruId(DEFAULT_MRU_ID)
                .userId(DEFAULT_USER_ID)
                .siteId(DEFAULT_SITE_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .dataItemText(DEFAULT_DATA_ITEM_TEXT)
                .url(DEFAULT_URL)
                .dateCreated(DEFAULT_DATE_CREATED);
        return mru;
    }

    @Before
    public void initTest() {
        mru = createEntity(em);
    }

    @Test
    @Transactional
    public void createMru() throws Exception {
        int databaseSizeBeforeCreate = mruRepository.findAll().size();

        // Create the Mru

        restMruMockMvc.perform(post("/api/mrus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mru)))
                .andExpect(status().isCreated());

        // Validate the Mru in the database
        List<Mru> mrus = mruRepository.findAll();
        assertThat(mrus).hasSize(databaseSizeBeforeCreate + 1);
        Mru testMru = mrus.get(mrus.size() - 1);
        assertThat(testMru.getMruId()).isEqualTo(DEFAULT_MRU_ID);
        assertThat(testMru.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMru.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testMru.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testMru.getDataItemText()).isEqualTo(DEFAULT_DATA_ITEM_TEXT);
        assertThat(testMru.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testMru.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllMrus() throws Exception {
        // Initialize the database
        mruRepository.saveAndFlush(mru);

        // Get all the mrus
        restMruMockMvc.perform(get("/api/mrus?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mru.getId().intValue())))
                .andExpect(jsonPath("$.[*].mruId").value(hasItem(DEFAULT_MRU_ID)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].dataItemText").value(hasItem(DEFAULT_DATA_ITEM_TEXT.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)));
    }

    @Test
    @Transactional
    public void getMru() throws Exception {
        // Initialize the database
        mruRepository.saveAndFlush(mru);

        // Get the mru
        restMruMockMvc.perform(get("/api/mrus/{id}", mru.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mru.getId().intValue()))
            .andExpect(jsonPath("$.mruId").value(DEFAULT_MRU_ID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.dataItemText").value(DEFAULT_DATA_ITEM_TEXT.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMru() throws Exception {
        // Get the mru
        restMruMockMvc.perform(get("/api/mrus/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMru() throws Exception {
        // Initialize the database
        mruRepository.saveAndFlush(mru);
        int databaseSizeBeforeUpdate = mruRepository.findAll().size();

        // Update the mru
        Mru updatedMru = mruRepository.findOne(mru.getId());
        updatedMru
                .mruId(UPDATED_MRU_ID)
                .userId(UPDATED_USER_ID)
                .siteId(UPDATED_SITE_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .dataItemText(UPDATED_DATA_ITEM_TEXT)
                .url(UPDATED_URL)
                .dateCreated(UPDATED_DATE_CREATED);

        restMruMockMvc.perform(put("/api/mrus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMru)))
                .andExpect(status().isOk());

        // Validate the Mru in the database
        List<Mru> mrus = mruRepository.findAll();
        assertThat(mrus).hasSize(databaseSizeBeforeUpdate);
        Mru testMru = mrus.get(mrus.size() - 1);
        assertThat(testMru.getMruId()).isEqualTo(UPDATED_MRU_ID);
        assertThat(testMru.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMru.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testMru.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testMru.getDataItemText()).isEqualTo(UPDATED_DATA_ITEM_TEXT);
        assertThat(testMru.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testMru.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void deleteMru() throws Exception {
        // Initialize the database
        mruRepository.saveAndFlush(mru);
        int databaseSizeBeforeDelete = mruRepository.findAll().size();

        // Get the mru
        restMruMockMvc.perform(delete("/api/mrus/{id}", mru.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mru> mrus = mruRepository.findAll();
        assertThat(mrus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
