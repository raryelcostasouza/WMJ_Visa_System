/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.Profile;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.ResultDialogSelectScan.AbstractResultDialogSelectScan;

/**
 *
 * @author WMJ_user
 */
public class CtrGUIMain
{
    
    private CtrMain ctrMain;
    private CtrDatePicker ctrDatePicker;
    
    @FXML
    private RadioButton rbDateFormatWestern;
    
    @FXML
    private ToggleGroup tgDateFormat;
    
    @FXML
    private CtrPaneSelection paneSelectionController;
    @FXML
    private Pane paneSelection;
    
    @FXML
    private CtrPaneEditSave paneEditSaveController;
    @FXML
    private HBox paneEditSave;
    
    @FXML
    private CtrPaneMonasticProfile paneMonasticProfileController;
    @FXML
    private VBox paneMonasticProfile;
    
    @FXML
    private CtrPaneMonastery paneMonasteryController;
    @FXML
    private VBox paneMonastery;
    
    @FXML
    private CtrPaneUpajjhaya paneUpajjhayaController;
    @FXML
    private VBox paneUpajjhaya;
    
    @FXML
    private CtrPanePassport panePassportController;
    @FXML
    private SplitPane panePassport;
    
    @FXML
    private CtrPaneBysuddhi paneBysuddhiController;
    @FXML
    private SplitPane paneBysuddhi;
    
    @FXML
    private CtrPaneDueTasks paneDueTasksController;
    @FXML
    private TabPane paneDueTasks;
    
    @FXML
    private CtrPane90DayNotice pane90DayNoticeController;
    @FXML
    private TabPane pane90DayNotice;
    
    @FXML
    private CtrPaneVisaExt paneVisaExtController;
    @FXML
    private TabPane paneVisaExt;
    
    @FXML
    private CtrPaneAddEntryReEntry paneReEntryController;
    @FXML
    private TabPane paneReEntry;
    
    @FXML
    private CtrPaneAddRenewPassport paneAddRenewPassportController;
    @FXML
    private VBox paneAddRenewPassport;
    
    @FXML
    private CtrPaneAddChangeVisa paneAddChangeVisaController;
    @FXML
    private TabPane paneAddChangeVisa;
    
    private CtrDialogSelectExtraScan ctrDialogSelectExtraScan;
    
    private Dialog<AbstractResultDialogSelectScan> dialogSelectExtraScan;
    
    @FXML
    private BorderPane mainPanel;
    
    private boolean containsUnsavedChanges = false;
    
    private AbstractChildPaneController currentPaneController;
    private ArrayList<AbstractChildPaneController> listPaneControllers;
    
    @FXML
    void initialize()
    {
        
        listPaneControllers = new ArrayList<>();
        
        listPaneControllers.add(paneDueTasksController);
        listPaneControllers.add(paneMonasticProfileController);
        listPaneControllers.add(paneMonasteryController);
        listPaneControllers.add(paneUpajjhayaController);
        listPaneControllers.add(panePassportController);
        listPaneControllers.add(paneBysuddhiController);
        listPaneControllers.add(paneSelectionController);
        listPaneControllers.add(paneEditSaveController);
        listPaneControllers.add(pane90DayNoticeController);
        listPaneControllers.add(paneVisaExtController);
        listPaneControllers.add(paneReEntryController);
        listPaneControllers.add(paneAddRenewPassportController);
        listPaneControllers.add(paneAddChangeVisaController);
        initDialogSelectExtraScan();
        
        for (AbstractChildPaneController acc : listPaneControllers)
        {
            acc.setParent(this);
        }
        
        mainPanel.setTop(paneSelection);
        
        this.ctrMain = new CtrMain(this);
        this.ctrDatePicker = new CtrDatePicker();
        
        Init.MAIN_STAGE.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                Init.MAIN_STAGE.close();
                CtrFileOperation.clearTMPFiles();
                ctrMain.getCtrDB().closeDBConnection();
                System.exit(0);
            }
        });
        
        initChildControllers();
        
        actionDueTasksButton(null);
    }
    
    private void initDialogSelectExtraScan()
    {
        FXMLLoader loader;
        DialogPane dp;
        try
        {
            loader = new FXMLLoader(getClass().getResource("dialogPaneSelectExtraScan.fxml"));
            dp = (DialogPane) loader.load();
            ctrDialogSelectExtraScan = ((CtrDialogSelectExtraScan) loader.getController());
            ctrDialogSelectExtraScan.setParent(this);
            
            dialogSelectExtraScan = new Dialog();
            dialogSelectExtraScan.setTitle("Select scan file");
            dialogSelectExtraScan.setDialogPane(dp);
            
            ctrDialogSelectExtraScan.init(dialogSelectExtraScan);
            
        } catch (Exception e)
        {
            CtrAlertDialog.exceptionDialog(e, "Error to load Dialog Panel.");
        }
        
    }
    
    public CtrMain getCtrMain()
    {
        return ctrMain;
    }
    
    public CtrDialogSelectExtraScan getCtrDialogSelectExtraScan()
    {
        return ctrDialogSelectExtraScan;
    }
    
    @FXML
    void actionChangeDateFormat(ActionEvent ae)
    {
        if (ae.getSource().equals(rbDateFormatWestern))
        {
            ctrDatePicker.setISOChronology();
        } else
        {
            ctrDatePicker.setThaiChronology();
        }
    }
    
    @FXML
    void actionDueTasksButton(ActionEvent ae)
    {
        mainPanel.setTop(null);
        mainPanel.setCenter(paneDueTasks);
        currentPaneController = null;
        mainPanel.setBottom(null);
        Init.MAIN_STAGE.sizeToScene();
        paneDueTasksController.fillData();
        
    }
    
    @FXML
    void actionMonasticProfileButton(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(paneMonasticProfile);
        currentPaneController = paneMonasticProfileController;
        mainPanel.setBottom(paneEditSave);
        Init.MAIN_STAGE.sizeToScene();
        actionChangeEditableForm();
        fillMonasticProfileData();
    }
    
    @FXML
    void actionButtonMonastery(ActionEvent ae)
    {
        mainPanel.setTop(null);
        mainPanel.setCenter(paneMonastery);
        currentPaneController = paneMonasteryController;
        mainPanel.setBottom(paneEditSave);
        Init.MAIN_STAGE.sizeToScene();
        actionChangeEditableForm();
        paneMonasteryController.fillMonasteryData(null);
    }
    
    @FXML
    void actionButtonUpajjhaya(ActionEvent ae)
    {
        mainPanel.setTop(null);
        mainPanel.setCenter(paneUpajjhaya);
        currentPaneController = paneUpajjhayaController;
        mainPanel.setBottom(paneEditSave);
        Init.MAIN_STAGE.sizeToScene();
        actionChangeEditableForm();
        paneUpajjhayaController.fillUpajjhayaData(null);
    }
    
    @FXML
    void actionPassportButton(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(panePassport);
        currentPaneController = panePassportController;
        mainPanel.setBottom(paneEditSave);
        Init.MAIN_STAGE.sizeToScene();
        actionChangeEditableForm();
        fillMonasticProfileData();
    }
    
    @FXML
    void actionBysuddhiButton(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(paneBysuddhi);
        currentPaneController = paneBysuddhiController;
        mainPanel.setBottom(paneEditSave);
        Init.MAIN_STAGE.sizeToScene();
        actionChangeEditableForm();
        fillMonasticProfileData();
    }
    
    @FXML
    void actionButton90DayNotice(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(pane90DayNotice);
        currentPaneController = pane90DayNoticeController;
        mainPanel.setBottom(null);
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
        
    }
    
    @FXML
    void actionButtonVisaExt(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(paneVisaExt);
        currentPaneController = paneVisaExtController;
        mainPanel.setBottom(null);
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }
    
    @FXML
    void actionButtonReEntry(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(paneReEntry);
        currentPaneController = paneReEntryController;
        mainPanel.setBottom(null);
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }
    
    @FXML
    void actionButtonAddRenewPassport(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(paneAddRenewPassport);
        currentPaneController = paneAddRenewPassportController;
        mainPanel.setBottom(null);
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }
    
    @FXML
    void actionButtonAddChangeVisa(ActionEvent ae)
    {
        mainPanel.setTop(paneSelection);
        mainPanel.setCenter(paneAddChangeVisa);
        currentPaneController = paneAddChangeVisaController;
        mainPanel.setBottom(null);
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }
    
    public Optional<AbstractResultDialogSelectScan> actionShowDialogSelectScan()
    {
        Profile p;
        
        p = getPaneSelectionController().getSelectedProfile();
        if (p != null)
        {
            ctrDialogSelectExtraScan.fillData(p);
        }
        
        return dialogSelectExtraScan.showAndWait();
    }
    
    public void actionChangeEditableForm()
    {
        IEditableGUIForm ief;
        if (currentPaneController instanceof IEditableGUIForm)
        {
            ief = (IEditableGUIForm) currentPaneController;
            if (paneEditSaveController.getLockStatus())
            {
                ief.actionLockEdit();
            } else
            {
                ief.actionUnlockEdit();
            }
        }
    }
    
    public void initChildControllers()
    {
        for (AbstractChildPaneController acc : listPaneControllers)
        {
            acc.init();
        }
    }
    
    public CtrPaneSelection getPaneSelectionController()
    {
        return paneSelectionController;
    }
    
    public Dialog getDialogSelectExtraScan()
    {
        return dialogSelectExtraScan;
    }
    
    public CtrPaneMonasticProfile getPaneMonasticProfileController()
    {
        return paneMonasticProfileController;
    }
    
    public void fillMonasticProfileData()
    {
        Profile p;
        
        p = paneSelectionController.getSelectedProfile();
        
        if ((currentPaneController != null) && currentPaneController instanceof IFormMonasticProfile)
        {
            if (p != null)
            {
                ((IFormMonasticProfile) currentPaneController).fillData(p);
            }
        }
    }
    
    public void setContainsUnsavedChanges(boolean newStatus)
    {
        containsUnsavedChanges = newStatus;
    }
    
    public boolean containsUnsavedChanges()
    {
        return containsUnsavedChanges;
    }
    
    public IEditableGUIForm getCurrentEditableGUIFormController()
    {
        if (currentPaneController instanceof IEditableGUIForm)
        {
            return (IEditableGUIForm) currentPaneController;
        } else
        {
            return null;
        }
    }
    
    public CtrPaneEditSave getPaneEditSaveController()
    {
        return paneEditSaveController;
    }
    
    public CtrDatePicker getCtrDatePicker()
    {
        return ctrDatePicker;
    }
    
}
