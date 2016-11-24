package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ExtraFieldSettings.
 */
@Entity
@Table(name = "extra_field_settings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtraFieldSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "extra_field_settings_id")
    private Integer extraFieldSettingsId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "imported_id")
    private Integer importedId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "extra_field_type")
    private Integer extraFieldType;

    @Column(name = "extra_field_options")
    private String extraFieldOptions;

    @Column(name = "position")
    private Integer position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExtraFieldSettingsId() {
        return extraFieldSettingsId;
    }

    public ExtraFieldSettings extraFieldSettingsId(Integer extraFieldSettingsId) {
        this.extraFieldSettingsId = extraFieldSettingsId;
        return this;
    }

    public void setExtraFieldSettingsId(Integer extraFieldSettingsId) {
        this.extraFieldSettingsId = extraFieldSettingsId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ExtraFieldSettings fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getImportedId() {
        return importedId;
    }

    public ExtraFieldSettings importedId(Integer importedId) {
        this.importedId = importedId;
        return this;
    }

    public void setImportedId(Integer importedId) {
        this.importedId = importedId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ExtraFieldSettings siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public ExtraFieldSettings dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public ExtraFieldSettings dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getExtraFieldType() {
        return extraFieldType;
    }

    public ExtraFieldSettings extraFieldType(Integer extraFieldType) {
        this.extraFieldType = extraFieldType;
        return this;
    }

    public void setExtraFieldType(Integer extraFieldType) {
        this.extraFieldType = extraFieldType;
    }

    public String getExtraFieldOptions() {
        return extraFieldOptions;
    }

    public ExtraFieldSettings extraFieldOptions(String extraFieldOptions) {
        this.extraFieldOptions = extraFieldOptions;
        return this;
    }

    public void setExtraFieldOptions(String extraFieldOptions) {
        this.extraFieldOptions = extraFieldOptions;
    }

    public Integer getPosition() {
        return position;
    }

    public ExtraFieldSettings position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtraFieldSettings extraFieldSettings = (ExtraFieldSettings) o;
        if(extraFieldSettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, extraFieldSettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExtraFieldSettings{" +
            "id=" + id +
            ", extraFieldSettingsId='" + extraFieldSettingsId + "'" +
            ", fieldName='" + fieldName + "'" +
            ", importedId='" + importedId + "'" +
            ", siteId='" + siteId + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", extraFieldType='" + extraFieldType + "'" +
            ", extraFieldOptions='" + extraFieldOptions + "'" +
            ", position='" + position + "'" +
            '}';
    }
}
