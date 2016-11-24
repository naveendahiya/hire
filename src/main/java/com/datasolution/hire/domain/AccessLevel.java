package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AccessLevel.
 */
@Entity
@Table(name = "access_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "access_level_id")
    private Integer accessLevelId;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccessLevelId() {
        return accessLevelId;
    }

    public AccessLevel accessLevelId(Integer accessLevelId) {
        this.accessLevelId = accessLevelId;
        return this;
    }

    public void setAccessLevelId(Integer accessLevelId) {
        this.accessLevelId = accessLevelId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public AccessLevel shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public AccessLevel longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccessLevel accessLevel = (AccessLevel) o;
        if(accessLevel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, accessLevel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccessLevel{" +
            "id=" + id +
            ", accessLevelId='" + accessLevelId + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", longDescription='" + longDescription + "'" +
            '}';
    }
}
