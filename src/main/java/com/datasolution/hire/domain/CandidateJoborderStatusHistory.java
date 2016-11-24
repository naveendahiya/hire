package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CandidateJoborderStatusHistory.
 */
@Entity
@Table(name = "candidate_joborder_status_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateJoborderStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_joborder_status_history_id")
    private Integer candidateJoborderStatusHistoryId;

    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "joborder_id")
    private Integer joborderId;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "status_from")
    private Integer statusFrom;

    @Column(name = "status_to")
    private Integer statusTo;

    @Column(name = "site_id")
    private Integer siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandidateJoborderStatusHistoryId() {
        return candidateJoborderStatusHistoryId;
    }

    public CandidateJoborderStatusHistory candidateJoborderStatusHistoryId(Integer candidateJoborderStatusHistoryId) {
        this.candidateJoborderStatusHistoryId = candidateJoborderStatusHistoryId;
        return this;
    }

    public void setCandidateJoborderStatusHistoryId(Integer candidateJoborderStatusHistoryId) {
        this.candidateJoborderStatusHistoryId = candidateJoborderStatusHistoryId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public CandidateJoborderStatusHistory candidateId(Integer candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getJoborderId() {
        return joborderId;
    }

    public CandidateJoborderStatusHistory joborderId(Integer joborderId) {
        this.joborderId = joborderId;
        return this;
    }

    public void setJoborderId(Integer joborderId) {
        this.joborderId = joborderId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public CandidateJoborderStatusHistory date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getStatusFrom() {
        return statusFrom;
    }

    public CandidateJoborderStatusHistory statusFrom(Integer statusFrom) {
        this.statusFrom = statusFrom;
        return this;
    }

    public void setStatusFrom(Integer statusFrom) {
        this.statusFrom = statusFrom;
    }

    public Integer getStatusTo() {
        return statusTo;
    }

    public CandidateJoborderStatusHistory statusTo(Integer statusTo) {
        this.statusTo = statusTo;
        return this;
    }

    public void setStatusTo(Integer statusTo) {
        this.statusTo = statusTo;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CandidateJoborderStatusHistory siteId(Integer siteId) {
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
        CandidateJoborderStatusHistory candidateJoborderStatusHistory = (CandidateJoborderStatusHistory) o;
        if(candidateJoborderStatusHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidateJoborderStatusHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateJoborderStatusHistory{" +
            "id=" + id +
            ", candidateJoborderStatusHistoryId='" + candidateJoborderStatusHistoryId + "'" +
            ", candidateId='" + candidateId + "'" +
            ", joborderId='" + joborderId + "'" +
            ", date='" + date + "'" +
            ", statusFrom='" + statusFrom + "'" +
            ", statusTo='" + statusTo + "'" +
            ", siteId='" + siteId + "'" +
            '}';
    }
}
