package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Joborder.
 */
@Entity
@Table(name = "joborder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Joborder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "joborder_id")
    private Integer joborderId;

    @Column(name = "recruiter")
    private Integer recruiter;

    @Column(name = "contact_id")
    private Integer contactId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "owner")
    private Integer owner;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "client_job_id")
    private String clientJobId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Column(name = "type")
    private String type;

    @Column(name = "duration")
    private String duration;

    @Column(name = "rate_max")
    private String rateMax;

    @Column(name = "salary")
    private String salary;

    @Column(name = "status")
    private String status;

    @Column(name = "is_hot")
    private Integer isHot;

    @Column(name = "openings")
    private Integer openings;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @Column(name = "public_v")
    private Integer publicV;

    @Column(name = "company_department_id")
    private Integer companyDepartmentId;

    @Column(name = "is_admin_hidden")
    private Integer isAdminHidden;

    @Column(name = "openings_available")
    private Integer openingsAvailable;

    @Column(name = "questionnaire_id")
    private Integer questionnaireId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJoborderId() {
        return joborderId;
    }

    public Joborder joborderId(Integer joborderId) {
        this.joborderId = joborderId;
        return this;
    }

    public void setJoborderId(Integer joborderId) {
        this.joborderId = joborderId;
    }

    public Integer getRecruiter() {
        return recruiter;
    }

    public Joborder recruiter(Integer recruiter) {
        this.recruiter = recruiter;
        return this;
    }

    public void setRecruiter(Integer recruiter) {
        this.recruiter = recruiter;
    }

    public Integer getContactId() {
        return contactId;
    }

    public Joborder contactId(Integer contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Joborder companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public Joborder enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getOwner() {
        return owner;
    }

    public Joborder owner(Integer owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Joborder siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getClientJobId() {
        return clientJobId;
    }

    public Joborder clientJobId(String clientJobId) {
        this.clientJobId = clientJobId;
        return this;
    }

    public void setClientJobId(String clientJobId) {
        this.clientJobId = clientJobId;
    }

    public String getTitle() {
        return title;
    }

    public Joborder title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Joborder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public Joborder notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public Joborder type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public Joborder duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRateMax() {
        return rateMax;
    }

    public Joborder rateMax(String rateMax) {
        this.rateMax = rateMax;
        return this;
    }

    public void setRateMax(String rateMax) {
        this.rateMax = rateMax;
    }

    public String getSalary() {
        return salary;
    }

    public Joborder salary(String salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public Joborder status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public Joborder isHot(Integer isHot) {
        this.isHot = isHot;
        return this;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getOpenings() {
        return openings;
    }

    public Joborder openings(Integer openings) {
        this.openings = openings;
        return this;
    }

    public void setOpenings(Integer openings) {
        this.openings = openings;
    }

    public String getCity() {
        return city;
    }

    public Joborder city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Joborder state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Joborder startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Joborder dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Joborder dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getPublicV() {
        return publicV;
    }

    public Joborder publicV(Integer publicV) {
        this.publicV = publicV;
        return this;
    }

    public void setPublicV(Integer publicV) {
        this.publicV = publicV;
    }

    public Integer getCompanyDepartmentId() {
        return companyDepartmentId;
    }

    public Joborder companyDepartmentId(Integer companyDepartmentId) {
        this.companyDepartmentId = companyDepartmentId;
        return this;
    }

    public void setCompanyDepartmentId(Integer companyDepartmentId) {
        this.companyDepartmentId = companyDepartmentId;
    }

    public Integer getIsAdminHidden() {
        return isAdminHidden;
    }

    public Joborder isAdminHidden(Integer isAdminHidden) {
        this.isAdminHidden = isAdminHidden;
        return this;
    }

    public void setIsAdminHidden(Integer isAdminHidden) {
        this.isAdminHidden = isAdminHidden;
    }

    public Integer getOpeningsAvailable() {
        return openingsAvailable;
    }

    public Joborder openingsAvailable(Integer openingsAvailable) {
        this.openingsAvailable = openingsAvailable;
        return this;
    }

    public void setOpeningsAvailable(Integer openingsAvailable) {
        this.openingsAvailable = openingsAvailable;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public Joborder questionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
        return this;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Joborder joborder = (Joborder) o;
        if(joborder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, joborder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Joborder{" +
            "id=" + id +
            ", joborderId='" + joborderId + "'" +
            ", recruiter='" + recruiter + "'" +
            ", contactId='" + contactId + "'" +
            ", companyId='" + companyId + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", owner='" + owner + "'" +
            ", siteId='" + siteId + "'" +
            ", clientJobId='" + clientJobId + "'" +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", notes='" + notes + "'" +
            ", type='" + type + "'" +
            ", duration='" + duration + "'" +
            ", rateMax='" + rateMax + "'" +
            ", salary='" + salary + "'" +
            ", status='" + status + "'" +
            ", isHot='" + isHot + "'" +
            ", openings='" + openings + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", startDate='" + startDate + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", publicV='" + publicV + "'" +
            ", companyDepartmentId='" + companyDepartmentId + "'" +
            ", isAdminHidden='" + isAdminHidden + "'" +
            ", openingsAvailable='" + openingsAvailable + "'" +
            ", questionnaireId='" + questionnaireId + "'" +
            '}';
    }
}
