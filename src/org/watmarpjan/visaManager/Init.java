/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;

/**
 *
 * @author WMJ_user
 */
public class Init extends Application
{

    public static Stage MAIN_STAGE;
    public static HostServices HOST_SERVICES;
    private VBox rootPanel;
    private BooleanProperty ready = new SimpleBooleanProperty(false);

    private void loadFXMLRootPanel()
    {
        //simulate long init in background
        Task task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                rootPanel = (VBox) FXMLLoader.load(Init.class.getResource("gui/panel/mainPane.fxml"));
                ready.setValue(Boolean.TRUE);
                
                //closes the preloader
                notifyPreloader(new Preloader.StateChangeNotification(
                        Preloader.StateChangeNotification.Type.BEFORE_START));

                return null;
            }
        };
        new Thread(task).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            MAIN_STAGE = primaryStage;
            HOST_SERVICES = getHostServices();
            
            loadFXMLRootPanel();
            ready.addListener(new ChangeListener<Boolean>()
            {
                public void changed(
                        ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
                {
                    if (Boolean.TRUE.equals(t1))
                    {
                        Platform.runLater(new Runnable()
                        {
                            public void run()
                            {
                                Scene scene = new Scene(rootPanel);
                                primaryStage.setScene(scene);
                                primaryStage.setTitle("WMJ Visa System");
                                primaryStage.setWidth(1600);
                                primaryStage.setHeight(990);
                                primaryStage.show();
                            }
                        });
                    }
                }
            });
        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to init app.");
        }
    }
    
    public static void main(String[] args)
    {
        Application.launch(Init.class, (java.lang.String[]) null);
    }
}
