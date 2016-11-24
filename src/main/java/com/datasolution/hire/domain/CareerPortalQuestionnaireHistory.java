package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CareerPortalQuestionnaireHistory.
 */
@Entity
@Table(name = "career_portal_questionnaire_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CareerPortalQuestionnaireHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "career_portal_questionnaire_history_id")
    private Integer careerPortalQuestionnaireHistoryId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "questionnaire_title")
    private String questionnaireTitle;

    @Column(name = "questionnaire_description")
    private String questionnaireDescription;

    @Column(name = "date")
    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCareerPortalQuestionnaireHistoryId() {
        return careerPortalQuestionnaireHistoryId;
    }

    public CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistoryId(Integer careerPortalQuestionnaireHistoryId) {
        this.careerPortalQuestionnaireHistoryId = careerPortalQuestionnaireHistoryId;
        return this;
    }

    public void setCareerPortalQuestionnaireHistoryId(Integer careerPortalQuestionnaireHistoryId) {
        this.careerPortalQuestionnaireHistoryId = careerPortalQuestionnaireHistoryId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CareerPortalQuestionnaireHistory siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public CareerPortalQuestionnaireHistory candidateId(Integer candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getQuestion() {
        return question;
    }

    public CareerPortalQuestionnaireHistory question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public CareerPortalQuestionnaireHistory answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionnaireTitle() {
        return questionnaireTitle;
    }

    public CareerPortalQuestionnaireHistory questionnaireTitle(String questionnaireTitle) {
        this.questionnaireTitle = questionnaireTitle;
        return this;
    }

    public void setQuestionnaireTitle(String questionnaireTitle) {
        this.questionnaireTitle = questionnaireTitle;
    }

    public String getQuestionnaireDescription() {
        return questionnaireDescription;
    }

    public CareerPortalQuestionnaireHistory questionnaireDescription(String questionnaireDescription) {
        this.questionnaireDescription = questionnaireDescription;
        return this;
    }

    public void setQuestionnaireDescription(String questionnaireDescription) {
        this.questionnaireDescription = questionnaireDescription;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public CareerPortalQuestionnaireHistory date(ZonedDateTime date) {
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
        CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistory = (CareerPortalQuestionnaireHistory) o;
        if(careerPortalQuestionnaireHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careerPortalQuestionnaireHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerPortalQuestionnaireHistory{" +
            "id=" + id +
            ", careerPortalQuestionnaireHistoryId='" + careerPortalQuestionnaireHistoryId + "'" +
            ", siteId='" + siteId + "'" +
            ", candidateId='" + candidateId + "'" +
            ", question='" + question + "'" +
            ", answer='" + answer + "'" +
            ", questionnaireTitle='" + questionnaireTitle + "'" +
            ", questionnaireDescription='" + questionnaireDescription + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
