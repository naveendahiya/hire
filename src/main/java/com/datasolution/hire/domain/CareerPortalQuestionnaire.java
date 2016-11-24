package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareerPortalQuestionnaire.
 */
@Entity
@Table(name = "career_portal_questionnaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CareerPortalQuestionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "career_portal_questionnaire_id")
    private Integer careerPortalQuestionnaireId;

    @Column(name = "title")
    private String title;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCareerPortalQuestionnaireId() {
        return careerPortalQuestionnaireId;
    }

    public CareerPortalQuestionnaire careerPortalQuestionnaireId(Integer careerPortalQuestionnaireId) {
        this.careerPortalQuestionnaireId = careerPortalQuestionnaireId;
        return this;
    }

    public void setCareerPortalQuestionnaireId(Integer careerPortalQuestionnaireId) {
        this.careerPortalQuestionnaireId = careerPortalQuestionnaireId;
    }

    public String getTitle() {
        return title;
    }

    public CareerPortalQuestionnaire title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CareerPortalQuestionnaire siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getDescription() {
        return description;
    }

    public CareerPortalQuestionnaire description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public CareerPortalQuestionnaire isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CareerPortalQuestionnaire careerPortalQuestionnaire = (CareerPortalQuestionnaire) o;
        if(careerPortalQuestionnaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careerPortalQuestionnaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerPortalQuestionnaire{" +
            "id=" + id +
            ", careerPortalQuestionnaireId='" + careerPortalQuestionnaireId + "'" +
            ", title='" + title + "'" +
            ", siteId='" + siteId + "'" +
            ", description='" + description + "'" +
            ", isActive='" + isActive + "'" +
            '}';
    }
}
