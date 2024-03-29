/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.watmarpjan.visaManager.model.stampedPage.output.InfoMainScanStampedPage;

/**
 *
 * @author WMJ_user
 */
public class AppFileNames
{

    public static final String RECEIPT_STATUS_PENDING = "PENDING";
    public static final String RECEIPT_STATUS_APPROVED = "APPROVED";
    
    public static final String ODT_LETTER_NEW_VISA_NON_IMM_LAYPERSON_ABROAD_EMBASSY = "NonImmLaypersonAbroadEmbassy.odt";
    public static final String ODT_LETTER_NEW_VISA_NON_IMM_LAYPERSON_ABROAD_EMBASSY_EN = "NonImmLaypersonAbroadEmbassyEN.odt";
    public static final String ODT_LETTER_NEW_VISA_NON_IMM_LAYPERSON_THAILAND_VIENTIANE_EMBASSY = "NonImmLaypersonThailandVientianeEmbassy.odt";
    
    public static final String ODT_LETTER_NEW_VISA_NON_IMM_MONASTIC_ABROAD_EMBASSY = "NonImmMonasticAbroadEmbassy.odt";
    public static final String ODT_LETTER_NEW_VISA_NON_IMM_MONASTIC_ABROAD_EMBASSY_EN = "NonImmMonasticAbroadEmbassyEN.odt";
    public static final String ODT_LETTER_NEW_VISA_NON_IMM_MONASTIC_ABROAD_SNP = "NonImmMonasticAbroadSamnakPut.odt";
    
    public static final String ODT_LETTER_GUARANTEE_SNP = "ResidenceGuaranteeLetterSNP.odt";
    public static final String ODT_LETTER_EXT_SNP = "ExtensionReqLetterSNP.odt";
    public static final String ODT_LETTER_EXT_IMM = "ExtensionReqLetterIMM.odt";

    public static String getProfilePhoto()
    {
        return "profile.jpg";
    }
    
    public static String getNaktamCertificate(String level)
    {
        switch(level)
        {
            case AppConstants.STUDIES_NAKTAM_TRI:
                return "certificate-naktam-tri.pdf";
            case AppConstants.STUDIES_NAKTAM_TOH:
                return "certificate-naktam-toh.pdf";
            case AppConstants.STUDIES_NAKTAM_EK:
                return "certificate-naktam-ek.pdf";
        }
        return "";  
        
    }
    
    public static String getEVisa()
    {
        return "e-visa.pdf";
    }

    public static String getScanBysuddhi(int number)
    {
        return "bysuddhi-" + number + ".jpg";
    }

    public static String getScanPassportFirstPage(String passportNumber)
    {
        return passportNumber + "-passport.jpg";
    }

    public static String getScanDepartureCard()
    {
        return "departure-card.jpg";
    }

    public static String generateFileNameMain3StampedPageScan(String passportNumber, InfoMainScanStampedPage ps)
    {
        return passportNumber + "-page" + ps.getLeftPageNumber()+ generateExtraScanSuffix(ps) + ".jpg";
    }
    
    public static String generateFileNameGenericStampedPageScan(String passportNumber, String pagenumber)
    {
        return passportNumber + "-page" + pagenumber +"-.jpg";
    }

    private static String generateExtraScanSuffix(InfoMainScanStampedPage ps)
    {
        String suffix = "";
        boolean noSuffix;

        noSuffix = true;
        if (ps.containsScanArriveStamp())
        {
            suffix += "-ArriveStamp";
            noSuffix = false;
        }
        if (ps.containsScanVisa())
        {
            suffix += "-Visa";
            noSuffix = false;
        }
        if (ps.containsScanLastVisaExt())
        {
            suffix += "-VisaExt";
            noSuffix = false;
        }
        
        //if no scan type is selected add an extra dash just for compatibility for the function that detects the page number
        if (noSuffix)
        {
            suffix = "-";
        }
       
        return suffix;
    }

    public static String getReceiptOnline90d(String referenceNumber, LocalDate ldReceiptDate, String status)
    {
        return ldReceiptDate.format(DateTimeFormatter.ISO_DATE) + "-" + status + "-" + referenceNumber + ".pdf";
    }
}
