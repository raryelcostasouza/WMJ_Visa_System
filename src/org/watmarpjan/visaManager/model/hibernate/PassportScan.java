/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WMJ_user
 */
@Entity
@Table(name = "passport_scan")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "PassportScan.findAll", query = "SELECT p FROM PassportScan p"),
            @NamedQuery(name = "PassportScan.findById", query = "SELECT p FROM PassportScan p WHERE p.id = :id"),
            @NamedQuery(name = "PassportScan.findByPageNumber", query = "SELECT p FROM PassportScan p WHERE p.pageNumber = :pageNumber"),
            @NamedQuery(name = "PassportScan.findByContentArriveStamp", query = "SELECT p FROM PassportScan p WHERE p.contentArriveStamp = :contentArriveStamp"),
            @NamedQuery(name = "PassportScan.findByContentVisaScan", query = "SELECT p FROM PassportScan p WHERE p.contentVisaScan = :contentVisaScan"),
            @NamedQuery(name = "PassportScan.findByContentLastVisaExt", query = "SELECT p FROM PassportScan p WHERE p.contentLastVisaExt = :contentLastVisaExt")
        })
public class PassportScan implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private int pageNumber;
    @Basic(optional = false)
    private boolean contentArriveStamp;
    @Basic(optional = false)
    private boolean contentVisaScan;
    @Basic(optional = false)
    private boolean contentLastVisaExt;
    @JoinColumn(name = "profile", referencedColumnName = "idprofile")
    @ManyToOne(optional = false)
    private Profile profile;

    public PassportScan()
    {
    }

    public PassportScan(Integer id)
    {
        this.id = id;
    }

    public PassportScan(Integer id, int pageNumber, boolean contentArriveStamp, boolean contentVisaScan, boolean contentLastVisaExt)
    {
        this.id = id;
        this.pageNumber = pageNumber;
        this.contentArriveStamp = contentArriveStamp;
        this.contentVisaScan = contentVisaScan;
        this.contentLastVisaExt = contentLastVisaExt;
    }

    public PassportScan(Profile p, int pageNumber, boolean contentArriveStamp, boolean contentVisaScan, boolean contentLastVisaExt)
    {
        this.profile = p;
        this.pageNumber = pageNumber;
        this.contentArriveStamp = contentArriveStamp;
        this.contentVisaScan = contentVisaScan;
        this.contentLastVisaExt = contentLastVisaExt;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public boolean isContentArriveStamp()
    {
        return contentArriveStamp;
    }

    public void setContentArriveStamp(boolean contentArriveStamp)
    {
        this.contentArriveStamp = contentArriveStamp;
    }

    public boolean isContentVisaScan()
    {
        return contentVisaScan;
    }

    public void setContentVisaScan(boolean contentVisaScan)
    {
        this.contentVisaScan = contentVisaScan;
    }

    public boolean isContentLastVisaExt()
    {
        return contentLastVisaExt;
    }

    public void setContentLastVisaExt(boolean contentLastVisaExt)
    {
        this.contentLastVisaExt = contentLastVisaExt;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PassportScan))
        {
            return false;
        }
        PassportScan other = (PassportScan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.PassportScan[ id=" + id + " ]";
    }
}
