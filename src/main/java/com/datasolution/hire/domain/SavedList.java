package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SavedList.
 */
@Entity
@Table(name = "saved_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SavedList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "saved_list_id")
    private Integer savedListId;

    @Column(name = "description")
    private String description;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "is_dynamic")
    private Integer isDynamic;

    @Column(name = "datagrid_instance")
    private String datagridInstance;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "number_entries")
    private Integer numberEntries;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSavedListId() {
        return savedListId;
    }

    public SavedList savedListId(Integer savedListId) {
        this.savedListId = savedListId;
        return this;
    }

    public void setSavedListId(Integer savedListId) {
        this.savedListId = savedListId;
    }

    public String getDescription() {
        return description;
    }

    public SavedList description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public SavedList dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public SavedList siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getIsDynamic() {
        return isDynamic;
    }

    public SavedList isDynamic(Integer isDynamic) {
        this.isDynamic = isDynamic;
        return this;
    }

    public void setIsDynamic(Integer isDynamic) {
        this.isDynamic = isDynamic;
    }

    public String getDatagridInstance() {
        return datagridInstance;
    }

    public SavedList datagridInstance(String datagridInstance) {
        this.datagridInstance = datagridInstance;
        return this;
    }

    public void setDatagridInstance(String datagridInstance) {
        this.datagridInstance = datagridInstance;
    }

    public String getParameters() {
        return parameters;
    }

    public SavedList parameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public SavedList createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getNumberEntries() {
        return numberEntries;
    }

    public SavedList numberEntries(Integer numberEntries) {
        this.numberEntries = numberEntries;
        return this;
    }

    public void setNumberEntries(Integer numberEntries) {
        this.numberEntries = numberEntries;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public SavedList dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public SavedList dateModified(ZonedDateTime dateModified) {
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
        SavedList savedList = (SavedList) o;
        if(savedList.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, savedList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SavedList{" +
            "id=" + id +
            ", savedListId='" + savedListId + "'" +
            ", description='" + description + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", siteId='" + siteId + "'" +
            ", isDynamic='" + isDynamic + "'" +
            ", datagridInstance='" + datagridInstance + "'" +
            ", parameters='" + parameters + "'" +
            ", createdBy='" + createdBy + "'" +
            ", numberEntries='" + numberEntries + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            '}';
    }
}
