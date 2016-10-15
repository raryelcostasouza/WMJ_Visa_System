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
    @NamedQuery(name = "Upajjhaya.findByIdUpajjhaya", query = "SELECT u FROM Upajjhaya u WHERE u.idUpajjhaya = :idUpajjhaya"),
    @NamedQuery(name = "Upajjhaya.findByUpajjhayaName", query = "SELECT u FROM Upajjhaya u WHERE u.upajjhayaName = :upajjhayaName")
})
public class Upajjhaya implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_UPAJJHAYA")
    private Integer idUpajjhaya;
    @Column(name = "UPAJJHAYA_NAME")
    private String upajjhayaName;
    @JoinColumn(name = "MONASTERY", referencedColumnName = "ID_MONASTERY")
    @ManyToOne
    private Monastery monastery;
    @OneToMany(mappedBy = "upajjhaya")
    private Set<MonasticProfile> monasticProfileSet;

    public Upajjhaya()
    {
    }

    public Upajjhaya(Integer idUpajjhaya)
    {
        this.idUpajjhaya = idUpajjhaya;
    }

    public Integer getIdUpajjhaya()
    {
        return idUpajjhaya;
    }

    public void setIdUpajjhaya(Integer idUpajjhaya)
    {
        this.idUpajjhaya = idUpajjhaya;
    }

    public String getUpajjhayaName()
    {
        return upajjhayaName;
    }

    public void setUpajjhayaName(String upajjhayaName)
    {
        this.upajjhayaName = upajjhayaName;
    }

    public Monastery getMonastery()
    {
        return monastery;
    }

    public void setMonastery(Monastery monastery)
    {
        this.monastery = monastery;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idUpajjhaya != null ? idUpajjhaya.hashCode() : 0);
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
        if ((this.idUpajjhaya == null && other.idUpajjhaya != null) || (this.idUpajjhaya != null && !this.idUpajjhaya.equals(other.idUpajjhaya)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Upajjhaya[ idUpajjhaya=" + idUpajjhaya + " ]";
    }

}
