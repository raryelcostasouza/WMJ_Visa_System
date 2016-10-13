/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "PASSPORT_SCAN")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "PassportScan.findAll", query = "SELECT p FROM PassportScan p"),
            @NamedQuery(name = "PassportScan.findByIdPassportScan", query = "SELECT p FROM PassportScan p WHERE p.idPassportScan = :idPassportScan"),
            @NamedQuery(name = "PassportScan.findByContentArriveStamp", query = "SELECT p FROM PassportScan p WHERE p.contentArriveStamp = :contentArriveStamp"),
            @NamedQuery(name = "PassportScan.findByContentLastVisaExt", query = "SELECT p FROM PassportScan p WHERE p.contentLastVisaExt = :contentLastVisaExt"),
            @NamedQuery(name = "PassportScan.findByContentVisaScan", query = "SELECT p FROM PassportScan p WHERE p.contentVisaScan = :contentVisaScan"),
            @NamedQuery(name = "PassportScan.findByPageNumber", query = "SELECT p FROM PassportScan p WHERE p.pageNumber = :pageNumber")
        })
public class PassportScan implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PASSPORT_SCAN")
    private Integer idPassportScan;
    @Basic(optional = false)
    @Column(name = "CONTENT_ARRIVE_STAMP")
    private Boolean contentArriveStamp;
    @Basic(optional = false)
    @Column(name = "CONTENT_LAST_VISA_EXT")
    private Boolean contentLastVisaExt;
    @Basic(optional = false)
    @Column(name = "CONTENT_VISA_SCAN")
    private Boolean contentVisaScan;
    @Basic(optional = false)
    @Column(name = "PAGE_NUMBER")
    private int pageNumber;
    @JoinColumn(name = "MONASTIC_PROFILE", referencedColumnName = "ID_PROFILE")
    @ManyToOne(optional = false)
    private MonasticProfile monasticProfile;

    public PassportScan()
    {
    }

    public PassportScan(Integer idPassportScan)
    {
        this.idPassportScan = idPassportScan;
    }

    public PassportScan(Integer idPassportScan, Boolean contentArriveStamp, Boolean contentLastVisaExt, Boolean contentVisaScan, int pageNumber)
    {
        this.idPassportScan = idPassportScan;
        this.contentArriveStamp = contentArriveStamp;
        this.contentLastVisaExt = contentLastVisaExt;
        this.contentVisaScan = contentVisaScan;
        this.pageNumber = pageNumber;
    }

    public PassportScan(MonasticProfile p, int pageNumber, boolean contentArriveStamp, boolean contentVisaScan, boolean contentLastVisaExt)
    {
        this.monasticProfile = p;
        this.pageNumber = pageNumber;
        this.contentArriveStamp = contentArriveStamp;
        this.contentVisaScan = contentVisaScan;
        this.contentLastVisaExt = contentLastVisaExt;
    }

    public Integer getIdPassportScan()
    {
        return idPassportScan;
    }

    public void setIdPassportScan(Integer idPassportScan)
    {
        this.idPassportScan = idPassportScan;
    }

    public Boolean isContentArriveStamp()
    {
        return contentArriveStamp;
    }

    public void setContentArriveStamp(Boolean contentArriveStamp)
    {
        this.contentArriveStamp = contentArriveStamp;
    }

    public Boolean isContentLastVisaExt()
    {
        return contentLastVisaExt;
    }

    public void setContentLastVisaExt(Boolean contentLastVisaExt)
    {
        this.contentLastVisaExt = contentLastVisaExt;
    }

    public Boolean isContentVisaScan()
    {
        return contentVisaScan;
    }

    public void setContentVisaScan(Boolean contentVisaScan)
    {
        this.contentVisaScan = contentVisaScan;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
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
        hash += (idPassportScan != null ? idPassportScan.hashCode() : 0);
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
        if ((this.idPassportScan == null && other.idPassportScan != null) || (this.idPassportScan != null && !this.idPassportScan.equals(other.idPassportScan)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.PassportScan[ idPassportScan=" + idPassportScan + " ]";
    }

}
