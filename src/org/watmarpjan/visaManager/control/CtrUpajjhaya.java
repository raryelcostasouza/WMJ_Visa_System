/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Upajjhaya;

/**
 *
 * @author WMJ_user
 */
public class CtrUpajjhaya
{

    private final CtrDatabase ctrDB;

    public CtrUpajjhaya(CtrDatabase ctrDB)
    {
        this.ctrDB = ctrDB;
    }

    public String addNew()
    {
        Upajjhaya u;
        Integer generatedID;
        String errorMessage;

        u = new Upajjhaya();
        errorMessage = "Unable to add new Upajjhaya.";
        try
        {
            ctrDB.openTransaction();

            ctrDB.getSession().persist(u);
            ctrDB.getSession().flush();

            generatedID = u.getIdUpajjhaya();
            u.setUpajjhayaName("New Upajjhaya " + generatedID);

            ctrDB.commitCurrentTransaction();

            return u.getUpajjhayaName();

        } catch (PersistenceException he)
        {
            ctrDB.rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) he, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(he, errorMessage);
            }
            return null;
        }
    }

    public int update(Upajjhaya u)
    {
        return ctrDB.updatePersistentObject(u, "Unable to update upajjhaya data.");
    }

    public ArrayList<String> loadUpajjhayaList()
    {
        ArrayList<String> alUpajjhaya;
        String hql;

        hql = "select u.upajjhayaName from Upajjhaya u";
        alUpajjhaya = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alUpajjhaya;
    }

    public Upajjhaya loadUpajjhayaByName(String name)
    {
        ArrayList<Upajjhaya> alUpajjhaya;
        String hql;

        hql = "from Upajjhaya u where u.upajjhayaName = " + "'" + name + "'";
        alUpajjhaya = (ArrayList<Upajjhaya>) ctrDB.getSession().createQuery(hql).getResultList();
        if (!alUpajjhaya.isEmpty())
        {
            return alUpajjhaya.get(0);
        }

        return null;
    }

}
