package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A HttpLog.
 */
@Entity
@Table(name = "http_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HttpLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "remote_addr")
    private String remoteAddr;

    @Column(name = "http_user_agent")
    private String httpUserAgent;

    @Column(name = "script_filename")
    private String scriptFilename;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "query_string")
    private String queryString;

    @Column(name = "request_uri")
    private String requestUri;

    @Column(name = "script_name")
    private String scriptName;

    @Column(name = "log_type")
    private Integer logType;

    @Column(name = "date")
    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLogId() {
        return logId;
    }

    public HttpLog logId(Integer logId) {
        this.logId = logId;
        return this;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public HttpLog siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public HttpLog remoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
        return this;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public HttpLog httpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
        return this;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    public String getScriptFilename() {
        return scriptFilename;
    }

    public HttpLog scriptFilename(String scriptFilename) {
        this.scriptFilename = scriptFilename;
        return this;
    }

    public void setScriptFilename(String scriptFilename) {
        this.scriptFilename = scriptFilename;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public HttpLog requestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getQueryString() {
        return queryString;
    }

    public HttpLog queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public HttpLog requestUri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getScriptName() {
        return scriptName;
    }

    public HttpLog scriptName(String scriptName) {
        this.scriptName = scriptName;
        return this;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Integer getLogType() {
        return logType;
    }

    public HttpLog logType(Integer logType) {
        this.logType = logType;
        return this;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public HttpLog date(ZonedDateTime date) {
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
        HttpLog httpLog = (HttpLog) o;
        if(httpLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, httpLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HttpLog{" +
            "id=" + id +
            ", logId='" + logId + "'" +
            ", siteId='" + siteId + "'" +
            ", remoteAddr='" + remoteAddr + "'" +
            ", httpUserAgent='" + httpUserAgent + "'" +
            ", scriptFilename='" + scriptFilename + "'" +
            ", requestMethod='" + requestMethod + "'" +
            ", queryString='" + queryString + "'" +
            ", requestUri='" + requestUri + "'" +
            ", scriptName='" + scriptName + "'" +
            ", logType='" + logType + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
