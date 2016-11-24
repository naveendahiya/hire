package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ExtensionStatistics.
 */
@Entity
@Table(name = "extension_statistics")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtensionStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "extension_statistics_id")
    private Integer extensionStatisticsId;

    @Column(name = "extension")
    private String extension;

    @Column(name = "action")
    private String action;

    @Column(name = "user")
    private String user;

    @Column(name = "date")
    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExtensionStatisticsId() {
        return extensionStatisticsId;
    }

    public ExtensionStatistics extensionStatisticsId(Integer extensionStatisticsId) {
        this.extensionStatisticsId = extensionStatisticsId;
        return this;
    }

    public void setExtensionStatisticsId(Integer extensionStatisticsId) {
        this.extensionStatisticsId = extensionStatisticsId;
    }

    public String getExtension() {
        return extension;
    }

    public ExtensionStatistics extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAction() {
        return action;
    }

    public ExtensionStatistics action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUser() {
        return user;
    }

    public ExtensionStatistics user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public ExtensionStatistics date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtensionStatistics extensionStatistics = (ExtensionStatistics) o;
        if(extensionStatistics.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, extensionStatistics.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExtensionStatistics{" +
            "id=" + id +
            ", extensionStatisticsId='" + extensionStatisticsId + "'" +
            ", extension='" + extension + "'" +
            ", action='" + action + "'" +
            ", user='" + user + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
