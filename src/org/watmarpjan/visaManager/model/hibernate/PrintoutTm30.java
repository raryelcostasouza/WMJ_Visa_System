/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author raryel
 */
@Entity
@Table(name = "PRINTOUT_TM30")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "PrintoutTm30.findAll", query = "SELECT p FROM PrintoutTm30 p"),
    @NamedQuery(name = "PrintoutTm30.findByIdPrintout", query = "SELECT p FROM PrintoutTm30 p WHERE p.idPrintout = :idPrintout"),
    @NamedQuery(name = "PrintoutTm30.findByNotifDate", query = "SELECT p FROM PrintoutTm30 p WHERE p.notifDate = :notifDate"),
    @NamedQuery(name = "PrintoutTm30.findByAuxIndex", query = "SELECT p FROM PrintoutTm30 p WHERE p.auxIndex = :auxIndex")
})
public class PrintoutTm30 implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRINTOUT")
    private Integer idPrintout;
    @Basic(optional = false)
    @Column(name = "NOTIF_DATE")
    @Temporal(TemporalType.DATE)
    private Date notifDate;
    @Column(name = "AUX_INDEX")
    private Integer auxIndex;
    @JoinColumn(name = "MONASTERY_RESIDENCE", referencedColumnName = "ID_MONASTERY")
    @ManyToOne
    private Monastery monasteryResidence;
    @OneToMany(mappedBy = "printoutTm30")
    private Set<MonasticProfile> monasticProfileSet;

    public PrintoutTm30()
    {
    }

    public PrintoutTm30(Integer idPrintout)
    {
        this.idPrintout = idPrintout;
    }

    public PrintoutTm30(Integer idPrintout, Date notifDate)
    {
        this.idPrintout = idPrintout;
        this.notifDate = notifDate;
    }

    public Integer getIdPrintout()
    {
        return idPrintout;
    }

    public void setIdPrintout(Integer idPrintout)
    {
        this.idPrintout = idPrintout;
    }

    public Date getNotifDate()
    {
        return notifDate;
    }

    public void setNotifDate(Date notifDate)
    {
        this.notifDate = notifDate;
    }

    public Integer getAuxIndex()
    {
        return auxIndex;
    }

    public void setAuxIndex(Integer auxIndex)
    {
        this.auxIndex = auxIndex;
    }

    public Monastery getMonasteryResidence()
    {
        return monasteryResidence;
    }

    public void setMonasteryResidence(Monastery monasteryResidence)
    {
        this.monasteryResidence = monasteryResidence;
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
        hash += (idPrintout != null ? idPrintout.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrintoutTm30))
        {
            return false;
        }
        PrintoutTm30 other = (PrintoutTm30) object;
        if ((this.idPrintout == null && other.idPrintout != null) || (this.idPrintout != null && !this.idPrintout.equals(other.idPrintout)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.PrintoutTm30[ idPrintout=" + idPrintout + " ]";
    }
    
}
