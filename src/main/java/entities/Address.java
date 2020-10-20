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
import javax.persistence.OneToOne;

/**
 *
 * @author josef
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address"),
@NamedQuery(name = "Address.getAllRows", query = "SELECT a from Address a")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long a_id;
    private String street;
    private String additionalInfo;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private CityInfo cityInfo;
    
    @OneToMany(mappedBy="address")
    private List<Person> persons;

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        persons = new ArrayList<>();
    }
    
    public Address() {
    }

    public void addPerson(Person person) {
        if (person != null){
            persons.add(person);
        }
    }
    
    public List<Person> getPerson() {
        return persons;
    }
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public Long getId() {
        return a_id;
    }

    public void setId(Long id) {
        this.a_id = id;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    
}

