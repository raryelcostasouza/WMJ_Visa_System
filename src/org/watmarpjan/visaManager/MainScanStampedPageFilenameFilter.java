/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author KroobaHariel
 */
public class MainScanStampedPageFilenameFilter implements FilenameFilter
{

    @Override
    public boolean accept(File dir, String name)
    {
        //if matches the filename regex format for the extra scans
        //they contain a final string with ArriveStamp, Visa or VisaExt String at the end
        
        //note that the string between the last dash and the extension CANNOT be EMPTY (\S stands for 1 or more ocurrences of non whitespace stuff)
        return name.matches("(\\w)+(-page)[0-9]+-(\\S)+(.jpg)");
    }
    
}
