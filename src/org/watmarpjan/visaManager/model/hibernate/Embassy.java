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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adhipanyo
 */
@Entity
@Table(name = "EMBASSY")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Embassy.findAll", query = "SELECT e FROM Embassy e"),
    @NamedQuery(name = "Embassy.findByIdEmbassy", query = "SELECT e FROM Embassy e WHERE e.idEmbassy = :idEmbassy"),
    @NamedQuery(name = "Embassy.findByNameTh", query = "SELECT e FROM Embassy e WHERE e.nameTh = :nameTh"),
    @NamedQuery(name = "Embassy.findByAddress", query = "SELECT e FROM Embassy e WHERE e.address = :address"),
    @NamedQuery(name = "Embassy.findByAddressLine1", query = "SELECT e FROM Embassy e WHERE e.addressLine1 = :addressLine1"),
    @NamedQuery(name = "Embassy.findByAddressLine2", query = "SELECT e FROM Embassy e WHERE e.addressLine2 = :addressLine2"),
    @NamedQuery(name = "Embassy.findByAddressLine3", query = "SELECT e FROM Embassy e WHERE e.addressLine3 = :addressLine3"),
    @NamedQuery(name = "Embassy.findByAddressLine4", query = "SELECT e FROM Embassy e WHERE e.addressLine4 = :addressLine4"),
    @NamedQuery(name = "Embassy.findByCountry", query = "SELECT e FROM Embassy e WHERE e.country = :country"),
    @NamedQuery(name = "Embassy.findByNameEn", query = "SELECT e FROM Embassy e WHERE e.nameEn = :nameEn")
})
public class Embassy implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_EMBASSY")
    private Integer idEmbassy;
    @Column(name = "NAME_TH")
    private String nameTh;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;
    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;
    @Column(name = "ADDRESS_LINE3")
    private String addressLine3;
    @Column(name = "ADDRESS_LINE4")
    private String addressLine4;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "NAME_EN")
    private String nameEn;

    public Embassy()
    {
    }

    public Embassy(Integer idEmbassy)
    {
        this.idEmbassy = idEmbassy;
    }

    public Integer getIdEmbassy()
    {
        return idEmbassy;
    }

    public void setIdEmbassy(Integer idEmbassy)
    {
        this.idEmbassy = idEmbassy;
    }

    public String getNameTh()
    {
        return nameTh;
    }

    public void setNameTh(String nameTh)
    {
        this.nameTh = nameTh;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddressLine1()
    {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2()
    {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3()
    {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3)
    {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4()
    {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4)
    {
        this.addressLine4 = addressLine4;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idEmbassy != null ? idEmbassy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Embassy))
        {
            return false;
        }
        Embassy other = (Embassy) object;
        if ((this.idEmbassy == null && other.idEmbassy != null) || (this.idEmbassy != null && !this.idEmbassy.equals(other.idEmbassy)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Embassy[ idEmbassy=" + idEmbassy + " ]";
    }
    
}
