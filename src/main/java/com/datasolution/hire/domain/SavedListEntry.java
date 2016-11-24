package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SavedListEntry.
 */
@Entity
@Table(name = "saved_list_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SavedListEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "saved_list_entry_id")
    private Integer savedListEntryId;

    @Column(name = "saved_list_id")
    private Integer savedListId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "data_item_id")
    private Integer dataItemId;

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

    public Integer getSavedListEntryId() {
        return savedListEntryId;
    }

    public SavedListEntry savedListEntryId(Integer savedListEntryId) {
        this.savedListEntryId = savedListEntryId;
        return this;
    }

    public void setSavedListEntryId(Integer savedListEntryId) {
        this.savedListEntryId = savedListEntryId;
    }

    public Integer getSavedListId() {
        return savedListId;
    }

    public SavedListEntry savedListId(Integer savedListId) {
        this.savedListId = savedListId;
        return this;
    }

    public void setSavedListId(Integer savedListId) {
        this.savedListId = savedListId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public SavedListEntry dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getDataItemId() {
        return dataItemId;
    }

    public SavedListEntry dataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
        return this;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public SavedListEntry siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public SavedListEntry dateCreated(ZonedDateTime dateCreated) {
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
        SavedListEntry savedListEntry = (SavedListEntry) o;
        if(savedListEntry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, savedListEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SavedListEntry{" +
            "id=" + id +
            ", savedListEntryId='" + savedListEntryId + "'" +
            ", savedListId='" + savedListId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", dataItemId='" + dataItemId + "'" +
            ", siteId='" + siteId + "'" +
            ", dateCreated='" + dateCreated + "'" +
            '}';
    }
}
