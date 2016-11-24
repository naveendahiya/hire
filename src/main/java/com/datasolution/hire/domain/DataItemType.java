package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DataItemType.
 */
@Entity
@Table(name = "data_item_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataItemType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "data_item_type_id")
    private Integer dataItemTypeId;

    @Column(name = "short_description")
    private String shortDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDataItemTypeId() {
        return dataItemTypeId;
    }

    public DataItemType dataItemTypeId(Integer dataItemTypeId) {
        this.dataItemTypeId = dataItemTypeId;
        return this;
    }

    public void setDataItemTypeId(Integer dataItemTypeId) {
        this.dataItemTypeId = dataItemTypeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public DataItemType shortDescription(String shortDescription) {
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
        DataItemType dataItemType = (DataItemType) o;
        if(dataItemType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dataItemType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DataItemType{" +
            "id=" + id +
            ", dataItemTypeId='" + dataItemTypeId + "'" +
            ", shortDescription='" + shortDescription + "'" +
            '}';
    }
}
