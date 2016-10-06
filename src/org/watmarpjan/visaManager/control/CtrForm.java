/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import org.apache.pdfbox.cos.COSName;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.printing.PDFPrintable;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.Profile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrForm
{
    
    public static final int OPTION_PRINT_FORM = 0;
    public static final int OPTION_PREVIEW_FORM = 1;
    
    public static final String DESTINATION_SAMNAKPUT = "SNP";
    public static final String DESTINATION_IMMIGRATION = "IMM";
    
    private static final String MSG_ERROR = "Error while generating PDF form.";
    
    private static COSName loadedThaiFontName = null;
    
    private static void fillTM7ReqExtension(PDAcroForm acroForm, Profile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        LocalDate ldPassportIssue, ldPassportExp, ldBirthDate, ldLastEntry;
        Monastery mResidingAt;
        
        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrNumberWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
        
        adjustFontThaiField(alThaiFields);
        
        acroForm.getField("titleThai").setValue(ProfileUtil.getTitle(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getName());
        if (p.getMiddleName() != null)
        {
            acroForm.getField("middleName").setValue(p.getMiddleName());
        }
        
        acroForm.getField("age").setValue(ProfileUtil.getAge(p.getBirthDate()) + "");
        
        ldBirthDate = Util.convertDateToLocalDate(p.getBirthDate());
        if (ldBirthDate != null)
        {
            acroForm.getField("birthDateDay").setValue(ldBirthDate.getDayOfMonth() + "");
            acroForm.getField("birthDateMonth").setValue(ldBirthDate.getMonthValue() + "");
            acroForm.getField("birthDateYear").setValue(Util.convertYearToThai(ldBirthDate.getYear()) + "");
        }
        
        acroForm.getField("birthPlace_birthCountry").setValue(p.getBirthPlace() + " " + p.getBirthCountry());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        
        ldPassportIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
        acroForm.getField("passportIssueDateDay").setValue(ldPassportIssue.getDayOfMonth() + "");
        acroForm.getField("passportIssueDateMonth").setValue(ldPassportIssue.getMonthValue() + "");
        acroForm.getField("passportIssueDateYear").setValue(Util.convertYearToThai(ldPassportIssue.getYear()) + "");
        acroForm.getField("passportIssuedAt").setValue(p.getPassportIssuedAt());
        
        ldPassportExp = Util.convertDateToLocalDate(p.getPassportExpiryDate());
        if (ldPassportExp != null)
        {
            acroForm.getField("passportExpiryDateDay").setValue(ldPassportExp.getDayOfMonth() + "");
            acroForm.getField("passportExpiryDateMonth").setValue(ldPassportExp.getMonthValue() + "");
            acroForm.getField("passportExpiryDateYear").setValue(Util.convertYearToThai(ldPassportExp.getYear()) + "");
        }
        
        acroForm.getField("visaType").setValue(p.getVisaType());
        acroForm.getField("arrivalTravelBy").setValue(p.getArrivalTravelBy());
        acroForm.getField("arrivalTravelFrom").setValue(p.getArrivalTravelFrom());
        acroForm.getField("arrivalPortOfEntry").setValue(p.getArrivalPortOfEntry());
        
        ldLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
        if (ldLastEntry != null)
        {
            acroForm.getField("arrivalLastEntryDateDay").setValue(ldLastEntry.getDayOfMonth() + "");
            acroForm.getField("arrivalLastEntryDateMonth").setValue(ldLastEntry.getMonthValue() + "");
            acroForm.getField("arrivalLastEntryDateYear").setValue(Util.convertYearToThai(ldLastEntry.getYear()) + "");
        }
        
        acroForm.getField("departureCardNumber").setValue(p.getArrivalCardNumber());
        //acroForm.getField("extensionPeriod").setValue();

        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getName());
            acroForm.getField("addrNumberWatResidingAtThai").setValue(mResidingAt.getAddrNumber());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
            
        }
    }
    
    private static void fillFormTM47_90DayNotice(PDAcroForm acroForm, Profile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        LocalDate ldArrival;
        Monastery mResidingAt;
        
        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("WatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
        adjustFontThaiField(alThaiFields);
        
        acroForm.getField("titleThai").setValue(ProfileUtil.getTitle(p));
        acroForm.getField("fullName").setValue(ProfileUtil.getFullName(p));
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("departureCardNumber").setValue(p.getArrivalCardNumber());
        acroForm.getField("arrivalTravelBy").setValue(p.getArrivalTravelBy());
        
        if (p.getVisaType().equals("Tourist"))
        {
            ((PDCheckBox) acroForm.getField("TOURIST")).check();
            ((PDCheckBox) acroForm.getField("NONIMM")).unCheck();
        }
        
        ldArrival = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
        
        if (ldArrival != null)
        {
            acroForm.getField("arrivalLastEntryDateDay").setValue(ldArrival.getDayOfMonth() + "");
            acroForm.getField("arrivalLastEntryDateMonth").setValue(ldArrival.getMonthValue() + "");
            acroForm.getField("arrivalLastEntryDateYear").setValue(Util.convertYearToThai(ldArrival.getYear()) + "");
        }
        
        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
            
            acroForm.getField("WatResidingAtThai").setValue(mResidingAt.getName());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
            acroForm.getField("addrPhoneWatResidingAtThai").setValue(mResidingAt.getPhoneNumber());
        }
        
    }
    
    public static void fillFormSTM2AckConditions2(PDAcroForm acroForm, Profile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        
        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        adjustFontThaiField(alThaiFields);
        
        acroForm.getField("titleThai").setValue(ProfileUtil.getTitle(p));
        acroForm.getField("fullName").setValue(ProfileUtil.getFullName(p));
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("age").setValue(ProfileUtil.getAge(p.getBirthDate()) + "");
        
    }
    
    public static void fillForm(File sourceFile, Profile p, int option)
    {
        PDDocument pdfDocument;
        PDAcroForm acroForm;
        PDFont font;
        File outputFile;
        
        outputFile = AppFiles.getFormTMPOutputPDF(sourceFile.getName());

        // load the document
        try
        {
            pdfDocument = PDDocument.load(sourceFile);
            //load the thai font
            font = PDType0Font.load(pdfDocument, AppFiles.getThaiFont());

            // get the document catalog
            acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            loadedThaiFontName = acroForm.getDefaultResources().add(font);
            
            System.out.println("Loaded Font: " + loadedThaiFontName.getName());
            
            if (sourceFile.getName().equals(AppFiles.getFormSTM2AckConditions().getName()))
            {
                fillFormSTM2AckConditions2(acroForm, p);
            } else if (sourceFile.getName().equals(AppFiles.getFormOverstay().getName()))
            {
                fillFormAckOverstayPenalties(acroForm, p);
            } else if (sourceFile.getName().equals(AppFiles.getFormTM47Notice90Day().getName()))
            {
                fillFormTM47_90DayNotice(acroForm, p);
            } else if (sourceFile.getName().equals(AppFiles.getExtReqLetterSNP().getName()))
            {
                fillExtReqLetter(acroForm, p, DESTINATION_SAMNAKPUT);
            } else if (sourceFile.getName().equals(AppFiles.getExtReqLetterIMM().getName()))
            {
                fillExtReqLetter(acroForm, p, DESTINATION_IMMIGRATION);
            } else if (sourceFile.getName().equals(AppFiles.getFormTM7ReqExtension().getName()))
            {
                fillTM7ReqExtension(acroForm, p);
            }

            // Save and close the filled out form.
            pdfDocument.save(outputFile);
            pdfDocument.close();
            
            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDocument);
            } else
            {
                openPDFOnDefaultProgram(outputFile);
            }
            
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, MSG_ERROR);
        }
    }
    
    public static void fillFormAckOverstayPenalties(PDAcroForm acroForm, Profile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        
        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        adjustFontThaiField(alThaiFields);
        
        acroForm.getField("titleThai").setValue(ProfileUtil.getTitle(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getName());
        acroForm.getField("middleName").setValue(p.getMiddleName());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("age").setValue(ProfileUtil.getAge(p.getBirthDate()) + "");
        
    }
    
    public static void fillExtReqLetter(PDAcroForm acroForm, Profile p, String destination) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        String strFullName, strTitle, strMOrdainedAt;
        Monastery mOrdainedAt, mResidingAt;
        Date dVisaExpiry, dArrivalLastEntry;
        LocalDate ldVisaExpiry, ldNewExpiryDate, ldExtensionExpiry;
        VisaExtension lastExt;
        int extensionsCount;
        
        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai1"));
        alThaiFields.add((PDTextField) acroForm.getField("titleThai2"));
        if (destination.equals(DESTINATION_SAMNAKPUT))
        {
            alThaiFields.add((PDTextField) acroForm.getField("titleThai3"));
        }
        alThaiFields.add((PDTextField) acroForm.getField("ordinationTypeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("WatOrdainedAtThai_addrAmpher_addrJangwat_addrCountry"));
        alThaiFields.add((PDTextField) acroForm.getField("WatResidingAtThai_addrAmpher_addrJangwat"));
        
        adjustFontThaiField(alThaiFields);
        strTitle = ProfileUtil.getTitle(p);
        strFullName = ProfileUtil.getFullName(p);
        mOrdainedAt = p.getMonasteryOrdainedAt();
        mResidingAt = p.getMonasteryResidingAt();
        
        acroForm.getField("currentDate").setValue(Util.toStringThaiDateFormat(LocalDate.now()));
        acroForm.getField("titleThai1").setValue(strTitle);
        acroForm.getField("titleThai2").setValue(strTitle);
        
        acroForm.getField("fullName1").setValue(strFullName);
        acroForm.getField("fullName2").setValue(strFullName);
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("ordinationTypeThai").setValue(ProfileUtil.getOrdinationType(p));
        acroForm.getField("ordinationDate").setValue(ProfileUtil.getStrOrdinationDate(p));
        
        dArrivalLastEntry = p.getArrivalLastEntryDate();
        if (dArrivalLastEntry != null)
        {
            acroForm.getField("arrivalLastEntryDate").setValue(Util.toStringThaiDateFormat(dArrivalLastEntry));
        }
        
        dVisaExpiry = p.getVisaExpiryDate();
        if (dVisaExpiry != null)
        {
            ldVisaExpiry = Util.convertDateToLocalDate(dVisaExpiry);
            acroForm.getField("visaExpiryDate").setValue(Util.toStringThaiDateFormat(ldVisaExpiry));

            //if there are visa extensions 
            if (p.getVisaExtensionSet() != null && !p.getVisaExtensionSet().isEmpty())
            {
                ArrayList<VisaExtension> listExt = new ArrayList<>();
                listExt.addAll(p.getVisaExtensionSet());
                lastExt = listExt.get(listExt.size() - 1);
                ldExtensionExpiry = Util.convertDateToLocalDate(lastExt.getExpiryDate());

                //the desired expiry date is one year from the last extension
                ldNewExpiryDate = ldExtensionExpiry.plusYears(1);
                
            } else
            {
                //if there are no extensions
                //the desired expiry date is one year from the visa expiry
                ldNewExpiryDate = ldVisaExpiry.plusYears(1);
            }
            acroForm.getField("visaExpiryDateDesired").setValue(Util.toStringThaiDateFormat(ldNewExpiryDate));
        }
        
        if (mOrdainedAt != null)
        {
            if (mOrdainedAt.getAddrCountry().equals("THAILAND"))
            {
                strMOrdainedAt = mOrdainedAt.getName() + " อ." + mOrdainedAt.getAddrAmpher() + " จ." + mOrdainedAt.getAddrJangwat();
                
            } else
            {
                strMOrdainedAt = mOrdainedAt.getName() + " " + mOrdainedAt.getAddrAmpher() + " " + mOrdainedAt.getAddrJangwat() + " " + mOrdainedAt.getAddrCountry();
            }
            acroForm.getField("WatOrdainedAtThai_addrAmpher_addrJangwat_addrCountry").setValue(strMOrdainedAt);
        }
        
        if (mResidingAt != null)
        {
            if (mResidingAt.getAddrCountry().equals("THAILAND"))
            {
                strMOrdainedAt = mResidingAt.getName() + " อ." + mResidingAt.getAddrAmpher() + " จ." + mResidingAt.getAddrJangwat();
                
            } else
            {
                strMOrdainedAt = mResidingAt.getName() + " " + mResidingAt.getAddrAmpher() + " " + mResidingAt.getAddrJangwat();
            }
            acroForm.getField("WatResidingAtThai_addrAmpher_addrJangwat").setValue(strMOrdainedAt);
        }
        
        if (destination.equals(DESTINATION_SAMNAKPUT))
        {
            acroForm.getField("titleThai3").setValue(strTitle);
            acroForm.getField("fullName3").setValue(strFullName);
            if (p.getVisaExtensionSet() != null)
            {
                extensionsCount = p.getVisaExtensionSet().size();
                acroForm.getField("extensionsCount").setValue(extensionsCount + "");
            }
        }
        
    }
    
    private static void openPDFOnDefaultProgram(File f)
    {
        //show the generated form on the default pdf viewer
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
        {
            try
            {
                Desktop.getDesktop().open(f);
            } catch (IOException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Error to open PDF file.");
            }
            
        } else
        {
            CtrAlertDialog.errorDialog("No support for opening files on this OS.");
        }
    }
    
    private static void printPDF(PDDocument p)
    {
        //shows the print dialog
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PDFPrintable(p));
        if (pj.printDialog())
        {
            try
            {
                pj.print();
            } catch (PrinterException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Error to print form.");
            }
        }
    }

    /*
     * For filling the fields with Thai characters it's necessary to: 1) load
     * the font file manually 2) reset the field appearance to use the loaded
     * font
     */
    private static void adjustFontThaiField(ArrayList<PDTextField> listThaiFields)
    {
        int indexFirstSpace;
        String subStringAppearance;
        String beforeFieldAppearance;
        String afterFieldAppearance;
        
        for (PDTextField thaiTextField : listThaiFields)
        {
            /*
             * The appearance string has a format similar to the following example
             * "/Arial 50 Tf 0 g"
             */
            beforeFieldAppearance = thaiTextField.getDefaultAppearance();
            //looks for the position of the first space on the string
            indexFirstSpace = beforeFieldAppearance.indexOf(" ");

            //the substring is everything from the space on...
            //following with the example, it would be 
            //" 50 Tf 0 g"
            subStringAppearance = beforeFieldAppearance.substring(indexFirstSpace, beforeFieldAppearance.length());

            //the new field appearance needs to use the loaded font name
            //but keeping the other appearance settings like size etc.
            //so the final result would be something like "/F1 50 Tf 0 g"
            afterFieldAppearance = "/" + loadedThaiFontName.getName() + subStringAppearance;
            
            thaiTextField.setDefaultAppearance(afterFieldAppearance);
        }
        
    }
    
}
