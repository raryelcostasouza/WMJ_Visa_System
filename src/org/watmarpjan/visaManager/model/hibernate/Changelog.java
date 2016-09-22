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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WMJ_user
 */
@Entity
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Changelog.findAll", query = "SELECT c FROM Changelog c"),
    @NamedQuery(name = "Changelog.findByIdChangeLog", query = "SELECT c FROM Changelog c WHERE c.idChangeLog = :idChangeLog"),
    @NamedQuery(name = "Changelog.findByOperationTime", query = "SELECT c FROM Changelog c WHERE c.operationTime = :operationTime")
})
public class Changelog implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idChangeLog;
    @Temporal(TemporalType.TIMESTAMP)
    private Date operationTime;
    @Lob
    private String description;
    @Lob
    private String userComment;
    @JoinColumn(name = "appUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private AppUser appUser;

    public Changelog()
    {
    }

    public Changelog(Integer idChangeLog)
    {
        this.idChangeLog = idChangeLog;
    }

    public Integer getIdChangeLog()
    {
        return idChangeLog;
    }

    public void setIdChangeLog(Integer idChangeLog)
    {
        this.idChangeLog = idChangeLog;
    }

    public Date getOperationTime()
    {
        return operationTime;
    }

    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUserComment()
    {
        return userComment;
    }

    public void setUserComment(String userComment)
    {
        this.userComment = userComment;
    }

    public AppUser getAppUser()
    {
        return appUser;
    }

    public void setAppUser(AppUser appUser)
    {
        this.appUser = appUser;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idChangeLog != null ? idChangeLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Changelog))
        {
            return false;
        }
        Changelog other = (Changelog) object;
        if ((this.idChangeLog == null && other.idChangeLog != null) || (this.idChangeLog != null && !this.idChangeLog.equals(other.idChangeLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.Changelog[ idChangeLog=" + idChangeLog + " ]";
    }

}
