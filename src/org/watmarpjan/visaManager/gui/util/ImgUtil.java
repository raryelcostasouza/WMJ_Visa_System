/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;

/**
 *
 * @author WMJ_user
 */
public class ImgUtil
{

    public static final String IMG_TYPE_PROFILE = "profile";
    public static final String IMG_TYPE_PASSPORT = "passport";
    public static final String IMG_TYPE_BYSUDDHI = "bysuddhi";

    public static void openClickedIMG(File fImg)
    {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
        {
            if (fImg.exists())
            {
                try
                {
                    Desktop.getDesktop().open(fImg);
                } catch (IOException ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to open image preview.");
                }
            }
        }
    }

    public static void loadImageView(ImageView iv, String imgType, File fImg)
    {
        File fDefaultIMG;
        if ((fImg != null) && (fImg.exists()))
        {
            iv.setImage(new Image(fImg.toURI().toString()));
        } else
        {
            fDefaultIMG = AppFiles.getDefaultIMG(imgType);
            iv.setImage(new Image(fDefaultIMG.toURI().toString()));
        }
    }
}
