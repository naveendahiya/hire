package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "billing_contact")
    private Integer billingContact;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "url")
    private String url;

    @Column(name = "key_technologies")
    private String keyTechnologies;

    @Column(name = "notes")
    private String notes;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "owner")
    private Integer owner;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @Column(name = "is_hot")
    private Integer isHot;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "imported_id")
    private Integer importedId;

    @Column(name = "default_company")
    private Integer defaultCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Company companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Company siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getBillingContact() {
        return billingContact;
    }

    public Company billingContact(Integer billingContact) {
        this.billingContact = billingContact;
        return this;
    }

    public void setBillingContact(Integer billingContact) {
        this.billingContact = billingContact;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Company city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Company state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Company zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public Company phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Company phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getUrl() {
        return url;
    }

    public Company url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyTechnologies() {
        return keyTechnologies;
    }

    public Company keyTechnologies(String keyTechnologies) {
        this.keyTechnologies = keyTechnologies;
        return this;
    }

    public void setKeyTechnologies(String keyTechnologies) {
        this.keyTechnologies = keyTechnologies;
    }

    public String getNotes() {
        return notes;
    }

    public Company notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public Company enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getOwner() {
        return owner;
    }

    public Company owner(Integer owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Company dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Company dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public Company isHot(Integer isHot) {
        this.isHot = isHot;
        return this;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public Company faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Integer getImportedId() {
        return importedId;
    }

    public Company importedId(Integer importedId) {
        this.importedId = importedId;
        return this;
    }

    public void setImportedId(Integer importedId) {
        this.importedId = importedId;
    }

    public Integer getDefaultCompany() {
        return defaultCompany;
    }

    public Company defaultCompany(Integer defaultCompany) {
        this.defaultCompany = defaultCompany;
        return this;
    }

    public void setDefaultCompany(Integer defaultCompany) {
        this.defaultCompany = defaultCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if(company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", companyId='" + companyId + "'" +
            ", siteId='" + siteId + "'" +
            ", billingContact='" + billingContact + "'" +
            ", name='" + name + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", phone1='" + phone1 + "'" +
            ", phone2='" + phone2 + "'" +
            ", url='" + url + "'" +
            ", keyTechnologies='" + keyTechnologies + "'" +
            ", notes='" + notes + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", owner='" + owner + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", isHot='" + isHot + "'" +
            ", faxNumber='" + faxNumber + "'" +
            ", importedId='" + importedId + "'" +
            ", defaultCompany='" + defaultCompany + "'" +
            '}';
    }
}
