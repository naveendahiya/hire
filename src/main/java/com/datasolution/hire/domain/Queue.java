package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Queue.
 */
@Entity
@Table(name = "queue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Queue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "queue_id")
    private Integer queueId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "task")
    private String task;

    @Column(name = "args")
    private String args;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_timeout")
    private ZonedDateTime dateTimeout;

    @Column(name = "date_completed")
    private ZonedDateTime dateCompleted;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "error")
    private Boolean error;

    @Column(name = "response")
    private String response;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public Queue queueId(Integer queueId) {
        this.queueId = queueId;
        return this;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Queue siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getTask() {
        return task;
    }

    public Queue task(String task) {
        this.task = task;
        return this;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getArgs() {
        return args;
    }

    public Queue args(String args) {
        this.args = args;
        return this;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Integer getPriority() {
        return priority;
    }

    public Queue priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Queue dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateTimeout() {
        return dateTimeout;
    }

    public Queue dateTimeout(ZonedDateTime dateTimeout) {
        this.dateTimeout = dateTimeout;
        return this;
    }

    public void setDateTimeout(ZonedDateTime dateTimeout) {
        this.dateTimeout = dateTimeout;
    }

    public ZonedDateTime getDateCompleted() {
        return dateCompleted;
    }

    public Queue dateCompleted(ZonedDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
        return this;
    }

    public void setDateCompleted(ZonedDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Boolean isLocked() {
        return locked;
    }

    public Queue locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean isError() {
        return error;
    }

    public Queue error(Boolean error) {
        this.error = error;
        return this;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getResponse() {
        return response;
    }

    public Queue response(String response) {
        this.response = response;
        return this;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Queue queue = (Queue) o;
        if(queue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, queue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Queue{" +
            "id=" + id +
            ", queueId='" + queueId + "'" +
            ", siteId='" + siteId + "'" +
            ", task='" + task + "'" +
            ", args='" + args + "'" +
            ", priority='" + priority + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateTimeout='" + dateTimeout + "'" +
            ", dateCompleted='" + dateCompleted + "'" +
            ", locked='" + locked + "'" +
            ", error='" + error + "'" +
            ", response='" + response + "'" +
            '}';
    }
}
