/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.CityInfo;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;

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
            String city, 
            int zip
    ) throws MissingInputException;

    public PersonDTO deletePerson(int id) throws PersonNotFoundException;
 
    public PersonsDTO getAllPersons();

    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException;
    
    public PersonsDTO allPersonsByHobby(String hobby); 
    
    public PersonsDTO allPersonsByCity(String cityinfo);
    
    public CityInfo allZipCodes(String cityinfo); 
    
    public PersonsDTO hobbyCount(String hobby);
    
    
}
