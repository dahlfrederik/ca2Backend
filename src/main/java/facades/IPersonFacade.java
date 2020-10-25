/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CityInfoDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.CityInfo;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.List;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public interface IPersonFacade {

    public PersonDTO addPerson(
            String fName,
            String lName,
            String email,
            int phoneNumber,
            String phoneDesc,
            String street,
            String hobbyName,
            int zip
    ) throws MissingInputException;

    public PersonDTO deletePerson(int id) throws PersonNotFoundException;
 
    public PersonsDTO getAllPersons();

    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException;
       
    public PersonsDTO allPersonsByZipcode(int zipcode);
    
    public List<CityInfoDTO> allZipCodes(); 
    
    public long hobbyCount(String hobby);
    
    
}
