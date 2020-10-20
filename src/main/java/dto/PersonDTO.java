
package dto;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */


import entities.Person;


public class PersonDTO {
    private int id;
    private String fName;
    private String lName;
    private int phoneNumber;
    private String phoneDesc; 
    private String street;
    private String addressInfo; 
    private String hobby; 

    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.phoneNumber = p.getPhone().getNumber();
        this.phoneDesc = p.getPhone().getDescription(); 
        this.id = p.getId();
        this.street = p.getAddress().getStreet();  
        this.addressInfo = p.getAddress().getAdditionalInfo();  
        this.hobby = p.getHobby().getHobbyName();    
    }

    public PersonDTO(String fn,String ln, int phoneNumber) {
        this.fName = fn;
        this.lName = ln;
        this.phoneNumber = phoneNumber;
    }

    public PersonDTO() {}

    public int getId() {
        return id;
    }

    public String getHobbyName() {
        return hobby;
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
}