package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import utils.EMF_Creator;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;
    private static Address a1, a2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Person p1 = new Person("Thor", "Christensen", "thor@hammer.dk");
        Person p2 = new Person("Frederik", "Dahl", "freddy@wong.dk");
        Address a1 = new Address("Tagensvej 154");
        Address a2 = new Address("Frederiksbergvej 1");
        Phone phone1 = new Phone(30303030, "Hjem");
        Phone phone2 = new Phone(40404040, "Hjem");
        CityInfo ci1 = new CityInfo(4200, "Slagelse");
        CityInfo ci2 = new CityInfo(2000, "Frederiksberg");
        Hobby h1 = new Hobby("Bodybuilding", "Bb.dk", "Bodybuilding", "Træning");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();

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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAllPersons() {
        PersonsDTO result = facade.getAllPersons();
        assertEquals(2, result.getAll().size(), "Expects two rows in the database");
    }

    @Test
    public void testAddPerson() throws MissingInputException{
        System.out.println("TESTING SIZE AFTER ADD METHOD ....");
                
        String fName = "Josef";
        String lName = "Marc";
        String email = "josef@glostrup.dk";
        int phoneNumber = 12345678; 
        String phoneDesc = "work";
        String street = "Jernbanevej";
        String city = "Frederiksberg";
        String hobbyName = "Bodybuilding";
        int zip = 2000; 
        //ZIP is already in DB thats why "2000" is being used. 
        facade.addPerson(fName, lName, email, phoneNumber, phoneDesc, street, hobbyName, city,zip);  
        System.out.println("TESTING SIZE AFTER ADD METHOD");
        assertEquals(3, facade.getAllPersons().getAll().size(), "Expects three rows in the database");
    }
    
    
    @Disabled
    @Test
    public void testEditPerson() throws Exception {
        System.out.println("editPerson");
        PersonDTO p = new PersonDTO(p1);
        PersonDTO expResult = new PersonDTO(p1);
        expResult.setfName("Thomas");
        p.setfName("Thomas");
        PersonDTO result = facade.editPerson(p);
        assertEquals(expResult.getfName(), result.getfName());
    }
    @Disabled
    @Test
    public void testEditPersonNotFoundException() {
        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
            PersonDTO p4DTO = new PersonDTO(p2);
            p4DTO.setId(8);
            facade.editPerson(p4DTO);
        });
    }
    @Disabled
    @Test
    public void testEditPersonMissingInput() {
        Exception exception = assertThrows(MissingInputException.class, () -> {
            PersonDTO p4DTO = new PersonDTO(p2);
            p4DTO.setfName("");
            p4DTO.setlName("");
            facade.editPerson(p4DTO);
        });
    }
}
