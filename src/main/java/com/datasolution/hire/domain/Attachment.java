package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "attachment_id")
    private Integer attachmentId;

    @Column(name = "data_item_id")
    private Integer dataItemId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "title")
    private String title;

    @Column(name = "original_filename")
    private String originalFilename;

    @Column(name = "stored_filename")
    private String storedFilename;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "resume")
    private Integer resume;

    @Column(name = "text")
    private String text;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @Column(name = "profile_image")
    private Integer profileImage;

    @Column(name = "directory_name")
    private String directoryName;

    @Column(name = "md_5_sum")
    private String md5Sum;

    @Column(name = "file_size_kb")
    private Integer fileSizeKb;

    @Column(name = "md_5_sum_text")
    private String md5SumText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public Attachment attachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
        return this;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getDataItemId() {
        return dataItemId;
    }

    public Attachment dataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
        return this;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public Attachment dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Attachment siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public Attachment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Attachment originalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStoredFilename() {
        return storedFilename;
    }

    public Attachment storedFilename(String storedFilename) {
        this.storedFilename = storedFilename;
        return this;
    }

    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public Attachment contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getResume() {
        return resume;
    }

    public Attachment resume(Integer resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(Integer resume) {
        this.resume = resume;
    }

    public String getText() {
        return text;
    }

    public Attachment text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Attachment dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Attachment dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getProfileImage() {
        return profileImage;
    }

    public Attachment profileImage(Integer profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public void setProfileImage(Integer profileImage) {
        this.profileImage = profileImage;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public Attachment directoryName(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getMd5Sum() {
        return md5Sum;
    }

    public Attachment md5Sum(String md5Sum) {
        this.md5Sum = md5Sum;
        return this;
    }

    public void setMd5Sum(String md5Sum) {
        this.md5Sum = md5Sum;
    }

    public Integer getFileSizeKb() {
        return fileSizeKb;
    }

    public Attachment fileSizeKb(Integer fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
        return this;
    }

    public void setFileSizeKb(Integer fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
    }

    public String getMd5SumText() {
        return md5SumText;
    }

    public Attachment md5SumText(String md5SumText) {
        this.md5SumText = md5SumText;
        return this;
    }

    public void setMd5SumText(String md5SumText) {
        this.md5SumText = md5SumText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attachment attachment = (Attachment) o;
        if(attachment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, attachment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + id +
            ", attachmentId='" + attachmentId + "'" +
            ", dataItemId='" + dataItemId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", siteId='" + siteId + "'" +
            ", title='" + title + "'" +
            ", originalFilename='" + originalFilename + "'" +
            ", storedFilename='" + storedFilename + "'" +
            ", contentType='" + contentType + "'" +
            ", resume='" + resume + "'" +
            ", text='" + text + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", profileImage='" + profileImage + "'" +
            ", directoryName='" + directoryName + "'" +
            ", md5Sum='" + md5Sum + "'" +
            ", fileSizeKb='" + fileSizeKb + "'" +
            ", md5SumText='" + md5SumText + "'" +
            '}';
    }
}
