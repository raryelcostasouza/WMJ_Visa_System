/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;

/**
 *
 * @author WMJ_user
 */
public class CtrDatabase
{

    private EntityManager entityManager;
    private EntityManagerFactory emFactory;

    public CtrDatabase()
    {
        final Configuration configuration;
        final StandardServiceRegistryBuilder builder;
        Instant start, finish;
        Path pathDB;

        pathDB = Paths.get(System.getProperty("user.dir")).resolve("../../Data");
        System.setProperty("derby.system.home", pathDB.toString());
        try
        {
            start = Instant.now();
            emFactory = Persistence.createEntityManagerFactory("WMJ_Visa_ManagerPU");
            entityManager = emFactory.createEntityManager();
            finish = Instant.now();

            System.out.println("Hibernate Load: " + Duration.between(start, finish));
        }
        catch (PersistenceException he)
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
        }
        else
        {
            entityManager = emFactory.createEntityManager();
        }
        return entityManager;
    }

    public void openTransaction()
    {
        getSession().getTransaction().begin();
    }

    public void commitCurrentTransaction()
    {
        entityManager.getTransaction().commit();
    }

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
            commitCurrentTransaction();
            return 0;
        }
        catch (PersistenceException he)
        {
            rollbackCurrentTransaction();

            if (he instanceof ConstraintViolationException)
            {
                CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) he, strErrorMessage + "\n\nDetails:");
            }
            else
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
        }
        catch (PersistenceException hex)
        {
            CtrAlertDialog.exceptionDialog(hex, "Error to close DB connection.");
        }
    }

    public Object loadEntityByUniqueProperty(String entityName, String propertyName, String value2Search)
    {
        String hql;

        hql = "from " + entityName + " e where e." + propertyName + " = '" + value2Search + "'";
        return getSession().createQuery(hql).getSingleResult();
    }
    
    public void handleException(PersistenceException pe, String errorMessage)
    {
        rollbackCurrentTransaction();

        if (pe instanceof ConstraintViolationException)
        {
            CtrAlertDialog.databaseExceptionDialog((ConstraintViolationException) pe, errorMessage);
        }
        else
        {
            CtrAlertDialog.exceptionDialog(pe, errorMessage);
        }
    }
}
