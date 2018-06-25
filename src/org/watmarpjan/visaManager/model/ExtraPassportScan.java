/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.io.File;

/**
 *
 * @author KroobaHariel
 */
public class ExtraPassportScan
{
    private File fileScan;

    public ExtraPassportScan(File fileScan)
    {
        this.fileScan = fileScan;
    }

    public File getFileScan()
    {
        return fileScan;
    }   
    
    public int getLeftPageNumber()
    {
        String filename;
        String strPageNumber;
        int iPageSection, iDashAfterPageSection;
        
        filename = fileScan.getName();
        
        //the page  number is located between the 'page' string in the file name and the next '-'
        iPageSection = filename.indexOf("page");
        iDashAfterPageSection = filename.indexOf("-", iPageSection);
        strPageNumber = filename.substring(iPageSection+4, iDashAfterPageSection);
        
        return Integer.parseInt(strPageNumber);
    }
    
    public boolean containsScanArriveStamp()
    {
        return fileScan.getName().contains("ArriveStamp");
    }
    
    public boolean containsScanLastVisaExt()
    {
        return fileScan.getName().contains("VisaExt");
    }
    
    public boolean containsScanVisa()
    {
        return fileScan.getName().contains("Visa-") || fileScan.getName().contains("Visa.");
    }
}
