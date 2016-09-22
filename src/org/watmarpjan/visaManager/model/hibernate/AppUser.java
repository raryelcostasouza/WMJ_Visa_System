/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.hibernate;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author WMJ_user
 */
@Entity
@Table(name = "app_user")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "AppUser.findAll", query = "SELECT a FROM AppUser a"),
            @NamedQuery(name = "AppUser.findByIdUser", query = "SELECT a FROM AppUser a WHERE a.idUser = :idUser"),
            @NamedQuery(name = "AppUser.findByLogin", query = "SELECT a FROM AppUser a WHERE a.login = :login"),
            @NamedQuery(name = "AppUser.findByPassword", query = "SELECT a FROM AppUser a WHERE a.password = :password"),
            @NamedQuery(name = "AppUser.findByProfileType", query = "SELECT a FROM AppUser a WHERE a.profileType = :profileType"),
            @NamedQuery(name = "AppUser.findByEmail", query = "SELECT a FROM AppUser a WHERE a.email = :email"),
            @NamedQuery(name = "AppUser.findByPhoneNumber", query = "SELECT a FROM AppUser a WHERE a.phoneNumber = :phoneNumber")
        })
public class AppUser implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idUser;
    private String login;
    private String password;
    private Integer profileType;
    private String email;
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    private Set<Changelog> changelogSet;

    public AppUser()
    {
    }

    public AppUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getProfileType()
    {
        return profileType;
    }

    public void setProfileType(Integer profileType)
    {
        this.profileType = profileType;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @XmlTransient
    public Set<Changelog> getChangelogSet()
    {
        return changelogSet;
    }

    public void setChangelogSet(Set<Changelog> changelogSet)
    {
        this.changelogSet = changelogSet;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser))
        {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "org.watmarpjan.visaManager.model.hibernate.AppUser[ idUser=" + idUser + " ]";
    }

}
