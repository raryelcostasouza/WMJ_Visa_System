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

    public String create()
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
            ctrDB.handleException(he, errorMessage);
            return null;
        }
    }

    public int update(Monastery m)
    {
        return ctrDB.updatePersistentObject(m, "Error when saving monastery info.");
    }

    public ArrayList<String> loadMonasteryList()
    {
        ArrayList<String> alMonastery;
        String hql;

        hql = "select m.monasteryName from Monastery m";
        alMonastery = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alMonastery;
    }

    public Monastery loadByName(String name)
    {
        return (Monastery) ctrDB.loadEntityByUniqueProperty("Monastery", "monasteryName", name);
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
