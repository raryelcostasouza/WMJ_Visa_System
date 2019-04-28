/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.EntryReceipt90Day;
import org.watmarpjan.visaManager.model.eps.ExtraPassportScanLoaded;
import org.watmarpjan.visaManager.util.Util;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author WMJ_user
 */
public class CtrFileOperation
{

    public static final String SCAN_TYPE_PASSPORT = "passport";
    public static final String SCAN_TYPE_BYSUDDHI = "bysuddhi";
    public static final String SCAN_TYPE_PROFILE = "profile";

    private static FileChooser STATIC_FILE_CHOOSER;
    private static File LAST_FOLDER;
    public static final String FILE_CHOOSER_TYPE_PDF = "PDF";
    public static final String FILE_CHOOSER_TYPE_JPG = "JPG";

    public static void renameProfileFolder(String previouseNickName, String newNickName)
    {
        File fPreviousFolder, fNewFolder;

        fPreviousFolder = AppPaths.getPathProfileFolder(previouseNickName).toFile();
        fNewFolder = AppPaths.getPathProfileFolder(newNickName).toFile();
        fPreviousFolder.renameTo(fNewFolder);
    }

    public static int archiveAllPassportScans(MonasticProfile p)
    {
        ArrayList<File> listFiles2Archive;
        ArrayList<ExtraPassportScanLoaded> listExtraPS;
        int opStatus, opStatusAux;

        if (p.getPassportNumber() != null)
        {
            listFiles2Archive = new ArrayList<>();
            listFiles2Archive.add(AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()));

            listExtraPS = AppFiles.getListExtraScans(p.getNickname(), p.getPassportNumber());
            if (!listExtraPS.isEmpty())
            {
                for (ExtraPassportScanLoaded objEPS : listExtraPS)
                {
                    listFiles2Archive.add(objEPS.getFileScan());
                }
            }

            opStatus = 0;
            for (File f2Archive : listFiles2Archive)
            {
                opStatusAux = archiveScanFile(p.getNickname(), SCAN_TYPE_PASSPORT, f2Archive);
                if (opStatusAux == -1)
                {
                    opStatus = -1;
                }
            }
            return opStatus;
        }

        return 0;
    }

    public static int archiveScanFile(String profileNickName, String scanType, File f2Archive)
    {
        //the path to the archive subfolder for the specified profile and scantype
        return archiveFile(f2Archive, AppPaths.getPathArchiveScan(profileNickName, scanType));
    }

    public static int archivePrintoutTM30(File fPrintoutTM30)
    {
        return archiveFile(fPrintoutTM30, AppPaths.getPathArchivePrintoutTM30());
    }
    
    public static int archiveNaktamCertificate(File fCertificate, String nickname)
    {
        return archiveFile(fCertificate, AppPaths.getPathArchiveNaktamCertificate(nickname));
    }

    private static int archiveFile(File f2Archive, Path pDestArchive)
    {
        File fDestArchive;
        Path pSourceFile, pDestFile;
        String fileNameWithoutExtension, fExtension;

        fileNameWithoutExtension = Util.getFileNameWithoutExtension(f2Archive);
        fExtension = Util.getFileExtension(f2Archive);

        pSourceFile = f2Archive.toPath();

        //the path to the archive subfolder for the specified profile and scantype
        fDestArchive = pDestArchive.toFile();

        //add a timestamp of the archiving time to the beginning of the fileName
        pDestFile = pDestArchive.resolve(LocalDateTime.now().format(Util.TIMESTAMP_FILE_NAME) + "-" + fileNameWithoutExtension + "." + fExtension);

        try
        {
            //if the archive directory does not exist, creates it
            if (!fDestArchive.exists())
            {
                fDestArchive.mkdirs();
            }
            Files.move(pSourceFile, pDestFile);
            return 0;

        } catch (IOException ex)
        {
            CtrAlertDialog.errorDialog("Error to archive. Details:\n\n" + ex.getMessage());
            return -1;
        }
    }

    public static int deleteFile(File fScan)
    {
        try
        {
            Files.delete(fScan.toPath());
            return 0;
        } catch (IOException ioe)
        {
            CtrAlertDialog.errorDialog("Unable to delete scan file. Details:\n\n" + ioe.getMessage());
            return -1;
        }
    }

    public static int renameFile(File fBefore, File fAfter)
    {
        try
        {
            fBefore.renameTo(fAfter);
            return 0;
        } catch (SecurityException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to rename scan file.");
            return -1;
        }
    }

    public static int copyOperation(File fSelected, File destFile)
    {
        String errorMessage;

        errorMessage = "Unable to copy file to the application storage directory.";
        try
        {
            //if the destination folder does not exist
            if (!destFile.exists())
            {
                //create all necessary parent folders
                destFile.mkdirs();
            }
            //copy the selected file to the proper application folder with a internally generated filename
            Files.copy(fSelected.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return 0;
        } catch (IOException ioe)
        {
            CtrAlertDialog.exceptionDialog(ioe, errorMessage);
            return -1;
        } catch (Exception e)
        {
            CtrAlertDialog.exceptionDialog(e, errorMessage);
            return -1;
        }

    }

    public static File selectFile(String titleFC, String fileChooserType)
    {
        File fSelected;
        //if the FileChooser is not initialized
        if (STATIC_FILE_CHOOSER == null)
        {
            STATIC_FILE_CHOOSER = new FileChooser();
            STATIC_FILE_CHOOSER.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        setupFileChooser(titleFC, fileChooserType);

        //if a file was previously selected, open on the parent folder of that file
        if (LAST_FOLDER != null)
        {
            STATIC_FILE_CHOOSER.setInitialDirectory(LAST_FOLDER);
        }

        fSelected = STATIC_FILE_CHOOSER.showOpenDialog(Init.MAIN_STAGE);

        //saves the reference to the parent of the last selected file
        if (fSelected != null)
        {
            LAST_FOLDER = fSelected.getParentFile();
        }

        return fSelected;
    }

    private static void setupFileChooser(String titleFC, String fileType)
    {
        STATIC_FILE_CHOOSER.setTitle(titleFC);
        STATIC_FILE_CHOOSER.getExtensionFilters().clear();

        switch (fileType)
        {
            case FILE_CHOOSER_TYPE_JPG:
                STATIC_FILE_CHOOSER.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
                break;
            case FILE_CHOOSER_TYPE_PDF:
                STATIC_FILE_CHOOSER.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                break;
            default:
                break;
        }

    }

    public static void clearTMPFiles()
    {
        Path pTMPFolder;
        File fTMPFolder;

        pTMPFolder = AppPaths.getPathAppTMPFolder();
        fTMPFolder = pTMPFolder.toFile();

        for (File f : fTMPFolder.listFiles())
        {
            f.delete();
        }
        fTMPFolder.delete();
    }

    public static void openFileOnDefaultProgram(File f)
    {
        //if is a valid and existent file
        if ((f != null) && f.exists())
        {
            //show the generated form on the default pdf viewer
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
            {
                new Thread(() ->
                {
                    try
                    {
                        Desktop.getDesktop().open(f);
                    } catch (IOException ex)
                    {
                        CtrAlertDialog.exceptionDialog(ex, "Error to open file.");
                    }
                }).start();
            } else
            {
                CtrAlertDialog.errorDialog("No support for opening files on this OS.");
            }
        } else
        {
            CtrAlertDialog.errorDialog("The requested file does not exist!");
        }
    }

    public static ArrayList<EntryReceipt90Day> loadListReceipts90Day(MonasticProfile mp)
    {
        ArrayList<EntryReceipt90Day> listReceipts90D;
        File fDirReceipts;
        File[] fList;
        String receiptDate, receiptStatus, refNumber;
        int indexLastSeparator;
        LocalDate ldReceipt;
        EntryReceipt90Day objEntryReceipt;

        listReceipts90D = new ArrayList<>();
        fDirReceipts = AppPaths.getPathToReceiptsOnline90dNotice(mp.getNickname()).toFile();
        if (fDirReceipts.exists())
        {
            fList = fDirReceipts.listFiles();

            for (File f : fList)
            {
                receiptDate = f.getName().substring(0, 10);

                //get the index of the '-' before the 'TM47' prefix of the receipt 
                indexLastSeparator = f.getName().lastIndexOf("TM47") - 1;
                receiptStatus = f.getName().substring(11, indexLastSeparator);
                refNumber = f.getName().substring(indexLastSeparator + 1, f.getName().length() - 4);

                ldReceipt = LocalDate.parse(receiptDate);

                objEntryReceipt = new EntryReceipt90Day(ldReceipt, refNumber, receiptStatus);
                listReceipts90D.add(objEntryReceipt);
            }
            listReceipts90D.sort(null);
            return listReceipts90D;
        } else
        {
            return null;
        }
    }

    public static void saveChangelog(String topLine)
    {
        List<String> listLinesBeforeUpdate;
        ArrayList<String> listLinesUpdated;
        LinkedList<String> lList;
        Path pFileChangelog;
        pFileChangelog = AppPaths.getPathChangelog();
        try
        {
            listLinesUpdated = new ArrayList<>();

            if (pFileChangelog.toFile().exists())
            {
                listLinesBeforeUpdate = Files.readAllLines(pFileChangelog);
            } else
            {
                listLinesBeforeUpdate = null;
            }

            listLinesUpdated.add(topLine);
            if (listLinesBeforeUpdate != null)
            {
                listLinesUpdated.addAll(listLinesBeforeUpdate);
            }

            Files.write(pFileChangelog, listLinesUpdated);
        } catch (IOException ex)
        {
            Logger.getLogger(CtrFileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String loadChangelog()
    {
        List<String> listLines;
        Path pFileChangelog;
        String strChangelog;

        pFileChangelog = AppPaths.getPathChangelog();

        try
        {
            if (pFileChangelog.toFile().exists())
            {
                listLines = Files.readAllLines(pFileChangelog);
                strChangelog = "";
                for (String line : listLines)
                {
                    strChangelog += line + "\n";
                }
                return strChangelog;
            } else
            {
                return "";
            }
        } catch (IOException ex)
        {
            Logger.getLogger(CtrFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
