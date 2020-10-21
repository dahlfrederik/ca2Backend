package facades;

import entities.Person;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Phone;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName,
            String lName,
            String email,
            String phoneNumber,
            String phoneDesc,
            String street,
            String wikiLink,
            String hobbyType,
            String hobbyCategory) throws MissingInputException {
        if ((fName.length() == 0) || (lName.length() == 0)) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        EntityManager em = getEntityManager();
        Person person = new Person(fName, lName, email);

        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Person.GetAddress");
            query.setParameter("street", street);
            List<Address> addresses = query.getResultList();
            if (addresses.size() > 0) {
                person.setAddress(addresses.get(0));
            } else {
                person.setAddress(new Address(street));
            }
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);

        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found, try something else", id));
        } else {

            try {
                em.getTransaction().begin();
                em.remove(person);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            PersonDTO personDTO = new PersonDTO(person);
            return personDTO;
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
      EntityManager em = getEntityManager();
        try {
            return new PersonsDTO(em.createNamedQuery("Person.getAllRows").getResultList());
        } finally{  
            em.close();
        }   
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
        if ((p.getfName().length() == 0) || (p.getlName().length() == 0) || (p.getEmail().length() == 0) || (p.getStreet().length() == 0)) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            Person person = em.find(Person.class, p.getId());
            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: (%d) not found", p.getId()));
            } else {
                person.setFirstName(p.getfName());
                person.setLastName(p.getlName());
                person.setEmail(p.getEmail());
                person.getAddress().setStreet(p.getStreet());
            }
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }

    }
    
    @Override
    public PersonsDTO allPersonsByHobby(String hobby) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonsDTO allPersonsByCity(String cityinfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CityInfo allZipCodes(String cityinfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonsDTO hobbyCount(String hobby) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void populateDB() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            Person p1 = new Person("Thor", "Christensen", "thor@hammer.dk");
            Person p2 = new Person("Frederik", "Dahl", "freddy@wong.dk");
            Address a1 = new Address("Tagensvej 154"); 
            Address a2 = new Address("Frederiksbergvej 1");
            Phone phone1 = new Phone(30303030, "Hjem");
            Phone phone2 = new Phone(40404040, "Hjem");
            CityInfo ci1 = new CityInfo(4200, "Slagelse");
            CityInfo ci2 = new CityInfo(2000, "Frederiksberg");
            Hobby h1 = new Hobby("Noget" ,"Bb.dk", "Bodybuilding", "Træning");
            a1.setCityInfo(ci1);
            a2.setCityInfo(ci2);
            p1.setAddress(a1);
            p1.addHobby(h1);
            p2.setAddress(a2);
            p2.addHobby(h1);
            p1.addPhone(phone1);
            p2.addPhone(phone2);
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        PersonFacade facade = PersonFacade.getFacadeExample(emf);
        facade.populateDB();
    }
}
