/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.PrintoutTm30;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author wmj_user
 */
public class CtrPrintoutTM30 extends AbstractControllerDB {

    private final CtrMonasticProfile ctrProfile;
    
    public CtrPrintoutTM30(CtrDatabase ctrDB, CtrMonasticProfile ctrProfile)
    {
        super(ctrDB);
        this.ctrProfile = ctrProfile;
    }

    public int create(LocalDate ldNotifDate, ArrayList<String> listNicknameSelectedMonastics, int auxIndex, Monastery mResidence)
    {
        ConstraintViolationException cve;
        PrintoutTm30 objTM30;
        MonasticProfile mp;

        objTM30 = new PrintoutTm30();
        objTM30.setNotifDate(Util.convertLocalDateToDate(ldNotifDate));
        objTM30.setAuxIndex(auxIndex);
        objTM30.setMonasteryResidence(mResidence);

        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(objTM30);
            for (String nickname : listNicknameSelectedMonastics)
            {
                mp = ctrProfile.loadByNickName(nickname);
                mp.setPrintoutTm30(objTM30);
            }
            ctrDB.commitCurrentTransaction();
            return 0;
        }
        catch (PersistenceException he)
        {
            ctrDB.handleException(he, "Error when saving profile info.");
            return -1;
        }
    }
    
    public int remove(PrintoutTm30 objTM30)
    {
       String errorMessage = "Unable to remove this TM30 Printout entry.";
        try
        {
            ctrDB.openTransaction();
            
            //removes the association between the monastic and the TM30 printout
            for (MonasticProfile objMP : objTM30.getMonasticProfileSet())
            {
                objMP.setPrintoutTm30(null);
            }
            ctrDB.getSession().remove(objTM30);
            
            
            ctrDB.commitCurrentTransaction();
            return 0;

        }
        catch (PersistenceException hex)
        {
            ctrDB.handleException(hex, errorMessage);
            return -1;
        }  
    }
    
    
    
    public ArrayList<PrintoutTm30> loadList()
    {
        String hql;

        hql = "from PrintoutTm30 p"
                + " where p.notifDate is not null"
                + " order by p.notifDate desc";
        return (ArrayList<PrintoutTm30>) ctrDB.getSession().createQuery(hql).getResultList();
    }
    
    //if there is more than one printout on the same date
    //they will have different auxIndex values
    //this function returns the max auxIndex value for a certain notif date    
    //if there is no entry for a certain date returns NULL
    public Integer getMaxAuxIndexPrintoutForNotifDate(LocalDate ldNotif)
    {
        String hql;
        Date dNotif;
        
        dNotif = Util.convertLocalDateToDate(ldNotif);
        
        hql = "select max(p.auxIndex) from PrintoutTm30 p"
                + " where p.notifDate = :pNotifDate";
               
        return ctrDB.getSession().createQuery(hql, Integer.class).setParameter("pNotifDate", dNotif).getSingleResult();
   }
    
    public void refresh(PrintoutTm30 objTM30)
    {
        ctrDB.getSession().refresh(objTM30);
    }

}
