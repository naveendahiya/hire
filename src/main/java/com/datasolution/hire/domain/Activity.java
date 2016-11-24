package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "activity_id")
    private Integer activityId;

    @Column(name = "data_item_id")
    private Integer dataItemId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "joborder_id")
    private Integer joborderId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "type")
    private Integer type;

    @Column(name = "notes")
    private String notes;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public Activity activityId(Integer activityId) {
        this.activityId = activityId;
        return this;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getDataItemId() {
        return dataItemId;
    }

    public Activity dataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
        return this;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public Activity dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getJoborderId() {
        return joborderId;
    }

    public Activity joborderId(Integer joborderId) {
        this.joborderId = joborderId;
        return this;
    }

    public void setJoborderId(Integer joborderId) {
        this.joborderId = joborderId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Activity siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public Activity enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Activity dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getType() {
        return type;
    }

    public Activity type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public Activity notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Activity dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if(activity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + id +
            ", activityId='" + activityId + "'" +
            ", dataItemId='" + dataItemId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", joborderId='" + joborderId + "'" +
            ", siteId='" + siteId + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", type='" + type + "'" +
            ", notes='" + notes + "'" +
            ", dateModified='" + dateModified + "'" +
            '}';
    }
}
