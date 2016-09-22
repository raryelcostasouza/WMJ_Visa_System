/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.stage.FileChooser;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrFileOperation
{

    public static final String SCAN_TYPE_PASSPORT = "passport";
    public static final String SCAN_TYPE_BYSUDDHI = "bysuddhi";
    public static final String SCAN_TYPE_PHOTO = "photo";

    private static FileChooser STATIC_FILE_CHOOSER;
    private static FileChooser FILE_CHOOSER_PDF_FILE;
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

    public static int archiveScanFile(String profileNickName, String scanType, File f2Archive)
    {
        File fProfileArchive;
        Path pSourceFile, pDestFile, pProfileArchive;
        String fileNameWithoutExtension;
        DateTimeFormatter dFFileName = DateTimeFormatter.ofPattern("yyyy-MM-dd-kk'h'mm'm'ss's'");

        fileNameWithoutExtension = Util.getFileNameWithoutExtension(f2Archive);
        pSourceFile = f2Archive.toPath();

        //the path to the archive subfolder for the specified profile and scantype
        pProfileArchive = AppPaths.getPathArchiveFolder(profileNickName, scanType);
        fProfileArchive = pProfileArchive.toFile();

        //add a timestamp of the archiving time to the beginning of the fileName
        pDestFile = pProfileArchive.resolve(LocalDateTime.now().format(dFFileName) + "-" + fileNameWithoutExtension + ".jpg");

        try
        {
            //if the archive directory for this profile does not exist, creates it
            if (!fProfileArchive.exists())
            {
                fProfileArchive.mkdirs();
            }
            Files.copy(pSourceFile, pDestFile);
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

}
