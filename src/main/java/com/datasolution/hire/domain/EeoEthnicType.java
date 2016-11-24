package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EeoEthnicType.
 */
@Entity
@Table(name = "eeo_ethnic_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EeoEthnicType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "eeo_ethnic_type_id")
    private Integer eeoEthnicTypeId;

    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEeoEthnicTypeId() {
        return eeoEthnicTypeId;
    }

    public EeoEthnicType eeoEthnicTypeId(Integer eeoEthnicTypeId) {
        this.eeoEthnicTypeId = eeoEthnicTypeId;
        return this;
    }

    public void setEeoEthnicTypeId(Integer eeoEthnicTypeId) {
        this.eeoEthnicTypeId = eeoEthnicTypeId;
    }

    public String getType() {
        return type;
    }

    public EeoEthnicType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EeoEthnicType eeoEthnicType = (EeoEthnicType) o;
        if(eeoEthnicType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eeoEthnicType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EeoEthnicType{" +
            "id=" + id +
            ", eeoEthnicTypeId='" + eeoEthnicTypeId + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
