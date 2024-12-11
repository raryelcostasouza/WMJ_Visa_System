/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrLetterODF;
import org.watmarpjan.visaManager.control.formFiller.PrawatVisaChangeFiller;
import org.watmarpjan.visaManager.control.formFiller.TM86Filler;
import org.watmarpjan.visaManager.control.formFiller.TM87Filler;
import org.watmarpjan.visaManager.control.letterFiller.dhammaPracticeInstituteLetter.DhammaPracticeInsLettertVisaChangeFiller;
import org.watmarpjan.visaManager.control.letterFiller.dhammaPracticeInstituteLetter.DhammaPracticeInstLetterNewVisaThaiFiller;
import org.watmarpjan.visaManager.control.letterFiller.goodConductLetter.GoodConductLetterVisaChangeFiller;
import org.watmarpjan.visaManager.control.letterFiller.goodConductLetter.GoodConductNewVisaThaiFiller;
import org.watmarpjan.visaManager.control.letterFiller.reqLetter.NewVisaReqLetterIMMFiller;
import org.watmarpjan.visaManager.control.letterFiller.reqLetter.NewVisaReqLetterSNPFiller;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterFiller;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterNewVisaOrdainedAbroad;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterNewVisaOrdainedThailand;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterVisaChangeOrdainedAbroad;
import org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter.OrdinationGuaranteeLetterVisaChangeOrdainedThailand;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterIMMNewVisaFiller;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterIMMVisaChangeFiller;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPNewVisaFiller;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPVisaChangeFiller;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPFiller;
import org.watmarpjan.visaManager.control.letterFiller.reqLetter.VisaChangeReqLetterIMMFiller;
import org.watmarpjan.visaManager.control.letterFiller.reqLetter.VisaChangeReqLetterSNPFiller;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerVisaForm;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.LetterInputData;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddChangeVisa extends AChildPaneControllerVisaForm implements IFormMonasticProfile
{

    @FXML
    private TabPane tpAddChangeVisa;

    @FXML
    private TextField tfVisaNumber;

    @FXML
    private ComboBox<String> cbVisaType;

    @FXML
    private DatePicker dpVisaExpiryDate;

    @FXML
    private DatePicker dpNext90DayNotice;

    @FXML
    private Button bClear;
    @FXML
    private Button bRegister;

    @FXML
    private ComboBox<String> cbLetterType;

    @FXML
    private DatePicker dpDepartureDateFromThai;

    @FXML
    private ComboBox<String> cbEmbassy;

    @FXML
    private TextField tfMonasticPhoneAbroad;

    @FXML
    private TextField tfMonasticAddr1;

    @FXML
    private TextField tfMonasticAddr2;

    @FXML
    private TextField tfMonasticAddr3;

    @FXML
    private TextField tfMonasticAddr4;

    @FXML
    private BorderPane bpLetterFields;

    @FXML
    private TitledPane tbExtraFields;

    @FXML
    private VBox vbExtraFields;

    @FXML
    private GridPane gpDeparture;

    @FXML
    private GridPane gpEmbassy;

    @FXML
    private TitledPane tbMonasticAddr;

    @FXML
    private GridPane gpMonasticPhone;

    @FXML
    private Button bVisaChangeRequestLetterSNP;

    @FXML
    private Button bVisaChangeRequestLetterIMM;

    @FXML
    private Button bVisaChangeResidenceGuaranteeLetter;

    @FXML
    private Button bVisaChangeResidenceGuaranteeLetterIMM;

    @FXML
    private Button bTM86;

    @FXML
    private Button bTM87;

    @FXML
    private Button bNewVisaRequestLetterSNP;

    @FXML
    private Button bNewVisaRequestLetterIMM;

    @FXML
    private Button bNewVisaResidenceGuaranteeLetter;

    @FXML
    private Button bNewVisaResidenceGuaranteeLetterIMM;

    @FXML
    private Button bVisaChangePrawat;

    @FXML
    private Button bNewVisaPrawat;

    @FXML
    private Button bVisaChangeOrdinationGuaranteeLetter;

    @FXML
    private Button bNewVisaOrdinationGuaranteeLetter;

    @FXML
    private Button bMonasteryMap;

    @FXML
    private Button bMonasteryMap2;
    
    @FXML
    private Button bTM30;
    
    @FXML
    private Button bTM30_2;

    @FXML
    private Button bAckPenaltiesOverstay;
    
    @FXML
    private Button bAckPenaltiesOverstay2;

    @FXML
    private Button bSTM2AckTermsAndConditions;

    @FXML
    private Button bSTM2AckTermsAndConditions2;

    
    @FXML
    private TitledPane tpTouristDocs;

    @FXML
    private TitledPane tpVisaExemptionDocs;

    @FXML
    private VBox vbMainPane;

    @FXML
    private Button bNewVisaGoodConductGuaranteeLetter;

    @FXML
    private Button bVisaChangeGoodConductGuaranteeLetter;

    @FXML
    private Button bNewVisaDhammaPracticeInstGuaranteeLetter;

    @FXML
    private Button bVisaChangeDhammaPracticeInstGuaranteeLetter;

    @Override
    public void init()
    {
        super.init();

        initButtonIcons();

        //refresh the GUI for visa type selection
        setViewVisaType(null);

        cbVisaType.getItems().addAll(AppConstants.LIST_VISA_TYPES);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNext90DayNotice);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpVisaExpiryDate);

        tfVisaNumber.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                tfVisaNumber.setText(newValue.toUpperCase());
            }
        });

        tfMonasticAddr1.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if ((newValue != null) && (newValue.contains(",")))
                {
                    String strCommasRemoved;

                    //remove all commas
                    strCommasRemoved = newValue.replace(",", "");
                    tfMonasticAddr1.setText(strCommasRemoved);
                }
            }
        });
        tfMonasticAddr2.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if ((newValue != null) && (newValue.contains(",")))
                {
                    String strCommasRemoved;

                    //remove all commas
                    strCommasRemoved = newValue.replace(",", "");
                    tfMonasticAddr2.setText(strCommasRemoved);
                }
            }
        });
        tfMonasticAddr3.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if ((newValue != null) && (newValue.contains(",")))
                {
                    String strCommasRemoved;

                    //remove all commas
                    strCommasRemoved = newValue.replace(",", "");
                    tfMonasticAddr3.setText(strCommasRemoved);
                }
            }
        });
        tfMonasticAddr4.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if ((newValue != null) && (newValue.contains(",")))
                {
                    String strCommasRemoved;

                    //remove all commas
                    strCommasRemoved = newValue.replace(",", "");
                    tfMonasticAddr4.setText(strCommasRemoved);
                }
            }
        });

        tfMonasticPhoneAbroad.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if ((newValue != null) && (newValue.contains(",")))
                {
                    String strCommasRemoved;

                    //remove all commas
                    strCommasRemoved = newValue.replace(",", "");
                    tfMonasticPhoneAbroad.setText(strCommasRemoved);
                }
            }
        });

    }

    private void initButtonIcons()
    {
        bTM86.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bVisaChangeRequestLetterSNP.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bVisaChangeRequestLetterIMM.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bVisaChangeResidenceGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bVisaChangeResidenceGuaranteeLetterIMM.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bVisaChangeDhammaPracticeInstGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bVisaChangeGoodConductGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));

        bTM87.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bNewVisaRequestLetterSNP.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaRequestLetterIMM.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaResidenceGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaResidenceGuaranteeLetterIMM.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaDhammaPracticeInstGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaGoodConductGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));

        bNewVisaPrawat.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bVisaChangePrawat.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bVisaChangeOrdinationGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaOrdinationGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bMonasteryMap.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bMonasteryMap2.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        
        bTM30.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bTM30_2.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bAckPenaltiesOverstay.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bAckPenaltiesOverstay2.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
       
        bSTM2AckTermsAndConditions.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bSTM2AckTermsAndConditions2.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
    }

    private void setViewVisaType(MonasticProfile p)
    {
        //remove all child panes
        vbMainPane.getChildren().clear();

        if (p != null && p.getVisaType() != null)
        {
            //and add the correct one accordingly to monastic profile
            if (p.getVisaType().equals(AppConstants.VISA_TYPE_TOURIST))
            {
                vbMainPane.getChildren().add(tpTouristDocs);
            }
            else if (ProfileUtil.hasVisaExemption(p))
            {
                vbMainPane.getChildren().add(tpVisaExemptionDocs);
            }

        }
    }

    private void clearFieldsVisaLetters(boolean partialClear)
    {
        if (!partialClear)
        {
            cbLetterType.setValue(null);
        }

        dpDepartureDateFromThai.setValue(null);
        cbEmbassy.setValue(null);
        tfMonasticAddr1.setText("");
        tfMonasticAddr2.setText("");
        tfMonasticAddr3.setText("");
        tfMonasticAddr4.setText("");
        tfMonasticPhoneAbroad.setText("");

        bpLetterFields.setCenter(null);
    }

    private void initTabLetters(String[] letterList)
    {
        GUIUtil.loadContentComboboxGeneric(cbEmbassy, ctrGUIMain.getCtrMain().getCtrEmbassy().loadList());
        cbLetterType.getItems().clear();
        cbLetterType.getItems().addAll(letterList);
    }

    @FXML
    void actionClear(ActionEvent ae)
    {
        int opStatus;
        boolean confirmation;
        MonasticProfile p;

        confirmation = CtrAlertDialog.confirmationDialog("Clear", "The visa information (and its extensions) will be cleared. \nDo you want to continue?");
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            opStatus = ctrGUIMain.getCtrMain().getCtrVisa().clearVisaInfoForProfile(p);
            if (opStatus == 0)
            {
                fillData(p);
                CtrAlertDialog.infoDialog("Cleared successfully", "The previous visa information was cleared successfully.");
            }
        }
    }

    @FXML
    void actionRegister(ActionEvent ae)
    {
        MonasticProfile p;
        int operationStatus;

        if (validateFields())
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            //if the scan copy is sucessful
            p.setVisaNumber(tfVisaNumber.getText());
            p.setVisaType(cbVisaType.getValue());
            p.setVisaExpiryDate(Util.convertLocalDateToDate(dpVisaExpiryDate.getValue()));
            p.setNext90DayNotice(Util.convertLocalDateToDate(dpNext90DayNotice.getValue()));
            operationStatus = ctrGUIMain.getCtrMain().getCtrVisa().addNewVisaForProfile(p);

            //if the DB update is successful
            if (operationStatus == 0)
            {
                CtrAlertDialog.infoDialog("Visa info registered", "The new visa information was successfully registered.");
                fillData(p);
            }

        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill out ALL the visa fields before registering");
        }
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        clearFieldsVisaLetters(false);
        setViewVisaType(p);
        if (p != null)
        {
            if (p.getSamaneraOrdDate() != null || p.getBhikkhuOrdDate() != null)
            {
                initTabLetters(AppConstants.LIST_LETTER_MONASTIC);
            }
            else
            {
                initTabLetters(AppConstants.LIST_LETTER_LAYPERSON);
            }

            tfVisaNumber.setText(p.getVisaNumber());
            cbVisaType.setValue(p.getVisaType());
            dpVisaExpiryDate.setValue(ctrGUIMain.getCtrMain().getCtrVisa().getVisaOrExtExpiryDate(p));
            dpNext90DayNotice.setValue(Util.convertDateToLocalDate(p.getNext90DayNotice()));

            if (p.getVisaNumber() != null)
            {
                bClear.setDisable(false);
                bRegister.setDisable(true);

                tfVisaNumber.setEditable(false);
                cbVisaType.setDisable(true);
                dpVisaExpiryDate.setDisable(true);
                dpNext90DayNotice.setDisable(true);

            }
            else
            {
                bClear.setDisable(true);
                bRegister.setDisable(false);

                tfVisaNumber.setEditable(true);
                cbVisaType.setDisable(false);
                dpVisaExpiryDate.setDisable(false);
                dpNext90DayNotice.setDisable(false);
            }
        }
    }

    private boolean validateFields()
    {
        return ((!tfVisaNumber.getText().isEmpty())
                && (dpVisaExpiryDate.getValue() != null)
                && (cbVisaType.getValue() != null));
    }

    @FXML
    void actionPreviewFormTM86VisaChange(ActionEvent ae)
    {
        MonasticProfile p;
        TM86Filler objFormFiller;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objFormFiller = new TM86Filler(ctrGUIMain.getCtrMain(), p);
        objFormFiller.saveAndOpenPDF();
    }

    @FXML
    void actionPreviewFormTM87NewVisa(ActionEvent ae)
    {
        MonasticProfile p;
        TM87Filler objFormFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objFormFiller = new TM87Filler(ctrGUIMain.getCtrMain(), p);
        objFormFiller.saveAndOpenPDF();
    }

    @FXML
    void actionPreviewVisaChangeLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        VisaChangeReqLetterSNPFiller vcf;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        vcf = new VisaChangeReqLetterSNPFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        vcf.saveAndOpenODT();
    }

    @FXML
    void actionPreviewVisaChangeLetterImm(ActionEvent ae)
    {
        MonasticProfile p;
        VisaChangeReqLetterIMMFiller vcf;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        vcf = new VisaChangeReqLetterIMMFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        vcf.saveAndOpenODT();
    }

    @FXML
    void actionPreviewResidenceGuaranteeLetterIMMTouristVisa(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterIMMVisaChangeFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        objLetterFiller = new ResidenceGuaranteeLetterIMMVisaChangeFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

    @FXML
    void actionPreviewResidenceGuaranteeLetterSNPTouristVisa(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterSNPFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        objLetterFiller = new ResidenceGuaranteeLetterSNPVisaChangeFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

    @FXML
    void actionPreviewResidenceGuaranteeLetterSNPVisaExemption(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterSNPFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        objLetterFiller = new ResidenceGuaranteeLetterSNPNewVisaFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

    @FXML
    void actionPreviewNewVisaLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        NewVisaReqLetterSNPFiller nvf;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        nvf = new NewVisaReqLetterSNPFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        nvf.saveAndOpenODT();
    }

    @FXML
    void actionPreviewNewVisaLetterImm(ActionEvent ae)
    {
        MonasticProfile p;
        NewVisaReqLetterIMMFiller nvf;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        nvf = new NewVisaReqLetterIMMFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        nvf.saveAndOpenODT();

    }

    @FXML
    void actionPreviewResidenceGuaranteeLetterIMMVisaExemption(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterIMMNewVisaFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new ResidenceGuaranteeLetterIMMNewVisaFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }
    
    @FXML
    void actionPreviewGoodConductLetterVisaChange(ActionEvent ae)
    {
        MonasticProfile p;
        GoodConductLetterVisaChangeFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new GoodConductLetterVisaChangeFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }
    
    @FXML
    void actionPreviewGoodConductLetterNewVisa(ActionEvent ae)
    {
        MonasticProfile p;
        GoodConductNewVisaThaiFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new GoodConductNewVisaThaiFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }
    
    @FXML
    void actionPreviewDhammaPracticeInstLetterNewVisa(ActionEvent ae)
    {
        MonasticProfile p;
        DhammaPracticeInstLetterNewVisaThaiFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new DhammaPracticeInstLetterNewVisaThaiFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }
    
    @FXML
    void actionPreviewDhammaPracticeInstLetterVisaChange(ActionEvent ae)
    {
        MonasticProfile p;
        DhammaPracticeInsLettertVisaChangeFiller objLetterFiller;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        objLetterFiller = new DhammaPracticeInsLettertVisaChangeFiller(p, ctrGUIMain.getCtrMain().getCtrConfig());
        objLetterFiller.saveAndOpenODT();
    }

    @FXML
    void actionPreviewPrawat(ActionEvent ae)
    {
        PrawatVisaChangeFiller pVCFiller;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        pVCFiller = new PrawatVisaChangeFiller(ctrGUIMain.getCtrMain(), p);
        pVCFiller.saveAndOpenPDF();

    }

    @FXML
    void actionOrdinationGuaranteeLetterVisaChange(ActionEvent ae)
    {
        actionOrdinationGuaranteeLetter(false);
    }

    @FXML
    void actionOrdinationGuaranteeLetterNewVisa(ActionEvent ae)
    {
        actionOrdinationGuaranteeLetter(true);
    }

    private void actionOrdinationGuaranteeLetter(boolean isNewVisa)
    {
        OrdinationGuaranteeLetterFiller oLF;
        MonasticProfile p;
        boolean isOrdainedInThai;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p.getMonasteryOrdainedAt() != null)
        {
            isOrdainedInThai = ProfileUtil.isOrdainedInThailand(p);
            if (isOrdainedInThai && !isNewVisa)
            {
                oLF = new OrdinationGuaranteeLetterVisaChangeOrdainedThailand(p, ctrGUIMain.getCtrMain().getCtrConfig());
            }
            else if (!isOrdainedInThai && !isNewVisa)
            {
                oLF = new OrdinationGuaranteeLetterVisaChangeOrdainedAbroad(p, ctrGUIMain.getCtrMain().getCtrConfig());
            }
            else if (isOrdainedInThai && isNewVisa)
            {
                oLF = new OrdinationGuaranteeLetterNewVisaOrdainedThailand(p, ctrGUIMain.getCtrMain().getCtrConfig());
            }
            else
            {
                oLF = new OrdinationGuaranteeLetterNewVisaOrdainedAbroad(p, ctrGUIMain.getCtrMain().getCtrConfig());

            }

            oLF.saveAndOpenODT();
        }
        else
        {
            CtrAlertDialog.errorDialog("Unordained person (empty value for Monastery Ordained at). Unable to generate letter");
        }
    }

    @FXML
    void actionMonasteryMap(ActionEvent ae)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().getMonasteryMap(p);
    }

    @FXML
    void listenerCBLetter(ActionEvent ae)
    {
        String strLetterSelected;

        clearFieldsVisaLetters(true);
        strLetterSelected = cbLetterType.getValue();
        if (strLetterSelected != null)
        {
            switch (strLetterSelected)
            {
                case AppConstants.LETTER_LAYPERSON_ABROAD_EMBASSY:
                    bpLetterFields.setCenter(null);
                    vbExtraFields.getChildren().clear();
                    break;
                case AppConstants.LETTER_LAYPERSON_ABROAD_EMBASSY_EN:
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    break;
                case AppConstants.LETTER_LAYPERSON_THAILAND_VIENTIANE_EMBASSY:
                    bpLetterFields.setCenter(null);
                    vbExtraFields.getChildren().clear();
                    break;

                case AppConstants.LETTER_MONASTIC_ABROAD_EMBASSY:
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    vbExtraFields.getChildren().add(gpDeparture);
                    break;
                case AppConstants.LETTER_MONASTIC_ABROAD_EMBASSY_EN:
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    break;
                case AppConstants.LETTER_MONASTIC_ABROAD_SAMNAKPUT:
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    vbExtraFields.getChildren().add(tbMonasticAddr);
                    vbExtraFields.getChildren().add(gpMonasticPhone);
                    vbExtraFields.getChildren().add(gpDeparture);
                    tpAddChangeVisa.requestLayout();
                    break;
            }
        }
    }

    private boolean requireEmbassySelected()
    {
        return (cbEmbassy.getValue() != null);
    }

    private boolean requireContactInfoAbroad()
    {
        return (!tfMonasticAddr1.getText().isEmpty())
                && (!tfMonasticAddr2.getText().isEmpty())
                && (!tfMonasticAddr3.getText().isEmpty())
                && (!tfMonasticPhoneAbroad.getText().isEmpty());
    }

    private boolean requireDepartureDateThai()
    {
        return (dpDepartureDateFromThai.getValue() != null);
    }

    private boolean validateLetterInput(String strLetterSelected)
    {
        switch (strLetterSelected)
        {
            case AppConstants.LETTER_LAYPERSON_ABROAD_EMBASSY:
                return true;
            case AppConstants.LETTER_LAYPERSON_ABROAD_EMBASSY_EN:
                return requireEmbassySelected();
            case AppConstants.LETTER_LAYPERSON_THAILAND_VIENTIANE_EMBASSY:
                return true;

            case AppConstants.LETTER_MONASTIC_ABROAD_EMBASSY:
                return requireEmbassySelected() && requireDepartureDateThai();
            case AppConstants.LETTER_MONASTIC_ABROAD_EMBASSY_EN:
                return requireEmbassySelected();
            case AppConstants.LETTER_MONASTIC_ABROAD_SAMNAKPUT:
                return requireEmbassySelected() && requireContactInfoAbroad() && requireDepartureDateThai();
            default:
                return false;
        }

    }

    @FXML
    void actionGenerateLetter(ActionEvent ae)
    {
        String strLetterSelected, strSelectedEmbassy;
        MonasticProfile p;
        Embassy e;
        LetterInputData objLetterInput;
        File fTemplateODT;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        strLetterSelected = cbLetterType.getValue();
        if (validateLetterInput(strLetterSelected))
        {
            strSelectedEmbassy = cbEmbassy.getValue();
            if (cbEmbassy.getValue() != null)
            {
                e = ctrGUIMain.getCtrMain().getCtrEmbassy().loadByName(strSelectedEmbassy);
            }
            else
            {
                e = null;
            }

            objLetterInput = new LetterInputData(p, e);
            objLetterInput.setAddrMonasticLine1(tfMonasticAddr1.getText());
            objLetterInput.setAddrMonasticLine2(tfMonasticAddr2.getText());
            objLetterInput.setAddrMonasticLine3(tfMonasticAddr3.getText());
            objLetterInput.setAddrMonasticLine4(tfMonasticAddr4.getText());
            objLetterInput.setPhoneAbroad(tfMonasticPhoneAbroad.getText());
            objLetterInput.setLdDepartureDateThai(dpDepartureDateFromThai.getValue());

            fTemplateODT = AppFiles.getODTNewVisaLetter(p.getMonasteryResidingAt(), strLetterSelected);
            CtrLetterODF.generateLetterGeneric(fTemplateODT, p, objLetterInput, null);
        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill all the required fields!");
        }

    }
//    @FXML
//    void actionPrintFormTM86VisaChange(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM86VisaChange(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
}
