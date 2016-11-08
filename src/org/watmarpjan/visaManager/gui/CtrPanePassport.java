/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

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
import static java.lang.Integer.parseInt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import org.watmarpjan.visaManager.AppPaths;

/**
 *
 * @author WMJ_user
 */
public class CtrPanePassport extends AbstractChildPaneController implements IFormMonasticProfile, IEditableGUIForm
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
    private Button bScan1;
    @FXML
    private Button bScan2;
    @FXML
    private Button bScan3;

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

    private FieldsPaneScanContent fieldsScan1;
    private FieldsPaneScanContent fieldsScan2;
    private FieldsPaneScanContent fieldsScan3;

    private final String ERROR_NO_PASSPORT_REGISTERED = "Please register a passport to this profile before adding scans.";

    @Override
    public void init()
    {
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

        fieldsScan1 = new FieldsPaneScanContent(bScan1, bArchive1, tfScan1LeftPageNumber, tfScan1RightPageNumber, rbScan1ArriveStamp, rbScan1Visa, rbScan1LastVisaExt);
        fieldsScan2 = new FieldsPaneScanContent(bScan2, bArchive2, tfScan2LeftPageNumber, tfScan2RightPageNumber, rbScan2ArriveStamp, rbScan2Visa, rbScan2LastVisaExt);
        fieldsScan3 = new FieldsPaneScanContent(bScan3, bArchive3, tfScan3LeftPageNumber, tfScan3RightPageNumber, rbScan3ArriveStamp, rbScan3Visa, rbScan3LastVisaExt);

        initChangeListener();
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
                fieldsScan1.setStatusScan(true);

                tfScan1LeftPageNumber.setText(ps1.getPageNumber() + "");
                if (ps1.isContentArriveStamp())
                {
                    /*
                 * if this scan contains the Arrive Stamp blocks the
                 * selection of ArriveStamp option for other scans
                     */
                    rbScan1ArriveStamp.setSelected(true);

                    rbScan2ArriveStamp.setDisable(true);
                    rbScan3ArriveStamp.setDisable(true);
                }
                if (ps1.isContentVisaScan())
                {
                    /*
                 * if this scan contains the Visa page blocks the selection
                 * of Visa option for other scans
                     */
                    rbScan1Visa.setSelected(true);

                    rbScan2Visa.setDisable(true);
                    rbScan3Visa.setDisable(true);
                }
                if (ps1.isContentLastVisaExt())
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
                fieldsScan2.setStatusScan(true);

                tfScan2LeftPageNumber.setText(ps2.getPageNumber() + "");
                if (ps2.isContentArriveStamp())
                {
                    rbScan2ArriveStamp.setSelected(true);

                    rbScan1ArriveStamp.setDisable(true);
                    rbScan3ArriveStamp.setDisable(true);
                }
                if (ps2.isContentVisaScan())
                {
                    rbScan2Visa.setSelected(true);

                    rbScan1Visa.setDisable(true);
                    rbScan3Visa.setDisable(true);
                }
                if (ps2.isContentLastVisaExt())
                {
                    rbScan2LastVisaExt.setSelected(true);

                    rbScan1LastVisaExt.setDisable(true);
                    rbScan3LastVisaExt.setDisable(true);
                }
            }

            if (listPassportScans.size() >= 3)
            {
                ps3 = listPassportScans.get(2);
                fieldsScan3.setStatusScan(true);

                tfScan3LeftPageNumber.setText(ps3.getPageNumber() + "");
                if (ps3.isContentArriveStamp())
                {
                    rbScan3ArriveStamp.setSelected(true);

                    rbScan1ArriveStamp.setDisable(true);
                    rbScan2ArriveStamp.setDisable(true);
                }
                if (ps3.isContentVisaScan())
                {
                    rbScan3Visa.setSelected(true);

                    rbScan1Visa.setDisable(true);
                    rbScan2Visa.setDisable(true);
                }
                if (ps3.isContentLastVisaExt())
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

        ImgUtil.loadImageView(ivPassportScan, ImgUtil.IMG_TYPE_PASSPORT, fPassportScan);
        ImgUtil.loadImageView(ivDepartureCardScan, ImgUtil.IMG_TYPE_PASSPORT, fDepartureCard);
        ImgUtil.loadImageView(ivScan1, ImgUtil.IMG_TYPE_PASSPORT, fScan1);
        ImgUtil.loadImageView(ivScan2, ImgUtil.IMG_TYPE_PASSPORT, fScan2);
        ImgUtil.loadImageView(ivScan3, ImgUtil.IMG_TYPE_PASSPORT, fScan3);

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
            ps = listPassportScan.get(0);
            fImgScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
        }
        else if (me.getSource().equals(ivScan2))
        {
            ps = listPassportScan.get(1);
            fImgScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
        }
        else
        {
            ps = listPassportScan.get(2);
            fImgScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
        }

        ImgUtil.openClickedIMG(fImgScan);
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
    void actionChooseExtraScan(ActionEvent ae)
    {
        MonasticProfile p;
        PassportScan ps;
        int statusCopyOperation;
        File fScanDestination, fSelected;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans
        if (p.getPassportNumber() != null)
        {
            if (validateExtraScanContent((Button) ae.getSource()))
            {
                if (ae.getSource().equals(bScan1))
                {
                    ps = new PassportScan(p, parseInt(tfScan1LeftPageNumber.getText()), rbScan1ArriveStamp.isSelected(), rbScan1Visa.isSelected(), rbScan1LastVisaExt.isSelected());

                    ctrGUIMain.getCtrMain().getCtrPassportScan().addPassportScan(ps);

                }
                else if (ae.getSource().equals(bScan2))
                {
                    ps = new PassportScan(p, parseInt(tfScan2LeftPageNumber.getText()), rbScan2ArriveStamp.isSelected(), rbScan2Visa.isSelected(), rbScan2LastVisaExt.isSelected());

                    ctrGUIMain.getCtrMain().getCtrPassportScan().addPassportScan(ps);
                }
                else
                {
                    ps = new PassportScan(p, parseInt(tfScan3LeftPageNumber.getText()), rbScan3ArriveStamp.isSelected(), rbScan3Visa.isSelected(), rbScan3LastVisaExt.isSelected());

                    ctrGUIMain.getCtrMain().getCtrPassportScan().addPassportScan(ps);
                }
                fScanDestination = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);

                fSelected = CtrFileOperation.selectFile("Extra Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

                if (fSelected != null)
                {
                    statusCopyOperation = CtrFileOperation.copyOperation(fSelected, fScanDestination);

                    //if the operation was successful
                    //saves the scan content information as well
                    if (statusCopyOperation == 0)
                    {
                        loadIMGPreviews(p);
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
        else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }

    }

    private boolean validateExtraScanContent(Button sourceButton)
    {
        //if the page number is not empty and at least one of the options is selected
        //returns true
        if (sourceButton.equals(bScan1))
        {
            return validatePageNumber(tfScan1LeftPageNumber.getText())
                    && (rbScan1ArriveStamp.isSelected()
                    || rbScan1LastVisaExt.isSelected()
                    || rbScan1Visa.isSelected());
        }
        else if (sourceButton.equals(bScan2))
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

        bScan1.setDisable(true);
        bScan2.setDisable(true);
        bScan3.setDisable(true);

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
}
