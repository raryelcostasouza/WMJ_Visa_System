/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.io.File;
import java.io.FilenameFilter;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author KroobaHariel
 */
public class GenericScanStampedPageFilenameFilter implements FilenameFilter
{

    @Override
    public boolean accept(File dir, String name)
    {
        //if matches the filename regex format for the extra scans
        //their filename ends with the extension after the pagenumber
        
        //note that the string between the last dash and the extension can be empty
        return name.matches("(\\w)+(-page)[0-9]+-(\\S)*(.jpg)");
    }
    
}
