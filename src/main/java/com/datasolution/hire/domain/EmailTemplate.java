package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EmailTemplate.
 */
@Entity
@Table(name = "email_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email_template_id")
    private Integer emailTemplateId;

    @Column(name = "text")
    private String text;

    @Column(name = "allow_substitution")
    private Integer allowSubstitution;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "tag")
    private String tag;

    @Column(name = "title")
    private String title;

    @Column(name = "possible_variables")
    private String possibleVariables;

    @Column(name = "disabled")
    private Integer disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmailTemplateId() {
        return emailTemplateId;
    }

    public EmailTemplate emailTemplateId(Integer emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
        return this;
    }

    public void setEmailTemplateId(Integer emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public String getText() {
        return text;
    }

    public EmailTemplate text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAllowSubstitution() {
        return allowSubstitution;
    }

    public EmailTemplate allowSubstitution(Integer allowSubstitution) {
        this.allowSubstitution = allowSubstitution;
        return this;
    }

    public void setAllowSubstitution(Integer allowSubstitution) {
        this.allowSubstitution = allowSubstitution;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public EmailTemplate siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getTag() {
        return tag;
    }

    public EmailTemplate tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public EmailTemplate title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPossibleVariables() {
        return possibleVariables;
    }

    public EmailTemplate possibleVariables(String possibleVariables) {
        this.possibleVariables = possibleVariables;
        return this;
    }

    public void setPossibleVariables(String possibleVariables) {
        this.possibleVariables = possibleVariables;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public EmailTemplate disabled(Integer disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailTemplate emailTemplate = (EmailTemplate) o;
        if(emailTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, emailTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
            "id=" + id +
            ", emailTemplateId='" + emailTemplateId + "'" +
            ", text='" + text + "'" +
            ", allowSubstitution='" + allowSubstitution + "'" +
            ", siteId='" + siteId + "'" +
            ", tag='" + tag + "'" +
            ", title='" + title + "'" +
            ", possibleVariables='" + possibleVariables + "'" +
            ", disabled='" + disabled + "'" +
            '}';
    }
}
