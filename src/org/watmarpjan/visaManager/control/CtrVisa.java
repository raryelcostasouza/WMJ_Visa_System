/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Query;
import javax.persistence.PersistenceException;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrVisa extends AbstractControllerDB
{
    
    private final String ERROR_UNIQUE_EXT_NUMBER = "The visa extension number you are trying to save already exists on database.";

    public CtrVisa(CtrDatabase ctrDB)
    {
        super(ctrDB);
    }

    public int createVisaExt(VisaExtension vExt)
    {
        String errorMessage = "Unable to add visa extension.";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(vExt);

            ctrDB.commitCurrentTransaction();
            return 0;

        } catch (PersistenceException hex)
        {
            ctrDB.handleException(hex, errorMessage, ERROR_UNIQUE_EXT_NUMBER);
            return -1;
        }
    }

    public int removeVisaExt(VisaExtension vExt)
    {
        String errorMessage = "Unable to remove visa extension.";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().remove(vExt);
            ctrDB.commitCurrentTransaction();
            return 0;

        } catch (PersistenceException hex)
        {
            ctrDB.handleException(hex, errorMessage, "");
            return -1;
        }
    }

    public VisaExtension loadVisaExtensionByNumber(String extNumber)
    {
        return (VisaExtension) ctrDB.loadEntityByUniqueProperty("VisaExtension", "extNumber", extNumber);
    }

    public LocalDate getStayPermittedUntil(MonasticProfile p)
    {
        String hql;
        Date dStayPermittedUntil;

        //if the visa was not extended yet
        if (p.getVisaExtensionSet().size() == 0)
        {
            dStayPermittedUntil = p.getVisaExpiryDate();
        } //if the visa already has been extended
        else
        {
            hql = "select  max(vext.expiryDate)"
                    + " from MonasticProfile p"
                    + " inner join p.visaExtensionSet vext"
                    + " where p.idProfile = " + p.getIdProfile()
                    + " order by max(vext.expiryDate)";
            dStayPermittedUntil = ctrDB.getSession().createQuery(hql, Date.class).getSingleResult();
        }
        return Util.convertDateToLocalDate(dStayPermittedUntil);
    }

    public ArrayList<EntryVisaExt> loadListExtensions(Integer idProfile)
    {
        Query result;
        String hql;
        ArrayList<EntryVisaExt> alVisaExtensions;

        hql = "select new org.watmarpjan.visaManager.model.EntryVisaExt(vext.extNumber, vext.expiryDate)"
                + " from MonasticProfile p"
                + " inner join p.visaExtensionSet vext"
                + " where p.idProfile = " + idProfile
                + " order by vext.expiryDate desc";

        result = ctrDB.getSession().createQuery(hql);
        alVisaExtensions = (ArrayList<EntryVisaExt>) result.getResultList();

        return alVisaExtensions;
    }

    private ArrayList<VisaExtension> loadListVisaExtForProfile(MonasticProfile p)
    {
        ArrayList<VisaExtension> listVisaExt;
        String hql;
        Query result;

        hql = "from VisaExtension vext"
                + " where vext.monasticProfile.idProfile = " + p.getIdProfile()
                + " order by vext.expiryDate";

        result = ctrDB.getSession().createQuery(hql);
        listVisaExt = (ArrayList<VisaExtension>) result.getResultList();

        return listVisaExt;
    }

    public LocalDate getVisaOrExtExpiryDate(MonasticProfile p)
    {
        VisaExtension lastVisaExt;
        
        if (p.getVisaExpiryDate() != null)
        {
            lastVisaExt = getLastExtension(p);
            //if the visa was already extend
            if (lastVisaExt != null)
            {
                //show the expiry date of the latest extension
                return Util.convertDateToLocalDate(lastVisaExt.getExpiryDate());
            } else
            {
                //otherwise show the expiry date of the original visa
                return Util.convertDateToLocalDate(p.getVisaExpiryDate());
            }

        } else
        {
            return null;
        }
    }

    public VisaExtension getLastExtension(MonasticProfile p)
    {
        ArrayList<VisaExtension> listVisaExt;
        VisaExtension lastVext;

        listVisaExt = loadListVisaExtForProfile(p);
        if (!listVisaExt.isEmpty())
        {
            lastVext = listVisaExt.get(listVisaExt.size() - 1);
        } else
        {
            lastVext = null;
        }

        return lastVext;

    }

    public int addNewVisaForProfile(MonasticProfile p)
    {
        String errorMessage = "Unable to save the visa information.";

        try
        {
            ctrDB.openTransaction();
            ctrDB.commitCurrentTransaction();
            return 0;

        } catch (PersistenceException hex)
        {
            ctrDB.handleException(hex, errorMessage, ERROR_UNIQUE_EXT_NUMBER);
            return -1;
        }
    }

    public int clearVisaInfoForProfile(MonasticProfile p)
    {
        String errorMessage = "Unable to clear visa/extensions data.";

        p.setVisaNumber(null);
        p.setVisaType(null);
        p.setVisaExpiryDate(null);
        p.setNext90DayNotice(null);

        try
        {
            ctrDB.openTransaction();

            //if there are any associated visa extensions
            if (p.getVisaExtensionSet() != null)
            {
                //loop for clearing any associated visa extensions
                for (Iterator<VisaExtension> it = p.getVisaExtensionSet().iterator(); it.hasNext();)
                {
                    VisaExtension objVisaExt = it.next();
                    ctrDB.getSession().remove(objVisaExt);

                }
                p.getVisaExtensionSet().clear();
            }

            ctrDB.commitCurrentTransaction();
            return 0;

        } catch (PersistenceException hex)
        {
            ctrDB.handleException(hex, errorMessage, "");
            return -1;
        }
    }

}
