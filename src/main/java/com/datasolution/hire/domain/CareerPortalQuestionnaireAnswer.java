package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareerPortalQuestionnaireAnswer.
 */
@Entity
@Table(name = "career_portal_questionnaire_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CareerPortalQuestionnaireAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "career_portal_questionnaire_answer_id")
    private Integer careerPortalQuestionnaireAnswerId;

    @Column(name = "career_portal_questionnaire_question_id")
    private Integer careerPortalQuestionnaireQuestionId;

    @Column(name = "career_portal_questionnaire_id")
    private Integer careerPortalQuestionnaireId;

    @Column(name = "text")
    private String text;

    @Column(name = "action_source")
    private String actionSource;

    @Column(name = "action_notes")
    private String actionNotes;

    @Column(name = "action_is_hot")
    private Boolean actionIsHot;

    @Column(name = "action_is_active")
    private Boolean actionIsActive;

    @Column(name = "action_can_relocate")
    private Boolean actionCanRelocate;

    @Column(name = "action_key_skills")
    private String actionKeySkills;

    @Column(name = "position")
    private Integer position;

    @Column(name = "site_id")
    private Integer siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCareerPortalQuestionnaireAnswerId() {
        return careerPortalQuestionnaireAnswerId;
    }

    public CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswerId(Integer careerPortalQuestionnaireAnswerId) {
        this.careerPortalQuestionnaireAnswerId = careerPortalQuestionnaireAnswerId;
        return this;
    }

    public void setCareerPortalQuestionnaireAnswerId(Integer careerPortalQuestionnaireAnswerId) {
        this.careerPortalQuestionnaireAnswerId = careerPortalQuestionnaireAnswerId;
    }

    public Integer getCareerPortalQuestionnaireQuestionId() {
        return careerPortalQuestionnaireQuestionId;
    }

    public CareerPortalQuestionnaireAnswer careerPortalQuestionnaireQuestionId(Integer careerPortalQuestionnaireQuestionId) {
        this.careerPortalQuestionnaireQuestionId = careerPortalQuestionnaireQuestionId;
        return this;
    }

    public void setCareerPortalQuestionnaireQuestionId(Integer careerPortalQuestionnaireQuestionId) {
        this.careerPortalQuestionnaireQuestionId = careerPortalQuestionnaireQuestionId;
    }

    public Integer getCareerPortalQuestionnaireId() {
        return careerPortalQuestionnaireId;
    }

    public CareerPortalQuestionnaireAnswer careerPortalQuestionnaireId(Integer careerPortalQuestionnaireId) {
        this.careerPortalQuestionnaireId = careerPortalQuestionnaireId;
        return this;
    }

    public void setCareerPortalQuestionnaireId(Integer careerPortalQuestionnaireId) {
        this.careerPortalQuestionnaireId = careerPortalQuestionnaireId;
    }

    public String getText() {
        return text;
    }

    public CareerPortalQuestionnaireAnswer text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getActionSource() {
        return actionSource;
    }

    public CareerPortalQuestionnaireAnswer actionSource(String actionSource) {
        this.actionSource = actionSource;
        return this;
    }

    public void setActionSource(String actionSource) {
        this.actionSource = actionSource;
    }

    public String getActionNotes() {
        return actionNotes;
    }

    public CareerPortalQuestionnaireAnswer actionNotes(String actionNotes) {
        this.actionNotes = actionNotes;
        return this;
    }

    public void setActionNotes(String actionNotes) {
        this.actionNotes = actionNotes;
    }

    public Boolean isActionIsHot() {
        return actionIsHot;
    }

    public CareerPortalQuestionnaireAnswer actionIsHot(Boolean actionIsHot) {
        this.actionIsHot = actionIsHot;
        return this;
    }

    public void setActionIsHot(Boolean actionIsHot) {
        this.actionIsHot = actionIsHot;
    }

    public Boolean isActionIsActive() {
        return actionIsActive;
    }

    public CareerPortalQuestionnaireAnswer actionIsActive(Boolean actionIsActive) {
        this.actionIsActive = actionIsActive;
        return this;
    }

    public void setActionIsActive(Boolean actionIsActive) {
        this.actionIsActive = actionIsActive;
    }

    public Boolean isActionCanRelocate() {
        return actionCanRelocate;
    }

    public CareerPortalQuestionnaireAnswer actionCanRelocate(Boolean actionCanRelocate) {
        this.actionCanRelocate = actionCanRelocate;
        return this;
    }

    public void setActionCanRelocate(Boolean actionCanRelocate) {
        this.actionCanRelocate = actionCanRelocate;
    }

    public String getActionKeySkills() {
        return actionKeySkills;
    }

    public CareerPortalQuestionnaireAnswer actionKeySkills(String actionKeySkills) {
        this.actionKeySkills = actionKeySkills;
        return this;
    }

    public void setActionKeySkills(String actionKeySkills) {
        this.actionKeySkills = actionKeySkills;
    }

    public Integer getPosition() {
        return position;
    }

    public CareerPortalQuestionnaireAnswer position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CareerPortalQuestionnaireAnswer siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswer = (CareerPortalQuestionnaireAnswer) o;
        if(careerPortalQuestionnaireAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careerPortalQuestionnaireAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerPortalQuestionnaireAnswer{" +
            "id=" + id +
            ", careerPortalQuestionnaireAnswerId='" + careerPortalQuestionnaireAnswerId + "'" +
            ", careerPortalQuestionnaireQuestionId='" + careerPortalQuestionnaireQuestionId + "'" +
            ", careerPortalQuestionnaireId='" + careerPortalQuestionnaireId + "'" +
            ", text='" + text + "'" +
            ", actionSource='" + actionSource + "'" +
            ", actionNotes='" + actionNotes + "'" +
            ", actionIsHot='" + actionIsHot + "'" +
            ", actionIsActive='" + actionIsActive + "'" +
            ", actionCanRelocate='" + actionCanRelocate + "'" +
            ", actionKeySkills='" + actionKeySkills + "'" +
            ", position='" + position + "'" +
            ", siteId='" + siteId + "'" +
            '}';
    }
}
