/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import javax.persistence.PersistenceException;
import org.watmarpjan.visaManager.model.hibernate.Upajjhaya;

/**
 *
 * @author WMJ_user
 */
public class CtrUpajjhaya extends AbstractControllerDB
{
    private final String ERROR_UNIQUE_NAME = "The upajjhaya name you are trying to save already exists on database.";
    
    public CtrUpajjhaya(CtrDatabase ctrDB)
    {
        super(ctrDB);
    }

    public String create()
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
            ctrDB.handleException(he, errorMessage, ERROR_UNIQUE_NAME);
            return null;
        }
    }

    public int update(Upajjhaya u)
    {
        return ctrDB.updatePersistentObject(u, "Unable to update upajjhaya data.", ERROR_UNIQUE_NAME);
    }

    public ArrayList<String> loadList()
    {
        ArrayList<String> alUpajjhaya;
        String hql;

        hql = "select u.upajjhayaName from Upajjhaya u";
        alUpajjhaya = (ArrayList<String>) ctrDB.getSession().createQuery(hql).getResultList();

        return alUpajjhaya;
    }

    public Upajjhaya loadByName(String name)
    {
        return (Upajjhaya) ctrDB.loadEntityByUniqueProperty("Upajjhaya", "upajjhayaName", name);
    }

}
