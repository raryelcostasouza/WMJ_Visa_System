/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import javax.persistence.Query;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;

/**
 *
 * @author WMJ_user
 */
public class CtrVisa extends AbstractControllerDB
{
    
    public CtrVisa(CtrDatabase ctrDB)
    {
        super(ctrDB);
    }
    
    public int addVisaExt(VisaExtension vExt)
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
            ctrDB.rollbackCurrentTransaction();
            if (hex instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) hex, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(hex, errorMessage);
            }
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
            ctrDB.rollbackCurrentTransaction();
            if (hex instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) hex, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(hex, errorMessage);
            }
            return -1;
        }
    }
    
    public VisaExtension loadVisaExtensionByNumber(String extNumber)
    {
        Query result;
        String hql;
        
        hql = "from VisaExtension vext"
                + " where vext.extNumber = '" + extNumber + "'";
        
        result = ctrDB.getSession().createQuery(hql);
        return (VisaExtension) result.getSingleResult();
        
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
    
    public VisaExtension getLastExtension(MonasticProfile p)
    {
        ArrayList<VisaExtension> listVisaExt;
        VisaExtension lastVext;
        
        listVisaExt = loadListVisaExtForProfile(p);
        if (!listVisaExt.isEmpty())
        {
            lastVext = listVisaExt.get(listVisaExt.size()-1);
        }
        else
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
            ctrDB.rollbackCurrentTransaction();
            if (hex instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) hex, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(hex, errorMessage);
            }
            return -1;
        }
    }
    
    public int clearVisaInfoForProfile(MonasticProfile p)
    {
        String errorMessage = "Unable to clear visa extensions and passport scans.";
        
        p.setVisaNumber(null);
        p.setVisaType(null);
        p.setVisaExpiryDate(null);
        p.setNext90DayNotice(null);
        
        try
        {
            ctrDB.openTransaction();
                     
            ctrDB.commitCurrentTransaction();
            return 0;
            
        } catch (PersistenceException hex)
        {
            ctrDB.rollbackCurrentTransaction();
            if (hex instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) hex, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(hex, errorMessage);
            }
            return -1;
        }
    }
    
}
