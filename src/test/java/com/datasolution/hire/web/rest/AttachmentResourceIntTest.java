package com.datasolution.hire.web.rest;

import com.datasolution.hire.HireApp;

import com.datasolution.hire.domain.Attachment;
import com.datasolution.hire.repository.AttachmentRepository;

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
 * Test class for the AttachmentResource REST controller.
 *
 * @see AttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HireApp.class)
public class AttachmentResourceIntTest {

    private static final Integer DEFAULT_ATTACHMENT_ID = 1;
    private static final Integer UPDATED_ATTACHMENT_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_ID = 1;
    private static final Integer UPDATED_DATA_ITEM_ID = 2;

    private static final Integer DEFAULT_DATA_ITEM_TYPE = 1;
    private static final Integer UPDATED_DATA_ITEM_TYPE = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_STORED_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_STORED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RESUME = 1;
    private static final Integer UPDATED_RESUME = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MODIFIED_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DATE_MODIFIED);

    private static final Integer DEFAULT_PROFILE_IMAGE = 1;
    private static final Integer UPDATED_PROFILE_IMAGE = 2;

    private static final String DEFAULT_DIRECTORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MD_5_SUM = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_SUM = "BBBBBBBBBB";

    private static final Integer DEFAULT_FILE_SIZE_KB = 1;
    private static final Integer UPDATED_FILE_SIZE_KB = 2;

    private static final String DEFAULT_MD_5_SUM_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_SUM_TEXT = "BBBBBBBBBB";

    @Inject
    private AttachmentRepository attachmentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAttachmentMockMvc;

    private Attachment attachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttachmentResource attachmentResource = new AttachmentResource();
        ReflectionTestUtils.setField(attachmentResource, "attachmentRepository", attachmentRepository);
        this.restAttachmentMockMvc = MockMvcBuilders.standaloneSetup(attachmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachment createEntity(EntityManager em) {
        Attachment attachment = new Attachment()
                .attachmentId(DEFAULT_ATTACHMENT_ID)
                .dataItemId(DEFAULT_DATA_ITEM_ID)
                .dataItemType(DEFAULT_DATA_ITEM_TYPE)
                .siteId(DEFAULT_SITE_ID)
                .title(DEFAULT_TITLE)
                .originalFilename(DEFAULT_ORIGINAL_FILENAME)
                .storedFilename(DEFAULT_STORED_FILENAME)
                .contentType(DEFAULT_CONTENT_TYPE)
                .resume(DEFAULT_RESUME)
                .text(DEFAULT_TEXT)
                .dateCreated(DEFAULT_DATE_CREATED)
                .dateModified(DEFAULT_DATE_MODIFIED)
                .profileImage(DEFAULT_PROFILE_IMAGE)
                .directoryName(DEFAULT_DIRECTORY_NAME)
                .md5Sum(DEFAULT_MD_5_SUM)
                .fileSizeKb(DEFAULT_FILE_SIZE_KB)
                .md5SumText(DEFAULT_MD_5_SUM_TEXT);
        return attachment;
    }

    @Before
    public void initTest() {
        attachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachment() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();

        // Create the Attachment

        restAttachmentMockMvc.perform(post("/api/attachments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attachment)))
                .andExpect(status().isCreated());

        // Validate the Attachment in the database
        List<Attachment> attachments = attachmentRepository.findAll();
        assertThat(attachments).hasSize(databaseSizeBeforeCreate + 1);
        Attachment testAttachment = attachments.get(attachments.size() - 1);
        assertThat(testAttachment.getAttachmentId()).isEqualTo(DEFAULT_ATTACHMENT_ID);
        assertThat(testAttachment.getDataItemId()).isEqualTo(DEFAULT_DATA_ITEM_ID);
        assertThat(testAttachment.getDataItemType()).isEqualTo(DEFAULT_DATA_ITEM_TYPE);
        assertThat(testAttachment.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testAttachment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAttachment.getOriginalFilename()).isEqualTo(DEFAULT_ORIGINAL_FILENAME);
        assertThat(testAttachment.getStoredFilename()).isEqualTo(DEFAULT_STORED_FILENAME);
        assertThat(testAttachment.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testAttachment.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testAttachment.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testAttachment.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAttachment.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testAttachment.getProfileImage()).isEqualTo(DEFAULT_PROFILE_IMAGE);
        assertThat(testAttachment.getDirectoryName()).isEqualTo(DEFAULT_DIRECTORY_NAME);
        assertThat(testAttachment.getMd5Sum()).isEqualTo(DEFAULT_MD_5_SUM);
        assertThat(testAttachment.getFileSizeKb()).isEqualTo(DEFAULT_FILE_SIZE_KB);
        assertThat(testAttachment.getMd5SumText()).isEqualTo(DEFAULT_MD_5_SUM_TEXT);
    }

    @Test
    @Transactional
    public void getAllAttachments() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachments
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
                .andExpect(jsonPath("$.[*].attachmentId").value(hasItem(DEFAULT_ATTACHMENT_ID)))
                .andExpect(jsonPath("$.[*].dataItemId").value(hasItem(DEFAULT_DATA_ITEM_ID)))
                .andExpect(jsonPath("$.[*].dataItemType").value(hasItem(DEFAULT_DATA_ITEM_TYPE)))
                .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].originalFilename").value(hasItem(DEFAULT_ORIGINAL_FILENAME.toString())))
                .andExpect(jsonPath("$.[*].storedFilename").value(hasItem(DEFAULT_STORED_FILENAME.toString())))
                .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME)))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].profileImage").value(hasItem(DEFAULT_PROFILE_IMAGE)))
                .andExpect(jsonPath("$.[*].directoryName").value(hasItem(DEFAULT_DIRECTORY_NAME.toString())))
                .andExpect(jsonPath("$.[*].md5Sum").value(hasItem(DEFAULT_MD_5_SUM.toString())))
                .andExpect(jsonPath("$.[*].fileSizeKb").value(hasItem(DEFAULT_FILE_SIZE_KB)))
                .andExpect(jsonPath("$.[*].md5SumText").value(hasItem(DEFAULT_MD_5_SUM_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", attachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attachment.getId().intValue()))
            .andExpect(jsonPath("$.attachmentId").value(DEFAULT_ATTACHMENT_ID))
            .andExpect(jsonPath("$.dataItemId").value(DEFAULT_DATA_ITEM_ID))
            .andExpect(jsonPath("$.dataItemType").value(DEFAULT_DATA_ITEM_TYPE))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.originalFilename").value(DEFAULT_ORIGINAL_FILENAME.toString()))
            .andExpect(jsonPath("$.storedFilename").value(DEFAULT_STORED_FILENAME.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED_STR))
            .andExpect(jsonPath("$.profileImage").value(DEFAULT_PROFILE_IMAGE))
            .andExpect(jsonPath("$.directoryName").value(DEFAULT_DIRECTORY_NAME.toString()))
            .andExpect(jsonPath("$.md5Sum").value(DEFAULT_MD_5_SUM.toString()))
            .andExpect(jsonPath("$.fileSizeKb").value(DEFAULT_FILE_SIZE_KB))
            .andExpect(jsonPath("$.md5SumText").value(DEFAULT_MD_5_SUM_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttachment() throws Exception {
        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);
        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // Update the attachment
        Attachment updatedAttachment = attachmentRepository.findOne(attachment.getId());
        updatedAttachment
                .attachmentId(UPDATED_ATTACHMENT_ID)
                .dataItemId(UPDATED_DATA_ITEM_ID)
                .dataItemType(UPDATED_DATA_ITEM_TYPE)
                .siteId(UPDATED_SITE_ID)
                .title(UPDATED_TITLE)
                .originalFilename(UPDATED_ORIGINAL_FILENAME)
                .storedFilename(UPDATED_STORED_FILENAME)
                .contentType(UPDATED_CONTENT_TYPE)
                .resume(UPDATED_RESUME)
                .text(UPDATED_TEXT)
                .dateCreated(UPDATED_DATE_CREATED)
                .dateModified(UPDATED_DATE_MODIFIED)
                .profileImage(UPDATED_PROFILE_IMAGE)
                .directoryName(UPDATED_DIRECTORY_NAME)
                .md5Sum(UPDATED_MD_5_SUM)
                .fileSizeKb(UPDATED_FILE_SIZE_KB)
                .md5SumText(UPDATED_MD_5_SUM_TEXT);

        restAttachmentMockMvc.perform(put("/api/attachments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAttachment)))
                .andExpect(status().isOk());

        // Validate the Attachment in the database
        List<Attachment> attachments = attachmentRepository.findAll();
        assertThat(attachments).hasSize(databaseSizeBeforeUpdate);
        Attachment testAttachment = attachments.get(attachments.size() - 1);
        assertThat(testAttachment.getAttachmentId()).isEqualTo(UPDATED_ATTACHMENT_ID);
        assertThat(testAttachment.getDataItemId()).isEqualTo(UPDATED_DATA_ITEM_ID);
        assertThat(testAttachment.getDataItemType()).isEqualTo(UPDATED_DATA_ITEM_TYPE);
        assertThat(testAttachment.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testAttachment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAttachment.getOriginalFilename()).isEqualTo(UPDATED_ORIGINAL_FILENAME);
        assertThat(testAttachment.getStoredFilename()).isEqualTo(UPDATED_STORED_FILENAME);
        assertThat(testAttachment.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testAttachment.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testAttachment.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testAttachment.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAttachment.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testAttachment.getProfileImage()).isEqualTo(UPDATED_PROFILE_IMAGE);
        assertThat(testAttachment.getDirectoryName()).isEqualTo(UPDATED_DIRECTORY_NAME);
        assertThat(testAttachment.getMd5Sum()).isEqualTo(UPDATED_MD_5_SUM);
        assertThat(testAttachment.getFileSizeKb()).isEqualTo(UPDATED_FILE_SIZE_KB);
        assertThat(testAttachment.getMd5SumText()).isEqualTo(UPDATED_MD_5_SUM_TEXT);
    }

    @Test
    @Transactional
    public void deleteAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);
        int databaseSizeBeforeDelete = attachmentRepository.findAll().size();

        // Get the attachment
        restAttachmentMockMvc.perform(delete("/api/attachments/{id}", attachment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Attachment> attachments = attachmentRepository.findAll();
        assertThat(attachments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
