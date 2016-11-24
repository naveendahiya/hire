package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EmailHistory.
 */
@Entity
@Table(name = "email_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email_history_id")
    private Integer emailHistoryId;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "recipients")
    private String recipients;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "date")
    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmailHistoryId() {
        return emailHistoryId;
    }

    public EmailHistory emailHistoryId(Integer emailHistoryId) {
        this.emailHistoryId = emailHistoryId;
        return this;
    }

    public void setEmailHistoryId(Integer emailHistoryId) {
        this.emailHistoryId = emailHistoryId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public EmailHistory fromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getRecipients() {
        return recipients;
    }

    public EmailHistory recipients(String recipients) {
        this.recipients = recipients;
        return this;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getText() {
        return text;
    }

    public EmailHistory text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUserId() {
        return userId;
    }

    public EmailHistory userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public EmailHistory siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public EmailHistory date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailHistory emailHistory = (EmailHistory) o;
        if(emailHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, emailHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmailHistory{" +
            "id=" + id +
            ", emailHistoryId='" + emailHistoryId + "'" +
            ", fromAddress='" + fromAddress + "'" +
            ", recipients='" + recipients + "'" +
            ", text='" + text + "'" +
            ", userId='" + userId + "'" +
            ", siteId='" + siteId + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
