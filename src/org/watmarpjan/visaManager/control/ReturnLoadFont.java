/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

/**
 *
 * @author WMJ_user
 */
public class ReturnLoadFont
{

    private final PDAcroForm acroForm;
    private final COSName fontName;

    public ReturnLoadFont(PDAcroForm acroForm, COSName fontName)
    {
        this.acroForm = acroForm;
        this.fontName = fontName;
    }

    public PDAcroForm getAcroForm()
    {
        return acroForm;
    }

    public COSName getFontName()
    {
        return fontName;
    }
}
