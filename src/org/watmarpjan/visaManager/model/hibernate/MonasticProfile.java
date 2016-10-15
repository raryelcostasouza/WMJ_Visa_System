/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author WMJ_user
 */
@Entity
@Table(name = "MONASTIC_PROFILE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "MonasticProfile.findAll", query = "SELECT m FROM MonasticProfile m"),
    @NamedQuery(name = "MonasticProfile.findByIdProfile", query = "SELECT m FROM MonasticProfile m WHERE m.idProfile = :idProfile"),
    @NamedQuery(name = "MonasticProfile.findByArrivalCardNumber", query = "SELECT m FROM MonasticProfile m WHERE m.arrivalCardNumber = :arrivalCardNumber"),
    @NamedQuery(name = "MonasticProfile.findByArrivalLastEntryDate", query = "SELECT m FROM MonasticProfile m WHERE m.arrivalLastEntryDate = :arrivalLastEntryDate"),
    @NamedQuery(name = "MonasticProfile.findByArrivalPortOfEntry", query = "SELECT m FROM MonasticProfile m WHERE m.arrivalPortOfEntry = :arrivalPortOfEntry"),
    @NamedQuery(name = "MonasticProfile.findByArrivalTravelBy", query = "SELECT m FROM MonasticProfile m WHERE m.arrivalTravelBy = :arrivalTravelBy"),
    @NamedQuery(name = "MonasticProfile.findByArrivalTravelFrom", query = "SELECT m FROM MonasticProfile m WHERE m.arrivalTravelFrom = :arrivalTravelFrom"),
    @NamedQuery(name = "MonasticProfile.findByBhikkhuOrdDate", query = "SELECT m FROM MonasticProfile m WHERE m.bhikkhuOrdDate = :bhikkhuOrdDate"),
    @NamedQuery(name = "MonasticProfile.findByBirthCountry", query = "SELECT m FROM MonasticProfile m WHERE m.birthCountry = :birthCountry"),
    @NamedQuery(name = "MonasticProfile.findByBirthDate", query = "SELECT m FROM MonasticProfile m WHERE m.birthDate = :birthDate"),
    @NamedQuery(name = "MonasticProfile.findByBirthPlace", query = "SELECT m FROM MonasticProfile m WHERE m.birthPlace = :birthPlace"),
    @NamedQuery(name = "MonasticProfile.findByBysuddhiIssueDate", query = "SELECT m FROM MonasticProfile m WHERE m.bysuddhiIssueDate = :bysuddhiIssueDate"),
    @NamedQuery(name = "MonasticProfile.findByCertificateDuration", query = "SELECT m FROM MonasticProfile m WHERE m.certificateDuration = :certificateDuration"),
    @NamedQuery(name = "MonasticProfile.findByCertificateEnglish", query = "SELECT m FROM MonasticProfile m WHERE m.certificateEnglish = :certificateEnglish"),
    @NamedQuery(name = "MonasticProfile.findByCertificateGradYear", query = "SELECT m FROM MonasticProfile m WHERE m.certificateGradYear = :certificateGradYear"),
    @NamedQuery(name = "MonasticProfile.findByCertificateThai", query = "SELECT m FROM MonasticProfile m WHERE m.certificateThai = :certificateThai"),
    @NamedQuery(name = "MonasticProfile.findByDhammaStudies", query = "SELECT m FROM MonasticProfile m WHERE m.dhammaStudies = :dhammaStudies"),
    @NamedQuery(name = "MonasticProfile.findByEmail", query = "SELECT m FROM MonasticProfile m WHERE m.email = :email"),
    @NamedQuery(name = "MonasticProfile.findByEthnicity", query = "SELECT m FROM MonasticProfile m WHERE m.ethnicity = :ethnicity"),
    @NamedQuery(name = "MonasticProfile.findByFatherName", query = "SELECT m FROM MonasticProfile m WHERE m.fatherName = :fatherName"),
    @NamedQuery(name = "MonasticProfile.findByFirstEntryDate", query = "SELECT m FROM MonasticProfile m WHERE m.firstEntryDate = :firstEntryDate"),
    @NamedQuery(name = "MonasticProfile.findByLastName", query = "SELECT m FROM MonasticProfile m WHERE m.lastName = :lastName"),
    @NamedQuery(name = "MonasticProfile.findByMiddleName", query = "SELECT m FROM MonasticProfile m WHERE m.middleName = :middleName"),
    @NamedQuery(name = "MonasticProfile.findByMotherName", query = "SELECT m FROM MonasticProfile m WHERE m.motherName = :motherName"),
    @NamedQuery(name = "MonasticProfile.findByMonasticName", query = "SELECT m FROM MonasticProfile m WHERE m.monasticName = :monasticName"),
    @NamedQuery(name = "MonasticProfile.findByNameAdviserToCome", query = "SELECT m FROM MonasticProfile m WHERE m.nameAdviserToCome = :nameAdviserToCome"),
    @NamedQuery(name = "MonasticProfile.findByNationality", query = "SELECT m FROM MonasticProfile m WHERE m.nationality = :nationality"),
    @NamedQuery(name = "MonasticProfile.findByNext90DayNotice", query = "SELECT m FROM MonasticProfile m WHERE m.next90DayNotice = :next90DayNotice"),
    @NamedQuery(name = "MonasticProfile.findByNickname", query = "SELECT m FROM MonasticProfile m WHERE m.nickname = :nickname"),
    @NamedQuery(name = "MonasticProfile.findByOccupationEnglish", query = "SELECT m FROM MonasticProfile m WHERE m.occupationEnglish = :occupationEnglish"),
    @NamedQuery(name = "MonasticProfile.findByOccupationThai", query = "SELECT m FROM MonasticProfile m WHERE m.occupationThai = :occupationThai"),
    @NamedQuery(name = "MonasticProfile.findByPahkahwOrdDate", query = "SELECT m FROM MonasticProfile m WHERE m.pahkahwOrdDate = :pahkahwOrdDate"),
    @NamedQuery(name = "MonasticProfile.findByPaliNameEnglish", query = "SELECT m FROM MonasticProfile m WHERE m.paliNameEnglish = :paliNameEnglish"),
    @NamedQuery(name = "MonasticProfile.findByPaliNameThai", query = "SELECT m FROM MonasticProfile m WHERE m.paliNameThai = :paliNameThai"),
    @NamedQuery(name = "MonasticProfile.findByPassportExpiryDate", query = "SELECT m FROM MonasticProfile m WHERE m.passportExpiryDate = :passportExpiryDate"),
    @NamedQuery(name = "MonasticProfile.findByPassportIssuedAt", query = "SELECT m FROM MonasticProfile m WHERE m.passportIssuedAt = :passportIssuedAt"),
    @NamedQuery(name = "MonasticProfile.findByPassportNumber", query = "SELECT m FROM MonasticProfile m WHERE m.passportNumber = :passportNumber"),
    @NamedQuery(name = "MonasticProfile.findByPreviousResidenceCountry", query = "SELECT m FROM MonasticProfile m WHERE m.previousResidenceCountry = :previousResidenceCountry"),
    @NamedQuery(name = "MonasticProfile.findBySamaneraOrdDate", query = "SELECT m FROM MonasticProfile m WHERE m.samaneraOrdDate = :samaneraOrdDate"),
    @NamedQuery(name = "MonasticProfile.findBySchool", query = "SELECT m FROM MonasticProfile m WHERE m.school = :school"),
    @NamedQuery(name = "MonasticProfile.findByStatus", query = "SELECT m FROM MonasticProfile m WHERE m.status = :status"),
    @NamedQuery(name = "MonasticProfile.findByVisaExpiryDate", query = "SELECT m FROM MonasticProfile m WHERE m.visaExpiryDate = :visaExpiryDate"),
    @NamedQuery(name = "MonasticProfile.findByVisaNumber", query = "SELECT m FROM MonasticProfile m WHERE m.visaNumber = :visaNumber"),
    @NamedQuery(name = "MonasticProfile.findByVisaType", query = "SELECT m FROM MonasticProfile m WHERE m.visaType = :visaType"),
    @NamedQuery(name = "MonasticProfile.findByPassportIssueDate", query = "SELECT m FROM MonasticProfile m WHERE m.passportIssueDate = :passportIssueDate")
})
public class MonasticProfile implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROFILE")
    private Integer idProfile;
    @Column(name = "ARRIVAL_CARD_NUMBER")
    private String arrivalCardNumber;
    @Column(name = "ARRIVAL_LAST_ENTRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date arrivalLastEntryDate;
    @Column(name = "ARRIVAL_PORT_OF_ENTRY")
    private String arrivalPortOfEntry;
    @Column(name = "ARRIVAL_TRAVEL_BY")
    private String arrivalTravelBy;
    @Column(name = "ARRIVAL_TRAVEL_FROM")
    private String arrivalTravelFrom;
    @Column(name = "BHIKKHU_ORD_DATE")
    @Temporal(TemporalType.DATE)
    private Date bhikkhuOrdDate;
    @Column(name = "BIRTH_COUNTRY")
    private String birthCountry;
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "BIRTH_PLACE")
    private String birthPlace;
    @Column(name = "BYSUDDHI_ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date bysuddhiIssueDate;
    @Column(name = "CERTIFICATE_DURATION")
    private Integer certificateDuration;
    @Column(name = "CERTIFICATE_ENGLISH")
    private String certificateEnglish;
    @Column(name = "CERTIFICATE_GRAD_YEAR")
    private Integer certificateGradYear;
    @Column(name = "CERTIFICATE_THAI")
    private String certificateThai;
    @Basic(optional = false)
    @Column(name = "DHAMMA_STUDIES")
    private String dhammaStudies;
    private String email;
    @Lob
    @Column(name = "EMERGENCY_CONTACT")
    private String emergencyContact;
    private String ethnicity;
    @Column(name = "FATHER_NAME")
    private String fatherName;
    @Column(name = "FIRST_ENTRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date firstEntryDate;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "MOTHER_NAME")
    private String motherName;
    @Column(name = "MONASTIC_NAME")
    private String monasticName;
    @Column(name = "NAME_ADVISER_TO_COME")
    private String nameAdviserToCome;
    private String nationality;
    @Column(name = "NEXT_90_DAY_NOTICE")
    @Temporal(TemporalType.DATE)
    private Date next90DayNotice;
    private String nickname;
    @Column(name = "OCCUPATION_ENGLISH")
    private String occupationEnglish;
    @Column(name = "OCCUPATION_THAI")
    private String occupationThai;
    @Column(name = "PAHKAHW_ORD_DATE")
    @Temporal(TemporalType.DATE)
    private Date pahkahwOrdDate;
    @Column(name = "PALI_NAME_ENGLISH")
    private String paliNameEnglish;
    @Column(name = "PALI_NAME_THAI")
    private String paliNameThai;
    @Column(name = "PASSPORT_EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date passportExpiryDate;
    @Column(name = "PASSPORT_ISSUED_AT")
    private String passportIssuedAt;
    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;
    @Column(name = "PREVIOUS_RESIDENCE_COUNTRY")
    private String previousResidenceCountry;
    @Column(name = "SAMANERA_ORD_DATE")
    @Temporal(TemporalType.DATE)
    private Date samaneraOrdDate;
    private String school;
    @Basic(optional = false)
    private String status;
    @Column(name = "VISA_EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date visaExpiryDate;
    @Column(name = "VISA_NUMBER")
    private String visaNumber;
    @Column(name = "VISA_TYPE")
    private String visaType;
    @Column(name = "PASSPORT_ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date passportIssueDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monasticProfile")
    private Set<PassportScan> passportScanSet;
    @JoinColumn(name = "MONASTERY_ADVISER_TO_COME", referencedColumnName = "ID_MONASTERY")
    @ManyToOne
    private Monastery monasteryAdviserToCome;
    @JoinColumn(name = "MONASTERY_RESIDING_AT", referencedColumnName = "ID_MONASTERY")
    @ManyToOne
    private Monastery monasteryResidingAt;
    @JoinColumn(name = "MONASTERY_ORDAINED_AT", referencedColumnName = "ID_MONASTERY")
    @ManyToOne
    private Monastery monasteryOrdainedAt;
    @JoinColumn(name = "FORM_TM30", referencedColumnName = "ID_FORM")
    @ManyToOne
    private Tm30NotificationResidence formTm30;
    @JoinColumn(name = "UPAJJHAYA", referencedColumnName = "ID_UPAJJHAYA")
    @ManyToOne
    private Upajjhaya upajjhaya;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monasticProfile")
    private Set<VisaExtension> visaExtensionSet;

    public MonasticProfile()
    {
    }

    public MonasticProfile(Integer idProfile)
    {
        this.idProfile = idProfile;
    }

    public MonasticProfile(Integer idProfile, String dhammaStudies, String status)
    {
        this.idProfile = idProfile;
        this.dhammaStudies = dhammaStudies;
        this.status = status;
    }

    public Integer getIdProfile()
    {
        return idProfile;
    }

    public void setIdProfile(Integer idProfile)
    {
        this.idProfile = idProfile;
    }

    public String getArrivalCardNumber()
    {
        return arrivalCardNumber;
    }

    public void setArrivalCardNumber(String arrivalCardNumber)
    {
        this.arrivalCardNumber = arrivalCardNumber;
    }

    public Date getArrivalLastEntryDate()
    {
        return arrivalLastEntryDate;
    }

    public void setArrivalLastEntryDate(Date arrivalLastEntryDate)
    {
        this.arrivalLastEntryDate = arrivalLastEntryDate;
    }

    public String getArrivalPortOfEntry()
    {
        return arrivalPortOfEntry;
    }

    public void setArrivalPortOfEntry(String arrivalPortOfEntry)
    {
        this.arrivalPortOfEntry = arrivalPortOfEntry;
    }

    public String getArrivalTravelBy()
    {
        return arrivalTravelBy;
    }

    public void setArrivalTravelBy(String arrivalTravelBy)
    {
        this.arrivalTravelBy = arrivalTravelBy;
    }

    public String getArrivalTravelFrom()
    {
        return arrivalTravelFrom;
    }

    public void setArrivalTravelFrom(String arrivalTravelFrom)
    {
        this.arrivalTravelFrom = arrivalTravelFrom;
    }

    public Date getBhikkhuOrdDate()
    {
        return bhikkhuOrdDate;
    }

    public void setBhikkhuOrdDate(Date bhikkhuOrdDate)
    {
        this.bhikkhuOrdDate = bhikkhuOrdDate;
    }

    public String getBirthCountry()
    {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry)
    {
        this.birthCountry = birthCountry;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    public Date getBysuddhiIssueDate()
    {
        return bysuddhiIssueDate;
    }

    public void setBysuddhiIssueDate(Date bysuddhiIssueDate)
    {
        this.bysuddhiIssueDate = bysuddhiIssueDate;
    }

    public Integer getCertificateDuration()
    {
        return certificateDuration;
    }

    public void setCertificateDuration(Integer certificateDuration)
    {
        this.certificateDuration = certificateDuration;
    }

    public String getCertificateEnglish()
    {
        return certificateEnglish;
    }

    public void setCertificateEnglish(String certificateEnglish)
    {
        this.certificateEnglish = certificateEnglish;
    }

    public Integer getCertificateGradYear()
    {
        return certificateGradYear;
    }

    public void setCertificateGradYear(Integer certificateGradYear)
    {
        this.certificateGradYear = certificateGradYear;
    }

    public String getCertificateThai()
    {
        return certificateThai;
    }

    public void setCertificateThai(String certificateThai)
    {
        this.certificateThai = certificateThai;
    }

    public String getDhammaStudies()
    {
        return dhammaStudies;
    }

    public void setDhammaStudies(String dhammaStudies)
    {
        this.dhammaStudies = dhammaStudies;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    public String getEthnicity()
    {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity)
    {
        this.ethnicity = ethnicity;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public void setFatherName(String fatherName)
    {
        this.fatherName = fatherName;
    }

    public Date getFirstEntryDate()
    {
        return firstEntryDate;
    }

    public void setFirstEntryDate(Date firstEntryDate)
    {
        this.firstEntryDate = firstEntryDate;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getMotherName()
    {
        return motherName;
    }

    public void setMotherName(String motherName)
    {
        this.motherName = motherName;
    }

    public String getMonasticName()
    {
        return monasticName;
    }

    public void setMonasticName(String monasticName)
    {
        this.monasticName = monasticName;
    }

    public String getNameAdviserToCome()
    {
        return nameAdviserToCome;
    }

    public void setNameAdviserToCome(String nameAdviserToCome)
    {
        this.nameAdviserToCome = nameAdviserToCome;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    public Date getNext90DayNotice()
    {
        return next90DayNotice;
    }

    public void setNext90DayNotice(Date next90DayNotice)
    {
        this.next90DayNotice = next90DayNotice;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getOccupationEnglish()
    {
        return occupationEnglish;
    }

    public void setOccupationEnglish(String occupationEnglish)
    {
        this.occupationEnglish = occupationEnglish;
    }

    public String getOccupationThai()
    {
        return occupationThai;
    }

    public void setOccupationThai(String occupationThai)
    {
        this.occupationThai = occupationThai;
    }

    public Date getPahkahwOrdDate()
    {
        return pahkahwOrdDate;
    }

    public void setPahkahwOrdDate(Date pahkahwOrdDate)
    {
        this.pahkahwOrdDate = pahkahwOrdDate;
    }

    public String getPaliNameEnglish()
    {
        return paliNameEnglish;
    }

    public void setPaliNameEnglish(String paliNameEnglish)
    {
        this.paliNameEnglish = paliNameEnglish;
    }

    public String getPaliNameThai()
    {
        return paliNameThai;
    }

    public void setPaliNameThai(String paliNameThai)
    {
        this.paliNameThai = paliNameThai;
    }

    public Date getPassportExpiryDate()
    {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(Date passportExpiryDate)
    {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getPassportIssuedAt()
    {
        return passportIssuedAt;
    }

    public void setPassportIssuedAt(String passportIssuedAt)
    {
        this.passportIssuedAt = passportIssuedAt;
    }

    public String getPassportNumber()
    {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
    }

    public String getPreviousResidenceCountry()
    {
        return previousResidenceCountry;
    }

    public void setPreviousResidenceCountry(String previousResidenceCountry)
    {
        this.previousResidenceCountry = previousResidenceCountry;
    }

    public Date getSamaneraOrdDate()
    {
        return samaneraOrdDate;
    }

    public void setSamaneraOrdDate(Date samaneraOrdDate)
    {
        this.samaneraOrdDate = samaneraOrdDate;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getVisaExpiryDate()
    {
        return visaExpiryDate;
    }

    public void setVisaExpiryDate(Date visaExpiryDate)
    {
        this.visaExpiryDate = visaExpiryDate;
    }

    public String getVisaNumber()
    {
        return visaNumber;
    }

    public void setVisaNumber(String visaNumber)
    {
        this.visaNumber = visaNumber;
    }

    public String getVisaType()
    {
        return visaType;
    }

    public void setVisaType(String visaType)
    {
        this.visaType = visaType;
    }

    public Date getPassportIssueDate()
    {
        return passportIssueDate;
    }

    public void setPassportIssueDate(Date passportIssueDate)
    {
        this.passportIssueDate = passportIssueDate;
    }

    @XmlTransient
    public Set<PassportScan> getPassportScanSet()
    {
        return passportScanSet;
    }

    public void setPassportScanSet(Set<PassportScan> passportScanSet)
    {
        this.passportScanSet = passportScanSet;
    }

    public Monastery getMonasteryAdviserToCome()
    {
        return monasteryAdviserToCome;
    }

    public void setMonasteryAdviserToCome(Monastery monasteryAdviserToCome)
    {
        this.monasteryAdviserToCome = monasteryAdviserToCome;
    }

    public Monastery getMonasteryResidingAt()
    {
        return monasteryResidingAt;
    }

    public void setMonasteryResidingAt(Monastery monasteryResidingAt)
    {
        this.monasteryResidingAt = monasteryResidingAt;
    }

    public Monastery getMonasteryOrdainedAt()
    {
        return monasteryOrdainedAt;
    }

    public void setMonasteryOrdainedAt(Monastery monasteryOrdainedAt)
    {
        this.monasteryOrdainedAt = monasteryOrdainedAt;
    }

    public Tm30NotificationResidence getFormTm30()
    {
        return formTm30;
    }

    public void setFormTm30(Tm30NotificationResidence formTm30)
    {
        this.formTm30 = formTm30;
    }

    public Upajjhaya getUpajjhaya()
    {
        return upajjhaya;
    }

    public void setUpajjhaya(Upajjhaya upajjhaya)
    {
        this.upajjhaya = upajjhaya;
    }

    @XmlTransient
    public Set<VisaExtension> getVisaExtensionSet()
    {
        return visaExtensionSet;
    }

    public void setVisaExtensionSet(Set<VisaExtension> visaExtensionSet)
    {
        this.visaExtensionSet = visaExtensionSet;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idProfile != null ? idProfile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonasticProfile))
        {
            return false;
        }
        MonasticProfile other = (MonasticProfile) object;
        if ((this.idProfile == null && other.idProfile != null) || (this.idProfile != null && !this.idProfile.equals(other.idProfile)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.MonasticProfile[ idProfile=" + idProfile + " ]";
    }

}
