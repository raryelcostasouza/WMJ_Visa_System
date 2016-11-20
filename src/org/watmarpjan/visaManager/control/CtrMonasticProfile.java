/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.dueTask.EntryDueTask;
import org.watmarpjan.visaManager.model.EntryUpdate90DayNotice;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author WMJ_user
 */
public class CtrMonasticProfile extends AbstractControllerDB
{

    public CtrMonasticProfile(CtrDatabase ctrDatabase)
    {
        super(ctrDatabase);
    }

    public String addProfile()
    {
        MonasticProfile p;
        Integer generatedID;
        String errorMessage;

        p = new MonasticProfile();
        p.setStatus("THAILAND");
        p.setDhammaStudies("Regular");

        errorMessage = "Unable to add new MonasticProfile.";

        try
        {
            ctrDB.openTransaction();
            ctrDB.getSession().persist(p);
            ctrDB.getSession().flush();
            generatedID = p.getIdProfile();
            p.setNickname("New MonasticProfile " + generatedID);
            ctrDB.commitCurrentTransaction();
            return p.getNickname();

        } catch (PersistenceException he)
        {
            ctrDB.rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) he, errorMessage);
            }
            else
            {
                CtrAlertDialog.exceptionDialog(he, errorMessage);
            }
            return null;
        }
    }

    public int updateProfile(MonasticProfile p)
    {
        ConstraintViolationException cve;

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

    public ArrayList<String> loadProfileNicknameList(boolean onlyActive)
    {
        String hql;
        List<MonasticProfile> listProfile;
        ArrayList<String> listNickname;

        if (onlyActive)
        {
            hql = "select p from MonasticProfile p"
                    + " where p.status <> 'INACTIVE'"
                    + " order by"
                    + " p.bhikkhuOrdDate asc nulls last,"
                    + " p.samaneraOrdDate asc nulls last,"
                    + " p.pahkahwOrdDate asc nulls last";

        }
        else
        {
            hql = "select p "
                    + "from MonasticProfile p "
                    + "order by "
                    + "p.bhikkhuOrdDate asc nulls last "
                    + "p.samaneraOrdDate asc nulls last, "
                    + "p.pahkahwOrdDate asc nulls last";

        }

        listProfile = ctrDB.getSession().createQuery(hql, MonasticProfile.class).getResultList();
        listNickname = new ArrayList<>();
        for (MonasticProfile p : listProfile)
        {
            listNickname.add(p.getNickname());
        }
        return listNickname;
    }

    public MonasticProfile loadProfileByIndex(int index)
    {
        ArrayList<MonasticProfile> alProfile;
        String hql;

        hql = "from MonasticProfile p "
                + "order by "
                + "p.bhikkhuOrdDate asc nulls last "
                + "p.samaneraOrdDate asc nulls last,"
                + "p.pahkahwOrdDate asc nulls last";

        alProfile = (ArrayList<MonasticProfile>) ctrDB.getSession().createQuery(hql, MonasticProfile.class).getResultList();

        if (!alProfile.isEmpty())
        {
            return alProfile.get(index);
        }
        else
        {
            return null;
        }
    }

    public MonasticProfile loadProfileByNickName(String nickName)
    {
        String hql;

        hql = "from MonasticProfile p where p.nickname = " + "'" + nickName + "'";
        return queryProfile(hql);
    }

    public MonasticProfile loadProfileByID(Integer id)
    {
        MonasticProfile p;
        try
        {
            p = (MonasticProfile) ctrDB.getSession().getReference(MonasticProfile.class, id);
            return p;
        } catch (PersistenceException he)
        {
            CtrAlertDialog.exceptionDialog(he, "Error when loading profile.");
            return null;
        }
    }

    public void refreshProfile(MonasticProfile p)
    {
        ctrDB.getSession().refresh(p);
    }

    public ArrayList<String> loadOccupationEnglishList()
    {
        String hql;

        hql = "select p.occupationEnglish"
                + " from MonasticProfile p "
                + " where p.occupationEnglish is not null"
                + " group by p.occupationEnglish"
                + " order by p.occupationEnglish";

        return queryStringField(hql);
    }

    public ArrayList<String> loadOccupationThaiList()
    {
        String hql;

        hql = "select p.occupationThai"
                + " from MonasticProfile p "
                + " where p.occupationThai is not null"
                + " group by p.occupationThai"
                + " order by p.occupationThai";

        return queryStringField(hql);
    }

    public ArrayList<String> loadCertificateEngList()
    {
        String hql;

        hql = "select p.certificateEnglish"
                + " from MonasticProfile p "
                + " where p.certificateEnglish is not null"
                + " group by p.certificateEnglish"
                + " order by p.certificateEnglish";

        return queryStringField(hql);
    }

    public ArrayList<String> loadCertificateThaiList()
    {
        String hql;

        hql = "select p.certificateThai"
                + " from MonasticProfile p "
                + " where p.certificateThai is not null"
                + " group by p.certificateThai"
                + " order by p.certificateThai";

        return queryStringField(hql);
    }

    public ArrayList<String> loadListPortOfEntry()
    {
        String hql;

        hql = "select p.arrivalPortOfEntry"
                + " from MonasticProfile p "
                + " where p.arrivalPortOfEntry is not null"
                + " group by p.arrivalPortOfEntry"
                + " order by p.arrivalPortOfEntry";
        return queryStringField(hql);
    }

    public ArrayList<String> loadListTravelFrom()
    {
        String hql;

        hql = "select p.arrivalTravelFrom"
                + " from MonasticProfile p "
                + " where p.arrivalTravelFrom is not null"
                + " group by p.arrivalTravelFrom"
                + " order by p.arrivalTravelFrom";
        return queryStringField(hql);
    }

    public ArrayList<EntryDueTask> loadListDue90DayNotice()
    {
        String hql;

        hql = "select new org.watmarpjan.visaManager.model.dueTask.Notice90DayTaskEntry(p.nickname, p.next90DayNotice)"
                + " from MonasticProfile p "
                + " where p.status = 'THAILAND' and"
                + " p.next90DayNotice is not null "
                + " order by p.next90DayNotice";

        return queryDueTaskEntry(hql);
    }

    public ArrayList<EntryUpdate90DayNotice> loadListUpdate90DayNotice()
    {
        String hql;
        hql = "select new org.watmarpjan.visaManager.model.EntryUpdate90DayNotice(p.idProfile, p.nickname, p.next90DayNotice)"
                + " from MonasticProfile p "
                + " where p.status = 'THAILAND' and"
                + " p.next90DayNotice is not null "
                + " order by p.next90DayNotice";

        return (ArrayList<EntryUpdate90DayNotice>) ctrDB.getSession().createQuery(hql).getResultList();
    }

    public ArrayList<EntryDueTask> loadListDueVisaExtension(String currentLocation)
    {
        String hql1, hql2;
        ArrayList<EntryDueTask> listVisaNotExtended, listVisaExtended, listMerged;

        hql1 = "select new org.watmarpjan.visaManager.model.dueTask.VisaExtTaskEntry(p.nickname, max(vext.expiryDate))"
                + " from MonasticProfile p"
                + " inner join p.visaExtensionSet vext"
                + " where p.status = '" + currentLocation + "'"
                + " and size(p.visaExtensionSet) > 0"
                + " group by p.nickname"
                + " order by max(vext.expiryDate)";
         listVisaExtended = queryDueTaskEntry(hql1);

        hql2 = "select new org.watmarpjan.visaManager.model.dueTask.VisaExtTaskEntry(p.nickname, p.visaExpiryDate)"
                + " from MonasticProfile p"
                + " where p.status = '" + currentLocation + "'"
                + " and p.visaExpiryDate is not null"
                + " and size(p.visaExtensionSet) = 0"
                + " order by p.visaExpiryDate";
        listVisaNotExtended = queryDueTaskEntry(hql2);
        
        listMerged = new ArrayList<>();
        
        listMerged.addAll(listVisaNotExtended);
        listMerged.addAll(listVisaExtended);
        listMerged.sort(null);
        
        return listMerged;
    }

    public ArrayList<EntryDueTask> loadListDuePassportRenewal(String currentLocation)
    {
        String hql;

        hql = "select new org.watmarpjan.visaManager.model.dueTask.PassportRenewTaskEntry(p.nickname, p.passportExpiryDate)"
                + " from MonasticProfile p "
                + " where p.status = '" + currentLocation + "' and"
                + " p.passportExpiryDate is not null "
                + " order by p.passportExpiryDate";

        return queryDueTaskEntry(hql);
    }

    private MonasticProfile queryProfile(String hql)
    {
        ArrayList<MonasticProfile> alProfile;

        alProfile = (ArrayList<MonasticProfile>) ctrDB.getSession().createQuery(hql).getResultList();

        if (!alProfile.isEmpty())
        {
            return alProfile.get(0);
        }
        else
        {
            return null;
        }
    }

    private ArrayList<String> queryStringField(String hql)
    {
        Query qResult;

        qResult = ctrDB.getSession().createQuery(hql);

        return (ArrayList<String>) qResult.getResultList();
    }

    private ArrayList<EntryDueTask> queryDueTaskEntry(String hql)
    {
        Query qResult;

        qResult = ctrDB.getSession().createQuery(hql);

        return (ArrayList<EntryDueTask>) qResult.getResultList();
    }
}
