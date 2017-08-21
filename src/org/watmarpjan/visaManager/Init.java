/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.time.Instant;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;

/**
 *
 * @author WMJ_user
 */
public class Init extends Application
{

    public static final String APP_VERSION = "v1.5 - BETA - 21/08/2017";
    public static Stage MAIN_STAGE;
    public static HostServices HOST_SERVICES;
    public static Application APP;

    public static Instant INSTANT_INIT_START = Instant.now();

    private void loadFXMLRootPanel()
    {
        //simulate long init in background
        Task task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader.load(Init.class.getResource("gui/panel/mainPane.fxml"));
                return null;
            }
        };
        new Thread(task).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        APP = this;
        try
        {
            MAIN_STAGE = primaryStage;
            HOST_SERVICES = getHostServices();

            loadFXMLRootPanel();
        }
        catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to init app.");
        }
    }

    public static void main(String[] args)
    {
        Application.launch(Init.class, (java.lang.String[]) null);
    }
}
