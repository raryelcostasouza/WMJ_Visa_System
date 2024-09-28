/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager;

/**
 *
 * @author WMJ_user
 */
public class AppConstants
{

    public static final String STATUS_THAILAND = "THAILAND";
    public static final String STATUS_ABROAD = "ABROAD";
    public static final String STATUS_INACTIVE = "INACTIVE";

    public static final String[] LIST_LETTER_LAYPERSON = new String[]
    {
        AppConstants.LETTER_LAYPERSON_ABROAD_EMBASSY, AppConstants.LETTER_LAYPERSON_ABROAD_EMBASSY_EN, AppConstants.LETTER_LAYPERSON_THAILAND_VIENTIANE_EMBASSY
    };
    
    public static final String[] LIST_LETTER_MONASTIC = new String[]
    {
         AppConstants.LETTER_MONASTIC_ABROAD_EMBASSY, AppConstants.LETTER_MONASTIC_ABROAD_EMBASSY_EN, AppConstants.LETTER_MONASTIC_ABROAD_SAMNAKPUT
    };
    
    public static final String LETTER_LAYPERSON_ABROAD_EMBASSY = "Layperson Abroad - Embassy";
    public static final String LETTER_LAYPERSON_ABROAD_EMBASSY_EN = "Layperson Abroad - Embassy EN";
    public static final String LETTER_LAYPERSON_THAILAND_VIENTIANE_EMBASSY = "Layperson Thailand - Vientiane Embassy";
    
    public static final String LETTER_MONASTIC_ABROAD_EMBASSY = "Monastic Abroad - Embassy";
    public static final String LETTER_MONASTIC_ABROAD_EMBASSY_EN = "Monastic Abroad - Embassy EN";
    public static final String LETTER_MONASTIC_ABROAD_SAMNAKPUT = "Monastic Abroad - Samnak Put";
    
    public static final String VISA_TYPE_TOURIST = "Tourist";
    
    public static final String[] LIST_VISA_TYPES = new String[]
    {
        "Privilege Entry", "ผ.15","ผ.30","ผผ.30","ผผ.90","Non-B","Non-O", "Non-RE", "Non-ED", VISA_TYPE_TOURIST
    };
    
    public static final String[] LIST_VISA_EXEMPTIONS= new String[]
    {
       "Privilege Entry","ผ.15","ผ.30","ผผ.30","ผผ.90"
    };

    public static final String[] LIST_TRAVEL_BY = new String[]
    {
        "Air", "Land", "Sea"
    };
    
    public static final String[] LIST_NAME_ORDER = new String[]
    {
        "Default (First-Middle-Last)", "Chinese (Last-First-Middle)"  
    };
    
    public static final String NAME_ORDER_DEFAULT = "Default (First-Middle-Last)";

    public static final String STUDIES_REGULAR = "Regular";
    public static final String STUDIES_NAKTAM_TRI = "Naktam Tri";
    public static final String STUDIES_NAKTAM_TOH = "Naktam Toh";
    public static final String STUDIES_NAKTAM_EK = "Naktam Ek";

    public static final String COUNTRY_THAILAND = "THAILAND";
    
}
