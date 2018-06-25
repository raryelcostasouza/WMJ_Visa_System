package org.watmarpjan.visaManager.model.eps;

import java.io.File;

public class ExtraPassportScanLoaded
{
    private File fileScan;
    
    public ExtraPassportScanLoaded(File fileScan)
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
