/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
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

    public int addNewPrintout(LocalDate ldNotifDate, ArrayList<String> listNicknameSelectedMonastics)
    {
        ConstraintViolationException cve;
        PrintoutTm30 objTM30;
        MonasticProfile mp;

        objTM30 = new PrintoutTm30();
        objTM30.setNotifDate(Util.convertLocalDateToDate(ldNotifDate));

        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(objTM30);
            for (String nickname : listNicknameSelectedMonastics)
            {
                mp = ctrProfile.loadProfileByNickName(nickname);
                mp.setPrintoutTm30(objTM30);
            }
            ctrDB.commitCurrentTransaction();
           
            
            return 0;
        }
        catch (PersistenceException he)
        {
            ctrDB.rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                cve = (ConstraintViolationException) he;
                CtrAlertDialog.databaseExceptionDialog(cve, "Error when saving profile info.");
            }
            else
            {
                CtrAlertDialog.exceptionDialog(he, "Error when saving profile info.");
            }
            return -1;
        }
    }
    
    
    
    public ArrayList<PrintoutTm30> loadListPrintoutTM30()
    {
        String hql;

        hql = "from PrintoutTm30 p"
                + " where p.notifDate is not null"
                + " order by p.notifDate desc";
        return (ArrayList<PrintoutTm30>) ctrDB.getSession().createQuery(hql).getResultList();
    }
    
    public void refresh(PrintoutTm30 objTM30)
    {
        ctrDB.getSession().refresh(objTM30);
    }

}
