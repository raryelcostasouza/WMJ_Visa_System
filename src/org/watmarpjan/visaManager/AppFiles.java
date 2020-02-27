/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.stampedPage.input.InfoFileScanStampedPage;
import org.watmarpjan.visaManager.model.stampedPage.output.InfoGenericScanStampedPage;
import org.watmarpjan.visaManager.model.stampedPage.output.InfoMainScanStampedPage;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.PrintoutTm30;
import org.watmarpjan.visaManager.util.Util;

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

    public static File generateFileNameMain3StampedPageScan(String nickName, String passportNumber, InfoMainScanStampedPage ps)
    {
        Path pSubfolder;
        String strFileName;

        if (ps != null)
        {
            pSubfolder = AppPaths.getPathToPassportSubFolder(nickName);
            strFileName = AppFileNames.generateFileNameMain3StampedPageScan(passportNumber, ps);

            return new File(pSubfolder.resolve(strFileName).toUri());
        } else
        {
            return null;
        }

    }

    public static File generateFileNameGenericStampedPageScan(String nickName, String passportNumber, InfoGenericScanStampedPage objInfoScan)
    {
        Path pSubfolder;
        String strFileName;

        if (passportNumber != null && objInfoScan != null)
        {
            pSubfolder = AppPaths.getPathToPassportSubFolder(nickName);
            strFileName = AppFileNames.generateFileNameGenericStampedPageScan(passportNumber, objInfoScan.getLeftPageNumber());

            return new File(pSubfolder.resolve(strFileName).toUri());
        } else
        {
            return null;
        }

    }
    
    public static ArrayList<InfoFileScanStampedPage> getListInfoPassportScansStampedPage(String nickName, String passportNumber, FilenameFilter objFF)
    {
        Path pSubfolder;
        File[] listFScans = null;
        ArrayList<InfoFileScanStampedPage> listFExtraScan;

        listFExtraScan = new ArrayList<>();
        pSubfolder = AppPaths.getPathToPassportSubFolder(nickName);
        if (pSubfolder.toFile().exists())
        {
            listFScans = pSubfolder.toFile().listFiles(objFF);
            for (File f : listFScans)
            {
                listFExtraScan.add(new InfoFileScanStampedPage(f));
            }
        }

        return listFExtraScan;
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

        pSubfolder = AppPaths.getPathToProfileScansSubFolder(nickName);
        strFileName = AppFileNames.getProfilePhoto();

        return new File(pSubfolder.resolve(strFileName).toUri());
    }
    
    public static File getNaktamCertificate(String nickName, String level)
    {
        Path pSubfolder;
        String strFileName;

        pSubfolder = AppPaths.getPathToProfileScansSubFolder(nickName);
        strFileName = AppFileNames.getNaktamCertificate(level);

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

    public static File getFormPrawat(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("Prawat.pdf").toFile();
    }

    public static File getFormPrawatPatimokkhaChanter(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("Prawat-Patimokkha-Chanter.pdf").toFile();
    }

    public static File getPrintoutTM30(PrintoutTm30 objTM30)
    {
        String fileName;
        LocalDate ldNotifDate;
        Integer auxIndex;

        ldNotifDate = Util.convertDateToLocalDate(objTM30.getNotifDate());
        auxIndex = objTM30.getAuxIndex();

        fileName = "TM30-" + ldNotifDate.format(DateTimeFormatter.ISO_DATE);

        //if there is more than one printout for a certain date
        //it is necessary to add the auxIndex to the filename
        if (auxIndex != null && auxIndex != 0)
        {
            fileName += "-" + auxIndex;
        }
        fileName += ".pdf";

        return AppPaths.getPathToTM30Printout().resolve(fileName).toFile();
    }

    public static File getPrintoutTM30(LocalDate ldNotif, int auxIndex)
    {
        PrintoutTm30 objTM30;

        objTM30 = new PrintoutTm30();
        objTM30.setNotifDate(Util.convertLocalDateToDate(ldNotif));
        objTM30.setAuxIndex(auxIndex);

        return getPrintoutTM30(objTM30);
    }

    
    public static File getFormTM7ReqExtension(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("TM7-ReqExtension.pdf").toFile();
    }

    public static File getFormTM8Reentry(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("TM8-Reentry.pdf").toFile();
    }

    public static File getFormTM86VisaChange(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("TM86-VisaChange.pdf").toFile();
    }

    public static File getFormOverstay(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("AckOverstayPenalties.pdf").toFile();
    }

    public static File getFormSTM2AckConditions(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("STM2-AckConditions.pdf").toFile();
    }

    public static File getFormTM47Notice90Day(String monasteryNickname)
    {
        return AppPaths.getPathToForms(monasteryNickname).resolve("TM47-90DayNotice.pdf").toFile();
    }

    public static File getODTVisaExtLetterGeneric(Monastery mResidence, String filename)
    {        
        return AppPaths.getPathToLetterTemplate(mResidence.getMonasteryNickname()).resolve(filename).toFile();
    }
    
    public static File getODTNewVisaLetter(Monastery mResidence, String letterSelected)
    {
        String fileNameWithoutExtension;
        
        fileNameWithoutExtension = "NonImm" + letterSelected.replaceAll("[-  ]+", "");
        return AppPaths.getPathToLetterTemplate(mResidence.getMonasteryNickname()).resolve(fileNameWithoutExtension + ".odt").toFile();
    }

    public static File getThaiFont()
    {
        return AppPaths.getPathToFonts().resolve("angsa.ttf").toFile();
    }
}
