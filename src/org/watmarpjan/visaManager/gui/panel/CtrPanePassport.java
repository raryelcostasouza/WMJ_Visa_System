/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.stampedPage.CtrModuleGenericScanStampedPage;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import org.watmarpjan.visaManager.util.Util;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.AppConstants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import org.watmarpjan.visaManager.AppPaths;
import static java.lang.Integer.parseInt;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hibernate.internal.util.compare.ComparableComparator;
import org.watmarpjan.visaManager.GenericScanStampedPageFilenameFilter;
import org.watmarpjan.visaManager.MainScanStampedPageFilenameFilter;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.gui.panel.stampedPage.CtrModuleMainScanStampedPage;
import org.watmarpjan.visaManager.model.stampedPage.input.InfoFileScanStampedPage;
import org.watmarpjan.visaManager.model.stampedPage.output.InfoMainScanStampedPage;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;

/**
 *
 * @author WMJ_user
 */
public class CtrPanePassport extends AChildPaneControllerExportPDF implements IFormMonasticProfile, IEditableGUIForm
{

    @FXML
    private Label labelLock;
    @FXML
    private Label labelLock2;
    @FXML
    private Label labelLock3;
    @FXML
    private Label labelLock4;

    @FXML
    private ImageView ivPassportScan;

    @FXML
    private ImageView ivDepartureCardScan;
    @FXML
    private ImageView ivScan1;
    @FXML
    private ImageView ivScan2;
    @FXML
    private ImageView ivScan3;

    @FXML
    private Button bArchivePassport;

    @FXML
    private Button bArchiveDepartureCard;

    @FXML
    private Button bScanPassport;

    @FXML
    private Button bScanDepartureCard;

    @FXML
    private Button bSelectScan1;
    @FXML
    private Button bSelectScan2;
    @FXML
    private Button bSelectScan3;

    @FXML
    private TextField tfpassportNumber;
    @FXML
    private TextField tfpassportCountry;
    @FXML
    private ComboBox<String> cbPassportIssuedAt;
    @FXML
    private DatePicker dpPassportIssueDate;
    @FXML
    private DatePicker dpPassportExpiryDate;
    @FXML
    private DatePicker dpFirstEntryDate;
    @FXML
    private TextField tfVisaNumber;
    @FXML
    private ComboBox<String> cbVisaType;
    @FXML
    private DatePicker dpVisaExpiryDate;
    @FXML
    private TextField tfArrivalTMNumber;
    @FXML
    private DatePicker dpLastEntry;
    @FXML
    private TextField tfPortOfEntry;
    @FXML
    private TextField tfTravelFrom;
    @FXML
    private ComboBox<String> cbTravelBy;

    @FXML
    private DatePicker dpNext90dayNotice;

    @FXML
    private TextField tfNVisaExt;

    @FXML
    private TableView<EntryVisaExt> tvExtensions;

    @FXML
    private ToggleGroup tgArriveStamp;
    @FXML
    private ToggleGroup tgVisa;
    @FXML
    private ToggleGroup tgLastVisaExt;

    @FXML
    private TextField tfScan1LeftPageNumber;
    @FXML
    private TextField tfScan1RightPageNumber;
    @FXML
    private RadioButton rbScan1ArriveStamp;
    @FXML
    private RadioButton rbScan1Visa;
    @FXML
    private RadioButton rbScan1LastVisaExt;

    @FXML
    private TextField tfScan2LeftPageNumber;
    @FXML
    private TextField tfScan2RightPageNumber;
    @FXML
    private RadioButton rbScan2ArriveStamp;
    @FXML
    private RadioButton rbScan2Visa;
    @FXML
    private RadioButton rbScan2LastVisaExt;

    @FXML
    private TextField tfScan3LeftPageNumber;
    @FXML
    private TextField tfScan3RightPageNumber;
    @FXML
    private RadioButton rbScan3ArriveStamp;
    @FXML
    private RadioButton rbScan3Visa;
    @FXML
    private RadioButton rbScan3LastVisaExt;

    @FXML
    private Button bArchive1;
    @FXML
    private Button bArchive2;
    @FXML
    private Button bArchive3;
    
    @FXML
    private Button bPreviewScansAllStampedPages;

    private InfoFileScanStampedPage objInfoScanLoaded1;
    private InfoFileScanStampedPage objInfoScanLoaded2;
    private InfoFileScanStampedPage objInfoScanLoaded3;

    private CtrModuleMainScanStampedPage ctrPaneScan1;
    private CtrModuleMainScanStampedPage ctrPaneScan2;
    private CtrModuleMainScanStampedPage ctrPaneScan3;

    @FXML
    private VBox vboxAllStampedPages;

    @FXML
    private ArrayList<HBox> listHBox;

    @FXML
    private Button bAddStampedPageScan;

    @FXML
    private Tab tAllStampedPages;

    private ArrayList<CtrModuleGenericScanStampedPage> listCtrModulePassportStampedPage;
    private ArrayList<InfoFileScanStampedPage> listGenericScans;

    public final String ERROR_NO_PASSPORT_REGISTERED = "Please register a passport to this profile before adding scans.";
    
    @FXML
    private Button bSelectEVisaScan;
    
    @FXML
    private Button bArchiveEVisaScan;
    
    @FXML
    private Button bViewEVisaScan;
    
    @FXML
    private TextField tfEVisaScanPath;
    
    File fPDFSelectedEVisa;
    
    @Override
    public void init()
    {
        super.init();

        ImageView ivAddButton;
        Path pIconAdd;
        TableColumn tc1;

        listCtrModulePassportStampedPage = new ArrayList<>();
        listHBox = new ArrayList<>();

        pIconAdd = AppPaths.getPathToIconSubfolder().resolve("add.png");
        ivAddButton = new ImageView(pIconAdd.toFile().toURI().toString());
        bAddStampedPageScan.setGraphic(ivAddButton);

        bPreviewScansAllStampedPages.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        labelLock.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        labelLock2.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        labelLock3.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        labelLock4.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        
        bViewEVisaScan.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPassportExpiryDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPassportIssueDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpFirstEntryDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpLastEntry);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpVisaExpiryDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNext90dayNotice);

        cbTravelBy.getItems().addAll(AppConstants.LIST_TRAVEL_BY);
        cbVisaType.getItems().addAll(AppConstants.LIST_VISA_TYPES);

        tc1 = tvExtensions.getColumns().get(0);
        tc1.setCellValueFactory(new PropertyValueFactory<>("extNumber"));

        tvExtensions.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        bSelectEVisaScan.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("add.png").toUri().toString()));
        bArchiveEVisaScan.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("remove.png").toUri().toString()));
        
        
        ctrPaneScan1 = new CtrModuleMainScanStampedPage(bSelectScan1, bArchive1, tfScan1LeftPageNumber, tfScan1RightPageNumber, rbScan1ArriveStamp, rbScan1Visa, rbScan1LastVisaExt, ivScan1, this);
        ctrPaneScan2 = new CtrModuleMainScanStampedPage(bSelectScan2, bArchive2, tfScan2LeftPageNumber, tfScan2RightPageNumber, rbScan2ArriveStamp, rbScan2Visa, rbScan2LastVisaExt, ivScan2, this);
        ctrPaneScan3 = new CtrModuleMainScanStampedPage(bSelectScan3, bArchive3, tfScan3LeftPageNumber, tfScan3RightPageNumber, rbScan3ArriveStamp, rbScan3Visa, rbScan3LastVisaExt, ivScan3, this);
        
        initNonDigitFilter(tfScan1LeftPageNumber);
        initNonDigitFilter(tfScan2LeftPageNumber);
        initNonDigitFilter(tfScan3LeftPageNumber);
        
        GUIUtil.initAutoHeightResize(tvExtensions, 3.5);
        initChangeListener();
    }
    
    private void fillEVisaDetails(MonasticProfile p)
    {
        tfEVisaScanPath.setText("");
   
        if (AppFiles.getEVisa(p.getNickname()).exists())
        {
            System.out.println("exists" + AppFiles.getEVisa(p.getNickname()).toString());
            bViewEVisaScan.setDisable(false);

            tfEVisaScanPath.setVisible(false);
            bSelectEVisaScan.setVisible(false);

            bArchiveEVisaScan.setVisible(true);
        }
        else
        {
            System.out.println("not exists");
            bViewEVisaScan.setDisable(true);

            tfEVisaScanPath.setVisible(true);
            bSelectEVisaScan.setVisible(true);

            bArchiveEVisaScan.setVisible(false);
        }
    }
     
    //listener to remove any non-digit char from the text field
    private void initNonDigitFilter(TextField objTF)
    {
        objTF.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    objTF.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });
    }

    private void initChangeListener()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(cbPassportIssuedAt);
        listFields.add(dpPassportIssueDate);
        listFields.add(dpPassportExpiryDate);
        listFields.add(dpFirstEntryDate);
        listFields.add(tgArriveStamp);
        listFields.add(tgLastVisaExt);
        listFields.add(tgVisa);

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        tfScan1LeftPageNumber.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfScan1LeftPageNumber.getText());
                        tfScan1RightPageNumber.setText(parsedPageNumberValue + 1 + "");

                    }
                    catch (NumberFormatException nfe)
                    {
                        tfScan1RightPageNumber.setText("");
                    }
                }
            }
        });
        tfScan2LeftPageNumber.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfScan2LeftPageNumber.getText());
                        tfScan2RightPageNumber.setText(parsedPageNumberValue + 1 + "");

                    }
                    catch (NumberFormatException nfe)
                    {
                        tfScan2RightPageNumber.setText("");
                    }
                }
            }
        });
        tfScan3LeftPageNumber.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfScan3LeftPageNumber.getText());
                        tfScan3RightPageNumber.setText(parsedPageNumberValue + 1 + "");

                    }
                    catch (NumberFormatException nfe)
                    {
                        tfScan3RightPageNumber.setText("");
                    }
                }
            }
        }
        );

    }

    @Override
    public void fillData(MonasticProfile p)
    {
        ArrayList<EntryVisaExt> alVisaExtensions;
        LocalDate ldPassportExp, ldPassptIssue, ldVisaExp, ldNext90day, ldLastEntry, ldFirstEntry;

        GUIUtil.loadContentComboboxGeneric(cbPassportIssuedAt, ctrGUIMain.getCtrMain().getCtrProfile().loadListPassportIssuedAt());
        //loadIMGPreviewsScans(p);
        if (p != null)
        {
            tfpassportNumber.setText(p.getPassportNumber());
            tfpassportCountry.setText(p.getPassportCountry());
            cbPassportIssuedAt.setValue(p.getPassportIssuedAt());

            fillDataTabMainScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            fillDataTabAllStampedPages(p,ctrGUIMain.getPaneEditSaveController().getLockStatus());

            if (p.getPassportExpiryDate() != null)
            {
                ldPassportExp = Util.convertDateToLocalDate(p.getPassportExpiryDate());
                dpPassportExpiryDate.setValue(ldPassportExp);
            }
            else
            {
                dpPassportExpiryDate.setValue(null);
            }

            if (p.getPassportIssueDate() != null)
            {
                ldPassptIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
                dpPassportIssueDate.setValue(ldPassptIssue);
            }
            else
            {
                dpPassportIssueDate.setValue(null);
            }

            if (p.getFirstEntryDate() != null)
            {
                ldFirstEntry = Util.convertDateToLocalDate(p.getFirstEntryDate());
                dpFirstEntryDate.setValue(ldFirstEntry);
            }
            else
            {
                dpFirstEntryDate.setValue(null);
            }

            tfVisaNumber.setText(p.getVisaNumber());
            cbVisaType.setValue(p.getVisaType());

            dpVisaExpiryDate.setValue(ctrGUIMain.getCtrMain().getCtrVisa().getVisaOrExtExpiryDate(p));

            if (p.getNext90DayNotice() != null)
            {
                ldNext90day = Util.convertDateToLocalDate(p.getNext90DayNotice());
                dpNext90dayNotice.setValue(ldNext90day);
            }
            else
            {
                dpNext90dayNotice.setValue(null);
            }

            tfArrivalTMNumber.setText(p.getArrivalCardNumber());
            tfPortOfEntry.setText(p.getArrivalPortOfEntry());
            tfTravelFrom.setText(p.getArrivalTravelFrom());
            cbTravelBy.setValue(p.getArrivalTravelBy());

            ldLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
            dpLastEntry.setValue(ldLastEntry);

            alVisaExtensions = ctrGUIMain.getCtrMain().getCtrVisa().loadListExtensions(p.getIdProfile());
            tvExtensions.getItems().clear();
            tvExtensions.getItems().addAll(alVisaExtensions);

            tfNVisaExt.setText("" + alVisaExtensions.size());
            
            fillEVisaDetails(p);
        }

    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }

    private void refreshInfoPassportScanStampedPages(MonasticProfile p)
    {
        ArrayList<InfoFileScanStampedPage> listExtraPScan;

        objInfoScanLoaded1 = objInfoScanLoaded2 = objInfoScanLoaded3 = null;
        listExtraPScan = AppFiles.getListInfoPassportScansStampedPage(p.getNickname(), p.getPassportNumber(), new MainScanStampedPageFilenameFilter());
        listExtraPScan.sort(new ComparableComparator<InfoFileScanStampedPage>());

        if (listExtraPScan.size() >= 1)
        {
            objInfoScanLoaded1 = listExtraPScan.get(0);
        }
        if (listExtraPScan.size() >= 2)
        {
            objInfoScanLoaded2 = listExtraPScan.get(1);
        }
        if (listExtraPScan.size() >= 3)
        {
            objInfoScanLoaded3 = listExtraPScan.get(2);
        }
    }

    private void toggleLockButtonsScanFirstPageNDepartureCard(boolean lockStatus, MonasticProfile p)
    {
        //if the system is on edit mode
        if (!lockStatus)
        {
            if (AppFiles.getScanDepartureCard(p.getNickname()).exists())
            {
                bArchiveDepartureCard.setDisable(false);
                bScanDepartureCard.setDisable(true);
            }
            else
            {
                bArchiveDepartureCard.setDisable(true);
                bScanDepartureCard.setDisable(false);
            }

            if (AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()).exists())
            {
                bArchivePassport.setDisable(false);
                bScanPassport.setDisable(true);
            }
            else
            {
                bArchivePassport.setDisable(true);
                bScanPassport.setDisable(false);
            }
        } //system on view mode
        else
        {
            bArchiveDepartureCard.setDisable(true);
            bScanDepartureCard.setDisable(true);

            bArchivePassport.setDisable(true);
            bScanPassport.setDisable(true);
        }
    }

    private void fillDataTabMainScans(MonasticProfile p, boolean lockStatus)
    {
        File fPassportScan, fDepartureCard;

        ctrPaneScan1.clearData();
        ctrPaneScan2.clearData();
        ctrPaneScan3.clearData();

        ctrPaneScan1.setFileScanSelectedButUnsaved(null);
        ctrPaneScan2.setFileScanSelectedButUnsaved(null);
        ctrPaneScan3.setFileScanSelectedButUnsaved(null);

        refreshInfoPassportScanStampedPages(p);
        toggleLockButtonsScanFirstPageNDepartureCard(lockStatus, p);

        ctrPaneScan1.fillData(objInfoScanLoaded1, lockStatus);
        ctrPaneScan2.fillData(objInfoScanLoaded2, lockStatus);
        ctrPaneScan3.fillData(objInfoScanLoaded3, lockStatus);

        fPassportScan = fDepartureCard = null;
        if (p != null)
        {
            fPassportScan = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
            fDepartureCard = AppFiles.getScanDepartureCard(p.getNickname());
        }

        GUIUtil.loadImageView(ivPassportScan, GUIUtil.IMG_TYPE_PASSPORT, fPassportScan);
        GUIUtil.loadImageView(ivDepartureCardScan, GUIUtil.IMG_TYPE_PASSPORT, fDepartureCard);
    }

    private void fillDataTabAllStampedPages(MonasticProfile p, boolean lockStatus)
    {
        CtrModuleGenericScanStampedPage objCtr;

        listGenericScans = AppFiles.getListInfoPassportScansStampedPage(p.getNickname(), p.getPassportNumber(), new GenericScanStampedPageFilenameFilter());
        //sort according to page number
        listGenericScans.sort(new ComparableComparator<InfoFileScanStampedPage>());

        //resets the stamped pages 
        listCtrModulePassportStampedPage.clear();
        vboxAllStampedPages.getChildren().clear();
        listHBox.clear();

        //one loop for creating all the GUI Modules
        for (InfoFileScanStampedPage objPS1 : listGenericScans)
        {
            //for each scan found add a GUI Module for managing the stamped page scan
            actionAddGUIModuleStampedPageScan();

            //retrieve the last controller added with the module and fills it with the data
            objCtr = listCtrModulePassportStampedPage.get(listCtrModulePassportStampedPage.size() - 1);
            objCtr.fillData(objPS1, lockStatus);
        }
    }
    
    public void fillDataScans()
    {
        MonasticProfile p;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        
        fillDataTabMainScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
        fillDataTabAllStampedPages(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
    }

    @FXML
    void actionArchiveExtraScan(ActionEvent ae)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (ae.getSource().equals(bArchive1))
        {
            ctrPaneScan1.archiveScan(p);
        }
        else if (ae.getSource().equals(bArchive2))
        {
            ctrPaneScan2.archiveScan(p);
        }
        else
        {
            ctrPaneScan3.archiveScan(p);
        }
    }

    @FXML
    void actionArchivePassportFirstPage(ActionEvent ae)
    {
        int opStatus;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        opStatus = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()));
        if (opStatus == 0)
        {
            fillDataScans();
            CtrAlertDialog.infoDialog("Archived successfully", "Passport scan archived successfully.");
        }

    }

    @FXML
    void actionArchiveDepartureCard(ActionEvent ae)
    {
        int opStatus;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        opStatus = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, AppFiles.getScanDepartureCard(p.getNickname()));
        if (opStatus == 0)
        {
            fillDataScans();
            CtrAlertDialog.infoDialog("Archived successfully", "Departure card scan archived successfully.");
        }

    }

    @FXML
    void actionIMGPassportScanClicked(MouseEvent me)
    {
        MonasticProfile p;
        File fImgScan, fScan1Selected, fScan2Selected, fScan3Selected;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        if (me.getSource().equals(ivPassportScan))
        {
            fImgScan = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
        }
        else if (me.getSource().equals(ivDepartureCardScan))
        {
            fImgScan = AppFiles.getScanDepartureCard(p.getNickname());
        }
        else if (me.getSource().equals(ivScan1))
        {
            fScan1Selected = ctrPaneScan1.getFileScanSelectedButUnsaved();
            //if there is a scan selected, but not yet added
            if (fScan1Selected != null)
            {
                fImgScan = fScan1Selected;
            } //for a registered scan
            else
            {
                if (objInfoScanLoaded1 != null)
                {
                    fImgScan = objInfoScanLoaded1.getFileScan();
                }
                else
                {
                    fImgScan = null;
                }
            }
        }
        else if (me.getSource().equals(ivScan2))
        {
            fScan2Selected = ctrPaneScan2.getFileScanSelectedButUnsaved();
            //if there is a scan selected, but not yet added
            if (fScan2Selected != null)
            {
                fImgScan = fScan2Selected;
            } //for a registered scan
            else
            {
                if (objInfoScanLoaded2 != null)
                {
                    fImgScan = objInfoScanLoaded2.getFileScan();
                }
                else
                {
                    fImgScan = null;
                }

            }
        }
        else
        {
            fScan3Selected = ctrPaneScan3.getFileScanSelectedButUnsaved();

            //if there is a scan selected, but not yet added
            if (fScan3Selected != null)
            {
                fImgScan = fScan3Selected;
            } //for a registered scan
            else
            {
                if (objInfoScanLoaded3 != null)
                {
                    fImgScan = objInfoScanLoaded3.getFileScan();
                }
                else
                {
                    fImgScan = null;
                }

            }
        }

        CtrFileOperation.openFileOnDefaultProgram(fImgScan);
    }

    @FXML
    void actionChooseScanPassport(ActionEvent ae)
    {
        MonasticProfile p;
        File fScanDestination;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p.getPassportNumber() != null)
        {
            fScanDestination = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
            actionChooseScan(p, fScanDestination, "Passport First Page Scan");
        }
        else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }

    }

    @FXML
    void actionChooseScanDepartureCard(ActionEvent ae)
    {
        MonasticProfile p;
        File fScanDestination;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p.getPassportNumber() != null)
        {
            fScanDestination = AppFiles.getScanDepartureCard(p.getNickname());
            actionChooseScan(p, fScanDestination, "Departure Card Scan");
        }
        else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }

    }

    private void actionChooseScan(MonasticProfile profile, File fScanDestination, String title)
    {
        File fSelected;
        int opStatus;

        fSelected = CtrFileOperation.selectFile(title, CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

        if (fSelected != null)
        {
            opStatus = CtrFileOperation.copyOperation(fSelected, fScanDestination);

            //if the operation was successful
            //saves the scan content information as well
            if (opStatus == 0)
            {
                //refresh the profile because the passportScan list was updated
                ctrGUIMain.getCtrMain().getCtrProfile().refresh(profile);
                fillDataScans();
            }
        }
    }

    @FXML
    private void actionAddGUIModuleStampedPageScan()
    {
        CtrModuleGenericScanStampedPage ctrModule;
        FXMLLoader objLoader;
        TitledPane loadedPane;
        HBox lastHBox, newHbox;
        int index, tabSelectedIndex;

        try
        {
            objLoader = new FXMLLoader(getClass().getResource("modulePassportStampedPagesScan.fxml"));

            //loads the gui element
            loadedPane = objLoader.load();

            //loads the controller for the module
            ctrModule = (CtrModuleGenericScanStampedPage) objLoader.getController();
            listCtrModulePassportStampedPage.add(ctrModule);

            index = listCtrModulePassportStampedPage.size();
            ctrModule.init(this, index);

            //gets the HBox on the last line of the GUI
            //if there are no lines of HBox
            //or if the number of elements per line did already reach 3 create a new HBox line
            if (listHBox.isEmpty() || listHBox.get(listHBox.size() - 1).getChildren().size() >= 3)
            {
                newHbox = new HBox();
                listHBox.add(newHbox);
                newHbox.getChildren().add(loadedPane);
                vboxAllStampedPages.getChildren().add(newHbox);

                //switch tabs and come back to reupdate tab height for scrolling
                tabSelectedIndex = tAllStampedPages.getTabPane().getSelectionModel().getSelectedIndex();
                switch2Tab(tabSelectedIndex);
            }
            //add on the last line
            else
            {
                lastHBox = listHBox.get(listHBox.size() - 1);
                lastHBox.getChildren().add(loadedPane);
            }
        }
        catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load Stamped Page Scan GUI Panel");
        }
    }

    private boolean saveGenericExtraScans()
    {
        boolean ret, errorHappened;

        errorHappened = false;

        for (CtrModuleGenericScanStampedPage objCtr : listCtrModulePassportStampedPage)
        {

            ret = objCtr.saveScan(listCtrModulePassportStampedPage);
            if (ret)
            {
                errorHappened = true;
            }
        }
        return errorHappened;

    }

    private boolean saveMainExtraScans()
    {
        boolean error1, error2, error3, errorHappened;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans

        error1 = error2 = error3 = false;

        //if there are any new extra scans added
        //only run the save operation if they are fully validated
        //in other cases... can just go ahead and save
        error1 = ctrPaneScan1.saveScan(p, listCtrModulePassportStampedPage);
        error2 = ctrPaneScan2.saveScan(p, listCtrModulePassportStampedPage);
        error3 = ctrPaneScan3.saveScan(p, listCtrModulePassportStampedPage);
        fillDataTabMainScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
        //if there was any error on file copying process
        if (error1 || error2 || error3)
        {
            errorHappened = true;

        }
        else
        {
            errorHappened = false;
        }

        return errorHappened;
    }

    @FXML
    void actionChooseExtraScan(ActionEvent ae)
    {
        File fSelected;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans
        if (p.getPassportNumber() != null)
        {
            fSelected = CtrFileOperation.selectFile("Extra Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
            if (fSelected != null)
            {
                ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
                if (ae.getSource().equals(bSelectScan1))
                {
                    ctrPaneScan1.setFileScanSelectedButUnsaved(fSelected);
                    GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                }
                else if (ae.getSource().equals(bSelectScan2))
                {
                    ctrPaneScan2.setFileScanSelectedButUnsaved(fSelected);
                    GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                }
                else
                {
                    ctrPaneScan3.setFileScanSelectedButUnsaved(fSelected);
                    GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                }
            }
        }
        else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }
    }

    @Override
    public void actionLockEdit()
    {
        MonasticProfile p;

        cbPassportIssuedAt.setDisable(true);
        dpPassportIssueDate.setDisable(true);
        dpPassportExpiryDate.setDisable(true);
        dpFirstEntryDate.setDisable(true);

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        toggleLockButtonsScanFirstPageNDepartureCard(true, p);

        ctrPaneScan1.actionLockEdit();
        ctrPaneScan2.actionLockEdit();
        ctrPaneScan3.actionLockEdit();
        
        bSelectEVisaScan.setDisable(true);
        bArchiveEVisaScan.setDisable(true);
        
        bAddStampedPageScan.setDisable(true);
        for (CtrModuleGenericScanStampedPage objCtrStampedPage : listCtrModulePassportStampedPage)
        {
            objCtrStampedPage.actionLockEdit();
        }
    }

    @Override
    public void actionUnlockEdit()
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        cbPassportIssuedAt.setDisable(false);
        dpPassportIssueDate.setDisable(false);
        dpPassportExpiryDate.setDisable(false);
        dpFirstEntryDate.setDisable(false);
        

        toggleLockButtonsScanFirstPageNDepartureCard(false, p);
        ctrPaneScan1.actionUnlockEdit();
        ctrPaneScan2.actionUnlockEdit();
        ctrPaneScan3.actionUnlockEdit();
        
        bSelectEVisaScan.setDisable(false);
        bArchiveEVisaScan.setDisable(false);
        
        bAddStampedPageScan.setDisable(false);
        for (CtrModuleGenericScanStampedPage objCtrStampedPage : listCtrModulePassportStampedPage)
        {
            objCtrStampedPage.actionUnlockEdit();
        }
    }

    @Override
    public int actionSave()
    {
        MonasticProfile p;

        int opStatus1;
        boolean errorHappenedOp2, errorHappenedOp3;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        p.setPassportIssuedAt(cbPassportIssuedAt.getValue());
        p.setPassportIssueDate(Util.convertLocalDateToDate(dpPassportIssueDate.getValue()));
        p.setPassportExpiryDate(Util.convertLocalDateToDate(dpPassportExpiryDate.getValue()));
        p.setFirstEntryDate(Util.convertLocalDateToDate(dpFirstEntryDate.getValue()));

        opStatus1 = ctrGUIMain.getCtrMain().getCtrProfile().update(p);
        errorHappenedOp2 = saveMainExtraScans();
        errorHappenedOp3 = saveGenericExtraScans();
        
        actionSaveEVisaScan(p, fPDFSelectedEVisa);
        //only shows success message if all saving procedures were successful
        if ((opStatus1 == 0) && !errorHappenedOp2 && !errorHappenedOp3)
        {
            //reload img Scans
            fillDataScans();
            ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
            CtrAlertDialog.infoDialog("Passport update", "The passport information was successfully updated.");
            return 0;
        }
        else
        {
            return -1;
        }
    }

    @FXML
    void actionPreviewScansPDF(ActionEvent ae)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if the preview button for the main scans is clicked
        if (ae.getSource().equals(bPreview))
        {
            ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PREVIEW_FORM, new MainScanStampedPageFilenameFilter());
        }
        //if the preview button for all stamped pages is clicked
        else
        {
            ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PREVIEW_FORM, new GenericScanStampedPageFilenameFilter());
        }
    }
    
    @FXML
    void actionSelectEVisaScan(ActionEvent ae)
    {
        fPDFSelectedEVisa = CtrFileOperation.selectFile("Select E-Visa Scan File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);
        if (fPDFSelectedEVisa != null)
        {
            tfEVisaScanPath.setText(fPDFSelectedEVisa.getAbsolutePath().toString());
            ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
        }
    }
    
    private void actionSaveEVisaScan(MonasticProfile p, File fPDFSelected)
    {
        if (fPDFSelected != null && fPDFSelected.exists())
        {
            CtrFileOperation.copyOperation(fPDFSelected, AppFiles.getEVisa(p.getNickname()));
        }
        //reset temp variable after save
        fPDFSelected = null;
        fillEVisaDetails(p);
    }
    
    @FXML
    void actionViewEVisaScan(ActionEvent ae)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        CtrFileOperation.openFileOnDefaultProgram(AppFiles.getEVisa(p.getNickname()));
    }
    
    @FXML
    void actionArchiveEVisa()
    {
        MonasticProfile p;
        String msg;
        int ret;
        File f2Archive;

        msg = "Are you sure that you want to remove this E-Visa from system?\n"
                + "(Note: The e-visa file will be archived)\n ";
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        f2Archive = AppFiles.getEVisa(p.getNickname());
        
        ret = CtrFileOperation.archiveAfterConfirmation(f2Archive, msg, p.getNickname(),CtrFileOperation.SCAN_TYPE_PASSPORT);
        if (ret == 0)
        {
            fillEVisaDetails(p);
            CtrAlertDialog.infoDialog("Archived successfully", "The E-Visa was archived successfully.");
        }
    }

//    @FXML
//    void actionPrintScans(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PRINT_FORM);
//    }
    public CtrGUIMain getCtrGUIMain()
    {
        return ctrGUIMain;
    }
    
    public void switch2Tab(int index)
    {
        tAllStampedPages.getTabPane().getSelectionModel().clearSelection();
        tAllStampedPages.getTabPane().getSelectionModel().select(index);
    }
}
