package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ModuleSchema.
 */
@Entity
@Table(name = "module_schema")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModuleSchema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "module_schema_id")
    private Integer moduleSchemaId;

    @Column(name = "version")
    private Integer version;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModuleSchemaId() {
        return moduleSchemaId;
    }

    public ModuleSchema moduleSchemaId(Integer moduleSchemaId) {
        this.moduleSchemaId = moduleSchemaId;
        return this;
    }

    public void setModuleSchemaId(Integer moduleSchemaId) {
        this.moduleSchemaId = moduleSchemaId;
    }

    public Integer getVersion() {
        return version;
    }

    public ModuleSchema version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public ModuleSchema name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleSchema moduleSchema = (ModuleSchema) o;
        if(moduleSchema.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, moduleSchema.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModuleSchema{" +
            "id=" + id +
            ", moduleSchemaId='" + moduleSchemaId + "'" +
            ", version='" + version + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
