/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dto;

import entities.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class PhonesDTO {
    private List<PhoneDTO> all = new ArrayList();

    public PhonesDTO(List<Phone> phoneEntities) {
        phoneEntities.forEach((h) -> {
            all.add(new PhoneDTO(h));
        });
    }

    public List<PhoneDTO> getAll() {
        return all;
    }




}
