package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SavedSearch.
 */
@Entity
@Table(name = "saved_search")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SavedSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "search_id")
    private Integer searchId;

    @Column(name = "data_item_text")
    private String dataItemText;

    @Column(name = "url")
    private String url;

    @Column(name = "is_custom")
    private Integer isCustom;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSearchId() {
        return searchId;
    }

    public SavedSearch searchId(Integer searchId) {
        this.searchId = searchId;
        return this;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public String getDataItemText() {
        return dataItemText;
    }

    public SavedSearch dataItemText(String dataItemText) {
        this.dataItemText = dataItemText;
        return this;
    }

    public void setDataItemText(String dataItemText) {
        this.dataItemText = dataItemText;
    }

    public String getUrl() {
        return url;
    }

    public SavedSearch url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsCustom() {
        return isCustom;
    }

    public SavedSearch isCustom(Integer isCustom) {
        this.isCustom = isCustom;
        return this;
    }

    public void setIsCustom(Integer isCustom) {
        this.isCustom = isCustom;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public SavedSearch dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getUserId() {
        return userId;
    }

    public SavedSearch userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public SavedSearch siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public SavedSearch dateCreated(ZonedDateTime dateCreated) {
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
        SavedSearch savedSearch = (SavedSearch) o;
        if(savedSearch.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, savedSearch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SavedSearch{" +
            "id=" + id +
            ", searchId='" + searchId + "'" +
            ", dataItemText='" + dataItemText + "'" +
            ", url='" + url + "'" +
            ", isCustom='" + isCustom + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", userId='" + userId + "'" +
            ", siteId='" + siteId + "'" +
            ", dateCreated='" + dateCreated + "'" +
            '}';
    }
}
