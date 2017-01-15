/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.FieldsPaneScanContent;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import java.io.File;
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
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.util.Util;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.AppConstants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrPDF;
import static java.lang.Integer.parseInt;

/**
 *
 * @author WMJ_user
 */
public class CtrPanePassport extends AChildPaneControllerExportPDF implements IFormMonasticProfile, IEditableGUIForm
{

    @FXML
    private Label labelLock;

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
    private Button bAddScan1;
    @FXML
    private Button bAddScan2;
    @FXML
    private Button bAddScan3;

    @FXML
    private TextField tfpassportNumber;
    @FXML
    private TextField tfpassportCountry;
    @FXML
    private TextField tfpassportIssuedAt;
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

    private File fScan1Selected;
    private File fScan2Selected;
    private File fScan3Selected;

    private FieldsPaneScanContent fieldsScan1;
    private FieldsPaneScanContent fieldsScan2;
    private FieldsPaneScanContent fieldsScan3;

    private final String ERROR_NO_PASSPORT_REGISTERED = "Please register a passport to this profile before adding scans.";

    @Override
    public void init()
    {
        super.init();
        TableColumn tc1;

        labelLock.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));

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

        fieldsScan1 = new FieldsPaneScanContent(bSelectScan1, bArchive1, tfScan1LeftPageNumber, tfScan1RightPageNumber, rbScan1ArriveStamp, rbScan1Visa, rbScan1LastVisaExt);
        fieldsScan2 = new FieldsPaneScanContent(bSelectScan2, bArchive2, tfScan2LeftPageNumber, tfScan2RightPageNumber, rbScan2ArriveStamp, rbScan2Visa, rbScan2LastVisaExt);
        fieldsScan3 = new FieldsPaneScanContent(bSelectScan3, bArchive3, tfScan3LeftPageNumber, tfScan3RightPageNumber, rbScan3ArriveStamp, rbScan3Visa, rbScan3LastVisaExt);

        initNonDigitFilter(tfScan1LeftPageNumber);
        initNonDigitFilter(tfScan2LeftPageNumber);
        initNonDigitFilter(tfScan3LeftPageNumber);

        initChangeListener();
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

        listFields.add(dpFirstEntryDate);
        listFields.add(dpNext90dayNotice);
        listFields.add(dpFirstEntryDate);

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

                    } catch (NumberFormatException nfe)
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

                    } catch (NumberFormatException nfe)
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

                    } catch (NumberFormatException nfe)
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

        loadIMGPreviews(p);
        if (p != null)
        {
            tfpassportNumber.setText(p.getPassportNumber());
            tfpassportCountry.setText(p.getPassportCountry());
            tfpassportIssuedAt.setText(p.getPassportIssuedAt());

            fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());

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

            if (p.getVisaExpiryDate() != null)
            {
                ldVisaExp = Util.convertDateToLocalDate(p.getVisaExpiryDate());
                dpVisaExpiryDate.setValue(ldVisaExp);
            }
            else
            {
                dpVisaExpiryDate.setValue(null);
            }

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
        }

    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }

    private void fillDataContentScans(MonasticProfile p, boolean lockStatus)
    {
        ArrayList<PassportScan> listPassportScans;
        PassportScan ps1, ps2, ps3;
        fieldsScan1.reset(lockStatus);
        fieldsScan2.reset(lockStatus);
        fieldsScan3.reset(lockStatus);

        if (p.getPassportScanSet() != null)
        {
            listPassportScans = new ArrayList<>();
            listPassportScans.addAll(p.getPassportScanSet());

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
            }
            //system on view mode
            else
            {
                bArchiveDepartureCard.setDisable(true);
                bScanDepartureCard.setDisable(true);

                bArchivePassport.setDisable(true);
                bScanPassport.setDisable(true);
            }

            if (listPassportScans.size() >= 1)
            {
                ps1 = listPassportScans.get(0);
                fieldsScan1.setContentTrue(lockStatus);

                tfScan1LeftPageNumber.setText(ps1.getPageNumber() + "");
                if (ps1.getContentArriveStamp())
                {
                    /*
                     * if this scan contains the Arrive Stamp blocks the
                     * selection of ArriveStamp option for other scans
                     */
                    rbScan1ArriveStamp.setSelected(true);

                    rbScan2ArriveStamp.setDisable(true);
                    rbScan3ArriveStamp.setDisable(true);
                }
                if (ps1.getContentVisaScan())
                {
                    /*
                     * if this scan contains the Visa page blocks the selection
                     * of Visa option for other scans
                     */
                    rbScan1Visa.setSelected(true);

                    rbScan2Visa.setDisable(true);
                    rbScan3Visa.setDisable(true);
                }
                if (ps1.getContentLastVisaExt())
                {
                    /*
                     * if this scan contains the Last Visa Ext blocks the
                     * selection of Last Visa Ext option for other scans
                     */
                    rbScan1LastVisaExt.setSelected(true);

                    rbScan2LastVisaExt.setDisable(true);
                    rbScan3LastVisaExt.setDisable(true);
                }
            }

            if (listPassportScans.size() >= 2)
            {
                ps2 = listPassportScans.get(1);
                fieldsScan2.setContentTrue(lockStatus);

                tfScan2LeftPageNumber.setText(ps2.getPageNumber() + "");
                if (ps2.getContentArriveStamp())
                {
                    rbScan2ArriveStamp.setSelected(true);

                    rbScan1ArriveStamp.setDisable(true);
                    rbScan3ArriveStamp.setDisable(true);
                }
                if (ps2.getContentVisaScan())
                {
                    rbScan2Visa.setSelected(true);

                    rbScan1Visa.setDisable(true);
                    rbScan3Visa.setDisable(true);
                }
                if (ps2.getContentLastVisaExt())
                {
                    rbScan2LastVisaExt.setSelected(true);

                    rbScan1LastVisaExt.setDisable(true);
                    rbScan3LastVisaExt.setDisable(true);
                }
            }

            if (listPassportScans.size() >= 3)
            {
                ps3 = listPassportScans.get(2);
                fieldsScan3.setContentTrue(lockStatus);

                tfScan3LeftPageNumber.setText(ps3.getPageNumber() + "");
                if (ps3.getContentArriveStamp())
                {
                    rbScan3ArriveStamp.setSelected(true);

                    rbScan1ArriveStamp.setDisable(true);
                    rbScan2ArriveStamp.setDisable(true);
                }
                if (ps3.getContentVisaScan())
                {
                    rbScan3Visa.setSelected(true);

                    rbScan1Visa.setDisable(true);
                    rbScan2Visa.setDisable(true);
                }
                if (ps3.getContentLastVisaExt())
                {
                    rbScan3LastVisaExt.setSelected(true);

                    rbScan1LastVisaExt.setDisable(true);
                    rbScan2LastVisaExt.setDisable(true);
                }
            }
        }

    }

    private void loadIMGPreviews(MonasticProfile p)
    {
        File fPassportScan, fDepartureCard, fScan1, fScan2, fScan3;
        PassportScan ps1 = null, ps2 = null, ps3 = null;
        ArrayList<PassportScan> listPassportScans;

        if (p != null)
        {
            fPassportScan = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
            fDepartureCard = AppFiles.getScanDepartureCard(p.getNickname());

            if (p.getPassportScanSet() != null)
            {
                listPassportScans = new ArrayList();
                listPassportScans.addAll(p.getPassportScanSet());
                if (listPassportScans.size() >= 1)
                {
                    ps1 = listPassportScans.get(0);
                }
                if (listPassportScans.size() >= 2)
                {
                    ps2 = listPassportScans.get(1);
                }
                if (listPassportScans.size() >= 3)
                {
                    ps3 = listPassportScans.get(2);
                }

                fScan1 = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps1);
                fScan2 = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps2);
                fScan3 = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps3);

            }
            else
            {
                fScan1 = fScan2 = fScan3 = null;
            }
        }
        else
        {
            fPassportScan = fDepartureCard = fScan1 = fScan2 = fScan3 = null;
        }

        GUIUtil.loadImageView(ivPassportScan, GUIUtil.IMG_TYPE_PASSPORT, fPassportScan);
        GUIUtil.loadImageView(ivDepartureCardScan, GUIUtil.IMG_TYPE_PASSPORT, fDepartureCard);
        GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_PASSPORT, fScan1);
        GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_PASSPORT, fScan2);
        GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_PASSPORT, fScan3);

    }

    @FXML
    void actionArchive(ActionEvent ae)
    {
        MonasticProfile p;
        PassportScan ps;
        ArrayList<PassportScan> listPassportScan;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        listPassportScan = new ArrayList<>();
        listPassportScan.addAll(p.getPassportScanSet());

        if (ae.getSource().equals(bArchive1))
        {
            ps = listPassportScan.get(0);
        }
        else if (ae.getSource().equals(bArchive2))
        {
            ps = listPassportScan.get(1);
        }
        else
        {
            ps = listPassportScan.get(2);
        }
        p.getPassportScanSet().remove(ps);

        CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps));
        ctrGUIMain.getCtrMain().getCtrPassportScan().remove(ps);

        fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
        loadIMGPreviews(p);
    }

    @FXML
    void actionArchivePassportScan(ActionEvent ae)
    {
        int opStatus;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        opStatus = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()));
        if (opStatus == 0)
        {
            loadIMGPreviews(p);
            CtrAlertDialog.infoDialog("Archived successfully", "Departure card scan archived successfully.");
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
            loadIMGPreviews(p);
            CtrAlertDialog.infoDialog("Archived successfully", "Departure card scan archived successfully.");
        }

    }

    @FXML
    void actionIMGPassportScanClicked(MouseEvent me)
    {
        MonasticProfile p;
        PassportScan ps;
        File fImgScan;
        ArrayList<PassportScan> listPassportScan;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        listPassportScan = new ArrayList<>();
        listPassportScan.addAll(p.getPassportScanSet());

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
            //if there is a scan selected, but not yet added
            if (fScan1Selected != null)
            {
                fImgScan = fScan1Selected;
            }
            //for a registered scan
            else
            {
                ps = listPassportScan.get(0);
                fImgScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
            }

        }
        else if (me.getSource().equals(ivScan2))
        {
            //if there is a scan selected, but not yet added
            if (fScan2Selected != null)
            {
                fImgScan = fScan2Selected;
            }
            //for a registered scan
            else
            {
                ps = listPassportScan.get(1);
                fImgScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
            }
        }
        else
        {
            //if there is a scan selected, but not yet added
            if (fScan3Selected != null)
            {
                fImgScan = fScan3Selected;
            }
            //for a registered scan
            else
            {
                ps = listPassportScan.get(2);
                fImgScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
            }
        }

        GUIUtil.openClickedIMG(fImgScan);
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
                ctrGUIMain.getCtrMain().getCtrProfile().refreshProfile(profile);
                loadIMGPreviews(profile);
                fillDataContentScans(profile, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            }
        }
    }

    @FXML
    void actionAddExtraScan(ActionEvent ae)
    {
        MonasticProfile p;
        PassportScan ps;
        int statusCopyOperation;
        File fScanDestination, fSelected;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans
        if (validateExtraScanContent((Button) ae.getSource()))
        {
            if (ae.getSource().equals(bAddScan1))
            {
                ps = new PassportScan(p, parseInt(tfScan1LeftPageNumber.getText()), rbScan1ArriveStamp.isSelected(), rbScan1Visa.isSelected(), rbScan1LastVisaExt.isSelected());
                fSelected = fScan1Selected;
                ctrGUIMain.getCtrMain().getCtrPassportScan().addPassportScan(ps);

            }
            else if (ae.getSource().equals(bAddScan2))
            {
                ps = new PassportScan(p, parseInt(tfScan2LeftPageNumber.getText()), rbScan2ArriveStamp.isSelected(), rbScan2Visa.isSelected(), rbScan2LastVisaExt.isSelected());
                fSelected = fScan2Selected;
                ctrGUIMain.getCtrMain().getCtrPassportScan().addPassportScan(ps);
            }
            else
            {
                ps = new PassportScan(p, parseInt(tfScan3LeftPageNumber.getText()), rbScan3ArriveStamp.isSelected(), rbScan3Visa.isSelected(), rbScan3LastVisaExt.isSelected());
                fSelected = fScan3Selected;
                ctrGUIMain.getCtrMain().getCtrPassportScan().addPassportScan(ps);
            }
            fScanDestination = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);

            if (fSelected != null)
            {
                statusCopyOperation = CtrFileOperation.copyOperation(fSelected, fScanDestination);

                //if the operation was successful
                //saves the scan content information as well
                if (statusCopyOperation == 0)
                {
                    //disables the add button
                    if (ae.getSource().equals(bAddScan1))
                    {
                        bAddScan1.setDisable(true);
                        fScan1Selected = null;
                    }
                    else if (ae.getSource().equals(bAddScan2))
                    {
                        bAddScan2.setDisable(true);
                        fScan2Selected = null;
                    }
                    else
                    {
                        bAddScan3.setDisable(true);
                        fScan3Selected = null;
                    }

                    //refresh the profile because the passportScan list was updated
                    ctrGUIMain.getCtrMain().getCtrProfile().refreshProfile(p);
                    loadIMGPreviews(p);
                    fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
                }
            }

        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill the scan contents information before selecting the file");
        }
    }

    @FXML
    void actionSelectExtraScan(ActionEvent ae)
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
                if (ae.getSource().equals(bSelectScan1))
                {
                    fScan1Selected = fSelected;
                    GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                    bAddScan1.setDisable(false);
                }
                else if (ae.getSource().equals(bSelectScan2))
                {
                    fScan2Selected = fSelected;
                    GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                    bAddScan2.setDisable(false);
                }
                else
                {
                    fScan3Selected = fSelected;
                    GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                    bAddScan3.setDisable(false);
                }
            }
        }
        else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }
    }

    private boolean validateExtraScanContent(Button sourceButton)
    {
        //if the page number is not empty and at least one of the options is selected
        //returns true
        if (sourceButton.equals(bAddScan1))
        {
            return validatePageNumber(tfScan1LeftPageNumber.getText())
                    && (rbScan1ArriveStamp.isSelected()
                    || rbScan1LastVisaExt.isSelected()
                    || rbScan1Visa.isSelected());
        }
        else if (sourceButton.equals(bAddScan2))
        {
            return validatePageNumber(tfScan2LeftPageNumber.getText())
                    && (rbScan2ArriveStamp.isSelected()
                    || rbScan2LastVisaExt.isSelected()
                    || rbScan2Visa.isSelected());
        }
        else
        {
            return validatePageNumber(tfScan3LeftPageNumber.getText())
                    && (rbScan3ArriveStamp.isSelected()
                    || rbScan3LastVisaExt.isSelected()
                    || rbScan3Visa.isSelected());
        }

    }

    private boolean validatePageNumber(String strPageNumber)
    {
        try
        {
            Integer.parseInt(strPageNumber);
            return true;
        } catch (NumberFormatException nfe)
        {
            CtrAlertDialog.errorDialog("Invalid page number");
            return false;
        }
    }

    @Override
    public void actionLockEdit()
    {
        MonasticProfile p;
        dpFirstEntryDate.setDisable(true);

        bScanPassport.setDisable(true);
        bScanDepartureCard.setDisable(true);

        bSelectScan1.setDisable(true);
        bSelectScan2.setDisable(true);
        bSelectScan3.setDisable(true);

        bArchive1.setDisable(true);
        bArchive2.setDisable(true);
        bArchive3.setDisable(true);
        bArchivePassport.setDisable(true);
        bArchiveDepartureCard.setDisable(true);

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        fillDataContentScans(p, true);
    }

    @Override
    public void actionUnlockEdit()
    {
        MonasticProfile p;

        dpFirstEntryDate.setDisable(false);

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        fillDataContentScans(p, false);
    }

    @Override
    public void actionSave()
    {
        MonasticProfile p;

        int operationStatus;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        p.setFirstEntryDate(Util.convertLocalDateToDate(dpFirstEntryDate.getValue()));
        operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);

        if (operationStatus == 0)
        {
            ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
            CtrAlertDialog.infoDialog("Passport update", "The passport information was successfully updated.");
        }
    }

    @FXML
    void actionPreviewScansPDF(ActionEvent ae)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PREVIEW_FORM);
    }

    @FXML
    void actionPrintScans(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PRINT_FORM);
    }
}
