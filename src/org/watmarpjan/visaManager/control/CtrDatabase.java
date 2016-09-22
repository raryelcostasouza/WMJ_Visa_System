/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;

/**
 *
 * @author WMJ_user
 */
public class CtrDatabase
{

//    private Session session;
//    private SessionFactory factory;
    private EntityManager entityManager;
    private EntityManagerFactory emFactory;

    public CtrDatabase()
    {
        final Configuration configuration;
        final StandardServiceRegistryBuilder builder;
        Instant start, finish;

        try
        {
            start = Instant.now();
            emFactory = Persistence.createEntityManagerFactory("WMJ_Visa_ManagerPU");
            entityManager = emFactory.createEntityManager();
            //entityManager.getTransaction().begin();
            //entityManager.persist(new Event("Our very first event!", new Date()));
            //entityManager.persist(new Event("A follow up event", new Date()));
            //entityManager.getTransaction().commit();
            //entityManager.close();

//            configuration = new Configuration().configure();
//            builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//
//            factory = configuration.buildSessionFactory(builder.build());
            finish = Instant.now();

            System.out.println("Hibernate load time: " + Duration.between(start, finish));
        } catch (HibernateException he)
        {
            CtrAlertDialog.exceptionDialog(he, "Error when connecting to Database: \n" + he.getMessage());
            System.exit(-1);
        }
    }

    public EntityManager getSession()
    {
        if (this.entityManager != null && entityManager.isOpen())
        {
            return entityManager;
        } else
        {
            entityManager = emFactory.createEntityManager();
        }
        return entityManager;
    }

//    public Session getSession()
//    {
//        if (this.session != null && session.isOpen())
//        {
//            return session;
//
//        } else
//        {
//            session = factory.openSession();
//        }
//
//        return session;
//    }
//    public void openTransaction()
//    {
//        getSession().beginTransaction();
//    }
    public void openTransaction()
    {
        getSession().getTransaction().begin();
    }

//    public void commitCurrentTransaction()
//    {
//        session.getTransaction().commit();
//    }
    public void commitCurrentTransaction()
    {
        entityManager.getTransaction().commit();
    }

//    public void rollbackCurrentTransaction()
//    {
//        session.getTransaction().rollback();
//        session.close();
//    }
    public void rollbackCurrentTransaction()
    {
        entityManager.getTransaction().rollback();
        entityManager.close();
    }

    public int updatePersistentObject(Serializable obj, String strErrorMessage)
    {
        try
        {
            openTransaction();
            getSession().refresh(obj);
            commitCurrentTransaction();
            return 0;
        } catch (HibernateException he)
        {
            rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) he, strErrorMessage + "\n\nDetails:");
            } else
            {
                CtrAlertDialog.exceptionDialog(he, strErrorMessage);
            }
            return -1;
        }
    }

    public void closeDBConnection()
    {
        try
        {
            if (entityManager.isOpen())
            {
                entityManager.close();
            }
            emFactory.close();
        } catch (HibernateException hex)
        {
            CtrAlertDialog.exceptionDialog(hex, "Error to close DB connection.");
        }
    }

    //TODO test this function
    public void saveOrUpdate(PassportScan ps)
    {
        PassportScan resultSearch;

        resultSearch = getSession().find(PassportScan.class, ps.getId());
        if (resultSearch != null)
        {
            getSession().refresh(ps);
        } else
        {
            getSession().persist(ps);
        }
    }
}
