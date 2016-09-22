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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Upajjhaya.findAll", query = "SELECT u FROM Upajjhaya u"),
    @NamedQuery(name = "Upajjhaya.findByIdupajjhaya", query = "SELECT u FROM Upajjhaya u WHERE u.idupajjhaya = :idupajjhaya"),
    @NamedQuery(name = "Upajjhaya.findByName", query = "SELECT u FROM Upajjhaya u WHERE u.name = :name")
})
public class Upajjhaya implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idupajjhaya;
    private String name;
    @OneToMany(mappedBy = "upajjhaya")
    private Set<Profile> profileSet;
    @JoinColumn(name = "monastery", referencedColumnName = "id")
    @ManyToOne
    private Monastery monastery;

    public Upajjhaya()
    {
    }

    public Upajjhaya(Integer idupajjhaya)
    {
        this.idupajjhaya = idupajjhaya;
    }

    public Integer getIdupajjhaya()
    {
        return idupajjhaya;
    }

    public void setIdupajjhaya(Integer idupajjhaya)
    {
        this.idupajjhaya = idupajjhaya;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public Monastery getMonastery()
    {
        return monastery;
    }

    public void setMonastery(Monastery monastery)
    {
        this.monastery = monastery;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idupajjhaya != null ? idupajjhaya.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Upajjhaya))
        {
            return false;
        }
        Upajjhaya other = (Upajjhaya) object;
        if ((this.idupajjhaya == null && other.idupajjhaya != null) || (this.idupajjhaya != null && !this.idupajjhaya.equals(other.idupajjhaya)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Upajjhaya[ idupajjhaya=" + idupajjhaya + " ]";
    }

}
