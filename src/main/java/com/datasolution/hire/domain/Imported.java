package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Imported.
 */
@Entity
@Table(name = "imported")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imported implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "imported_id")
    private Integer importedId;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "reverted")
    private Integer reverted;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "imported_errors")
    private String importedErrors;

    @Column(name = "added_lines")
    private Integer addedLines;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getImportedId() {
        return importedId;
    }

    public Imported importedId(Integer importedId) {
        this.importedId = importedId;
        return this;
    }

    public void setImportedId(Integer importedId) {
        this.importedId = importedId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Imported moduleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getReverted() {
        return reverted;
    }

    public Imported reverted(Integer reverted) {
        this.reverted = reverted;
        return this;
    }

    public void setReverted(Integer reverted) {
        this.reverted = reverted;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Imported siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getImportedErrors() {
        return importedErrors;
    }

    public Imported importedErrors(String importedErrors) {
        this.importedErrors = importedErrors;
        return this;
    }

    public void setImportedErrors(String importedErrors) {
        this.importedErrors = importedErrors;
    }

    public Integer getAddedLines() {
        return addedLines;
    }

    public Imported addedLines(Integer addedLines) {
        this.addedLines = addedLines;
        return this;
    }

    public void setAddedLines(Integer addedLines) {
        this.addedLines = addedLines;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Imported dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Imported imported = (Imported) o;
        if(imported.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, imported.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Imported{" +
            "id=" + id +
            ", importedId='" + importedId + "'" +
            ", moduleName='" + moduleName + "'" +
            ", reverted='" + reverted + "'" +
            ", siteId='" + siteId + "'" +
            ", importedErrors='" + importedErrors + "'" +
            ", addedLines='" + addedLines + "'" +
            ", dateCreated='" + dateCreated + "'" +
            '}';
    }
}
