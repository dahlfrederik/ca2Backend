package facades;

import dto.CityInfoDTO;
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

    /**
     * @param fName
     * @param lName
     * @param email
     * @param phoneNumber
     * @param phoneDesc
     * @param street
     * @param hobbyName
     * @param city
     * @param zip
     * @return a DTO Object of the created person
     * @throws MissingInputException HOBBIES CAN NEVER BE ZERO SINCE IT'S
     * PREDEFINED IN DB, THEREFORE NO OTHER PART OF IF
     */
    @Override
    public PersonDTO addPerson(
            String fName,
            String lName,
            String email,
            int phoneNumber,
            String phoneDesc,
            String street,
            String hobbyName,
            int zip
    ) throws MissingInputException {
        if ((fName.length() == 0) || (lName.length() == 0 || (email.length() == 0)) || (phoneNumber == 0)) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        EntityManager em = getEntityManager();
        Person person = new Person(fName, lName, email);

        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Person.GetAddress");
            query.setParameter("street", street);
            Query query2 = em.createNamedQuery("Person.GetHobby");
            query2.setParameter("hobby", hobbyName);
            Query query3 = em.createNamedQuery("Person.GetPhone");
            query3.setParameter("phone", phoneNumber);
            Query query4 = em.createNamedQuery("Person.GetCityInfo");
            query4.setParameter("zip", zip);
            List<Address> addresses = query.getResultList();
            Hobby hobby = (Hobby) query2.getSingleResult();
            List<Phone> phoneNumberList = query3.getResultList();
            CityInfo cityInfoList = (CityInfo) query4.getSingleResult();
            if (addresses.size() > 0 && hobby == null && phoneNumberList.size() > 0 && cityInfoList == null) {
                Address address = addresses.get(0);
                address.setCityInfo(cityInfoList);
                person.setAddress(address);
                person.addHobby(hobby);
                person.addPhone(phoneNumberList.get(0));
            } else {
                Address address = new Address(street);
                address.setCityInfo(cityInfoList);
                person.setAddress(address);
                person.addPhone(new Phone(phoneNumber, phoneDesc));
                person.addHobby(hobby);
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
        } finally {
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
    public PersonsDTO allPersonsByHobby(String hobby) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Person.GetHobby");
            query.setParameter("hobby", hobby);
            Hobby personHobby = (Hobby) query.getSingleResult();
            if (personHobby == null) {
                throw new PersonNotFoundException(String.format("Persons with hobby: (%d) not found", hobby));
            } else {
                return new PersonsDTO(personHobby.getPersons());
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO allPersonsByZipcode(int zipcode) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Person.GetCityInfo");
            query.setParameter("zip", zipcode);
            List<CityInfo> personCityInfo = query.getResultList();

            if (zipcode < 0) {
                System.out.println("Fejl");
            } else {
                List<Address> addresses = new ArrayList();
                List<Person> persons = new ArrayList();
                for (CityInfo cityInfo : personCityInfo) {
                    addresses = cityInfo.getAddressList();
                    for (Address addresse : addresses) {
                        persons = addresse.getPerson();
                    }
                }
                return new PersonsDTO(persons);
            }
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public List<CityInfoDTO> allZipCodes() {
        EntityManager em = getEntityManager();
        try {
            List<CityInfo> zipCodes = new ArrayList();
            zipCodes = em.createQuery("SELECT z FROM CityInfo z").getResultList();
            
            List<CityInfoDTO> newList = new ArrayList();
            for (CityInfo zipCode : zipCodes) {
                newList.add(new CityInfoDTO(zipCode));
            }
            return newList;
        } finally {
            em.close();
        }
    }

    @Override
    public long hobbyCount(String hobby) {
        EntityManager em = emf.createEntityManager();
        String qouteString = "\"" + hobby + "\"";
        try {
            long hobbyCount = (long) em.createQuery("SELECT COUNT(h) FROM Hobby h WHERE h.name = " + qouteString).getSingleResult();
            return hobbyCount;
        } finally {
            em.close();
        }
    }


    public static void main(String[] args) throws MissingInputException, PersonNotFoundException {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade facade = PersonFacade.getFacadeExample(emf);
        EntityManager em = emf.createEntityManager();
        //facade.populateDB();
        String fName = "Josef";
        String lName = "Marc";
        String email = "josef@glostrup.dk";
        int phoneNumber = 12345678;
        String phoneDesc = "work";
        String street = "Jernbanevej";
        String city = "Glostrup";
        String hobbyName = "Airsoft";
        //ZIP is already in DB thats why "2000" is being used. 
        facade.addPerson(fName, lName, email, phoneNumber, phoneDesc, street, hobbyName, 2000);

        String hobby = "Airsoft";

        System.out.println("-----------------TESTER HER ------------------------");
        //System.out.println(PersonsWithHobby); 

        System.out.println(facade.hobbyCount("Airsoft"));
        

        //System.out.println(facade.allPersonsByHobby(hobby).getAll().get(0).getfName());
        
    }

}
