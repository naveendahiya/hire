package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Mru.
 */
@Entity
@Table(name = "mru")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mru implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mru_id")
    private Integer mruId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "data_item_text")
    private String dataItemText;

    @Column(name = "url")
    private String url;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMruId() {
        return mruId;
    }

    public Mru mruId(Integer mruId) {
        this.mruId = mruId;
        return this;
    }

    public void setMruId(Integer mruId) {
        this.mruId = mruId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Mru userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Mru siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public Mru dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public String getDataItemText() {
        return dataItemText;
    }

    public Mru dataItemText(String dataItemText) {
        this.dataItemText = dataItemText;
        return this;
    }

    public void setDataItemText(String dataItemText) {
        this.dataItemText = dataItemText;
    }

    public String getUrl() {
        return url;
    }

    public Mru url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Mru dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mru mru = (Mru) o;
        if(mru.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mru.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mru{" +
            "id=" + id +
            ", mruId='" + mruId + "'" +
            ", userId='" + userId + "'" +
            ", siteId='" + siteId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", dataItemText='" + dataItemText + "'" +
            ", url='" + url + "'" +
            ", dateCreated='" + dateCreated + "'" +
            '}';
    }
}
