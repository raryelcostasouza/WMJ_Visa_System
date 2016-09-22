/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class EntryUpdate90DayNotice
{

    private final SimpleStringProperty profileNickname;
    private final SimpleStringProperty strDueDate;
    private final BooleanProperty selected;
    private final Integer idProfile;
    private final Date dueDate;

    public EntryUpdate90DayNotice(Integer idProfile, String profileNickname, Date dueDate)
    {
        LocalDate ld;

        this.idProfile = idProfile;
        this.profileNickname = new SimpleStringProperty(profileNickname);

        ld = Util.convertDateToLocalDate(dueDate);
        this.dueDate = dueDate;
        this.strDueDate = new SimpleStringProperty(ld.format(Util.DEFAULT_DATE_FORMAT));
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getProfileNickname()
    {
        return profileNickname.get();
    }

    public String getStrDueDate()
    {
        return strDueDate.get();
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public boolean isSelected()
    {
        return selected.get();
    }

    public void setSelected(boolean selected)
    {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty()
    {
        return selected;
    }

    public Integer getIdProfile()
    {
        return idProfile;
    }

}
