package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateJoborderStatus.
 */
@Entity
@Table(name = "candidate_joborder_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateJoborderStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_joborder_status_id")
    private Integer candidateJoborderStatusId;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "can_be_scheduled")
    private Integer canBeScheduled;

    @Column(name = "triggers_email")
    private Integer triggersEmail;

    @Column(name = "is_enabled")
    private Integer isEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandidateJoborderStatusId() {
        return candidateJoborderStatusId;
    }

    public CandidateJoborderStatus candidateJoborderStatusId(Integer candidateJoborderStatusId) {
        this.candidateJoborderStatusId = candidateJoborderStatusId;
        return this;
    }

    public void setCandidateJoborderStatusId(Integer candidateJoborderStatusId) {
        this.candidateJoborderStatusId = candidateJoborderStatusId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public CandidateJoborderStatus shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getCanBeScheduled() {
        return canBeScheduled;
    }

    public CandidateJoborderStatus canBeScheduled(Integer canBeScheduled) {
        this.canBeScheduled = canBeScheduled;
        return this;
    }

    public void setCanBeScheduled(Integer canBeScheduled) {
        this.canBeScheduled = canBeScheduled;
    }

    public Integer getTriggersEmail() {
        return triggersEmail;
    }

    public CandidateJoborderStatus triggersEmail(Integer triggersEmail) {
        this.triggersEmail = triggersEmail;
        return this;
    }

    public void setTriggersEmail(Integer triggersEmail) {
        this.triggersEmail = triggersEmail;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public CandidateJoborderStatus isEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateJoborderStatus candidateJoborderStatus = (CandidateJoborderStatus) o;
        if(candidateJoborderStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidateJoborderStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateJoborderStatus{" +
            "id=" + id +
            ", candidateJoborderStatusId='" + candidateJoborderStatusId + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", canBeScheduled='" + canBeScheduled + "'" +
            ", triggersEmail='" + triggersEmail + "'" +
            ", isEnabled='" + isEnabled + "'" +
            '}';
    }
}
