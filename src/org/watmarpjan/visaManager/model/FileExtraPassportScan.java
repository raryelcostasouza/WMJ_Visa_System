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
public class FileExtraPassportScan
{
    private File fExtraScan;

    public FileExtraPassportScan(File fExtraScan)
    {
        this.fExtraScan = fExtraScan;
    }
    
    public int getLeftPageNumber()
    {
        String filename;
        String strPageNumber;
        int iPageSection, iDashAfterPageSection;
        
        filename = fExtraScan.getName();
        
        //the page  number is located between the 'page' string in the file name and the next '-'
        iPageSection = filename.indexOf("page");
        iDashAfterPageSection = filename.indexOf("-", iPageSection);
        strPageNumber = filename.substring(iPageSection+4, iDashAfterPageSection);
        
        return Integer.parseInt(strPageNumber);
    }
    
    public boolean containsScanArriveStamp()
    {
        return fExtraScan.getName().contains("ArriveStamp");
    }
    
    public boolean containsScanLastVisaExt()
    {
        return fExtraScan.getName().contains("VisaExt");
    }
    
    public boolean containsScanVisa()
    {
        return fExtraScan.getName().contains("Visa-") || fExtraScan.getName().contains("Visa.");
    }
}
