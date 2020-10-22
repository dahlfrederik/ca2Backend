package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
@NamedQueries({
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
    @NamedQuery(name = "Person.getAllRows", query = "SELECT p from Person p"),
    @NamedQuery(name = "Person.getPersonById", query = "SELECT p from Person p WHERE p.id LIKE :id"),
    @NamedQuery(name = "Person.GetAddress", query = "SELECT a FROM Address a WHERE a.street = :street"),
    @NamedQuery(name = "Person.GetHobby", query = "SELECT h FROM Hobby h WHERE h.name = :hobby"),
    @NamedQuery(name = "Person.GetPhone", query = "SELECT p FROM Phone p WHERE p.number = :phone"),
    @NamedQuery(name = "Person.GetCityInfo", query = "SELECT c FROM CityInfo c WHERE c.zip = :zip"),
    @NamedQuery(name = "Person.GetCity", query = "SELECT c FROM CityInfo c WHERE c.city = :city")
})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(cascade = (CascadeType.PERSIST))
    private Address address;

    @ManyToMany(mappedBy = "persons", cascade = (CascadeType.ALL))
    List<Hobby> hobbies;
    
    @OneToMany(mappedBy = "person", cascade = (CascadeType.ALL))
    List<Phone> phones;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = new ArrayList<>();
        this.phones = new ArrayList<>();
    }

    public Address getAddress() {
        return address;
    }
    
    public int getZipFromAddress(){
        return address.getCityInfo().getZip(); 
    }

    public void setAddress(Address address) {
        if (address != null) {
            this.address = address;
            address.addPerson(this);
        } else {
            this.address = null;
        }
    }

    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);
        }

    }

    public void removeHobby(Hobby hobby) {
        if (hobby != null) {
            hobbies.remove(hobby);
            hobby.getPersons().remove(this);
        }
    }
    
    public void addPhone(Phone phone) {
        this.phones.add(phone);
        if (phone != null) {
            phone.setPerson(this);
        }
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public List<Phone> getPhones() {
        return phones;
    }
    

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
