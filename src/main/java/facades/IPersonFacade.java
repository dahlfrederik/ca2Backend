/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import dto.PersonDTO;
import dto.PersonsDTO;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public interface IPersonFacade {
    public PersonDTO addPerson(String fName, String lName, String phone, String street, String zip, String city);

    public PersonDTO deletePerson(int id);

    public PersonDTO getPerson(int id);

    public PersonsDTO getAllPersons();

    public PersonDTO editPerson(PersonDTO p);
    
    public PersonsDTO getPersonsByHobby(String hobby);
    
    public PersonsDTO getPersonsByCity(String city); 
    
    public int getAmountOfPersonsWithThisHobby(String hobby); 
        
}
