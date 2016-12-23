/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppFiles;

/**
 *
 * @author WMJ_user
 */
public class GUIUtil
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
    
    public static void loadContentComboboxGeneric(ComboBox<String> objCB, ArrayList<String> listItems)
    {
        objCB.getItems().clear();
        objCB.getItems().addAll(listItems);
    }
    
    public static void initAutoHeightResize(TableView tv, double headerAdjust)
    {
        tv.prefHeightProperty().bind(tv.fixedCellSizeProperty().multiply(Bindings.size(tv.getItems()).add(headerAdjust)));
        tv.minHeightProperty().bind(tv.prefHeightProperty());
        tv.maxHeightProperty().bind(tv.prefHeightProperty());
    }
}
