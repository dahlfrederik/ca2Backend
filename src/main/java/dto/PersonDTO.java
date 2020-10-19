
package dto;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.HashSet;

public class PersonDTO {
    private int id;
    private String fName;
    private String lName;
    private Phone phone;
    private Address address;  
    private HashSet<Hobby> hobby; 

    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.phone = p.getPhone();
        this.id = p.getId();
        this.address = p.getAddress(); 
        this.hobby = p.getHobby(); 
    }

    public PersonDTO(String fn,String ln, Phone phone) {
        this.fName = fn;
        this.lName = ln;
        this.phone = phone;
    }

    public PersonDTO() {}

    public int getId() {
        return id;
    }

    public HashSet<Hobby> getHobby() {
        return hobby;
    }

    public void setHobby(HashSet<Hobby> hobby) {
        this.hobby = hobby;
    }   

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    
    
}