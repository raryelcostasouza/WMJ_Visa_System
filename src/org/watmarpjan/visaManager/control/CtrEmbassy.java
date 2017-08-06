/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import javax.persistence.PersistenceException;
import org.watmarpjan.visaManager.model.hibernate.Embassy;

/**
 *
 * @author KroobaHariel
 */
public class CtrEmbassy extends AbstractControllerDB
{

    public CtrEmbassy(CtrDatabase ctrDB)
    {
        super(ctrDB);
    }

    public Embassy loadEmbassyByName(String name)
    {
        return (Embassy) ctrDB.loadEntityByProperty("Embassy", "nameEn", name);
    }
    
    public ArrayList<String> loadEmbassyList()
    {
        ArrayList<String> alEmbassy;
        String hql;

        hql = "select e.nameEn from Embassy e";
        alEmbassy = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alEmbassy;
    }

     public String add()
    {
        Embassy e;
        Integer generatedID;
        String errorMessage;

        e = new Embassy();
        errorMessage = "Unable to add new Embassy";
        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(e);
            ctrDB.getSession().flush();

            generatedID = e.getIdEmbassy();
            e.setNameEn("New Embassy " + generatedID);

            ctrDB.commitCurrentTransaction();

            return e.getNameEn();

        } catch (PersistenceException he)
        {
            ctrDB.handleException(he, errorMessage);
            return null;
        }
    }
    
    public int update(Embassy e)
    {
        try
        {
            ctrDB.openTransaction();
            ctrDB.commitCurrentTransaction();
            return 0;
        }
        catch (PersistenceException he)
        {
            ctrDB.handleException(he, "Error when saving embassy info.");
            return -1;
        }
    }

}
