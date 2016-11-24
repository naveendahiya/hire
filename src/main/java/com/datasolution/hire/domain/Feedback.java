package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "feedback_id")
    private Integer feedbackId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "subject")
    private String subject;

    @Column(name = "reply_to_address")
    private String replyToAddress;

    @Column(name = "reply_to_name")
    private String replyToName;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "archived")
    private Integer archived;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public Feedback feedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
        return this;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Feedback userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Feedback siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Feedback dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSubject() {
        return subject;
    }

    public Feedback subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReplyToAddress() {
        return replyToAddress;
    }

    public Feedback replyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
        return this;
    }

    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }

    public String getReplyToName() {
        return replyToName;
    }

    public Feedback replyToName(String replyToName) {
        this.replyToName = replyToName;
        return this;
    }

    public void setReplyToName(String replyToName) {
        this.replyToName = replyToName;
    }

    public String getFeedback() {
        return feedback;
    }

    public Feedback feedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getArchived() {
        return archived;
    }

    public Feedback archived(Integer archived) {
        this.archived = archived;
        return this;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        if(feedback.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + id +
            ", feedbackId='" + feedbackId + "'" +
            ", userId='" + userId + "'" +
            ", siteId='" + siteId + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", subject='" + subject + "'" +
            ", replyToAddress='" + replyToAddress + "'" +
            ", replyToName='" + replyToName + "'" +
            ", feedback='" + feedback + "'" +
            ", archived='" + archived + "'" +
            '}';
    }
}
