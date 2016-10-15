/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author WMJ_user
 */
@Entity
@Table(name = "TM30_NOTIFICATION_RESIDENCE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Tm30NotificationResidence.findAll", query = "SELECT t FROM Tm30NotificationResidence t"),
    @NamedQuery(name = "Tm30NotificationResidence.findByIdForm", query = "SELECT t FROM Tm30NotificationResidence t WHERE t.idForm = :idForm"),
    @NamedQuery(name = "Tm30NotificationResidence.findByNotificationDate", query = "SELECT t FROM Tm30NotificationResidence t WHERE t.notificationDate = :notificationDate")
})
public class Tm30NotificationResidence implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_FORM")
    private Integer idForm;
    @Basic(optional = false)
    @Column(name = "NOTIFICATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date notificationDate;
    @OneToMany(mappedBy = "formTm30")
    private Set<MonasticProfile> monasticProfileSet;

    public Tm30NotificationResidence()
    {
    }

    public Tm30NotificationResidence(Integer idForm)
    {
        this.idForm = idForm;
    }

    public Tm30NotificationResidence(Integer idForm, Date notificationDate)
    {
        this.idForm = idForm;
        this.notificationDate = notificationDate;
    }

    public Integer getIdForm()
    {
        return idForm;
    }

    public void setIdForm(Integer idForm)
    {
        this.idForm = idForm;
    }

    public Date getNotificationDate()
    {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate)
    {
        this.notificationDate = notificationDate;
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
        hash += (idForm != null ? idForm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tm30NotificationResidence))
        {
            return false;
        }
        Tm30NotificationResidence other = (Tm30NotificationResidence) object;
        if ((this.idForm == null && other.idForm != null) || (this.idForm != null && !this.idForm.equals(other.idForm)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Tm30NotificationResidence[ idForm=" + idForm + " ]";
    }

}
