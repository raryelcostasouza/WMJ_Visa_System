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
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;

/**
 *
 * @author WMJ_user
 */
public class AppPaths
{

    private static Path PATH_TMP_FOLDER;

    public static Path getPathProfileFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../Data/" + nickName);
    }

    public static Path getPathArchiveFolder(String nickName, String scanType)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../Archive/" + nickName + "/Scans/" + scanType);
    }

    public static Path getPathToPassportSubFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../Data/" + nickName + "/Scans/Passport");
    }

    public static Path getPathToBysuddhiSubFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../Data/" + nickName + "/Scans/Bysuddhi");
    }

    public static Path getPathToProfilePhotoSubFolder(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../Data/" + nickName + "/Scans/Profile");
    }

    public static Path getPathToForms()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("appData/Forms-Templates");
    }

    public static Path getPathToIconSubfolder()
    {
        return Paths.get(System.getProperty("user.dir")).resolve("icons");
    }

    public static Path getPathToReceiptsOnline90dNotice(String nickName)
    {
        return Paths.get(System.getProperty("user.dir")).resolve("../Data/" + nickName + "/Receipts-Online-90day");
    }

    public static Path getPathAppTMPFolder()
    {
        if (PATH_TMP_FOLDER == null)
        {
            try
            {
                PATH_TMP_FOLDER = Files.createTempDirectory("wmj-visa-manager");
            } catch (IOException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Unable to create tmp folder.");
            }
        }

        return PATH_TMP_FOLDER;
    }

}
