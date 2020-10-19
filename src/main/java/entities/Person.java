package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author josef
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
@NamedQuery(name = "Person.getAllRows", query = "SELECT p from Person p")})
@NamedQuery(name = "Person.getPersonById", query = "SELECT p FROM Person p WHERE p.id LIKE :id")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Address address;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Phone phone;
    
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private HashSet<Hobby> hobby; 

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Person() {
    }

    public void setHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobby = new HashSet<>();
            hobby.addPerson(this);
        } else {
            this.hobby = null;
        }
    }
    
    public void setPhone(Phone phone) {
        if (phone != null) {
            this.phone = phone;
            phone.addPerson(this);
        } else {
            this.phone = null;
        }
    }
    
    public void setAddress(Address address) {
        if (address != null) {
            this.address = address;
            address.addPerson(this);
        } else {
            this.address = null;
        }
    }

    public Address getAddress() {
        return address;
    }
    
    public Phone getPhone(){
        return phone;
    }

    public HashSet<Hobby> getHobby() {
        return hobby;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
