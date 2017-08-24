/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author KroobaHariel
 */
public class LetterInputData
{
    private MonasticProfile monasticProfile;
    private Embassy embassy;
    private String addrMonasticLine1, addrMonasticLine2, addrMonasticLine3, addrMonasticLine4, phoneAbroad;
    private LocalDate ldDepartureDateThai;

    public LetterInputData(MonasticProfile monasticProfile, Embassy embassy)
    {
        this.monasticProfile = monasticProfile;
        this.embassy = embassy;
    }

    public String getAddrMonasticLine1()
    {
        return addrMonasticLine1;
    }

    public void setAddrMonasticLine1(String addrMonasticLine1)
    {
        this.addrMonasticLine1 = addrMonasticLine1;
    }

    public String getAddrMonasticLine2()
    {
        return addrMonasticLine2;
    }

    public void setAddrMonasticLine2(String addrMonasticLine2)
    {
        this.addrMonasticLine2 = addrMonasticLine2;
    }

    public String getAddrMonasticLine3()
    {
        return addrMonasticLine3;
    }

    public void setAddrMonasticLine3(String addrMonasticLine3)
    {
        this.addrMonasticLine3 = addrMonasticLine3;
    }

    public String getAddrMonasticLine4()
    {
        return addrMonasticLine4;
    }

    public void setAddrMonasticLine4(String addrMonasticLine4)
    {
        this.addrMonasticLine4 = addrMonasticLine4;
    }
    
    public MonasticProfile getMonasticProfile()
    {
        return monasticProfile;
    }

    public Embassy getEmbassy()
    {
        return embassy;
    }

    public String getPhoneAbroad()
    {
        return phoneAbroad;
    }

    public LocalDate getLdDepartureDateThai()
    {
        return ldDepartureDateThai;
    }

    public void setPhoneAbroad(String phoneAbroad)
    {
        this.phoneAbroad = phoneAbroad;
    }

    public void setLdDepartureDateThai(LocalDate ldDepartureDateThai)
    {
        this.ldDepartureDateThai = ldDepartureDateThai;
    }
    
    
    
    
}
