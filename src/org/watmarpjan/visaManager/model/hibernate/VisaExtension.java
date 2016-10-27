/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wmj_user
 */
@Entity
@Table(name = "VISA_EXTENSION")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "VisaExtension.findAll", query = "SELECT v FROM VisaExtension v"),
    @NamedQuery(name = "VisaExtension.findByIdVisaExtension", query = "SELECT v FROM VisaExtension v WHERE v.idVisaExtension = :idVisaExtension"),
    @NamedQuery(name = "VisaExtension.findByExpiryDate", query = "SELECT v FROM VisaExtension v WHERE v.expiryDate = :expiryDate"),
    @NamedQuery(name = "VisaExtension.findByExtNumber", query = "SELECT v FROM VisaExtension v WHERE v.extNumber = :extNumber")
})
public class VisaExtension implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VISA_EXTENSION")
    private Integer idVisaExtension;
    @Basic(optional = false)
    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    @Basic(optional = false)
    @Column(name = "EXT_NUMBER")
    private String extNumber;
    @JoinColumn(name = "MONASTIC_PROFILE", referencedColumnName = "ID_PROFILE")
    @ManyToOne(optional = false)
    private MonasticProfile monasticProfile;

    public VisaExtension()
    {
    }

    public VisaExtension(Integer idVisaExtension)
    {
        this.idVisaExtension = idVisaExtension;
    }

    public VisaExtension(Integer idVisaExtension, Date expiryDate, String extNumber)
    {
        this.idVisaExtension = idVisaExtension;
        this.expiryDate = expiryDate;
        this.extNumber = extNumber;
    }

    public Integer getIdVisaExtension()
    {
        return idVisaExtension;
    }

    public void setIdVisaExtension(Integer idVisaExtension)
    {
        this.idVisaExtension = idVisaExtension;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getExtNumber()
    {
        return extNumber;
    }

    public void setExtNumber(String extNumber)
    {
        this.extNumber = extNumber;
    }

    public MonasticProfile getMonasticProfile()
    {
        return monasticProfile;
    }

    public void setMonasticProfile(MonasticProfile monasticProfile)
    {
        this.monasticProfile = monasticProfile;
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
