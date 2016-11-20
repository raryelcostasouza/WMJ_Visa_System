/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;

/**
 *
 * @author WMJ_user
 */
public class AppFiles
{

    public static File getScanPassportFirstPage(String nickName, String passportNumber)
    {
        Path pSubfolder;
        String strFileName;

        pSubfolder = AppPaths.getPathToPassportSubFolder(nickName);
        strFileName = AppFileNames.getScanPassportFirstPage(passportNumber);

        return new File(pSubfolder.resolve(strFileName).toUri());
    }

    public static File getScanDepartureCard(String nickName)
    {
        Path pSubfolder;
        String strFileName;

        pSubfolder = AppPaths.getPathToPassportSubFolder(nickName);
        strFileName = AppFileNames.getScanDepartureCard();

        return new File(pSubfolder.resolve(strFileName).toUri());
    }

    public static File getExtraScan(String nickName, String passportNumber, PassportScan ps)
    {
        Path pSubfolder;
        String strFileName;

        if (ps != null)
        {
            pSubfolder = AppPaths.getPathToPassportSubFolder(nickName);
            strFileName = AppFileNames.getExtraScan(passportNumber, ps);

            return new File(pSubfolder.resolve(strFileName).toUri());
        } else
        {
            return null;
        }

    }

    public static File getScanBysuddhi(String nickName, int scanNumber)
    {
        Path pSubfolder;
        String strFileName;

        pSubfolder = AppPaths.getPathToBysuddhiSubFolder(nickName);
        strFileName = AppFileNames.getScanBysuddhi(scanNumber);

        return new File(pSubfolder.resolve(strFileName).toUri());
    }

    public static File getProfilePhoto(String nickName)
    {
        Path pSubfolder;
        String strFileName;

        pSubfolder = AppPaths.getPathToProfilePhotoSubFolder(nickName);
        strFileName = AppFileNames.getProfilePhoto();

        return new File(pSubfolder.resolve(strFileName).toUri());
    }

    public static File getDefaultIMG(String imgType)
    {
        Path pSubfolder;
        String strFileName;

        pSubfolder = AppPaths.getPathToIconSubfolder();
        strFileName = imgType + ".png";

        return new File(pSubfolder.resolve(strFileName).toUri());
    }

    public static File getFormTMPOutputPDF(String formFileName)
    {
        Path pathTMPFile = null;

        try
        {
            pathTMPFile = Files.createTempFile(AppPaths.getPathAppTMPFolder(), formFileName, ".pdf");
            return pathTMPFile.toFile();
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to create tmp file for saving form.");
            return null;
        }
    }

    public static File getReceiptOnline90d(String nickName, String referenceNumber, LocalDate ldReceiptDate, String status)
    {
        Path pSubFolder;
        String strFileName;

        pSubFolder = AppPaths.getPathToReceiptsOnline90dNotice(nickName);
        strFileName = AppFileNames.getReceiptOnline90d(referenceNumber, ldReceiptDate, status);
        return pSubFolder.resolve(strFileName).toFile();
    }

    public static File getFormPrawat()
    {
        return AppPaths.getPathToForms().resolve("Prawat.pdf").toFile();
    }

    public static File getPrintoutTM30(LocalDate ldNotifDate)
    {
        return AppPaths.getPathToTM30Printout().resolve("TM30-" + ldNotifDate.format(DateTimeFormatter.ISO_DATE) + ".pdf").toFile();
    }

    public static File getOverlayWatermark()
    {
        return AppPaths.getPathToForms().resolve("overlay/overlay.png").toFile();
    }

    public static File getFormTM7ReqExtension()
    {
        return AppPaths.getPathToForms().resolve("TM7-ReqExtension.pdf").toFile();
    }
    public static File getFormTM8Reentry()
    {
        return AppPaths.getPathToForms().resolve("TM8-Reentry.pdf").toFile();
    }

    public static File getFormOverstay()
    {
        return AppPaths.getPathToForms().resolve("AckOverstayPenalties.pdf").toFile();
    }

    public static File getFormSTM2AckConditions()
    {
        return AppPaths.getPathToForms().resolve("STM2-AckConditions.pdf").toFile();
    }

    public static File getFormTM47Notice90Day()
    {
        return AppPaths.getPathToForms().resolve("TM47-90DayNotice.pdf").toFile();
    }

    public static File getExtReqLetterSNP()
    {
        return AppPaths.getPathToForms().resolve("ExtReqLetterSNP.pdf").toFile();
    }

    public static File getExtReqLetterIMM()
    {
        return AppPaths.getPathToForms().resolve("ExtReqLetterIMM.pdf").toFile();
    }

    public static File getThaiFont()
    {
        return AppPaths.getPathToForms().resolve("font/angsa.ttf").toFile();
    }
}
