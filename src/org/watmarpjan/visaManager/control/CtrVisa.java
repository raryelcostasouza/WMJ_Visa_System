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
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
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
    
    public int addVisaExt(VisaExtension vExt, PassportScan psExtScan)
    {
        String errorMessage = "Unable to add visa extension.";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(vExt);
            if (psExtScan.getIdPassportScan() == null)
            {
                ctrDB.getSession().persist(psExtScan);
            }
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
                + " from Profile p"
                + " inner join p.visaExtensionSet vext"
                + " where p.idprofile = " + idProfile
                + " order by vext.expiryDate";
        
        result = ctrDB.getSession().createQuery(hql);
        alVisaExtensions = (ArrayList<EntryVisaExt>) result.getResultList();
        
        return alVisaExtensions;
    }
    
    private ArrayList<VisaExtension> loadListVisaExtForProfile(Integer idProfile)
    {
        ArrayList<VisaExtension> listVisaExt;
        String hql;
        Query result;
        int status;
        
        hql = "from VisaExtension vext"
                + " where vext.profile.idprofile = " + idProfile + "";
        
        result = ctrDB.getSession().createQuery(hql);
        listVisaExt = (ArrayList<VisaExtension>) result.getResultList();
        
        return listVisaExt;
    }
    
    public int addNewVisaForProfile(MonasticProfile p, PassportScan psVisaScan)
    {
        String errorMessage = "Unable to save the visa information.";
        
        try
        {
            ctrDB.openTransaction();
            
            if (psVisaScan.getIdPassportScan() == null)
            {
                ctrDB.getSession().persist(psVisaScan);
            }
            
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
    
    public int clearVisaInfoForProfile(MonasticProfile p, ArrayList<PassportScan> listPassportScanToDelete, PassportScan psVisaScan)
    {
        ArrayList<VisaExtension> listVisaExt;
        String errorMessage = "Unable to clear visa extensions and passport scans.";
        
        listVisaExt = new ArrayList<>();
        listVisaExt.addAll(p.getVisaExtensionSet());

//        loadListVisaExtForProfile(p.getIdprofile());
        p.setVisaNumber(null);
        p.setVisaType(null);
        p.setVisaExpiryDate(null);
        p.setNext90DayNotice(null);
        
        try
        {
            ctrDB.openTransaction();

            //if the visa page scan contains the arrive stamp or last visa extension scan
            if (psVisaScan.isContentArriveStamp() || psVisaScan.isContentLastVisaExt())
            {
                psVisaScan.setContentVisaScan(false);
            }

            //deletes all extensions under this visa
            for (VisaExtension vext : listVisaExt)
            {
                ctrDB.getSession()
                        .remove(vext);
            }
            
            for (PassportScan ps : listPassportScanToDelete)
            {
                ctrDB.getSession()
                        .remove(ps);
            }
            
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
