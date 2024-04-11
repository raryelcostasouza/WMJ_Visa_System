/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import org.watmarpjan.visaManager.control.Form2AppFieldMapper;
import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public abstract class ODTLetterFiller
{

    protected TextDocument objTD;
    protected File fTemplate;
    protected MonasticProfile monastic;

    public ODTLetterFiller(File fTemplate, MonasticProfile p)
    {
        try
        {
            this.monastic = p;
            this.fTemplate = fTemplate;
            this.objTD = TextDocument.loadDocument(this.fTemplate);
            fillLetter();
        }
        catch (InvalidNavigationException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error while search/replacing fields on letter template.");
        }
        catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to generate letter.");
        }
    }

    public abstract void fillLetter() throws InvalidNavigationException;

    public void saveAndOpenODT()
    {
        Path pProfileLetterStorage;
        String fileNameGeneratedLetter;
        File fDestination;

        fileNameGeneratedLetter = Util.getFileNameWithoutExtension(this.fTemplate) + generateLetterFileNameSuffix(monastic) + ".odt";
        pProfileLetterStorage = AppPaths.getPathToProfileLetters(monastic.getNickname());
        fDestination = pProfileLetterStorage.resolve(fileNameGeneratedLetter).toFile();

        try
        {
            if (!pProfileLetterStorage.toFile().exists())
            {
                pProfileLetterStorage.toFile().mkdirs();
            }
            objTD.save(fDestination);
            CtrFileOperation.openFileOnDefaultProgram(fDestination);
        }
        catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to save generated Letter.");
        }
    }

    protected void fillField(String fieldName) throws InvalidNavigationException
    {
        //Search for fieldName and replace with value from App
        TextNavigation search2 = null;
        String replace;

        try
        {
            replace = Form2AppFieldMapper.getProfileField(monastic, fieldName);
            if (replace != null)
            {
                search2 = new TextNavigation(fieldName, objTD);

                while (search2.hasNext())
                {
                    TextSelection item = (TextSelection) search2.nextSelection();

                    item.replaceWith(replace);
                }
            }
            else
            {
                CtrAlertDialog.warningDialog("Field Data Missing on System for this Monastic: " + fieldName);
            }
        }
        catch (NullPointerException npe)
        {
            CtrAlertDialog.warningDialog("Field Data Missing on System for this Monastic: " + fieldName);
        }

    }

    private String generateLetterFileNameSuffix(MonasticProfile p)
    {
        return p.getMonasticName() + p.getLastName() + "-" + LocalDateTime.now().format(Util.TIMESTAMP_FILE_NAME);
    }
}
