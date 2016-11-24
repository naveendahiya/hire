package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EeoVeteranType.
 */
@Entity
@Table(name = "eeo_veteran_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EeoVeteranType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "eeo_veteran_type_id")
    private Integer eeoVeteranTypeId;

    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEeoVeteranTypeId() {
        return eeoVeteranTypeId;
    }

    public EeoVeteranType eeoVeteranTypeId(Integer eeoVeteranTypeId) {
        this.eeoVeteranTypeId = eeoVeteranTypeId;
        return this;
    }

    public void setEeoVeteranTypeId(Integer eeoVeteranTypeId) {
        this.eeoVeteranTypeId = eeoVeteranTypeId;
    }

    public String getType() {
        return type;
    }

    public EeoVeteranType type(String type) {
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
        EeoVeteranType eeoVeteranType = (EeoVeteranType) o;
        if(eeoVeteranType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eeoVeteranType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EeoVeteranType{" +
            "id=" + id +
            ", eeoVeteranTypeId='" + eeoVeteranTypeId + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
