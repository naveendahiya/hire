package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ExtraField.
 */
@Entity
@Table(name = "extra_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtraField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "extra_field_id")
    private Integer extraFieldId;

    @Column(name = "data_item_id")
    private Integer dataItemId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "value")
    private String value;

    @Column(name = "importeded_id")
    private Integer importededId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExtraFieldId() {
        return extraFieldId;
    }

    public ExtraField extraFieldId(Integer extraFieldId) {
        this.extraFieldId = extraFieldId;
        return this;
    }

    public void setExtraFieldId(Integer extraFieldId) {
        this.extraFieldId = extraFieldId;
    }

    public Integer getDataItemId() {
        return dataItemId;
    }

    public ExtraField dataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
        return this;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ExtraField fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public ExtraField value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getImportededId() {
        return importededId;
    }

    public ExtraField importededId(Integer importededId) {
        this.importededId = importededId;
        return this;
    }

    public void setImportededId(Integer importededId) {
        this.importededId = importededId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ExtraField siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public ExtraField dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtraField extraField = (ExtraField) o;
        if(extraField.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, extraField.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExtraField{" +
            "id=" + id +
            ", extraFieldId='" + extraFieldId + "'" +
            ", dataItemId='" + dataItemId + "'" +
            ", fieldName='" + fieldName + "'" +
            ", value='" + value + "'" +
            ", importededId='" + importededId + "'" +
            ", siteId='" + siteId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            '}';
    }
}
