/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;

/**
 *
 * @author WMJ_user
 */
public class Init extends Application
{

    public static Stage MAIN_STAGE;
    public static HostServices HOST_SERVICES;

    public static void main(String[] args)
    {
        Application.launch(Init.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            MAIN_STAGE = primaryStage;
            HOST_SERVICES = getHostServices();
            VBox page = (VBox) FXMLLoader.load(Init.class.getResource("gui/mainPane.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("WMJ Visa Manager");
            primaryStage.setWidth(1300);
            primaryStage.setHeight(1050);
            primaryStage.show();
        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to init app.");
        }
    }
}
