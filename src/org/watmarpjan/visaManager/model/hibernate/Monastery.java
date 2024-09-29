/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author raryel
 */
@Entity
@Table(name = "MONASTERY")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Monastery.findAll", query = "SELECT m FROM Monastery m"),
    @NamedQuery(name = "Monastery.findByIdMonastery", query = "SELECT m FROM Monastery m WHERE m.idMonastery = :idMonastery"),
    @NamedQuery(name = "Monastery.findByAddrAmpher", query = "SELECT m FROM Monastery m WHERE m.addrAmpher = :addrAmpher"),
    @NamedQuery(name = "Monastery.findByAddrCountry", query = "SELECT m FROM Monastery m WHERE m.addrCountry = :addrCountry"),
    @NamedQuery(name = "Monastery.findByAddrJangwat", query = "SELECT m FROM Monastery m WHERE m.addrJangwat = :addrJangwat"),
    @NamedQuery(name = "Monastery.findByAddrNumber", query = "SELECT m FROM Monastery m WHERE m.addrNumber = :addrNumber"),
    @NamedQuery(name = "Monastery.findByAddrRoad", query = "SELECT m FROM Monastery m WHERE m.addrRoad = :addrRoad"),
    @NamedQuery(name = "Monastery.findByAddrTambon", query = "SELECT m FROM Monastery m WHERE m.addrTambon = :addrTambon"),
    @NamedQuery(name = "Monastery.findByMonasteryOfJaokana", query = "SELECT m FROM Monastery m WHERE m.monasteryOfJaokana = :monasteryOfJaokana"),
    @NamedQuery(name = "Monastery.findByMonasteryName", query = "SELECT m FROM Monastery m WHERE m.monasteryName = :monasteryName"),
    @NamedQuery(name = "Monastery.findByPhoneNumber", query = "SELECT m FROM Monastery m WHERE m.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Monastery.findByAbbotName", query = "SELECT m FROM Monastery m WHERE m.abbotName = :abbotName"),
    @NamedQuery(name = "Monastery.findByMonasteryNickname", query = "SELECT m FROM Monastery m WHERE m.monasteryNickname = :monasteryNickname"),
    @NamedQuery(name = "Monastery.findByAddrAmpher90DayOnline", query = "SELECT m FROM Monastery m WHERE m.addrAmpher90DayOnline = :addrAmpher90DayOnline"),
    @NamedQuery(name = "Monastery.findByAddrTambon90DayOnline", query = "SELECT m FROM Monastery m WHERE m.addrTambon90DayOnline = :addrTambon90DayOnline"),
    @NamedQuery(name = "Monastery.findByAddrRoad90DayOnline", query = "SELECT m FROM Monastery m WHERE m.addrRoad90DayOnline = :addrRoad90DayOnline"),
    @NamedQuery(name = "Monastery.findByAddrNumber90DayOnline", query = "SELECT m FROM Monastery m WHERE m.addrNumber90DayOnline = :addrNumber90DayOnline"),
    @NamedQuery(name = "Monastery.findByAddrJangwat90DayOnline", query = "SELECT m FROM Monastery m WHERE m.addrJangwat90DayOnline = :addrJangwat90DayOnline"),
    @NamedQuery(name = "Monastery.findByMonasteryNameEnglish", query = "SELECT m FROM Monastery m WHERE m.monasteryNameEnglish = :monasteryNameEnglish")
})
public class Monastery implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MONASTERY")
    private Integer idMonastery;
    @Column(name = "ADDR_AMPHER")
    private String addrAmpher;
    @Column(name = "ADDR_COUNTRY")
    private String addrCountry;
    @Column(name = "ADDR_JANGWAT")
    private String addrJangwat;
    @Column(name = "ADDR_NUMBER")
    private String addrNumber;
    @Column(name = "ADDR_ROAD")
    private String addrRoad;
    @Column(name = "ADDR_TAMBON")
    private String addrTambon;
    @Basic(optional = false)
    @Column(name = "MONASTERY_OF_JAOKANA")
    private String monasteryOfJaokana;
    @Column(name = "MONASTERY_NAME")
    private String monasteryName;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "ABBOT_NAME")
    private String abbotName;
    @Column(name = "MONASTERY_NICKNAME")
    private String monasteryNickname;
    @Column(name = "ADDR_AMPHER_90_DAY_ONLINE")
    private String addrAmpher90DayOnline;
    @Column(name = "ADDR_TAMBON_90_DAY_ONLINE")
    private String addrTambon90DayOnline;
    @Column(name = "ADDR_ROAD_90_DAY_ONLINE")
    private String addrRoad90DayOnline;
    @Column(name = "ADDR_NUMBER_90_DAY_ONLINE")
    private String addrNumber90DayOnline;
    @Column(name = "ADDR_JANGWAT_90_DAY_ONLINE")
    private String addrJangwat90DayOnline;
    @Column(name = "MONASTERY_NAME_ENGLISH")
    private String monasteryNameEnglish;
    @OneToMany(mappedBy = "monastery")
    private Set<Upajjhaya> upajjhayaSet;
    @OneToMany(mappedBy = "monasteryResidence")
    private Set<PrintoutTm30> printoutTm30Set;
    @OneToMany(mappedBy = "monasteryAdviserToCome")
    private Set<MonasticProfile> monasticProfileSet;
    @OneToMany(mappedBy = "monasteryResidingAt")
    private Set<MonasticProfile> monasticProfileSet1;
    @OneToMany(mappedBy = "monasteryOrdainedAt")
    private Set<MonasticProfile> monasticProfileSet2;

    public Monastery()
    {
    }

    public Monastery(Integer idMonastery)
    {
        this.idMonastery = idMonastery;
    }

    public Monastery(Integer idMonastery, String monasteryOfJaokana)
    {
        this.idMonastery = idMonastery;
        this.monasteryOfJaokana = monasteryOfJaokana;
    }

    public Integer getIdMonastery()
    {
        return idMonastery;
    }

    public void setIdMonastery(Integer idMonastery)
    {
        this.idMonastery = idMonastery;
    }

    public String getAddrAmpher()
    {
        return addrAmpher;
    }

    public void setAddrAmpher(String addrAmpher)
    {
        this.addrAmpher = addrAmpher;
    }

    public String getAddrCountry()
    {
        return addrCountry;
    }

    public void setAddrCountry(String addrCountry)
    {
        this.addrCountry = addrCountry;
    }

    public String getAddrJangwat()
    {
        return addrJangwat;
    }

    public void setAddrJangwat(String addrJangwat)
    {
        this.addrJangwat = addrJangwat;
    }

    public String getAddrNumber()
    {
        return addrNumber;
    }

    public void setAddrNumber(String addrNumber)
    {
        this.addrNumber = addrNumber;
    }

    public String getAddrRoad()
    {
        return addrRoad;
    }

    public void setAddrRoad(String addrRoad)
    {
        this.addrRoad = addrRoad;
    }

    public String getAddrTambon()
    {
        return addrTambon;
    }

    public void setAddrTambon(String addrTambon)
    {
        this.addrTambon = addrTambon;
    }

    public String getMonasteryOfJaokana()
    {
        return monasteryOfJaokana;
    }

    public void setMonasteryOfJaokana(String monasteryOfJaokana)
    {
        this.monasteryOfJaokana = monasteryOfJaokana;
    }

    public String getMonasteryName()
    {
        return monasteryName;
    }

    public void setMonasteryName(String monasteryName)
    {
        this.monasteryName = monasteryName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAbbotName()
    {
        return abbotName;
    }

    public void setAbbotName(String abbotName)
    {
        this.abbotName = abbotName;
    }

    public String getMonasteryNickname()
    {
        return monasteryNickname;
    }

    public void setMonasteryNickname(String monasteryNickname)
    {
        this.monasteryNickname = monasteryNickname;
    }

    public String getAddrAmpher90DayOnline()
    {
        return addrAmpher90DayOnline;
    }

    public void setAddrAmpher90DayOnline(String addrAmpher90DayOnline)
    {
        this.addrAmpher90DayOnline = addrAmpher90DayOnline;
    }

    public String getAddrTambon90DayOnline()
    {
        return addrTambon90DayOnline;
    }

    public void setAddrTambon90DayOnline(String addrTambon90DayOnline)
    {
        this.addrTambon90DayOnline = addrTambon90DayOnline;
    }

    public String getAddrRoad90DayOnline()
    {
        return addrRoad90DayOnline;
    }

    public void setAddrRoad90DayOnline(String addrRoad90DayOnline)
    {
        this.addrRoad90DayOnline = addrRoad90DayOnline;
    }

    public String getAddrNumber90DayOnline()
    {
        return addrNumber90DayOnline;
    }

    public void setAddrNumber90DayOnline(String addrNumber90DayOnline)
    {
        this.addrNumber90DayOnline = addrNumber90DayOnline;
    }

    public String getAddrJangwat90DayOnline()
    {
        return addrJangwat90DayOnline;
    }

    public void setAddrJangwat90DayOnline(String addrJangwat90DayOnline)
    {
        this.addrJangwat90DayOnline = addrJangwat90DayOnline;
    }

    public String getMonasteryNameEnglish()
    {
        return monasteryNameEnglish;
    }

    public void setMonasteryNameEnglish(String monasteryNameEnglish)
    {
        this.monasteryNameEnglish = monasteryNameEnglish;
    }

    @XmlTransient
    public Set<Upajjhaya> getUpajjhayaSet()
    {
        return upajjhayaSet;
    }

    public void setUpajjhayaSet(Set<Upajjhaya> upajjhayaSet)
    {
        this.upajjhayaSet = upajjhayaSet;
    }

    @XmlTransient
    public Set<PrintoutTm30> getPrintoutTm30Set()
    {
        return printoutTm30Set;
    }

    public void setPrintoutTm30Set(Set<PrintoutTm30> printoutTm30Set)
    {
        this.printoutTm30Set = printoutTm30Set;
    }

    @XmlTransient
    public Set<MonasticProfile> getMonasticProfileSet()
    {
        return monasticProfileSet;
    }

    public void setMonasticProfileSet(Set<MonasticProfile> monasticProfileSet)
    {
        this.monasticProfileSet = monasticProfileSet;
    }

    @XmlTransient
    public Set<MonasticProfile> getMonasticProfileSet1()
    {
        return monasticProfileSet1;
    }

    public void setMonasticProfileSet1(Set<MonasticProfile> monasticProfileSet1)
    {
        this.monasticProfileSet1 = monasticProfileSet1;
    }

    @XmlTransient
    public Set<MonasticProfile> getMonasticProfileSet2()
    {
        return monasticProfileSet2;
    }

    public void setMonasticProfileSet2(Set<MonasticProfile> monasticProfileSet2)
    {
        this.monasticProfileSet2 = monasticProfileSet2;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idMonastery != null ? idMonastery.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monastery))
        {
            return false;
        }
        Monastery other = (Monastery) object;
        if ((this.idMonastery == null && other.idMonastery != null) || (this.idMonastery != null && !this.idMonastery.equals(other.idMonastery)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Monastery[ idMonastery=" + idMonastery + " ]";
    }
    
}
