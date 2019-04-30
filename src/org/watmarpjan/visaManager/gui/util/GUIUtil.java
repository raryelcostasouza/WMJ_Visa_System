/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
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
    
     /**
     * Hack allowing to modify the default behavior of the tooltips.
     *
     * @param openDelay The open delay, knowing that by default it is set to
     * 1000.
     * @param visibleDuration The visible duration, knowing that by default it
     * is set to 5000.
     * @param closeDelay The close delay, knowing that by default it is set to
     * 200.
     * @param hideOnExit Indicates whether the tooltip should be hide on exit,
     * knowing that by default it is set to false.
     */
    public static void updateTooltipBehavior(double openDelay, double visibleDuration,
            double closeDelay, boolean hideOnExit)
    {
        try
        {
            // Get the non public field "BEHAVIOR"
            Field fieldBehavior = Tooltip.class.getDeclaredField("BEHAVIOR");
            // Make the field accessible to be able to get and set its value
            fieldBehavior.setAccessible(true);
            // Get the value of the static field
            Object objBehavior = fieldBehavior.get(null);
            // Get the constructor of the private static inner class TooltipBehavior
            Constructor<?> constructor = objBehavior.getClass().getDeclaredConstructor(
                    Duration.class, Duration.class, Duration.class, boolean.class
            );
            // Make the constructor accessible to be able to invoke it
            constructor.setAccessible(true);
            // Create a new instance of the private static inner class TooltipBehavior
            Object tooltipBehavior = constructor.newInstance(
                    new Duration(openDelay), new Duration(visibleDuration),
                    new Duration(closeDelay), hideOnExit
            );
            // Set the new instance of TooltipBehavior
            fieldBehavior.set(null, tooltipBehavior);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }
}
