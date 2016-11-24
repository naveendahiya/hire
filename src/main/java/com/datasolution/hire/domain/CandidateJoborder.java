package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CandidateJoborder.
 */
@Entity
@Table(name = "candidate_joborder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateJoborder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_joborder_id")
    private Integer candidateJoborderId;

    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "joborder_id")
    private Integer joborderId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "date_submitted")
    private ZonedDateTime dateSubmitted;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @Column(name = "rating_value")
    private Integer ratingValue;

    @Column(name = "added_by")
    private Integer addedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandidateJoborderId() {
        return candidateJoborderId;
    }

    public CandidateJoborder candidateJoborderId(Integer candidateJoborderId) {
        this.candidateJoborderId = candidateJoborderId;
        return this;
    }

    public void setCandidateJoborderId(Integer candidateJoborderId) {
        this.candidateJoborderId = candidateJoborderId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public CandidateJoborder candidateId(Integer candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getJoborderId() {
        return joborderId;
    }

    public CandidateJoborder joborderId(Integer joborderId) {
        this.joborderId = joborderId;
        return this;
    }

    public void setJoborderId(Integer joborderId) {
        this.joborderId = joborderId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CandidateJoborder siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getStatus() {
        return status;
    }

    public CandidateJoborder status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ZonedDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public CandidateJoborder dateSubmitted(ZonedDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
        return this;
    }

    public void setDateSubmitted(ZonedDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public CandidateJoborder dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public CandidateJoborder dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public CandidateJoborder ratingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
        return this;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public CandidateJoborder addedBy(Integer addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateJoborder candidateJoborder = (CandidateJoborder) o;
        if(candidateJoborder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidateJoborder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateJoborder{" +
            "id=" + id +
            ", candidateJoborderId='" + candidateJoborderId + "'" +
            ", candidateId='" + candidateId + "'" +
            ", joborderId='" + joborderId + "'" +
            ", siteId='" + siteId + "'" +
            ", status='" + status + "'" +
            ", dateSubmitted='" + dateSubmitted + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", ratingValue='" + ratingValue + "'" +
            ", addedBy='" + addedBy + "'" +
            '}';
    }
}
