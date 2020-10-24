package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityInfoDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("persons")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade pf = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allPersons() {      
            PersonsDTO personsDTO = pf.getAllPersons();
            return GSON.toJson(personsDTO);    
    }
       
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("count/{hobbyName}")
    public String hobbyCount(@PathParam("hobbyName") String hobby) throws PersonNotFoundException {      
        long hobbyCount = pf.hobbyCount(hobby);
            return GSON.toJson(hobbyCount);    
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("zipcodes")
    public String allZipcodes() {      
            List<CityInfoDTO> zipCodes = pf.allZipCodes();
            return GSON.toJson(zipCodes);
    }
    
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path("persons/{zipCode}")
//    public String allPersonsByZipcode(@PathParam("zipCode") int zipCode) throws PersonNotFoundException {      
//        PersonsDTO personsDTO;
//        personsDTO = pf.allPersonsByZipcode(zipCode);
//            return GSON.toJson(personsDTO);    
//    }
//    
    

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String person) throws MissingInputException {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        PersonDTO pNew = pf.addPerson(p.getfName(), p.getlName(), p.getEmail(),p.getPhoneNumber(), p.getPhoneDesc(), p.getStreet(), p.getHobbyName(), p.getZip()); 
        
        return GSON.toJson(pNew);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException{
        PersonDTO pd = pf.deletePerson(id);
        return "Deleted: " + GSON.toJson(pd);
    }
    
    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editPerson(@PathParam("id") int id,  String person) throws PersonNotFoundException, MissingInputException {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        pDTO.setId(id); 
        PersonDTO pNew = pf.editPerson(pDTO);
        return GSON.toJson(pNew);
    }   
    

}
