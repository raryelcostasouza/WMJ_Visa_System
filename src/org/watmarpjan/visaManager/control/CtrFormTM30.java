/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.Tm30NotificationResidence;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrFormTM30 extends AbstractControllerDB
{

    private final CtrMonasticProfile ctrMP;

    public CtrFormTM30(CtrDatabase ctrDB, CtrMonasticProfile ctrMP)
    {
        super(ctrDB);
        this.ctrMP = ctrMP;
    }

    public int create(Tm30NotificationResidence objFormTM30, ArrayList<String> listMonastics)
    {
        MonasticProfile mp;
        String errorMessage;

        errorMessage = "Unable to add new Form TM30.";
        try
        {

            ctrDB.openTransaction();

            ctrDB.getSession().persist(objFormTM30);
            for (String nickName : listMonastics)
            {
                mp = ctrMP.loadProfileByNickName(nickName);
                mp.setFormTm30(objFormTM30);
            }

            ctrDB.commitCurrentTransaction();
            return 0;
        } catch (PersistenceException e)
        {
            ctrDB.rollbackCurrentTransaction();

            if (e instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) e, errorMessage);
            } else
            {
                CtrAlertDialog.exceptionDialog(e, errorMessage);
            }
            return -1;
        }
    }

    public ArrayList<Tm30NotificationResidence> loadAll()
    {
        Query result;

        result = ctrDB.getSession().createNamedQuery("Tm30NotificationResidence.findAll");
        return (ArrayList<Tm30NotificationResidence>) result.getResultList();
    }

}
