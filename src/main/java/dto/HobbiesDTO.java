/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dto;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class HobbiesDTO {

    private List<HobbyDTO> all = new ArrayList();

    public HobbiesDTO(List<Hobby> hobbiesEntities) {
        hobbiesEntities.forEach((h) -> {
            all.add(new HobbyDTO(h));
        });
    }

    public List<HobbyDTO> getAll() {
        return all;
    }


}
