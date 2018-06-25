package org.watmarpjan.visaManager.model.eps;


public class ExtraPassportScanNew
{
    private boolean containScanArriveStamp;
    private boolean containScanVisa;
    private boolean containScanLastVisaExt;
    private int leftPageNumber;

    public ExtraPassportScanNew(int leftPageNumber, boolean containScanArriveStamp, boolean containScanVisa, boolean containScanLastVisaExt)
    {
        this.containScanArriveStamp = containScanArriveStamp;
        this.containScanVisa = containScanVisa;
        this.containScanLastVisaExt = containScanLastVisaExt;
        this.leftPageNumber = leftPageNumber;
    }

    public boolean containsScanArriveStamp()
    {
        return containScanArriveStamp;
    }

    public boolean containsScanVisa()
    {
        return containScanVisa;
    }

    public boolean containsScanLastVisaExt()
    {
        return containScanLastVisaExt;
    }

    public int getLeftPageNumber()
    {
        return leftPageNumber;
    }
    
    
    
    
}
