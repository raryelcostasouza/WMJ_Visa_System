/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class EntryWorkflowVisaExt implements Comparable<EntryWorkflowVisaExt>
{
    
    private SimpleStringProperty monastic;
    private SimpleStringProperty strDueDate;
    
    private SimpleStringProperty snpPrawat;
    private SimpleStringProperty snpLetter;
    private SimpleStringProperty snpPhotocopies;
    
    private SimpleStringProperty immApprovalSNP;
    private SimpleStringProperty immTM7;
    private SimpleStringProperty immLetter;
    private SimpleStringProperty immPhotocopies;
    private SimpleStringProperty immExtra;
    
    private LocalDate ldDueDate;
    
    public static final String STATUS_MISSING = "Missing";
    public static final String STATUS_PRINTED = "Printed";
    public static final String STATUS_SIGNED_MONASTIC = "Signed by monastic";
    
    public EntryWorkflowVisaExt(String monastic, String snpPrawat, String snpLetter,
            String photocopiesSNP, String immApprovalSNP, String immTM7,
            String immLetter, String photocopiesIMM, Boolean immExtra, Date dueDate)
    {
        
        this.monastic = new SimpleStringProperty(monastic);
        
        this.ldDueDate = Util.convertDateToLocalDate(dueDate);
        System.out.println(ldDueDate.format(Util.DEFAULT_DATE_FORMAT));
        this.strDueDate = new SimpleStringProperty(ldDueDate.format(Util.DEFAULT_DATE_FORMAT));
        
        if (snpPrawat != null)
        {
            this.snpPrawat = new SimpleStringProperty(snpPrawat);
        }
        else
        {
            this.snpPrawat = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (snpLetter != null)
        {
            this.snpLetter = new SimpleStringProperty(snpLetter);
        }
        else
        {
            this.snpLetter = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (photocopiesSNP != null)
        {
            this.snpPhotocopies = new SimpleStringProperty(photocopiesSNP);
        }
        else
        {
            this.snpPhotocopies = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (immApprovalSNP != null)
        {
            this.immApprovalSNP = new SimpleStringProperty(immApprovalSNP);
        }
        else
        {
            this.immApprovalSNP = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (immTM7 != null)
        {
            this.immTM7 = new SimpleStringProperty(immTM7);
        }
        else
        {
            this.immTM7 = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (immLetter != null)
        {
            this.immLetter = new SimpleStringProperty(immLetter);
        }
        else
        {
            this.immLetter = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (photocopiesIMM != null)
        {
            this.immPhotocopies = new SimpleStringProperty(photocopiesIMM);
        }
        else
        {
            this.immPhotocopies = new SimpleStringProperty(STATUS_MISSING);
        }
        
        if (immExtra != null && immExtra)
        {
            this.immExtra = new SimpleStringProperty(STATUS_PRINTED);
            
        }
        else
        {
            this.immExtra = new SimpleStringProperty(STATUS_MISSING);
        }
        
    }
    
    public EntryWorkflowVisaExt(MonasticProfile p, Date dueDate)
    {
        this(p.getNickname(), p.getWfExtPrawat(), p.getWfExtLetterSnp(),
                p.getWfExtPhotocopiesSnp(), p.getWfExtApprovalSnp(), p.getWfExtTm7(),
                p.getWfExtLetterImm(), p.getWfExtPhotocopiesImm(), p.getWfExtExtraImm(), dueDate);
    }
    
    @Override
    public int compareTo(EntryWorkflowVisaExt o)
    {
        if (o == null)
        {
            return 1;
        }
        else
        {
            return ldDueDate.compareTo(o.getLDDueDate());
        }
    }
    
    public String getMonastic()
    {
        return monastic.get();
    }
    
    public String getSnpPrawat()
    {
        return snpPrawat.get();
    }
    
    public String getSnpLetter()
    {
        return snpLetter.get();
    }
    
    public String getSnpPhotocopies()
    {
        return snpPhotocopies.get();
    }
    
    public String getImmApprovalSNP()
    {
        return immApprovalSNP.get();
    }
    
    public String getImmTM7()
    {
        return immTM7.get();
    }
    
    public String getImmLetter()
    {
        return immLetter.get();
    }
    
    public String getImmPhotocopies()
    {
        return immPhotocopies.get();
    }
    
    public String getImmExtra()
    {
        return immExtra.get();
    }
    
    public String getStrDueDate()
    {
        return strDueDate.get();
    }
    
    public LocalDate getLDDueDate()
    {
        return ldDueDate;
    }
    
}
