/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author WMJ_user
 */
public class CtrMonastery extends AbstractControllerDB
{

    public CtrMonastery(CtrDatabase ctrDB)
    {
        super(ctrDB);
    }

    public String addMonastery()
    {
        Monastery m;
        Integer generatedID;
        String errorMessage;

        m = new Monastery();
        m.setAddrCountry("THAILAND");
        m.setMonasteryOfJaokana("NO");
        errorMessage = "Unable to add new Monastery";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(m);
            ctrDB.getSession().flush();

            generatedID = m.getIdMonastery();
            m.setMonasteryName("New Monastery " + generatedID);

            ctrDB.commitCurrentTransaction();

            return m.getMonasteryName();

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

    public int updateMonastery(Monastery m)
    {
        try
        {
            ctrDB.openTransaction();
            ctrDB.commitCurrentTransaction();
            return 0;
        } catch (PersistenceException he)
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

        hql = "select m.monasteryName from Monastery m";
        alMonastery = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alMonastery;
    }

    public Monastery loadMonasteryByName(String name)
    {
        String hql;

        hql = "from Monastery m where m.monasteryName = '" + name + "'";
        return (Monastery) ctrDB.getSession().createQuery(hql).getSingleResult();
    }

    public Monastery loadMonasteryJaoKanaAmpher()
    {
        TypedQuery<Monastery> tq;

        try
        {
            tq = ctrDB.getSession().createNamedQuery("Monastery.findByMonasteryOfJaokana", Monastery.class);
            tq.setParameter("monasteryOfJaokana", "AMPHER");

            return tq.getSingleResult();
        } catch (NoResultException ex)
        {
            return null;
        }
    }

    public Monastery loadMonasteryJaoKanaJangwat()
    {
        TypedQuery<Monastery> tq;

        try
        {
            tq = ctrDB.getSession().createNamedQuery("Monastery.findByMonasteryOfJaokana", Monastery.class);
            tq.setParameter("monasteryOfJaokana", "JANGWAT");

            return tq.getSingleResult();
        } catch (NoResultException ex)
        {
            return null;
        }

    }
    
    public ArrayList<String> loadListMonasteryCountry()
    {
        Query qResult;
        String hql;
        
         hql = "select m.addrCountry"
                + " from Monastery m "
                + " where m.addrCountry <> 'THAILAND'"
                + " group by m.addrCountry"
                + " order by m.addrCountry";
        
        qResult = ctrDB.getSession().createQuery(hql);

        return (ArrayList<String>) qResult.getResultList();
    }

}
