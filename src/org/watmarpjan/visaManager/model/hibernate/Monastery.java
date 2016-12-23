/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    @NamedQuery(name = "Monastery.findByPhoneNumber", query = "SELECT m FROM Monastery m WHERE m.phoneNumber = :phoneNumber")
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
    @OneToMany(mappedBy = "monastery")
    private Set<Upajjhaya> upajjhayaSet;
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
