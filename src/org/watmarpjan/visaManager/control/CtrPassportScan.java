/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;

/**
 *
 * @author WMJ_user
 */
public class CtrPassportScan
{

    public static final int SCAN_NUMBER_1 = 1;
    public static final int SCAN_NUMBER_2 = 2;
    public static final int SCAN_NUMBER_3 = 3;

    private final CtrDatabase ctrDB;

    public CtrPassportScan(CtrDatabase ctrDB)
    {
        this.ctrDB = ctrDB;
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

        } catch (HibernateException hex)
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

    //TODO Review and remove this function
    public int removeByScanNumber(Integer idProfile, int scanNumber)
    {
        PassportScan ps;
        String hql;

        String errorMessage = "Unable to remove passport scan data.";

        hql = "from PassportScan ps"
                + " where ps.profile.idprofile = " + idProfile
                + " and ps.scanNumber = '" + scanNumber + "'";

        ps = (PassportScan) ctrDB.getSession().createQuery(hql).getSingleResult();
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().remove(ps);
            ctrDB.commitCurrentTransaction();
            return 0;

        } catch (HibernateException hex)
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
            ctrDB.commitCurrentTransaction();
            return 0;
        } catch (HibernateException hex)
        {
            ctrDB.rollbackCurrentTransaction();
            CtrAlertDialog.exceptionDialog(hex, "Error to clear passport scans");
            return -1;
        }
    }

    public PassportScan getPassportScanByIndex(Integer idProfile, int index)
    {
        String hql;
        ArrayList<PassportScan> listPassportScans;

        hql = "from PassportScan ps"
                + " where ps.profile.idprofile = " + idProfile;

        listPassportScans = (ArrayList<PassportScan>) ctrDB.getSession().createQuery(hql).getResultList();
        if (index <= listPassportScans.size() - 1)
        {
            return listPassportScans.get(index);
        } else
        {
            return null;
        }
    }

    public PassportScan getScanVisa(Integer idProfile)
    {
        String hql;
        PassportScan ps;

        hql = "from PassportScan ps"
                + " where ps.profile.idprofile = " + idProfile
                + " and ps.contentVisaScan = true";

        try
        {
            ps = ctrDB.getSession().createQuery(hql, PassportScan.class).getSingleResult();

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
                + " where ps.profile.idprofile = " + idProfile
                + " and ps.contentArriveStamp = true";

        try
        {
            ps = ctrDB.getSession().createQuery(hql, PassportScan.class).getSingleResult();
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
                + " where ps.profile.idprofile = " + idProfile
                + " and ps.contentLastVisaExt = true";

        try
        {
            ps = ctrDB.getSession().createQuery(hql, PassportScan.class).getSingleResult();
        } catch (NoResultException ex)
        {
            ps = null;
        }

        return ps;
    }

}
