package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateJobordrerStatusType.
 */
@Entity
@Table(name = "candidate_jobordrer_status_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateJobordrerStatusType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_status_type_id")
    private Integer candidateStatusTypeId;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "can_be_scheduled")
    private Integer canBeScheduled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandidateStatusTypeId() {
        return candidateStatusTypeId;
    }

    public CandidateJobordrerStatusType candidateStatusTypeId(Integer candidateStatusTypeId) {
        this.candidateStatusTypeId = candidateStatusTypeId;
        return this;
    }

    public void setCandidateStatusTypeId(Integer candidateStatusTypeId) {
        this.candidateStatusTypeId = candidateStatusTypeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public CandidateJobordrerStatusType shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getCanBeScheduled() {
        return canBeScheduled;
    }

    public CandidateJobordrerStatusType canBeScheduled(Integer canBeScheduled) {
        this.canBeScheduled = canBeScheduled;
        return this;
    }

    public void setCanBeScheduled(Integer canBeScheduled) {
        this.canBeScheduled = canBeScheduled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateJobordrerStatusType candidateJobordrerStatusType = (CandidateJobordrerStatusType) o;
        if(candidateJobordrerStatusType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidateJobordrerStatusType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateJobordrerStatusType{" +
            "id=" + id +
            ", candidateStatusTypeId='" + candidateStatusTypeId + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", canBeScheduled='" + canBeScheduled + "'" +
            '}';
    }
}
