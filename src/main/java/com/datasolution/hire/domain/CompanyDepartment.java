package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CompanyDepartment.
 */
@Entity
@Table(name = "company_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_department_id")
    private Integer companyDepartmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "created_by")
    private Integer createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompanyDepartmentId() {
        return companyDepartmentId;
    }

    public CompanyDepartment companyDepartmentId(Integer companyDepartmentId) {
        this.companyDepartmentId = companyDepartmentId;
        return this;
    }

    public void setCompanyDepartmentId(Integer companyDepartmentId) {
        this.companyDepartmentId = companyDepartmentId;
    }

    public String getName() {
        return name;
    }

    public CompanyDepartment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public CompanyDepartment companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CompanyDepartment siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public CompanyDepartment dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public CompanyDepartment createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyDepartment companyDepartment = (CompanyDepartment) o;
        if(companyDepartment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, companyDepartment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyDepartment{" +
            "id=" + id +
            ", companyDepartmentId='" + companyDepartmentId + "'" +
            ", name='" + name + "'" +
            ", companyId='" + companyId + "'" +
            ", siteId='" + siteId + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", createdBy='" + createdBy + "'" +
            '}';
    }
}
