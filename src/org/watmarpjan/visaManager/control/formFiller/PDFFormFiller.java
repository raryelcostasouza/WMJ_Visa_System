/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrMain;
import static org.watmarpjan.visaManager.control.CtrPDF.OPTION_PRINT_FORM;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public abstract class PDFFormFiller
{
    private final String MSG_ERROR = "Error while generating PDF form.";
    
    protected CtrMain ctrMain;
    protected COSName loadedThaiFontName = null;
    protected PDAcroForm acroForm;
    protected PDDocument pdfDocument;
    private File outputFile;
    
    public PDFFormFiller(CtrMain ctrMain)
    {
        this.ctrMain = ctrMain; 
    }
    
    protected void init(File sourceFormFile)
    {
        PDFont font;
        this.outputFile = AppFiles.getFormTMPOutputPDF(sourceFormFile.getName());
        
        try
        {
            this.pdfDocument = PDDocument.load(sourceFormFile);
            //load the thai font
            font = PDType0Font.load(pdfDocument, AppFiles.getThaiFont());

            // get the document catalog
            this.acroForm = this.pdfDocument.getDocumentCatalog().getAcroForm();
            
            //acroForm.setNeedAppearances(true);
            
            //if the document is a PDF Form
            if (this.acroForm != null)
            {
                this.loadedThaiFontName = this.acroForm.getDefaultResources().add(font);
            }
        }
        catch(IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, MSG_ERROR);
        }
    }
    
    protected abstract void fillFormData(MonasticProfile p) throws IOException;
    
    public void saveAndOpenPDF()
    {
        try
        {
            // Save and close the filled out form.
            pdfDocument.save(outputFile);
            pdfDocument.close();
            CtrFileOperation.openFileOnDefaultProgram(outputFile);
        }
        catch(IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error while saving/opening PDF file.");
        }        
    }    
    
    /*
     * For filling the fields with Thai characters it's necessary to: 1) load
     * the font file manually 2) reset the field appearance to use the loaded
     * font
     */
    protected void adjustFontThaiField(ArrayList<PDTextField> listThaiFields)
    {
        int indexFirstSpace, i;
        String subStringAppearance;
        String beforeFieldAppearance;
        String afterFieldAppearance;

        i = 0;
        for (PDTextField thaiTextField : listThaiFields)
        {
            /*
             * The appearance string has a format similar to the following example
             * "/Arial 50 Tf 0 g"
             */
            if (thaiTextField != null)
            {
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
            else
            {
                CtrAlertDialog.errorDialog("PDF form missing field with index "+i);
            }
            i++;
        }
    }
}
