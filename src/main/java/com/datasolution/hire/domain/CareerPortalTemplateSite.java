package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareerPortalTemplateSite.
 */
@Entity
@Table(name = "career_portal_template_site")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CareerPortalTemplateSite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "career_portal_template_id")
    private Integer careerPortalTemplateId;

    @Column(name = "career_portal_name")
    private String careerPortalName;

    @Column(name = "site_id")
    private Integer siteId;

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

    public CareerPortalTemplateSite careerPortalTemplateId(Integer careerPortalTemplateId) {
        this.careerPortalTemplateId = careerPortalTemplateId;
        return this;
    }

    public void setCareerPortalTemplateId(Integer careerPortalTemplateId) {
        this.careerPortalTemplateId = careerPortalTemplateId;
    }

    public String getCareerPortalName() {
        return careerPortalName;
    }

    public CareerPortalTemplateSite careerPortalName(String careerPortalName) {
        this.careerPortalName = careerPortalName;
        return this;
    }

    public void setCareerPortalName(String careerPortalName) {
        this.careerPortalName = careerPortalName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public CareerPortalTemplateSite siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSetting() {
        return setting;
    }

    public CareerPortalTemplateSite setting(String setting) {
        this.setting = setting;
        return this;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getValue() {
        return value;
    }

    public CareerPortalTemplateSite value(String value) {
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
        CareerPortalTemplateSite careerPortalTemplateSite = (CareerPortalTemplateSite) o;
        if(careerPortalTemplateSite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careerPortalTemplateSite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerPortalTemplateSite{" +
            "id=" + id +
            ", careerPortalTemplateId='" + careerPortalTemplateId + "'" +
            ", careerPortalName='" + careerPortalName + "'" +
            ", siteId='" + siteId + "'" +
            ", setting='" + setting + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
