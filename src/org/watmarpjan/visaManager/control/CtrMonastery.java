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
    
    public ArrayList<String> loadMonasteryNicknameList()
    {
        ArrayList<String> alMonastery;
        String hql;

        hql = "select m.monasteryNickname from Monastery m where m.monasteryNickname is not null";
        alMonastery = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alMonastery;
    }

    public Monastery loadByName(String name)
    {
        return (Monastery) ctrDB.loadEntityByUniqueProperty("Monastery", "monasteryName", name);
    }
    
    public Monastery loadByNickname(String nickname)
    {
        return (Monastery) ctrDB.loadEntityByUniqueProperty("Monastery", "monasteryNickname", nickname);
    }

    public Monastery loadMonasteryJaoKanaAmpher(Monastery residenceMonastery)
    {
        String hql;
        Monastery mJaokanaAmpher;
        
        try
        {
            
            //looks for the jaokana ampher in the DB that is on the same Province and Ampher
            //as the Residence Monatery of the Monastic
            hql = "select m "
                    + " from Monastery m"
                    + " where m.monasteryOfJaokana = 'AMPHER' "
                    
                    + " and m.addrAmpher = '" + residenceMonastery.getAddrAmpher() +"'"
                    + " and m.addrJangwat = '" + residenceMonastery.getAddrJangwat()+"'";
            
            mJaokanaAmpher = ctrDB.getSession().createQuery(hql, Monastery.class).getSingleResult();
            

            return mJaokanaAmpher;
        } catch (NoResultException ex)
        {
            return null;
        }
    }

    public Monastery loadMonasteryJaoKanaJangwat(Monastery residenceMonastery)
    {
        String hql;
        Monastery mJaokanaJangwat;

        try
        {
             //looks for the jaokana jangwat in the DB that is on the same province
            //as the Residence Monatery of the Monastic
           hql = "select m "
                    + " from Monastery m"
                    + " where m.monasteryOfJaokana = 'JANGWAT' "
                    + " and m.addrJangwat = '" + residenceMonastery.getAddrJangwat() +"'";
            
            mJaokanaJangwat = ctrDB.getSession().createQuery(hql, Monastery.class).getSingleResult();
            

            return mJaokanaJangwat;
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
