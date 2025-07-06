/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public class TM8XFiller extends PDFFormFiller
{

    public TM8XFiller(CtrMain ctrMain, File fTemplate, MonasticProfile p)
    {
        super(ctrMain);
        
        super.init(fTemplate);
        try
        {
            fillFormData(p);
        }
        catch(IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error while filling TM8X form!");
        }
    }

    
    @Override
    protected void fillFormData(MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        Monastery mResidingAt;
        LocalDate ldBirthDate, ldPassportIssue, ldPassportExpiry, ldLastEntry;
        String immOffice;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("immigrationOfficeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrNumberWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
        
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getMonasticName());
        acroForm.getField("middleName").setValue(p.getMiddleName());

        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));

        ldBirthDate = Util.convertDateToLocalDate(p.getBirthDate());
        if (ldBirthDate != null)
        {
            acroForm.getField("birthDateDay").setValue(ldBirthDate.getDayOfMonth() + "");
            acroForm.getField("birthDateMonth").setValue(ldBirthDate.getMonthValue() + "");
            acroForm.getField("birthDateYear").setValue(Util.convertYearToThai(ldBirthDate.getYear()) + "");
        }

        acroForm.getField("birthPlace_birthCountry").setValue(p.getBirthPlace() + ", " + p.getBirthCountry());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());

        ldPassportIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
        if (ldPassportIssue != null)
        {
            acroForm.getField("passportIssueDateDay").setValue(ldPassportIssue.getDayOfMonth() + "");
            acroForm.getField("passportIssueDateMonth").setValue(ldPassportIssue.getMonthValue() + "");
            acroForm.getField("passportIssueDateYear").setValue(Util.convertYearToThai(ldPassportIssue.getYear()) + "");
        }
        acroForm.getField("passportIssuedAt").setValue(p.getPassportIssuedAt());

        ldPassportExpiry = Util.convertDateToLocalDate(p.getPassportExpiryDate());
        if (ldPassportExpiry != null)
        {
            acroForm.getField("passportExpiryDateDay").setValue(ldPassportExpiry.getDayOfMonth() + "");
            acroForm.getField("passportExpiryDateMonth").setValue(ldPassportExpiry.getMonthValue() + "");
            acroForm.getField("passportExpiryDateYear").setValue(Util.convertYearToThai(ldPassportExpiry.getYear()) + "");
        }

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
        acroForm.getField("fullName").setValue(ProfileUtil.getFullName(p));
        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
             //immigration office string need to have the province of the residence monastery
            immOffice = "ตรวจคนเข้าเมืองจังหวัด"+mResidingAt.getAddrJangwat();
            acroForm.getField("immigrationOfficeThai").setValue(immOffice);
        
            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getMonasteryName());
            acroForm.getField("addrNumberWatResidingAtThai").setValue(mResidingAt.getAddrNumber());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
        }
    }
    
}
