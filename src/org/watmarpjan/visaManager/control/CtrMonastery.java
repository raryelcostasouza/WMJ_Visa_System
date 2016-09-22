/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author WMJ_user
 */
public class CtrMonastery
{

    private CtrDatabase ctrDB;

    public CtrMonastery(CtrDatabase ctrDB)
    {
        this.ctrDB = ctrDB;
    }

    public String addMonastery()
    {
        Monastery m;
        Integer generatedID;
        String errorMessage;

        m = new Monastery();
        m.setMonasteryOfJaokana("NO");
        errorMessage = "Unable to add new Monastery";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(m);
            ctrDB.getSession().flush();

            generatedID = m.getId();
            m.setName("New Monastery " + generatedID);

            ctrDB.commitCurrentTransaction();

            return m.getName();

        } catch (HibernateException he)
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

    public int updateMonastery(Monastery m)
    {
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().refresh(m);
            ctrDB.commitCurrentTransaction();
            return 0;
        } catch (HibernateException he)
        {
            ctrDB.rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) he, "Error when saving monastery info.");
            } else
            {
                CtrAlertDialog.exceptionDialog(he, "Error when saving monastery info.");
            }
            return -1;
        }
    }

    public ArrayList<String> loadMonasteryList()
    {
        ArrayList<String> alMonastery;
        String hql;

        hql = "select m.name from Monastery m";
        alMonastery = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alMonastery;
    }

    public Monastery loadMonasteryByName(String name)
    {
        String hql;

        hql = "from Monastery m where m.name = '" + name + "'";
        return (Monastery) ctrDB.getSession().createQuery(hql).getSingleResult();
    }

}
