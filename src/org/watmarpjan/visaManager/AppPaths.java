/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;

/**
 *
 * @author WMJ_user
 */
public class AppPaths
{

    private static final Path PATH_TMP_FOLDER = Paths.get(System.getProperty("user.dir")).resolve("appTmp");

    public static Path getPathProfileFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/" + nickName);
    }

    private static Path getPathArchiveFolder()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Archive");
    }

    public static Path getPathArchiveScan(String nickName, String scanType)
    {
        return getPathArchiveFolder().resolve(nickName + "/Scans/" + scanType);
    }

    public static Path getPathArchivePrintoutTM30()
    {
        return getPathArchiveFolder().resolve("TM30-Printout/");
    }
    
    public static Path getPathArchiveProfilePhotoOrCertificates(String nickName)
    {
        return getPathArchiveScan(nickName, CtrFileOperation.SCAN_TYPE_PROFILE);
    }
    
    public static Path getPathArchiveBysuddhiScan(String nickName)
    {
        return getPathArchiveScan(nickName, CtrFileOperation.SCAN_TYPE_BYSUDDHI);
    }

    public static Path getPathToPassportSubFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/" + nickName + "/Scans/Passport");
    }

    public static Path getPathToBysuddhiSubFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/" + nickName + "/Scans/Bysuddhi");
    }

    public static Path getPathToProfileScansSubFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/" + nickName + "/Scans/Profile");
    }

    public static Path getPathToTM30Printout()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/TM30-Printout");
    }
    
    public static Path getPathToProfileLetters(String nickname)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/"+nickname + "/Letters");
    }

    public static Path getPathToForms(String monasteryNickname)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/"+monasteryNickname+"/Forms-Templates");
    }
    
    public static Path getPathToMonasteryMap(String monasteryNickname)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/"+monasteryNickname+"/MonasteryMap.pdf");
    }
    
    public static Path getPathToConfigs()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/config");
    }
    
    public static Path getPathToFonts()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/fonts");
    }
    
    public static Path getPathToHelp()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/Help/");
    }
    
    public static Path getPathToImmigrationWebsite()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/ImmigrationWebsite/");
    }
    
    public static Path getPathToLetterTemplate(String monasteryNickname)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../appData/"+monasteryNickname+"/Letter-Templates");
    }

    public static Path getPathToIconSubfolder()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../icons");
    }

    public static Path getPathIconPrint()
    {
        return getPathToIconSubfolder().resolve("print.png");
    }

    public static Path getPathIconPDF()
    {
        return getPathToIconSubfolder().resolve("pdf.png");
    }
    
     public static Path getPathEVisa()
    {
        return getPathToIconSubfolder().resolve("evisa.png");
    }
    
    
    public static Path getPathIconHelp()
    {
        return getPathToIconSubfolder().resolve("help.png");
    }
    
    public static Path getPathIconODT()
    {
        return getPathToIconSubfolder().resolve("odt.png");
    }

    public static Path getPathChangelog()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/changelog.txt");
    }

    public static Path getPathToReceiptsOnline90dNotice(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../../Data/" + nickName + "/Receipts-Online-90day");
    }

    public static Path getPathAppTMPFolder()
    {
        //if the tmp folder does not exist
        //create it
        if (!PATH_TMP_FOLDER.toFile().exists())
        {
            try
            {
                Files.createDirectory(PATH_TMP_FOLDER);
            }
            catch (IOException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Unable to create TMP folder");
                return null;
            }
        }
        return PATH_TMP_FOLDER;
    }

}
