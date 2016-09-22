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
import java.util.ArrayList;
import org.apache.pdfbox.cos.COSName;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.printing.PDFPrintable;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Profile;

/**
 *
 * @author WMJ_user
 */
public class CtrForm
{

    public static final int OPTION_PRINT_FORM = 0;
    public static final int OPTION_PREVIEW_FORM = 1;

    public CtrForm()
    {

    }

    /*
     * public void fillFormTM7ReqExtension(Profile p, int option) { PDFont font;
     * COSName fontName; Path pathForm90Notice; Path pathOutputFile; Path
     * pathFontFile; File outputFile; LocalDate ldArrival;
     * ArrayList<PDTextField> alThaiFields; String title; int age;
     *
     * pathForm90Notice =
     * Paths.get(System.getProperty("user.dir")).resolve("appData/forms/TM7ReqExtension.pdf");
     * pathOutputFile = Paths.get(System.getProperty("user.dir")).resolve("tmp/"
     * + p.getIdprofile() + "TM7ReqExtension.pdf"); pathFontFile =
     * Paths.get(System.getProperty("user.dir")).resolve("appData/forms/font/angsa.ttf");
     * outputFile = new File(pathOutputFile.toUri());
     *
     * try { Files.copy(pathForm90Notice, pathOutputFile,
     * StandardCopyOption.REPLACE_EXISTING); } catch (IOException ex) {
     * System.out.println("IO Error: " + ex.getMessage()); }
     *
     * // load the document PDDocument pdfDocument; try { pdfDocument =
     * PDDocument.load(outputFile);
     *
     * //load the thai font font = PDType0Font.load(pdfDocument, new
     * File(pathFontFile.toUri()));
     *
     * // get the document catalog PDAcroForm acroForm =
     * pdfDocument.getDocumentCatalog().getAcroForm(); fontName =
     * acroForm.getDefaultResources().add(font);
     *
     * // as there might not be an AcroForm entry a null check is necessary if
     * (acroForm != null) { // Retrieve an individual field and set its value.
     * acroForm.getField("titleANDLastName").setValue(p.getLastName());
     * acroForm.getField("name").setValue(p.getName());
     * acroForm.getField("middleName").setValue(p.getMiddleName());
     *
     * acroForm.getField("age").setValue(p.getMiddleName());
     *
     * acroForm.getField("birthDateDay").setValue(p.getMiddleName());
     * acroForm.getField("birthDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("birthDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("birthPlace").setValue(p.getMiddleName());
     * acroForm.getField("nationality").setValue(p.getMiddleName());
     * acroForm.getField("passportNumber").setValue(p.getMiddleName());
     * acroForm.getField("passportIssueDateDay").setValue(p.getMiddleName());
     * acroForm.getField("passportIssueDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("passportIssueDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("passportIssuedAt").setValue(p.getMiddleName());
     * acroForm.getField("passportExpiryDateDay").setValue(p.getMiddleName());
     * acroForm.getField("passportExpiryDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("passportExpiryDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("visaType").setValue(p.getMiddleName());
     * acroForm.getField("arrivalTravelBy").setValue(p.getMiddleName());
     *
     * acroForm.getField("arrivalTravelFrom").setValue(p.getMiddleName());
     * acroForm.getField("arrivalPortOfEntry").setValue(p.getMiddleName());
     * acroForm.getField("arrivalLastEntryDateDay").setValue(p.getMiddleName());
     * acroForm.getField("arrivalLastEntryDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("arrivalLastEntryDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("departureCardNumber").setValue(p.getMiddleName());
     * acroForm.getField("extensionPeriod").setValue(p.getMiddleName());
     * acroForm.getField("extensionReason").setValue(p.getMiddleName());
     *
     * acroForm.getField("thaiNameWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrNumberWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrRoadWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrRoadWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrRoadWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrTambolWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrAmpherWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrJangwatWatResidingAt").setValue(p.getMiddleName());
     *
     *
     * ldArrival =
     * org.watmarpjan.visaManager.util.Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
     *
     * if (ldArrival != null) {
     * acroForm.getField("Day").setValue(ldArrival.getDayOfMonth() + "");
     * acroForm.getField("Month").setValue(ldArrival.getMonthValue() + "");
     * acroForm.getField("Year").setValue((ldArrival.getYear() + 543) + "");
     *
     * }
     *
     * acroForm.getField("ArrivedBy").setValue(p.getArrivalTravelBy());
     * acroForm.getField("PassportNumber").setValue(p.getPassportNumber());
     * acroForm.getField("ArrivalCardNumber").setValue(p.getArrivalCardNumber());
     *
     * if (p.getWatByIdWatResidingAt() != null) { alThaiFields = new
     * ArrayList<>(); alThaiFields.add((PDTextField)
     * acroForm.getField("Addr1")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrRoad")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrTambol")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrAmphur")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrProvince"));
     *
     * for (PDTextField tf : alThaiFields) { adjustFontThaiField(tf, fontName);
     * }
     *
     * acroForm.getField("Addr1").setValue(p.getWatByIdWatResidingAt().getThaiName());
     * acroForm.getField("AddrRoad").setValue(p.getWatByIdWatResidingAt().getAddrRoad());
     * acroForm.getField("AddrTambol").setValue(p.getWatByIdWatResidingAt().getAddrTambon());
     * acroForm.getField("AddrAmphur").setValue(p.getWatByIdWatResidingAt().getAddrAmpher());
     * acroForm.getField("AddrProvince").setValue(p.getWatByIdWatResidingAt().getAddrJangwat());
     * acroForm.getField("PhoneNumber").setValue(p.getWatByIdWatResidingAt().getPhoneNumber());
     * } }
     *
     * // Save and close the filled out form. pdfDocument.save(outputFile);
     *
     * if (option == OPTION_PRINT_FORM) { printPDF(pdfDocument); } else {
     * openPDFOnDefaultProgram(outputFile); }
     *
     * } catch (IOException ex) { System.out.println("IO Error: " +
     * ex.getMessage()); } }
     */
    public void fillForm90DayNotice(Profile p, int option)
    {
        PDDocument pdfDocument;
        PDAcroForm acroForm;
        PDFont font;
        COSName fontName;
        File outputFile;
        File sourceFile;
        LocalDate ldArrival;
        ArrayList<PDTextField> alThaiFields;

        sourceFile = AppFiles.getFormTM47Notice90Day();
        outputFile = AppFiles.getFormTMPOutputPDF("tm47-form90dayNotice");

        // load the document
        try
        {
            pdfDocument = PDDocument.load(sourceFile);

            //load the thai font
            font = PDType0Font.load(pdfDocument, AppFiles.getThaiFont());

            // get the document catalog
            acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            fontName = acroForm.getDefaultResources().add(font);

            // as there might not be an AcroForm entry a null check is necessary
            if (acroForm != null)
            {
                // Retrieve an individual field and set its value.
                acroForm.getField("Name").setValue(p.getName() + " " + p.getMiddleName() + " " + p.getLastName());
                acroForm.getField("Nationality").setValue(p.getNationality());

                ldArrival = org.watmarpjan.visaManager.util.Util.convertDateToLocalDate(p.getArrivalLastEntryDate());

                if (ldArrival != null)
                {
                    acroForm.getField("Day").setValue(ldArrival.getDayOfMonth() + "");
                    acroForm.getField("Month").setValue(ldArrival.getMonthValue() + "");
                    acroForm.getField("Year").setValue((ldArrival.getYear() + 543) + "");

                }

                acroForm.getField("ArrivedBy").setValue(p.getArrivalTravelBy());
                acroForm.getField("PassportNumber").setValue(p.getPassportNumber());
                acroForm.getField("ArrivalCardNumber").setValue(p.getArrivalCardNumber());

                if (p.getMonasteryResidingAt() != null)
                {
                    alThaiFields = new ArrayList<>();
                    alThaiFields.add((PDTextField) acroForm.getField("Addr1"));
                    alThaiFields.add((PDTextField) acroForm.getField("AddrRoad"));
                    alThaiFields.add((PDTextField) acroForm.getField("AddrTambol"));
                    alThaiFields.add((PDTextField) acroForm.getField("AddrAmphur"));
                    alThaiFields.add((PDTextField) acroForm.getField("AddrProvince"));

                    alThaiFields.add((PDTextField) acroForm.getField("ArrivalCardNumber"));

                    for (PDTextField tf : alThaiFields)
                    {
                        adjustFontThaiField(tf, fontName);
                    }

                    acroForm.getField("Addr1").setValue(p.getMonasteryResidingAt().getName());
                    acroForm.getField("AddrRoad").setValue(p.getMonasteryResidingAt().getAddrRoad());
                    acroForm.getField("AddrTambol").setValue(p.getMonasteryResidingAt().getAddrTambon());
                    acroForm.getField("AddrAmphur").setValue(p.getMonasteryResidingAt().getAddrAmpher());
                    acroForm.getField("AddrProvince").setValue(p.getMonasteryResidingAt().getAddrJangwat());
                    acroForm.getField("PhoneNumber").setValue(p.getMonasteryResidingAt().getPhoneNumber());

                }
            }

            // Save and close the filled out form.
            pdfDocument.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDocument);

            } else
            {
                openPDFOnDefaultProgram(outputFile);
            }

        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error when generating form from template.");
        }
    }

    private void openPDFOnDefaultProgram(File f)
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

    private void printPDF(PDDocument p)
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
    private void adjustFontThaiField(PDTextField tf, COSName fontName)
    {
        int indexFirstSpace;
        String subStringAppearance;
        String beforeFieldAppearance;
        String afterFieldAppearance;

        /*
         * The appearance string has a format similar to the following example
         * "/Arial 50 Tf 0 g"
         */
        beforeFieldAppearance = tf.getDefaultAppearance();

        //looks for the position of the first space on the string
        indexFirstSpace = beforeFieldAppearance.indexOf(" ");

        //the substring is everything from the space on...
        //following with the example, it would be 
        //" 50 Tf 0 g"
        subStringAppearance = beforeFieldAppearance.substring(indexFirstSpace, beforeFieldAppearance.length());

        //the new field appearance needs to use the loaded font name
        //but keeping the other appearance settings like size etc.
        //so the final result would be something like "/F1 50 Tf 0 g"
        afterFieldAppearance = "/" + fontName.getName() + subStringAppearance;

        tf.setDefaultAppearance(afterFieldAppearance);
    }

}
