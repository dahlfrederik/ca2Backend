/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dto;

import entities.Phone;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class PhoneDTO {
    private int number; 
    private String desc; 
    
    public PhoneDTO(Phone p){
        this.number = p.getNumber(); 
        this.desc = p.getDesc(); 
    }
}
