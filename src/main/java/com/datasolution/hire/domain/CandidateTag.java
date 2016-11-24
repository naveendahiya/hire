package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateTag.
 */
@Entity
@Table(name = "candidate_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "tag_id")
    private Integer tagId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CandidateTag siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public CandidateTag candidateId(Integer candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public CandidateTag tagId(Integer tagId) {
        this.tagId = tagId;
        return this;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateTag candidateTag = (CandidateTag) o;
        if(candidateTag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidateTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateTag{" +
            "id=" + id +
            ", siteId='" + siteId + "'" +
            ", candidateId='" + candidateId + "'" +
            ", tagId='" + tagId + "'" +
            '}';
    }
}
