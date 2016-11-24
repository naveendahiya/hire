package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contact_id")
    private Integer contactId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "title")
    private String title;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "phone_work")
    private String phoneWork;

    @Column(name = "phone_cell")
    private String phoneCell;

    @Column(name = "phone_other")
    private String phoneOther;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "is_hot")
    private Integer isHot;

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

    @Column(name = "left_company")
    private Integer leftCompany;

    @Column(name = "imported_id")
    private Integer importedId;

    @Column(name = "company_department_id")
    private Integer companyDepartmentId;

    @Column(name = "reports_to")
    private Integer reportsTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContactId() {
        return contactId;
    }

    public Contact contactId(Integer contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Contact companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Contact siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public Contact title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail1() {
        return email1;
    }

    public Contact email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Contact email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public Contact phoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
        return this;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getPhoneCell() {
        return phoneCell;
    }

    public Contact phoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
        return this;
    }

    public void setPhoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
    }

    public String getPhoneOther() {
        return phoneOther;
    }

    public Contact phoneOther(String phoneOther) {
        this.phoneOther = phoneOther;
        return this;
    }

    public void setPhoneOther(String phoneOther) {
        this.phoneOther = phoneOther;
    }

    public String getAddress() {
        return address;
    }

    public Contact address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Contact city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Contact state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Contact zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public Contact isHot(Integer isHot) {
        this.isHot = isHot;
        return this;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getNotes() {
        return notes;
    }

    public Contact notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public Contact enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getOwner() {
        return owner;
    }

    public Contact owner(Integer owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Contact dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Contact dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getLeftCompany() {
        return leftCompany;
    }

    public Contact leftCompany(Integer leftCompany) {
        this.leftCompany = leftCompany;
        return this;
    }

    public void setLeftCompany(Integer leftCompany) {
        this.leftCompany = leftCompany;
    }

    public Integer getImportedId() {
        return importedId;
    }

    public Contact importedId(Integer importedId) {
        this.importedId = importedId;
        return this;
    }

    public void setImportedId(Integer importedId) {
        this.importedId = importedId;
    }

    public Integer getCompanyDepartmentId() {
        return companyDepartmentId;
    }

    public Contact companyDepartmentId(Integer companyDepartmentId) {
        this.companyDepartmentId = companyDepartmentId;
        return this;
    }

    public void setCompanyDepartmentId(Integer companyDepartmentId) {
        this.companyDepartmentId = companyDepartmentId;
    }

    public Integer getReportsTo() {
        return reportsTo;
    }

    public Contact reportsTo(Integer reportsTo) {
        this.reportsTo = reportsTo;
        return this;
    }

    public void setReportsTo(Integer reportsTo) {
        this.reportsTo = reportsTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if(contact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + id +
            ", contactId='" + contactId + "'" +
            ", companyId='" + companyId + "'" +
            ", siteId='" + siteId + "'" +
            ", lastName='" + lastName + "'" +
            ", firstName='" + firstName + "'" +
            ", title='" + title + "'" +
            ", email1='" + email1 + "'" +
            ", email2='" + email2 + "'" +
            ", phoneWork='" + phoneWork + "'" +
            ", phoneCell='" + phoneCell + "'" +
            ", phoneOther='" + phoneOther + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", isHot='" + isHot + "'" +
            ", notes='" + notes + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", owner='" + owner + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", leftCompany='" + leftCompany + "'" +
            ", importedId='" + importedId + "'" +
            ", companyDepartmentId='" + companyDepartmentId + "'" +
            ", reportsTo='" + reportsTo + "'" +
            '}';
    }
}
