package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActivityType.
 */
@Entity
@Table(name = "activity_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActivityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "activity_type_id")
    private Integer activityTypeId;

    @Column(name = "short_description")
    private String shortDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public ActivityType activityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
        return this;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public ActivityType shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityType activityType = (ActivityType) o;
        if(activityType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activityType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ActivityType{" +
            "id=" + id +
            ", activityTypeId='" + activityTypeId + "'" +
            ", shortDescription='" + shortDescription + "'" +
            '}';
    }
}
