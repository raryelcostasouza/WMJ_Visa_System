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
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.EntryDueTask;
import org.watmarpjan.visaManager.model.EntryUpdate90DayNotice;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author WMJ_user
 */
public class CtrMonasticProfile
{

    private final CtrDatabase ctrDatabase;

    public CtrMonasticProfile(CtrDatabase ctrDatabase)
    {
        this.ctrDatabase = ctrDatabase;
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
            ctrDatabase.openTransaction();
            ctrDatabase.getSession().persist(p);
            ctrDatabase.getSession().flush();
            generatedID = p.getIdProfile();
            p.setNickname("New MonasticProfile " + generatedID);
            ctrDatabase.commitCurrentTransaction();
            return p.getNickname();

        } catch (PersistenceException he)
        {
            ctrDatabase.rollbackCurrentTransaction();

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

    public int updateProfile(MonasticProfile p)
    {
        ConstraintViolationException cve;

        try
        {
            ctrDatabase.openTransaction();

            ctrDatabase.commitCurrentTransaction();
            return 0;
        } catch (PersistenceException he)
        {
            ctrDatabase.rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                cve = (ConstraintViolationException) he;
                CtrAlertDialog.databaseExceptionDialog(cve, "Error when saving profile info.");
            } else
            {
                CtrAlertDialog.exceptionDialog(he, "Error when saving profile info.");
            }
            return -1;
        }
    }

    public int addEntryReentry(MonasticProfile p, PassportScan psArriveStamp)
    {
        ConstraintViolationException cve;
        final String msg = "Unable to register entry/reentry information.";

        try
        {
            ctrDatabase.openTransaction();
            if (psArriveStamp.getIdPassportScan() == null)
            {
                ctrDatabase.getSession().persist(psArriveStamp);
            }

            ctrDatabase.commitCurrentTransaction();
            return 0;
        } catch (PersistenceException he)
        {
            ctrDatabase.rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                cve = (ConstraintViolationException) he;
                CtrAlertDialog.databaseExceptionDialog(cve, msg);
            } else
            {
                CtrAlertDialog.exceptionDialog(he, msg);
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

        } else
        {
            hql = "select p "
                    + "from MonasticProfile p "
                    + "order by "
                    + "p.bhikkhuOrdDate asc nulls last "
                    + "p.samaneraOrdDate asc nulls last, "
                    + "p.pahkahwOrdDate asc nulls last";

        }

        listProfile = ctrDatabase.getSession().createQuery(hql, MonasticProfile.class).getResultList();
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

        alProfile = (ArrayList<MonasticProfile>) ctrDatabase.getSession().createQuery(hql, MonasticProfile.class).getResultList();

        if (!alProfile.isEmpty())
        {
            return alProfile.get(index);
        } else
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
            p = (MonasticProfile) ctrDatabase.getSession().getReference(MonasticProfile.class, id);
            return p;
        } catch (PersistenceException he)
        {
            CtrAlertDialog.exceptionDialog(he, "Error when loading profile.");
            return null;
        }
    }

    public void refreshProfile(MonasticProfile p)
    {
        ctrDatabase.getSession().refresh(p);
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

    public ArrayList<EntryDueTask> loadListDue90DayNotice()
    {
        String hql;

        hql = "select new org.watmarpjan.visaManager.model.Notice90DayTaskEntry(p.nickname, p.next90DayNotice)"
                + " from MonasticProfile p "
                + " where p.status = 'THAILAND' and"
                + " p.next90DayNotice is not null "
                + " order by p.next90DayNotice";

        return queryDueTaskEntry(hql);
    }

    public ArrayList<EntryUpdate90DayNotice> loadListUpdate90DayNotice()
    {
        String hql;
        hql = "select new org.watmarpjan.visaManager.model.EntryUpdate90DayNotice(p.idprofile, p.nickname, p.next90DayNotice)"
                + " from MonasticProfile p "
                + " where p.status = 'THAILAND' and"
                + " p.next90DayNotice is not null "
                + " order by p.next90DayNotice";

        return (ArrayList<EntryUpdate90DayNotice>) ctrDatabase.getSession().createQuery(hql).getResultList();
    }

    public ArrayList<EntryDueTask> loadListDueVisaExtension(String currentLocation)
    {
        String hql;

        hql = "select new org.watmarpjan.visaManager.model.VisaExtTaskEntry(p.nickname, p.visaExpiryDate)"
                + " from MonasticProfile p "
                + " where p.status = '" + currentLocation + "' and"
                + " p.visaExpiryDate is not null "
                + " order by p.visaExpiryDate";

        return queryDueTaskEntry(hql);
    }

    public ArrayList<EntryDueTask> loadListDuePassportRenewal(String currentLocation)
    {
        String hql;

        hql = "select new org.watmarpjan.visaManager.model.PassportRenewTaskEntry(p.nickname, p.passportExpiryDate)"
                + " from MonasticProfile p "
                + " where p.status = '" + currentLocation + "' and"
                + " p.passportExpiryDate is not null "
                + " order by p.passportExpiryDate";

        return queryDueTaskEntry(hql);
    }

    private MonasticProfile queryProfile(String hql)
    {
        ArrayList<MonasticProfile> alProfile;

        alProfile = (ArrayList<MonasticProfile>) ctrDatabase.getSession().createQuery(hql).getResultList();

        if (!alProfile.isEmpty())
        {
            return alProfile.get(0);
        } else
        {
            return null;
        }
    }

    private ArrayList<String> queryStringField(String hql)
    {
        Query qResult;

        qResult = ctrDatabase.getSession().createQuery(hql);

        return (ArrayList<String>) qResult.getResultList();
    }

    private ArrayList<EntryDueTask> queryDueTaskEntry(String hql)
    {
        Query qResult;

        qResult = ctrDatabase.getSession().createQuery(hql);

        return (ArrayList<EntryDueTask>) qResult.getResultList();
    }

}
