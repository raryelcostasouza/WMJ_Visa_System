/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.io.File;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import java.time.LocalDate;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppFileNames;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrLetterODF;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.control.formFiller.PrawatVisaExtFiller;
import org.watmarpjan.visaManager.control.letterFiller.dhammaPracticeInstituteLetter.DhammaPracticeInsLettertVisaExtFiller;
import org.watmarpjan.visaManager.control.letterFiller.goodConductLetter.GoodConductLetterVisaExtFiller;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterFiller;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterVisaChangeOrdainedAbroad;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterVisaChangeOrdainedThailand;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterVisaExtOrdainedAbroad;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterVisaExtOrdainedThailand;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPVisaExtFiller;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerVisaForm;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneVisaExt extends AChildPaneControllerVisaForm implements IFormMonasticProfile
{

    @FXML
    private TextField tfParentVisaNumber;

    @FXML
    private TableView<EntryVisaExt> tvExtensions;

    @FXML
    private TextField tfExtNumber;

    @FXML
    private DatePicker dpExpiryDate;
    @FXML
    private Button bRegister;

    @FXML
    private Button bPreview1;
    @FXML
    private Button bPreview2;
    @FXML
    private Button bPreview3;
    @FXML
    private Button bPreview4;
    @FXML
    private Button bPreview5;
    @FXML
    private Button bPreview6;
    @FXML
    private Button bPreview7;
    @FXML
    private Button bPreview8;

    @FXML
    private Button bPreview9;

    @FXML
    private Button bPreview10;

    @FXML
    private Button bPreview11;


//    @FXML
//    private Button bPrint1;
//    @FXML
//    private Button bPrint2;
//    @FXML
//    private Button bPrint3;
//    @FXML
//    private Button bPrint4;
//    @FXML
//    private Button bPrint5;
//    @FXML
//    private Button bPrint6;
//    @FXML
//    private Button bPrint7;
    @Override
    public void init()
    {
        TableColumn<EntryVisaExt, String> tc;

        bPreview1.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPreview2.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bPreview3.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bPreview4.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPreview5.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPreview6.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPreview7.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPreview8.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bPreview9.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bPreview10.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bPreview11.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));

//        bPrint1.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
//        bPrint2.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
//        bPrint3.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
//        bPrint4.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
//        bPrint5.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
//        bPrint6.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
//        bPrint7.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpExpiryDate);

        //code for inserting a remove button on the last column
        Callback<TableColumn<EntryVisaExt, String>, TableCell<EntryVisaExt, String>> removeColumnCellFactory
                = new Callback<TableColumn<EntryVisaExt, String>, TableCell<EntryVisaExt, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryVisaExt, String> param)
            {
                final TableCell<EntryVisaExt, String> cell = new TableCell<EntryVisaExt, String>()
                {

                    final Button btn = new Button("");
                    final ImageView ivRemove = new ImageView(AppPaths.getPathToIconSubfolder().resolve("remove.png").toUri().toString());

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            btn.setGraphic(ivRemove);
                            btn.setOnAction(new EventHandler<ActionEvent>()
                            {
                                @Override
                                public void handle(ActionEvent event)
                                {
                                    actionRemoveVisaExt(event, getIndex());
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        tvExtensions.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("extNumber"));
        tvExtensions.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        tc = (TableColumn<EntryVisaExt, String>) tvExtensions.getColumns().get(2);

        tc.setCellValueFactory(new PropertyValueFactory<>("action"));
        tc.setCellFactory(removeColumnCellFactory);
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        ArrayList<EntryVisaExt> alVisaExtensions;
        LocalDate ldExpVisa, ldExpLastExtension;
        Date dLastExt;
        VisaExtension lastExt;
        //String levelDhammaStudies;

        if (p != null)
        {
            if (p.getVisaNumber() != null)
            {
                tfParentVisaNumber.setText(p.getVisaNumber());

                tfExtNumber.setEditable(true);
                dpExpiryDate.setDisable(false);

                bRegister.setDisable(false);
            }
            else
            {
                tfParentVisaNumber.setText("");
                tfExtNumber.setEditable(false);
                dpExpiryDate.setDisable(true);

                bRegister.setDisable(true);
            }

            tfExtNumber.setText("");

            alVisaExtensions = ctrGUIMain.getCtrMain().getCtrVisa().loadListExtensions(p.getIdProfile());
            tvExtensions.getItems().clear();
            tvExtensions.getItems().addAll(alVisaExtensions);

            //pre-set the expiry date for the next extension
            //1) If the visa has extensions -> 1 Year after the last extension expiry date
            lastExt = ctrGUIMain.getCtrMain().getCtrVisa().getLastExtension(p);
            if (lastExt != null)
            {
                dLastExt = lastExt.getExpiryDate();
                ldExpLastExtension = Util.convertDateToLocalDate(dLastExt);
                dpExpiryDate.setValue(ldExpLastExtension.plusYears(1));
            }
            //2) If the visa has not been extended yet -> 1 Year after the visa expiry
            else if (p.getVisaExpiryDate() != null)
            {
                ldExpVisa = Util.convertDateToLocalDate(p.getVisaExpiryDate());
                dpExpiryDate.setValue(ldExpVisa.plusYears(1));
            }
            else
            {
                dpExpiryDate.setValue(null);
            }

            //only enable certificate button if file exists and dhamma studies is set to something else than 
            //REGULAR
            //removed at v1.17
            /*levelDhammaStudies = p.getDhammaStudies();
            if ((levelDhammaStudies.compareTo(AppConstants.STUDIES_REGULAR) == 0)
                    || (!AppFiles.getNaktamCertificate(p.getNickname(), levelDhammaStudies).exists()))
            {
                bPreviewNaktamCertificate.setDisable(true);
            }
            else
            {
                bPreviewNaktamCertificate.setDisable(false);
            }*/
        }

    }

    @FXML
    void actionRegisterVisaExt(ActionEvent ae)
    {
        MonasticProfile p;
        VisaExtension vExt;
        int operationStatus;

        if (validateFields())
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            vExt = new VisaExtension();
            vExt.setExtNumber(tfExtNumber.getText());
            vExt.setExpiryDate(Util.convertLocalDateToDate(dpExpiryDate.getValue()));
            vExt.setMonasticProfile(p);

            operationStatus = ctrGUIMain.getCtrMain().getCtrVisa().createVisaExt(vExt);

            //if the DB update is successful
            if (operationStatus == 0)
            {
                CtrAlertDialog.infoDialog("Extension info registered", "The visa extension was successfully registered.");
                ctrGUIMain.getCtrMain().getCtrProfile().refresh(p);
                fillData(p);
            }
        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill out ALL the visa extension fields before registering");
        }
    }

    private void actionRemoveVisaExt(ActionEvent ae, int selectedRowIndex)
    {
        EntryVisaExt selectedEntry;
        VisaExtension vExt;
        int operationStatus;
        boolean confirmation;
        String msg;

        selectedEntry = tvExtensions.getItems().get(selectedRowIndex);
        vExt = ctrGUIMain.getCtrMain().getCtrVisa().loadVisaExtensionByNumber(selectedEntry.getExtNumber());

        msg = "Are you sure that you want to remove the following visa extension?\n"
                + "Extension Number: " + selectedEntry.getExtNumber() + "\n"
                + "Expiry Date: " + selectedEntry.getLdExpiryDate().toString();

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", msg);
        if (confirmation)
        {
            operationStatus = ctrGUIMain.getCtrMain().getCtrVisa().removeVisaExt(vExt);

            if (operationStatus == 0)
            {
                tvExtensions.getItems().remove(selectedEntry);
            }
        }
    }

    public boolean validateFields()
    {
        return (!tfExtNumber.getText().isEmpty())
                && (dpExpiryDate.getValue() != null);
    }

    @FXML
    void actionPreviewPrawat(ActionEvent ae)
    {
        PrawatVisaExtFiller pVextFiller;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        pVextFiller = new PrawatVisaExtFiller(ctrGUIMain.getCtrMain(), p);
        pVextFiller.saveAndOpenPDF();
    }

    @FXML
    void actionPreviewNaktamCertificate(ActionEvent ae)
    {
        MonasticProfile p;
        String levelDhammaStudies;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        levelDhammaStudies = p.getDhammaStudies();
        CtrFileOperation.openFileOnDefaultProgram(AppFiles.getNaktamCertificate(p.getNickname(), levelDhammaStudies));
    }

    @FXML
    void actionPreviewLetterSamnakput(ActionEvent ae)
    {
        //old version for PDF form
        //MonasticProfile p;
        //p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getExtReqLetterSNP(), p, CtrPDF.OPTION_PREVIEW_FORM, false);

        actionPreviewLetterODTGeneric(AppFileNames.ODT_LETTER_EXT_SNP);
    }

    @FXML
    void actionPreviewLetterImmigration(ActionEvent ae)
    {
        //old version for PDF form
        //MonasticProfile p;
        //p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getExtReqLetterIMM(), p, CtrPDF.OPTION_PREVIEW_FORM, false);

        actionPreviewLetterODTGeneric(AppFileNames.ODT_LETTER_EXT_IMM);
    }

    private void actionPreviewLetterODTGeneric(String letterFilename)
    {
        File fLetter;
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        fLetter = AppFiles.getODTVisaExtLetterGeneric(p.getMonasteryResidingAt(), letterFilename);
        CtrLetterODF.generateLetterGeneric(fLetter, p, null, ctrGUIMain.getCtrMain().getCtrVisa());
    }

    @FXML
    void actionPreviewTM7ExtRequest(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM7ReqExtension(p.getMonasteryResidingAt().getMonasteryNickname()), p, CtrPDF.OPTION_PREVIEW_FORM, false);
    }

    @FXML
    void actionPreviewResidenceGuaranteeLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterSNPVisaExtFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        objLetterFiller = new ResidenceGuaranteeLetterSNPVisaExtFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

    @FXML
    void actionPreviewOrdinationGuaranteeLetterSNP(ActionEvent ae)
    {
        OrdinationGuaranteeLetterFiller oLF;
        MonasticProfile p;
        boolean isOrdainedInThai;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p.getMonasteryOrdainedAt() != null)
        {
            isOrdainedInThai = ProfileUtil.isOrdainedInThailand(p);
            if (isOrdainedInThai)
            {
                oLF = new OrdinationGuaranteeLetterVisaExtOrdainedThailand(p, ctrGUIMain.getCtrMain().getCtrConfig());
            }
            else
            {
                oLF = new OrdinationGuaranteeLetterVisaExtOrdainedAbroad(p, ctrGUIMain.getCtrMain().getCtrConfig());
            }

            oLF.saveAndOpenODT();
        }
        else
        {
            CtrAlertDialog.errorDialog("Unordained person (empty value for Monastery Ordained at). Unable to generate letter");
        }
    }

    @FXML
    void actionPreviewDhammaPracticeInstituteGuaranteeLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        DhammaPracticeInsLettertVisaExtFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new DhammaPracticeInsLettertVisaExtFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

    @FXML
    void actionPreviewGoodConductGuaranteeLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        GoodConductLetterVisaExtFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new GoodConductLetterVisaExtFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

//    @FXML
//    void actionPrintPrawat(ActionEvent ae)
//    {
//        File fPrawatTemplate;
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        
//        fPrawatTemplate = getPrawatTemplate(p);
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(fPrawatTemplate, p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
//
//    @FXML
//    void actionPrintLetterSamnakput(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getExtReqLetterSNP(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
//
//    @FXML
//    void actionPrintLetterImmigration(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getExtReqLetterIMM(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
//
//    @FXML
//    void actionPrintTM7ExtRequest(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM7ReqExtension(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
//
//    @FXML
//    void actionPrintTM30NotifResidence(ActionEvent ae)
//    {
//        MonasticProfile p;
//        PrintoutTm30 objTM30;
//
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        objTM30 = p.getPrintoutTm30();
//
//        if (objTM30 != null)
//        {
//            ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getPrintoutTM30(objTM30), p, CtrPDF.OPTION_PRINT_FORM, false);
//        }
//        else
//        {
//            CtrAlertDialog.errorDialog("No TM30 Printout registered for this monastic profile yet.");
//        }
//    }
//
//    @FXML
//    void actionPrintAckOverstayPenalties(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormOverstay(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
//
//    @FXML
//    void actionPrintSTM2AckConditions(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormSTM2AckConditions(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
}
