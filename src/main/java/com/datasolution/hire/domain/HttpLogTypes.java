package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HttpLogTypes.
 */
@Entity
@Table(name = "http_log_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HttpLogTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "log_type_id")
    private Integer logTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "default_log_type")
    private Boolean defaultLogType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLogTypeId() {
        return logTypeId;
    }

    public HttpLogTypes logTypeId(Integer logTypeId) {
        this.logTypeId = logTypeId;
        return this;
    }

    public void setLogTypeId(Integer logTypeId) {
        this.logTypeId = logTypeId;
    }

    public String getName() {
        return name;
    }

    public HttpLogTypes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public HttpLogTypes description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isDefaultLogType() {
        return defaultLogType;
    }

    public HttpLogTypes defaultLogType(Boolean defaultLogType) {
        this.defaultLogType = defaultLogType;
        return this;
    }

    public void setDefaultLogType(Boolean defaultLogType) {
        this.defaultLogType = defaultLogType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpLogTypes httpLogTypes = (HttpLogTypes) o;
        if(httpLogTypes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, httpLogTypes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HttpLogTypes{" +
            "id=" + id +
            ", logTypeId='" + logTypeId + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", defaultLogType='" + defaultLogType + "'" +
            '}';
    }
}
