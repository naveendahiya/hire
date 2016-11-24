package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A History.
 */
@Entity
@Table(name = "history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "history_id")
    private Integer historyId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "data_item_id")
    private Integer dataItemId;

    @Column(name = "the_field")
    private String theField;

    @Column(name = "previous_value")
    private String previousValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "description")
    private String description;

    @Column(name = "set_date")
    private ZonedDateTime setDate;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "site_id")
    private Integer siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public History historyId(Integer historyId) {
        this.historyId = historyId;
        return this;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public History dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getDataItemId() {
        return dataItemId;
    }

    public History dataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
        return this;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public String getTheField() {
        return theField;
    }

    public History theField(String theField) {
        this.theField = theField;
        return this;
    }

    public void setTheField(String theField) {
        this.theField = theField;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public History previousValue(String previousValue) {
        this.previousValue = previousValue;
        return this;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public History newValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getDescription() {
        return description;
    }

    public History description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getSetDate() {
        return setDate;
    }

    public History setDate(ZonedDateTime setDate) {
        this.setDate = setDate;
        return this;
    }

    public void setSetDate(ZonedDateTime setDate) {
        this.setDate = setDate;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public History enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public History siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        History history = (History) o;
        if(history.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + id +
            ", historyId='" + historyId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", dataItemId='" + dataItemId + "'" +
            ", theField='" + theField + "'" +
            ", previousValue='" + previousValue + "'" +
            ", newValue='" + newValue + "'" +
            ", description='" + description + "'" +
            ", setDate='" + setDate + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", siteId='" + siteId + "'" +
            '}';
    }
}
