package dto;

import entities.Person;
import entities.Phone;

public class PersonDTO {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private String street;
    private String city;
    private String hobbyName;
     
    private int phoneNumber, zip;
    private String phoneDesc;

    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.email = p.getEmail();
        this.id = p.getId();
        this.phoneNumber = p.getPhones().get(0).getNumber(); 
        this.street = p.getAddress().getStreet(); 
        this.zip = p.getZipFromAddress(); 
    }

    public PersonDTO(String fn,String ln, String email, String hobbyName, int phoneNumber, String phoneDesc) {
        this.fName = fn;
        this.lName = ln;
        this.email = email;
        this.hobbyName = hobbyName;
        this.phoneNumber = phoneNumber;
        this.phoneDesc = phoneDesc;
    }

    public PersonDTO(String fName, String lName, String email, String street, String hobbyName, int phoneNumber, int zip, String phoneDesc) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.street = street;
        this.hobbyName = hobbyName;
        this.phoneNumber = phoneNumber;
        this.zip = zip;
        this.phoneDesc = phoneDesc;
    }

    public PersonDTO() {}

    public int getId() {
        return id;
    }
 
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneDesc() {
        return phoneDesc;
    }

    public void setPhoneDesc(String phoneDesc) {
        this.phoneDesc = phoneDesc;
    }
    
    

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }
    
    

}