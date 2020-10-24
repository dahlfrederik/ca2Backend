package rest;

import dto.PersonDTO;
import dto.PersonsDTO;
import dto.PhoneDTO;
import dto.PhonesDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import static io.restassured.path.json.config.JsonParserType.GSON;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static PersonFacade facade;
    private static Person p1, p2;
    private static Address a1, a2;
    private static Hobby h3;
    private static Phone phone1; 

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacadeExample(emf);
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p2 = new Person("Thor", "Christensen", "thor@hammer.dk");
        p1 = new Person("Frederik", "Dahl", "fd@td.dk");
        Address a1 = new Address("Tagensvej");
        Address a2 = new Address("Frederiksbergvej 1");
        phone1 = new Phone(20460020, "Hjem");
        Phone phone2 = new Phone(40404040, "Hjem");
        CityInfo ci1 = new CityInfo(2200, "Slagelse");
        CityInfo ci2 = new CityInfo(4200, "Frederiksberg");
        a1.setCityInfo(ci1);
        a2.setCityInfo(ci2);
        Hobby h1 = new Hobby("Bodybuilding", "Bb.dk", "Bodybuilding", "Træning");
        h3 = new Hobby("Træfældning", "træ.dk", "Chop chop", "Udendørs");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();

            p1.setAddress(a1);
            p1.addHobby(h1);
            p2.setAddress(a2);
            p2.addHobby(h1);
            p1.addPhone(phone1);
            p2.addPhone(phone2);
            em.persist(h3);
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons").then().statusCode(200);
    }

    // @Disabled
    @Test
    public void getAllPersons() {
        List<PersonDTO> personsDTOs
                = given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/persons/")
                        .then()
                        .extract().body().jsonPath().getList("all", PersonDTO.class);

        String p1 = "Frederik";
        String p2 = "Thor";

        List<String> personsDTOnames = new ArrayList();
        personsDTOnames.add(p1);
        personsDTOnames.add(p2);

        List<String> resultList = new ArrayList();
        String res1 = personsDTOs.get(0).getfName();
        String res2 = personsDTOs.get(1).getfName();
        resultList.add(res1);
        resultList.add(res2);

        assertTrue(resultList.contains(p1));
        assertTrue(resultList.contains(p2));
    }


    @Test
    public void addPerson() {
       
        PersonDTO p = new PersonDTO(p1);
        p.setHobbyName(p1.getHobbies().get(0).getName());
        p.setPhoneDesc(p1.getPhones().get(0).getDesc());
        List<Phone> pList = new ArrayList(); 
        pList.add(phone1); 
        PhonesDTO pDTO = new PhonesDTO(pList); 
        p.setPhoneList(pDTO);
        p.setPhoneNumber(20460020);
        
        given()
                .contentType(ContentType.JSON)
                .body(p)
                .when()
                .post("persons")
                .then()
                .body("fName", equalTo("Frederik"))
                .body("lName", equalTo("Dahl"))
                .body("email", equalTo("fd@td.dk"))
                .body("phoneNumber", equalTo(20460020))
                .body("phoneDesc", equalTo("Hjem"))
                .body("street", equalTo("Tagensvej"))
                .body("zip", equalTo(2200))
                .body("hobbyName", equalTo("Bodybuilding"))
                .body("id", notNullValue());
    }
    

}
