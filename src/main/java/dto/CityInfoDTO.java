/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josef
 */
public class CityInfoDTO {
   private int zip;
   private String city;

    public CityInfoDTO(CityInfo cityInfo) {
        this.zip = cityInfo.getZip();
        this.city = cityInfo.getCity();
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
}
