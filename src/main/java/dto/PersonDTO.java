package dto;

import entities.Person;

public class PersonDTO {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private String street;
    private String hobbyName;

    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.email = p.getEmail();
        this.id = p.getId();
        this.street = p.getAddress().getStreet(); 
    }

    public PersonDTO(String fn,String ln, String email, String hobbyName) {
        this.fName = fn;
        this.lName = ln;
        this.email = email;
        this.hobbyName = hobbyName;
    }

    public PersonDTO() {}

    public int getId() {
        return id;
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

}