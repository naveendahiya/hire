package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CalendarEventType.
 */
@Entity
@Table(name = "calendar_event_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CalendarEventType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "calendar_event_type_id")
    private Integer calendarEventTypeId;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "icon_image")
    private String iconImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCalendarEventTypeId() {
        return calendarEventTypeId;
    }

    public CalendarEventType calendarEventTypeId(Integer calendarEventTypeId) {
        this.calendarEventTypeId = calendarEventTypeId;
        return this;
    }

    public void setCalendarEventTypeId(Integer calendarEventTypeId) {
        this.calendarEventTypeId = calendarEventTypeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public CalendarEventType shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getIconImage() {
        return iconImage;
    }

    public CalendarEventType iconImage(String iconImage) {
        this.iconImage = iconImage;
        return this;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CalendarEventType calendarEventType = (CalendarEventType) o;
        if(calendarEventType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, calendarEventType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalendarEventType{" +
            "id=" + id +
            ", calendarEventTypeId='" + calendarEventTypeId + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", iconImage='" + iconImage + "'" +
            '}';
    }
}
