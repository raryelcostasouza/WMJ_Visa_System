/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import static org.watmarpjan.visaManager.control.CtrLetterODF.searchNReplace;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.MonasteryUtil;
import org.watmarpjan.visaManager.util.ProfileUtil;

/**
 *
 * @author raryel
 */
public class ResidenceGuaranteeLetterFiller extends ODTLetterFiller
{

    public ResidenceGuaranteeLetterFiller(File fLetter, MonasticProfile p)
    {
        super(fLetter, p);
    }

    
    @Override
    public void fillLetter(MonasticProfile p) throws InvalidNavigationException
    {
        Monastery mResidingAt;
        String strMResidingAt;
        
        searchNReplace(objTD, "«titleTH2»", ProfileUtil.getTitleTH2(p));
        searchNReplace(objTD, "«fullName»", ProfileUtil.getFullName(p));
        searchNReplace(objTD, "«nationality»", p.getNationality());
        searchNReplace(objTD, "«ageThai»", ProfileUtil.getStrAgeThai(p.getBirthDate()));
        searchNReplace(objTD, "«passportNumber»", p.getPassportNumber());
        searchNReplace(objTD, "«visaType»", p.getVisaType());
        
        mResidingAt = p.getMonasteryResidingAt();
        strMResidingAt = MonasteryUtil.getStringWatAddrFull(mResidingAt, false, true);
        searchNReplace(objTD, "«WatResidingAtThai_addrTambon_addrAmpher_addrJangwat»", strMResidingAt);
    }
    
}
