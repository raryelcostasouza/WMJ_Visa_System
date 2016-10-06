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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author WMJ_user
 */
@Entity
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Profile.findAll", query = "SELECT p FROM Profile p"),
    @NamedQuery(name = "Profile.findByIdprofile", query = "SELECT p FROM Profile p WHERE p.idprofile = :idprofile"),
    @NamedQuery(name = "Profile.findByNickname", query = "SELECT p FROM Profile p WHERE p.nickname = :nickname"),
    @NamedQuery(name = "Profile.findByName", query = "SELECT p FROM Profile p WHERE p.name = :name"),
    @NamedQuery(name = "Profile.findByMiddleName", query = "SELECT p FROM Profile p WHERE p.middleName = :middleName"),
    @NamedQuery(name = "Profile.findByLastName", query = "SELECT p FROM Profile p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Profile.findByFatherName", query = "SELECT p FROM Profile p WHERE p.fatherName = :fatherName"),
    @NamedQuery(name = "Profile.findByMotherName", query = "SELECT p FROM Profile p WHERE p.motherName = :motherName"),
    @NamedQuery(name = "Profile.findByBirthPlace", query = "SELECT p FROM Profile p WHERE p.birthPlace = :birthPlace"),
    @NamedQuery(name = "Profile.findByBirthDate", query = "SELECT p FROM Profile p WHERE p.birthDate = :birthDate"),
    @NamedQuery(name = "Profile.findByBirthCountry", query = "SELECT p FROM Profile p WHERE p.birthCountry = :birthCountry"),
    @NamedQuery(name = "Profile.findByPreviousResidenceCountry", query = "SELECT p FROM Profile p WHERE p.previousResidenceCountry = :previousResidenceCountry"),
    @NamedQuery(name = "Profile.findByNationality", query = "SELECT p FROM Profile p WHERE p.nationality = :nationality"),
    @NamedQuery(name = "Profile.findByEthnicity", query = "SELECT p FROM Profile p WHERE p.ethnicity = :ethnicity"),
    @NamedQuery(name = "Profile.findByOccupationEnglish", query = "SELECT p FROM Profile p WHERE p.occupationEnglish = :occupationEnglish"),
    @NamedQuery(name = "Profile.findByOccupationThai", query = "SELECT p FROM Profile p WHERE p.occupationThai = :occupationThai"),
    @NamedQuery(name = "Profile.findBySchool", query = "SELECT p FROM Profile p WHERE p.school = :school"),
    @NamedQuery(name = "Profile.findByCertificateEnglish", query = "SELECT p FROM Profile p WHERE p.certificateEnglish = :certificateEnglish"),
    @NamedQuery(name = "Profile.findByCertificateThai", query = "SELECT p FROM Profile p WHERE p.certificateThai = :certificateThai"),
    @NamedQuery(name = "Profile.findByCertificateDuration", query = "SELECT p FROM Profile p WHERE p.certificateDuration = :certificateDuration"),
    @NamedQuery(name = "Profile.findByCertificateGradYear", query = "SELECT p FROM Profile p WHERE p.certificateGradYear = :certificateGradYear"),
    @NamedQuery(name = "Profile.findByPahkahwOrdDate", query = "SELECT p FROM Profile p WHERE p.pahkahwOrdDate = :pahkahwOrdDate"),
    @NamedQuery(name = "Profile.findBySamaneraOrdDate", query = "SELECT p FROM Profile p WHERE p.samaneraOrdDate = :samaneraOrdDate"),
    @NamedQuery(name = "Profile.findByBhikkhuOrdDate", query = "SELECT p FROM Profile p WHERE p.bhikkhuOrdDate = :bhikkhuOrdDate"),
    @NamedQuery(name = "Profile.findByPaliNameEnglish", query = "SELECT p FROM Profile p WHERE p.paliNameEnglish = :paliNameEnglish"),
    @NamedQuery(name = "Profile.findByPaliNameThai", query = "SELECT p FROM Profile p WHERE p.paliNameThai = :paliNameThai"),
    @NamedQuery(name = "Profile.findByBysuddhiIssueDate", query = "SELECT p FROM Profile p WHERE p.bysuddhiIssueDate = :bysuddhiIssueDate"),
    @NamedQuery(name = "Profile.findByNameAdviserToCome", query = "SELECT p FROM Profile p WHERE p.nameAdviserToCome = :nameAdviserToCome"),
    @NamedQuery(name = "Profile.findByPassportNumber", query = "SELECT p FROM Profile p WHERE p.passportNumber = :passportNumber"),
    @NamedQuery(name = "Profile.findByPassportIssuedAt", query = "SELECT p FROM Profile p WHERE p.passportIssuedAt = :passportIssuedAt"),
    @NamedQuery(name = "Profile.findByPassportIssueDate", query = "SELECT p FROM Profile p WHERE p.passportIssueDate = :passportIssueDate"),
    @NamedQuery(name = "Profile.findByPassportExpiryDate", query = "SELECT p FROM Profile p WHERE p.passportExpiryDate = :passportExpiryDate"),
    @NamedQuery(name = "Profile.findByStatus", query = "SELECT p FROM Profile p WHERE p.status = :status"),
    @NamedQuery(name = "Profile.findByFirstEntryDate", query = "SELECT p FROM Profile p WHERE p.firstEntryDate = :firstEntryDate"),
    @NamedQuery(name = "Profile.findByVisaNumber", query = "SELECT p FROM Profile p WHERE p.visaNumber = :visaNumber"),
    @NamedQuery(name = "Profile.findByVisaType", query = "SELECT p FROM Profile p WHERE p.visaType = :visaType"),
    @NamedQuery(name = "Profile.findByVisaExpiryDate", query = "SELECT p FROM Profile p WHERE p.visaExpiryDate = :visaExpiryDate"),
    @NamedQuery(name = "Profile.findByNext90DayNotice", query = "SELECT p FROM Profile p WHERE p.next90DayNotice = :next90DayNotice"),
    @NamedQuery(name = "Profile.findByArrivalCardNumber", query = "SELECT p FROM Profile p WHERE p.arrivalCardNumber = :arrivalCardNumber"),
    @NamedQuery(name = "Profile.findByArrivalLastEntryDate", query = "SELECT p FROM Profile p WHERE p.arrivalLastEntryDate = :arrivalLastEntryDate"),
    @NamedQuery(name = "Profile.findByArrivalPortOfEntry", query = "SELECT p FROM Profile p WHERE p.arrivalPortOfEntry = :arrivalPortOfEntry"),
    @NamedQuery(name = "Profile.findByArrivalTravelFrom", query = "SELECT p FROM Profile p WHERE p.arrivalTravelFrom = :arrivalTravelFrom"),
    @NamedQuery(name = "Profile.findByArrivalTravelBy", query = "SELECT p FROM Profile p WHERE p.arrivalTravelBy = :arrivalTravelBy"),
    @NamedQuery(name = "Profile.findByEmail", query = "SELECT p FROM Profile p WHERE p.email = :email"),
    @NamedQuery(name = "Profile.findByDhammaStudies", query = "SELECT p FROM Profile p WHERE p.dhammaStudies = :dhammaStudies")
})
public class Profile implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idprofile;
    private String nickname;
    private String name;
    private String middleName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String birthPlace;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String birthCountry;
    private String previousResidenceCountry;
    private String nationality;
    private String ethnicity;
    private String occupationEnglish;
    private String occupationThai;
    private String school;
    private String certificateEnglish;
    private String certificateThai;
    private Integer certificateDuration;
    private Integer certificateGradYear;
    @Temporal(TemporalType.DATE)
    private Date pahkahwOrdDate;
    @Temporal(TemporalType.DATE)
    private Date samaneraOrdDate;
    @Temporal(TemporalType.DATE)
    private Date bhikkhuOrdDate;
    private String paliNameEnglish;
    private String paliNameThai;
    @Temporal(TemporalType.DATE)
    private Date bysuddhiIssueDate;
    private String nameAdviserToCome;
    @Lob
    private String emergencyContact;
    private String passportNumber;
    private String passportIssuedAt;
    @Column(name = "passport_Issue_Date")
    @Temporal(TemporalType.DATE)
    private Date passportIssueDate;
    @Temporal(TemporalType.DATE)
    private Date passportExpiryDate;
    @Basic(optional = false)
    private String status;
    @Temporal(TemporalType.DATE)
    private Date firstEntryDate;
    private String visaNumber;
    private String visaType;
    @Temporal(TemporalType.DATE)
    private Date visaExpiryDate;
    @Temporal(TemporalType.DATE)
    private Date next90DayNotice;
    private String arrivalCardNumber;
    @Temporal(TemporalType.DATE)
    private Date arrivalLastEntryDate;
    private String arrivalPortOfEntry;
    private String arrivalTravelFrom;
    private String arrivalTravelBy;
    private String email;
    @Basic(optional = false)
    private String dhammaStudies;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Set<PassportScan> passportScanSet;
    @JoinColumn(name = "monasteryAdviserToCome", referencedColumnName = "id")
    @ManyToOne
    private Monastery monasteryAdviserToCome;
    @JoinColumn(name = "monasteryOrdainedAt", referencedColumnName = "id")
    @ManyToOne
    private Monastery monasteryOrdainedAt;
    @JoinColumn(name = "monasteryResidingAt", referencedColumnName = "id")
    @ManyToOne
    private Monastery monasteryResidingAt;
    @JoinColumn(name = "upajjhaya", referencedColumnName = "idupajjhaya")
    @ManyToOne
    private Upajjhaya upajjhaya;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Set<VisaExtension> visaExtensionSet;

    public Profile()
    {
    }

    public Profile(Integer idprofile)
    {
        this.idprofile = idprofile;
    }

    public Profile(Integer idprofile, String status, String dhammaStudies)
    {
        this.idprofile = idprofile;
        this.status = status;
        this.dhammaStudies = dhammaStudies;
    }

    public Integer getIdprofile()
    {
        return idprofile;
    }

    public void setIdprofile(Integer idprofile)
    {
        this.idprofile = idprofile;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public void setFatherName(String fatherName)
    {
        this.fatherName = fatherName;
    }

    public String getMotherName()
    {
        return motherName;
    }

    public void setMotherName(String motherName)
    {
        this.motherName = motherName;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getBirthCountry()
    {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry)
    {
        this.birthCountry = birthCountry;
    }

    public String getPreviousResidenceCountry()
    {
        return previousResidenceCountry;
    }

    public void setPreviousResidenceCountry(String previousResidenceCountry)
    {
        this.previousResidenceCountry = previousResidenceCountry;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    public String getEthnicity()
    {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity)
    {
        this.ethnicity = ethnicity;
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

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getCertificateEnglish()
    {
        return certificateEnglish;
    }

    public void setCertificateEnglish(String certificateEnglish)
    {
        this.certificateEnglish = certificateEnglish;
    }

    public String getCertificateThai()
    {
        return certificateThai;
    }

    public void setCertificateThai(String certificateThai)
    {
        this.certificateThai = certificateThai;
    }

    public Integer getCertificateDuration()
    {
        return certificateDuration;
    }

    public void setCertificateDuration(Integer certificateDuration)
    {
        this.certificateDuration = certificateDuration;
    }

    public Integer getCertificateGradYear()
    {
        return certificateGradYear;
    }

    public void setCertificateGradYear(Integer certificateGradYear)
    {
        this.certificateGradYear = certificateGradYear;
    }

    public Date getPahkahwOrdDate()
    {
        return pahkahwOrdDate;
    }

    public void setPahkahwOrdDate(Date pahkahwOrdDate)
    {
        this.pahkahwOrdDate = pahkahwOrdDate;
    }

    public Date getSamaneraOrdDate()
    {
        return samaneraOrdDate;
    }

    public void setSamaneraOrdDate(Date samaneraOrdDate)
    {
        this.samaneraOrdDate = samaneraOrdDate;
    }

    public Date getBhikkhuOrdDate()
    {
        return bhikkhuOrdDate;
    }

    public void setBhikkhuOrdDate(Date bhikkhuOrdDate)
    {
        this.bhikkhuOrdDate = bhikkhuOrdDate;
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

    public Date getBysuddhiIssueDate()
    {
        return bysuddhiIssueDate;
    }

    public void setBysuddhiIssueDate(Date bysuddhiIssueDate)
    {
        this.bysuddhiIssueDate = bysuddhiIssueDate;
    }

    public String getNameAdviserToCome()
    {
        return nameAdviserToCome;
    }

    public void setNameAdviserToCome(String nameAdviserToCome)
    {
        this.nameAdviserToCome = nameAdviserToCome;
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    public String getPassportNumber()
    {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
    }

    public String getPassportIssuedAt()
    {
        return passportIssuedAt;
    }

    public void setPassportIssuedAt(String passportIssuedAt)
    {
        this.passportIssuedAt = passportIssuedAt;
    }

    public Date getPassportIssueDate()
    {
        return passportIssueDate;
    }

    public void setPassportIssueDate(Date passportIssueDate)
    {
        this.passportIssueDate = passportIssueDate;
    }

    public Date getPassportExpiryDate()
    {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(Date passportExpiryDate)
    {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getFirstEntryDate()
    {
        return firstEntryDate;
    }

    public void setFirstEntryDate(Date firstEntryDate)
    {
        this.firstEntryDate = firstEntryDate;
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

    public Date getVisaExpiryDate()
    {
        return visaExpiryDate;
    }

    public void setVisaExpiryDate(Date visaExpiryDate)
    {
        this.visaExpiryDate = visaExpiryDate;
    }

    public Date getNext90DayNotice()
    {
        return next90DayNotice;
    }

    public void setNext90DayNotice(Date next90DayNotice)
    {
        this.next90DayNotice = next90DayNotice;
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

    public String getArrivalTravelFrom()
    {
        return arrivalTravelFrom;
    }

    public void setArrivalTravelFrom(String arrivalTravelFrom)
    {
        this.arrivalTravelFrom = arrivalTravelFrom;
    }

    public String getArrivalTravelBy()
    {
        return arrivalTravelBy;
    }

    public void setArrivalTravelBy(String arrivalTravelBy)
    {
        this.arrivalTravelBy = arrivalTravelBy;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDhammaStudies()
    {
        return dhammaStudies;
    }

    public void setDhammaStudies(String dhammaStudies)
    {
        this.dhammaStudies = dhammaStudies;
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

    public Monastery getMonasteryOrdainedAt()
    {
        return monasteryOrdainedAt;
    }

    public void setMonasteryOrdainedAt(Monastery monasteryOrdainedAt)
    {
        this.monasteryOrdainedAt = monasteryOrdainedAt;
    }

    public Monastery getMonasteryResidingAt()
    {
        return monasteryResidingAt;
    }

    public void setMonasteryResidingAt(Monastery monasteryResidingAt)
    {
        this.monasteryResidingAt = monasteryResidingAt;
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
        hash += (idprofile != null ? idprofile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile))
        {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.idprofile == null && other.idprofile != null) || (this.idprofile != null && !this.idprofile.equals(other.idprofile)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Profile[ idprofile=" + idprofile + " ]";
    }

}
