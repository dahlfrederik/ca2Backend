/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * 
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address"),
@NamedQuery(name = "Address.getAllRows", query = "SELECT a from Address a")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String street; 
    
    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<Person> persons; 
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private CityInfo cityInfo;
    
    public Address() {
    }

    public Address(String street) {
        this.street = street;
        persons = new ArrayList<>();  
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
    
    
    public Integer getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Person> getPerson() {
        return persons;
    }

     public void addPerson(Person person) {
        if (person != null){
            persons.add(person);
        }
    }
    
    

    
}
