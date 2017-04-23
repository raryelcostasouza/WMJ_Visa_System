/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel.abs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppPaths;

/**
 *
 * @author wmj_user
 */
public class AChildPaneControllerExportPDF extends AChildPaneController
{

    @FXML
    protected Button bPreview;

//    @FXML
//    protected Button bPrint;

    @Override
    public void init()
    {
        bPreview.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
//        bPrint.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
    }

}
