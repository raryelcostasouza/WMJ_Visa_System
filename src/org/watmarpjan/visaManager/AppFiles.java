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
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
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

    public static File getFormTM47Notice90Day()
    {
        return AppPaths.getPathToForms().resolve("form90DayNotice.pdf").toFile();
    }

    public static File getExtReqLetterSNP()
    {
        return AppPaths.getPathToForms().resolve("ExtReqLetterSNP.pdf").toFile();
    }

    public static File getThaiFont()
    {
        return AppPaths.getPathToForms().resolve("font/angsa.ttf").toFile();
    }
}
