package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CalendarEvent.
 */
@Entity
@Table(name = "calendar_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CalendarEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "calendar_event_id")
    private Integer calendarEventId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "title")
    private String title;

    @Column(name = "all_day")
    private Integer allDay;

    @Column(name = "data_item_id")
    private Integer dataItemId;

    @Column(name = "data_item_type")
    private Integer dataItemType;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "joborder_id")
    private Integer joborderId;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "reminder_enabled")
    private Integer reminderEnabled;

    @Column(name = "reminder_email")
    private String reminderEmail;

    @Column(name = "reminder_time")
    private Integer reminderTime;

    @Column(name = "public_v")
    private Integer publicV;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCalendarEventId() {
        return calendarEventId;
    }

    public CalendarEvent calendarEventId(Integer calendarEventId) {
        this.calendarEventId = calendarEventId;
        return this;
    }

    public void setCalendarEventId(Integer calendarEventId) {
        this.calendarEventId = calendarEventId;
    }

    public Integer getType() {
        return type;
    }

    public CalendarEvent type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public CalendarEvent date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public CalendarEvent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAllDay() {
        return allDay;
    }

    public CalendarEvent allDay(Integer allDay) {
        this.allDay = allDay;
        return this;
    }

    public void setAllDay(Integer allDay) {
        this.allDay = allDay;
    }

    public Integer getDataItemId() {
        return dataItemId;
    }

    public CalendarEvent dataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
        return this;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public Integer getDataItemType() {
        return dataItemType;
    }

    public CalendarEvent dataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
        return this;
    }

    public void setDataItemType(Integer dataItemType) {
        this.dataItemType = dataItemType;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public CalendarEvent enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public CalendarEvent dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public CalendarEvent dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CalendarEvent siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getJoborderId() {
        return joborderId;
    }

    public CalendarEvent joborderId(Integer joborderId) {
        this.joborderId = joborderId;
        return this;
    }

    public void setJoborderId(Integer joborderId) {
        this.joborderId = joborderId;
    }

    public String getDescription() {
        return description;
    }

    public CalendarEvent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public CalendarEvent duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getReminderEnabled() {
        return reminderEnabled;
    }

    public CalendarEvent reminderEnabled(Integer reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
        return this;
    }

    public void setReminderEnabled(Integer reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }

    public String getReminderEmail() {
        return reminderEmail;
    }

    public CalendarEvent reminderEmail(String reminderEmail) {
        this.reminderEmail = reminderEmail;
        return this;
    }

    public void setReminderEmail(String reminderEmail) {
        this.reminderEmail = reminderEmail;
    }

    public Integer getReminderTime() {
        return reminderTime;
    }

    public CalendarEvent reminderTime(Integer reminderTime) {
        this.reminderTime = reminderTime;
        return this;
    }

    public void setReminderTime(Integer reminderTime) {
        this.reminderTime = reminderTime;
    }

    public Integer getPublicV() {
        return publicV;
    }

    public CalendarEvent publicV(Integer publicV) {
        this.publicV = publicV;
        return this;
    }

    public void setPublicV(Integer publicV) {
        this.publicV = publicV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CalendarEvent calendarEvent = (CalendarEvent) o;
        if(calendarEvent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, calendarEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
            "id=" + id +
            ", calendarEventId='" + calendarEventId + "'" +
            ", type='" + type + "'" +
            ", date='" + date + "'" +
            ", title='" + title + "'" +
            ", allDay='" + allDay + "'" +
            ", dataItemId='" + dataItemId + "'" +
            ", dataItemType='" + dataItemType + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", siteId='" + siteId + "'" +
            ", joborderId='" + joborderId + "'" +
            ", description='" + description + "'" +
            ", duration='" + duration + "'" +
            ", reminderEnabled='" + reminderEnabled + "'" +
            ", reminderEmail='" + reminderEmail + "'" +
            ", reminderTime='" + reminderTime + "'" +
            ", publicV='" + publicV + "'" +
            '}';
    }
}
