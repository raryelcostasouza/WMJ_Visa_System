/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.watmarpjan.visaManager.model.eps.ExtraPassportScanLoaded;
import org.watmarpjan.visaManager.model.eps.ExtraPassportScanNew;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;

/**
 *
 * @author WMJ_user
 */
public class AppFileNames
{

    public static final String RECEIPT_STATUS_PENDING = "PENDING";
    public static final String RECEIPT_STATUS_APPROVED = "APPROVED";

    public static String getProfilePhoto()
    {
        return "profile.jpg";
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

    public static String getExtraScan(String passportNumber, ExtraPassportScanNew ps)
    {
        return passportNumber + "-page" + ps.getLeftPageNumber()+ generateExtraScanSuffix(ps) + ".jpg";
    }

    private static String generateExtraScanSuffix(ExtraPassportScanNew ps)
    {
        String suffix = "";

        if (ps.containsScanArriveStamp())
        {
            suffix += "-ArriveStamp";
        }
        if (ps.containsScanVisa())
        {
            suffix += "-Visa";
        }
        if (ps.containsScanLastVisaExt())
        {
            suffix += "-VisaExt";
        }

        return suffix;
    }

    public static String getReceiptOnline90d(String referenceNumber, LocalDate ldReceiptDate, String status)
    {
        return ldReceiptDate.format(DateTimeFormatter.ISO_DATE) + "-" + status + "-" + referenceNumber + ".pdf";
    }
}
