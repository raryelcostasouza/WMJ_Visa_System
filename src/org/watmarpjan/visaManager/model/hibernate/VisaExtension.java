/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WMJ_user
 */
@Entity
@Table(name = "visa_extension")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "VisaExtension.findAll", query = "SELECT v FROM VisaExtension v"),
            @NamedQuery(name = "VisaExtension.findByIdVisaExtension", query = "SELECT v FROM VisaExtension v WHERE v.idVisaExtension = :idVisaExtension"),
            @NamedQuery(name = "VisaExtension.findByExtNumber", query = "SELECT v FROM VisaExtension v WHERE v.extNumber = :extNumber"),
            @NamedQuery(name = "VisaExtension.findByExpiryDate", query = "SELECT v FROM VisaExtension v WHERE v.expiryDate = :expiryDate")
        })
public class VisaExtension implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idVisaExtension;
    @Basic(optional = false)
    private String extNumber;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    @JoinColumn(name = "profile", referencedColumnName = "idprofile")
    @ManyToOne(optional = false)
    private Profile profile;

    public VisaExtension()
    {
    }

    public VisaExtension(Integer idVisaExtension)
    {
        this.idVisaExtension = idVisaExtension;
    }

    public VisaExtension(Integer idVisaExtension, String extNumber, Date expiryDate)
    {
        this.idVisaExtension = idVisaExtension;
        this.extNumber = extNumber;
        this.expiryDate = expiryDate;
    }

    public Integer getIdVisaExtension()
    {
        return idVisaExtension;
    }

    public void setIdVisaExtension(Integer idVisaExtension)
    {
        this.idVisaExtension = idVisaExtension;
    }

    public String getExtNumber()
    {
        return extNumber;
    }

    public void setExtNumber(String extNumber)
    {
        this.extNumber = extNumber;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idVisaExtension != null ? idVisaExtension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisaExtension))
        {
            return false;
        }
        VisaExtension other = (VisaExtension) object;
        if ((this.idVisaExtension == null && other.idVisaExtension != null) || (this.idVisaExtension != null && !this.idVisaExtension.equals(other.idVisaExtension)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.VisaExtension[ idVisaExtension=" + idVisaExtension + " ]";
    }

}
