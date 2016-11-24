package com.datasolution.hire.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "site_id")
    private Integer siteId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_home")
    private String phoneHome;

    @Column(name = "phone_cell")
    private String phoneCell;

    @Column(name = "phone_work")
    private String phoneWork;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "source")
    private String source;

    @Column(name = "date_available")
    private ZonedDateTime dateAvailable;

    @Column(name = "can_relocate")
    private Integer canRelocate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "key_skills")
    private String keySkills;

    @Column(name = "current_employer")
    private String currentEmployer;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "owner")
    private Integer owner;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "imported_id")
    private Integer importedId;

    @Column(name = "is_hot")
    private Integer isHot;

    @Column(name = "eeo_ethnic_type_id")
    private Integer eeoEthnicTypeId;

    @Column(name = "eeo_veteran_type_id")
    private Integer eeoVeteranTypeId;

    @Column(name = "eeo_disability_status")
    private String eeoDisabilityStatus;

    @Column(name = "eeo_gender")
    private String eeoGender;

    @Column(name = "desired_pay")
    private String desiredPay;

    @Column(name = "current_pay")
    private String currentPay;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "is_admin_hidden")
    private Integer isAdminHidden;

    @Column(name = "best_time_to_call")
    private String bestTimeToCall;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public Candidate candidateId(Integer candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Candidate siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getLastName() {
        return lastName;
    }

    public Candidate lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Candidate firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Candidate middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public Candidate phoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
        return this;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public String getPhoneCell() {
        return phoneCell;
    }

    public Candidate phoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
        return this;
    }

    public void setPhoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public Candidate phoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
        return this;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getAddress() {
        return address;
    }

    public Candidate address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Candidate city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Candidate state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Candidate zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSource() {
        return source;
    }

    public Candidate source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ZonedDateTime getDateAvailable() {
        return dateAvailable;
    }

    public Candidate dateAvailable(ZonedDateTime dateAvailable) {
        this.dateAvailable = dateAvailable;
        return this;
    }

    public void setDateAvailable(ZonedDateTime dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public Integer getCanRelocate() {
        return canRelocate;
    }

    public Candidate canRelocate(Integer canRelocate) {
        this.canRelocate = canRelocate;
        return this;
    }

    public void setCanRelocate(Integer canRelocate) {
        this.canRelocate = canRelocate;
    }

    public String getNotes() {
        return notes;
    }

    public Candidate notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getKeySkills() {
        return keySkills;
    }

    public Candidate keySkills(String keySkills) {
        this.keySkills = keySkills;
        return this;
    }

    public void setKeySkills(String keySkills) {
        this.keySkills = keySkills;
    }

    public String getCurrentEmployer() {
        return currentEmployer;
    }

    public Candidate currentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
        return this;
    }

    public void setCurrentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public Candidate enteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getOwner() {
        return owner;
    }

    public Candidate owner(Integer owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Candidate dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Candidate dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public String getEmail1() {
        return email1;
    }

    public Candidate email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Candidate email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getWebSite() {
        return webSite;
    }

    public Candidate webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Integer getImportedId() {
        return importedId;
    }

    public Candidate importedId(Integer importedId) {
        this.importedId = importedId;
        return this;
    }

    public void setImportedId(Integer importedId) {
        this.importedId = importedId;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public Candidate isHot(Integer isHot) {
        this.isHot = isHot;
        return this;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getEeoEthnicTypeId() {
        return eeoEthnicTypeId;
    }

    public Candidate eeoEthnicTypeId(Integer eeoEthnicTypeId) {
        this.eeoEthnicTypeId = eeoEthnicTypeId;
        return this;
    }

    public void setEeoEthnicTypeId(Integer eeoEthnicTypeId) {
        this.eeoEthnicTypeId = eeoEthnicTypeId;
    }

    public Integer getEeoVeteranTypeId() {
        return eeoVeteranTypeId;
    }

    public Candidate eeoVeteranTypeId(Integer eeoVeteranTypeId) {
        this.eeoVeteranTypeId = eeoVeteranTypeId;
        return this;
    }

    public void setEeoVeteranTypeId(Integer eeoVeteranTypeId) {
        this.eeoVeteranTypeId = eeoVeteranTypeId;
    }

    public String getEeoDisabilityStatus() {
        return eeoDisabilityStatus;
    }

    public Candidate eeoDisabilityStatus(String eeoDisabilityStatus) {
        this.eeoDisabilityStatus = eeoDisabilityStatus;
        return this;
    }

    public void setEeoDisabilityStatus(String eeoDisabilityStatus) {
        this.eeoDisabilityStatus = eeoDisabilityStatus;
    }

    public String getEeoGender() {
        return eeoGender;
    }

    public Candidate eeoGender(String eeoGender) {
        this.eeoGender = eeoGender;
        return this;
    }

    public void setEeoGender(String eeoGender) {
        this.eeoGender = eeoGender;
    }

    public String getDesiredPay() {
        return desiredPay;
    }

    public Candidate desiredPay(String desiredPay) {
        this.desiredPay = desiredPay;
        return this;
    }

    public void setDesiredPay(String desiredPay) {
        this.desiredPay = desiredPay;
    }

    public String getCurrentPay() {
        return currentPay;
    }

    public Candidate currentPay(String currentPay) {
        this.currentPay = currentPay;
        return this;
    }

    public void setCurrentPay(String currentPay) {
        this.currentPay = currentPay;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public Candidate isActive(Integer isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsAdminHidden() {
        return isAdminHidden;
    }

    public Candidate isAdminHidden(Integer isAdminHidden) {
        this.isAdminHidden = isAdminHidden;
        return this;
    }

    public void setIsAdminHidden(Integer isAdminHidden) {
        this.isAdminHidden = isAdminHidden;
    }

    public String getBestTimeToCall() {
        return bestTimeToCall;
    }

    public Candidate bestTimeToCall(String bestTimeToCall) {
        this.bestTimeToCall = bestTimeToCall;
        return this;
    }

    public void setBestTimeToCall(String bestTimeToCall) {
        this.bestTimeToCall = bestTimeToCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        if(candidate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, candidate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + id +
            ", candidateId='" + candidateId + "'" +
            ", siteId='" + siteId + "'" +
            ", lastName='" + lastName + "'" +
            ", firstName='" + firstName + "'" +
            ", middleName='" + middleName + "'" +
            ", phoneHome='" + phoneHome + "'" +
            ", phoneCell='" + phoneCell + "'" +
            ", phoneWork='" + phoneWork + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", source='" + source + "'" +
            ", dateAvailable='" + dateAvailable + "'" +
            ", canRelocate='" + canRelocate + "'" +
            ", notes='" + notes + "'" +
            ", keySkills='" + keySkills + "'" +
            ", currentEmployer='" + currentEmployer + "'" +
            ", enteredBy='" + enteredBy + "'" +
            ", owner='" + owner + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", email1='" + email1 + "'" +
            ", email2='" + email2 + "'" +
            ", webSite='" + webSite + "'" +
            ", importedId='" + importedId + "'" +
            ", isHot='" + isHot + "'" +
            ", eeoEthnicTypeId='" + eeoEthnicTypeId + "'" +
            ", eeoVeteranTypeId='" + eeoVeteranTypeId + "'" +
            ", eeoDisabilityStatus='" + eeoDisabilityStatus + "'" +
            ", eeoGender='" + eeoGender + "'" +
            ", desiredPay='" + desiredPay + "'" +
            ", currentPay='" + currentPay + "'" +
            ", isActive='" + isActive + "'" +
            ", isAdminHidden='" + isAdminHidden + "'" +
            ", bestTimeToCall='" + bestTimeToCall + "'" +
            '}';
    }
}
