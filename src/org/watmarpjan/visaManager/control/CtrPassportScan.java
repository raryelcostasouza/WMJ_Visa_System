/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.Iterator;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;

/**
 *
 * @author WMJ_user
 */
public class CtrPassportScan extends AbstractControllerDB
{
    
    public static final int SCAN_NUMBER_1 = 1;
    public static final int SCAN_NUMBER_2 = 2;
    public static final int SCAN_NUMBER_3 = 3;
    
    public CtrPassportScan(CtrDatabase ctrDB)
    {
        super(ctrDB);
    }
    
    public int addPassportScan(PassportScan ps)
    {
        String errorMessage = "Unable to add passport scan data.";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(ps);
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
    
    public int update()
    {
        String errorMessage = "Unable to update Passport Scan data.";
        try
        {
            ctrDB.openTransaction();
            ctrDB.commitCurrentTransaction();
            return 0;
            
        } catch (PersistenceException pe)
        {
            ctrDB.rollbackCurrentTransaction();
            if (pe instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) pe, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(pe, errorMessage);
            }
            return -1;
        }
    }
    
    public int updateAndRemoveScan(PassportScan ps2Remove)
    {
        String errorMessage = "Unable to remove Passport Scan data.";
        try
        {
            ctrDB.openTransaction();
            if (ps2Remove != null)
            {
                ctrDB.getSession().remove(ps2Remove);
            }
            
            ctrDB.commitCurrentTransaction();
            return 0;
            
        } catch (PersistenceException pe)
        {
            ctrDB.rollbackCurrentTransaction();
            if (pe instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) pe, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(pe, errorMessage);
            }
            return -1;
        }
    }
    
    public int remove(PassportScan ps)
    {
        String errorMessage = "Unable to remove Passport Scan data.";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().remove(ps);
            ctrDB.commitCurrentTransaction();
            return 0;
            
        } catch (PersistenceException pe)
        {
            ctrDB.rollbackCurrentTransaction();
            if (pe instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) pe, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(pe, errorMessage);
            }
            return -1;
        }
    }
    
    public int removeExtraScans(Set<PassportScan> listPassportScan)
    {
        try
        {
            ctrDB.openTransaction();
            for (Iterator<PassportScan> it = listPassportScan.iterator(); it.hasNext();)
            {
                PassportScan ps = it.next();
                ctrDB.getSession().remove(ps);
            }
            listPassportScan.clear();            
            ctrDB.commitCurrentTransaction();
            return 0;
        } catch (PersistenceException hex)
        {
            ctrDB.rollbackCurrentTransaction();
            CtrAlertDialog.exceptionDialog(hex, "Error to clear passport scans");
            return -1;
        }
    }
    
    public PassportScan getScanVisa(Integer idProfile)
    {
        String hql;
        PassportScan ps;
        
        hql = "from PassportScan ps"
                + " where ps.monasticProfile.idProfile = " + idProfile
                + " and ps.contentVisaScan = true";
        
        try
        {
            ps = ctrDB.getSession().createQuery(hql, PassportScan.class
            ).getSingleResult();
            
        } catch (NoResultException nrex)
        {
            ps = null;
        }
        
        return ps;
    }
    
    public PassportScan getScanArriveStamp(Integer idProfile)
    {
        String hql;
        PassportScan ps;
        
        hql = "from PassportScan ps"
                + " where ps.monasticProfile.idProfile = " + idProfile
                + " and ps.contentArriveStamp = true";
        
        try
        {
            ps = ctrDB.getSession().createQuery(hql, PassportScan.class
            ).getSingleResult();
        } catch (NoResultException nrex)
        {
            ps = null;
        }
        
        return ps;
    }
    
    public PassportScan getScanLastVisaExt(Integer idProfile)
    {
        String hql;
        PassportScan ps;
        
        hql = "from PassportScan ps"
                + " where ps.monasticProfile.idProfile = " + idProfile
                + " and ps.contentLastVisaExt = true";
        
        try
        {
            ps = ctrDB.getSession().createQuery(hql, PassportScan.class
            ).getSingleResult();
        } catch (NoResultException ex)
        {
            ps = null;
        }
        
        return ps;
    }
    
}
