package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareerPortalQuestionnaireQuestion.
 */
@Entity
@Table(name = "career_portal_questionnaire_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CareerPortalQuestionnaireQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "career_portal_questionnaire_question_id")
    private Integer careerPortalQuestionnaireQuestionId;

    @Column(name = "career_portal_questionnaire_id")
    private Integer careerPortalQuestionnaireId;

    @Column(name = "text")
    private String text;

    @Column(name = "minimum_length")
    private Integer minimumLength;

    @Column(name = "maximum_length")
    private Integer maximumLength;

    @Column(name = "requir")
    private Boolean requir;

    @Column(name = "position")
    private Integer position;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "type")
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCareerPortalQuestionnaireQuestionId() {
        return careerPortalQuestionnaireQuestionId;
    }

    public CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestionId(Integer careerPortalQuestionnaireQuestionId) {
        this.careerPortalQuestionnaireQuestionId = careerPortalQuestionnaireQuestionId;
        return this;
    }

    public void setCareerPortalQuestionnaireQuestionId(Integer careerPortalQuestionnaireQuestionId) {
        this.careerPortalQuestionnaireQuestionId = careerPortalQuestionnaireQuestionId;
    }

    public Integer getCareerPortalQuestionnaireId() {
        return careerPortalQuestionnaireId;
    }

    public CareerPortalQuestionnaireQuestion careerPortalQuestionnaireId(Integer careerPortalQuestionnaireId) {
        this.careerPortalQuestionnaireId = careerPortalQuestionnaireId;
        return this;
    }

    public void setCareerPortalQuestionnaireId(Integer careerPortalQuestionnaireId) {
        this.careerPortalQuestionnaireId = careerPortalQuestionnaireId;
    }

    public String getText() {
        return text;
    }

    public CareerPortalQuestionnaireQuestion text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMinimumLength() {
        return minimumLength;
    }

    public CareerPortalQuestionnaireQuestion minimumLength(Integer minimumLength) {
        this.minimumLength = minimumLength;
        return this;
    }

    public void setMinimumLength(Integer minimumLength) {
        this.minimumLength = minimumLength;
    }

    public Integer getMaximumLength() {
        return maximumLength;
    }

    public CareerPortalQuestionnaireQuestion maximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
        return this;
    }

    public void setMaximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
    }

    public Boolean isRequir() {
        return requir;
    }

    public CareerPortalQuestionnaireQuestion requir(Boolean requir) {
        this.requir = requir;
        return this;
    }

    public void setRequir(Boolean requir) {
        this.requir = requir;
    }

    public Integer getPosition() {
        return position;
    }

    public CareerPortalQuestionnaireQuestion position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CareerPortalQuestionnaireQuestion siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getType() {
        return type;
    }

    public CareerPortalQuestionnaireQuestion type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestion = (CareerPortalQuestionnaireQuestion) o;
        if(careerPortalQuestionnaireQuestion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careerPortalQuestionnaireQuestion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerPortalQuestionnaireQuestion{" +
            "id=" + id +
            ", careerPortalQuestionnaireQuestionId='" + careerPortalQuestionnaireQuestionId + "'" +
            ", careerPortalQuestionnaireId='" + careerPortalQuestionnaireId + "'" +
            ", text='" + text + "'" +
            ", minimumLength='" + minimumLength + "'" +
            ", maximumLength='" + maximumLength + "'" +
            ", requir='" + requir + "'" +
            ", position='" + position + "'" +
            ", siteId='" + siteId + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
