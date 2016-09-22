/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
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
    @NamedQuery(name = "Monastery.findById", query = "SELECT m FROM Monastery m WHERE m.id = :id"),
    @NamedQuery(name = "Monastery.findByName", query = "SELECT m FROM Monastery m WHERE m.name = :name"),
    @NamedQuery(name = "Monastery.findByAddrCountry", query = "SELECT m FROM Monastery m WHERE m.addrCountry = :addrCountry"),
    @NamedQuery(name = "Monastery.findByAddrJangwat", query = "SELECT m FROM Monastery m WHERE m.addrJangwat = :addrJangwat"),
    @NamedQuery(name = "Monastery.findByAddrTambon", query = "SELECT m FROM Monastery m WHERE m.addrTambon = :addrTambon"),
    @NamedQuery(name = "Monastery.findByAddrAmpher", query = "SELECT m FROM Monastery m WHERE m.addrAmpher = :addrAmpher"),
    @NamedQuery(name = "Monastery.findByAddrRoad", query = "SELECT m FROM Monastery m WHERE m.addrRoad = :addrRoad"),
    @NamedQuery(name = "Monastery.findByAddrNumber", query = "SELECT m FROM Monastery m WHERE m.addrNumber = :addrNumber"),
    @NamedQuery(name = "Monastery.findByPhoneNumber", query = "SELECT m FROM Monastery m WHERE m.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Monastery.findByMonasteryOfJaokana", query = "SELECT m FROM Monastery m WHERE m.monasteryOfJaokana = :monasteryOfJaokana")
})
public class Monastery implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String name;
    private String addrCountry;
    private String addrJangwat;
    private String addrTambon;
    private String addrAmpher;
    private String addrRoad;
    private String addrNumber;
    private String phoneNumber;
    @Basic(optional = false)
    private String monasteryOfJaokana;
    @OneToMany(mappedBy = "monasteryAdviserToCome")
    private Set<Profile> profileSet;
    @OneToMany(mappedBy = "monasteryOrdainedAt")
    private Set<Profile> profileSet1;
    @OneToMany(mappedBy = "monasteryResidingAt")
    private Set<Profile> profileSet2;
    @OneToMany(mappedBy = "monastery")
    private Set<Upajjhaya> upajjhayaSet;

    public Monastery()
    {
    }

    public Monastery(Integer id)
    {
        this.id = id;
    }

    public Monastery(Integer id, String monasteryOfJaokana)
    {
        this.id = id;
        this.monasteryOfJaokana = monasteryOfJaokana;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public String getAddrTambon()
    {
        return addrTambon;
    }

    public void setAddrTambon(String addrTambon)
    {
        this.addrTambon = addrTambon;
    }

    public String getAddrAmpher()
    {
        return addrAmpher;
    }

    public void setAddrAmpher(String addrAmpher)
    {
        this.addrAmpher = addrAmpher;
    }

    public String getAddrRoad()
    {
        return addrRoad;
    }

    public void setAddrRoad(String addrRoad)
    {
        this.addrRoad = addrRoad;
    }

    public String getAddrNumber()
    {
        return addrNumber;
    }

    public void setAddrNumber(String addrNumber)
    {
        this.addrNumber = addrNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getMonasteryOfJaokana()
    {
        return monasteryOfJaokana;
    }

    public void setMonasteryOfJaokana(String monasteryOfJaokana)
    {
        this.monasteryOfJaokana = monasteryOfJaokana;
    }

    @XmlTransient
    public Set<Profile> getProfileSet()
    {
        return profileSet;
    }

    public void setProfileSet(Set<Profile> profileSet)
    {
        this.profileSet = profileSet;
    }

    @XmlTransient
    public Set<Profile> getProfileSet1()
    {
        return profileSet1;
    }

    public void setProfileSet1(Set<Profile> profileSet1)
    {
        this.profileSet1 = profileSet1;
    }

    @XmlTransient
    public Set<Profile> getProfileSet2()
    {
        return profileSet2;
    }

    public void setProfileSet2(Set<Profile> profileSet2)
    {
        this.profileSet2 = profileSet2;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Monastery[ id=" + id + " ]";
    }

}
