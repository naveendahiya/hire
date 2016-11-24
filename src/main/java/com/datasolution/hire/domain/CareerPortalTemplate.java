package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareerPortalTemplate.
 */
@Entity
@Table(name = "career_portal_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CareerPortalTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "career_portal_template_id")
    private Integer careerPortalTemplateId;

    @Column(name = "career_portal_name")
    private String careerPortalName;

    @Column(name = "setting")
    private String setting;

    @Column(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCareerPortalTemplateId() {
        return careerPortalTemplateId;
    }

    public CareerPortalTemplate careerPortalTemplateId(Integer careerPortalTemplateId) {
        this.careerPortalTemplateId = careerPortalTemplateId;
        return this;
    }

    public void setCareerPortalTemplateId(Integer careerPortalTemplateId) {
        this.careerPortalTemplateId = careerPortalTemplateId;
    }

    public String getCareerPortalName() {
        return careerPortalName;
    }

    public CareerPortalTemplate careerPortalName(String careerPortalName) {
        this.careerPortalName = careerPortalName;
        return this;
    }

    public void setCareerPortalName(String careerPortalName) {
        this.careerPortalName = careerPortalName;
    }

    public String getSetting() {
        return setting;
    }

    public CareerPortalTemplate setting(String setting) {
        this.setting = setting;
        return this;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getValue() {
        return value;
    }

    public CareerPortalTemplate value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CareerPortalTemplate careerPortalTemplate = (CareerPortalTemplate) o;
        if(careerPortalTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careerPortalTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerPortalTemplate{" +
            "id=" + id +
            ", careerPortalTemplateId='" + careerPortalTemplateId + "'" +
            ", careerPortalName='" + careerPortalName + "'" +
            ", setting='" + setting + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
