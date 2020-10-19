package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author josef
 */
@Entity
public class Hobby implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 50)
    private String name;

    private String wikiLink;
    private String category;
    private String type;

    @ManyToMany(mappedBy="hobby")
    private List<Person> persons;
    
    public Hobby() {
    }

    public Hobby(String wikiLink, String category, String type) {
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
        persons = new ArrayList<>(); 
    }
    
    public void addPerson(Person person) {
        if (person != null){
            persons.add(person);
        }
    }
    
}


