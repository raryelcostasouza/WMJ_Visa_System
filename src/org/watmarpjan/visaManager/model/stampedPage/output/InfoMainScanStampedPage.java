package org.watmarpjan.visaManager.model.stampedPage.output;


public class InfoMainScanStampedPage extends InfoGenericScanStampedPage
{
    private boolean containScanArriveStamp;
    private boolean containScanVisa;
    private boolean containScanLastVisaExt;
    
    public InfoMainScanStampedPage(String leftPageNumber, boolean containScanArriveStamp, boolean containScanVisa, boolean containScanLastVisaExt)
    {
        super(leftPageNumber);
        this.containScanArriveStamp = containScanArriveStamp;
        this.containScanVisa = containScanVisa;
        this.containScanLastVisaExt = containScanLastVisaExt;
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

}
