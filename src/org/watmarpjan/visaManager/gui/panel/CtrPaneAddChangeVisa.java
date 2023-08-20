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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrLetterODF;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.control.formFiller.PrawatVisaChangeFiller;
import org.watmarpjan.visaManager.control.formFiller.TM86Filler;
import org.watmarpjan.visaManager.control.formFiller.TM87Filler;
import org.watmarpjan.visaManager.control.letterFiller.NewVisaReqLetterIMMFiller;
import org.watmarpjan.visaManager.control.letterFiller.NewVisaReqLetterSNPFiller;
import org.watmarpjan.visaManager.control.letterFiller.OrdinationGuaranteeLetterFiller;
import org.watmarpjan.visaManager.control.letterFiller.ResidenceGuaranteeLetterIMMNewVisaFiller;
import org.watmarpjan.visaManager.control.letterFiller.ResidenceGuaranteeLetterIMMVisaChangeFiller;
import org.watmarpjan.visaManager.control.letterFiller.VisaChangeReqLetterIMMFiller;
import org.watmarpjan.visaManager.control.letterFiller.VisaChangeReqLetterSNPFiller;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerVisaForm;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.LetterInputData;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddChangeVisa extends AChildPaneControllerVisaForm implements IFormMonasticProfile
{

    @FXML
    private TabPane  tpAddChangeVisa;
    
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
    private Button bPrawat;
    
    @FXML
    private Button bOrdinationGuaranteeLetter;
    
    @FXML
    private Button bMonasteryMap;
    
    @FXML
    private Button bTM30;
    
    @FXML
    private Button bAckPenaltiesOverstay;
    
    @FXML
    private Button bSTM2AckTermsAndConditions;
    
    @FXML
    private RadioButton rbVisaExemption;
    
    @FXML
    private RadioButton rbTourist;
    
    @FXML
    private ToggleGroup tgVisaType;
    
    @FXML
    private TitledPane tpTouristDocs;
    
    @FXML
    private TitledPane tpVisaExemptionDocs;
    
    @FXML
    private VBox vbSpecificDocs;
   
    @FXML
    private ToggleGroup tgVisaExemptionType;
    
    @FXML
    private RadioButton rbpoh15;
    
    @FXML
    private RadioButton rbpoh30;
    
    @FXML
    private RadioButton rbpohpoh30;
    
    @FXML
    private RadioButton rbpohpoh90;
    
    
    @Override
    public void init()
    {
        super.init();
        
        initButtonIcons();
        
        //refresh the GUI for visa type selection
        actionSelectVisaType(null);
        
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
        
        bTM87.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bNewVisaRequestLetterSNP.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaRequestLetterIMM.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bNewVisaResidenceGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        
        bPrawat.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bOrdinationGuaranteeLetter.setGraphic(new ImageView(AppPaths.getPathIconODT().toUri().toString()));
        bMonasteryMap.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bTM30.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bAckPenaltiesOverstay.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bSTM2AckTermsAndConditions.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
    }
    
    @FXML
    void actionSelectVisaType(ActionEvent ae)
    {
        //remove all child panes
        vbSpecificDocs.getChildren().clear();
        
        //and add the selected one
        if (rbTourist.isSelected())
        {
            vbSpecificDocs.getChildren().add(tpTouristDocs);
        }
        else
        {
            rbpoh30.setSelected(true);
            vbSpecificDocs.getChildren().add(tpVisaExemptionDocs);
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
    
    private String getSelectedVisaExemptionType()
    {
        RadioButton rbSelected;
        
        rbSelected = (RadioButton)tgVisaExemptionType.getSelectedToggle();
        return rbSelected.getText();
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
        String visaExemptionType, visaTypeOriginal;
        TM87Filler objFormFiller;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        
        //temporarily sets the visa type as the exemption type
        visaTypeOriginal = p.getVisaType();
        visaExemptionType = getSelectedVisaExemptionType();
        p.setVisaType(visaExemptionType);
        
        objFormFiller = new TM87Filler(ctrGUIMain.getCtrMain(), p);
        objFormFiller.saveAndOpenPDF();
        //resets back
        p.setVisaType(visaTypeOriginal);
    }
    
    @FXML
    void actionPreviewVisaChangeLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        VisaChangeReqLetterSNPFiller vcf;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        vcf = new VisaChangeReqLetterSNPFiller(p);
        vcf.saveAndOpenODT(p);
    }
    
    @FXML
    void actionPreviewVisaChangeLetterImm(ActionEvent ae)
    {
        MonasticProfile p;
        VisaChangeReqLetterIMMFiller vcf;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        vcf = new VisaChangeReqLetterIMMFiller(p);
        vcf.saveAndOpenODT(p);
    }
    
    @FXML
    void actionPreviewResidenceGuaranteeLetterTouristVisa(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterIMMVisaChangeFiller objLetterFiller;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        objLetterFiller = new ResidenceGuaranteeLetterIMMVisaChangeFiller(p);
        objLetterFiller.saveAndOpenODT(p);
    }
    
    @FXML
    void actionPreviewNewVisaLetterSNP(ActionEvent ae)
    {
        MonasticProfile p;
        NewVisaReqLetterSNPFiller nvf;
        String visaExemptionType, visaTypeOriginal;
        
        visaExemptionType = getSelectedVisaExemptionType();
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        visaTypeOriginal = p.getVisaType();
        
        //temporarily set the visa type as the visa extension
        p.setVisaType(visaExemptionType);
        nvf = new NewVisaReqLetterSNPFiller(p);
        nvf.saveAndOpenODT(p);
        //and change it back
        p.setVisaType(visaTypeOriginal);
    }
    
    @FXML
    void actionPreviewNewVisaLetterImm(ActionEvent ae)
    {
        MonasticProfile p;
        NewVisaReqLetterIMMFiller nvf;
        String visaExemptionType, visaTypeOriginal;
        
         
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        
        visaExemptionType = getSelectedVisaExemptionType();
        visaTypeOriginal = p.getVisaType();
        
        //temporarily set the visa type as the visa extension
        p.setVisaType(visaExemptionType);
       
        nvf = new NewVisaReqLetterIMMFiller(p);
        nvf.saveAndOpenODT(p);
        
        //and change it back
        p.setVisaType(visaTypeOriginal);
    }
    
    @FXML
    void actionPreviewResidenceGuaranteeLetterVisaExemption(ActionEvent ae)
    {
        MonasticProfile p;
        ResidenceGuaranteeLetterIMMNewVisaFiller objLetterFiller;
        String visaTypeOriginal, visaExemptionType;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        
         
        visaExemptionType = getSelectedVisaExemptionType();
        visaTypeOriginal = p.getVisaType();
        
        //temporarily set the visa type as the visa extension
        p.setVisaType(visaExemptionType);
        
        objLetterFiller = new ResidenceGuaranteeLetterIMMNewVisaFiller(p);
        objLetterFiller.saveAndOpenODT(p);
        
        p.setVisaType(visaTypeOriginal);
    }
    
    @FXML
    void actionPreviewPrawat(ActionEvent ae)
    {
        PrawatVisaChangeFiller pVCFiller;
        MonasticProfile p;
        String visaExemptionType, visaTypeOriginal;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        
        visaExemptionType = getSelectedVisaExemptionType();
        visaTypeOriginal = p.getVisaType();
        
        //temporarily set the visa type as the visa extension
        if (rbVisaExemption.isSelected())
        {
            p.setVisaType(visaExemptionType);    
        }       
        
        pVCFiller = new PrawatVisaChangeFiller(ctrGUIMain.getCtrMain(), p);
        pVCFiller.saveAndOpenPDF();
        
        //resets
        p.setVisaType(visaTypeOriginal);
    }
    
    @FXML
    void actionOrdinationGuaranteeLetter(ActionEvent ae)
    {
        OrdinationGuaranteeLetterFiller oLF;
        
        MonasticProfile p;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        oLF = new OrdinationGuaranteeLetterFiller(p);
        oLF.saveAndOpenODT(p);
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
