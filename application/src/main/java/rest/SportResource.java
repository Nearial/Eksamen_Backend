package rest;

import DTOs.SportDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import errorhandling.exceptions.API_Exception;
import errorhandling.exceptions.DatabaseException;
import errorhandling.exceptions.SportCreationException;
import facades.SportFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Nikolaj Larsen
 */
@Path("sport")
public class SportResource {
    
        private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        private static final SportFacade FACADE = SportFacade.getSportFacade(EMF);
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    @POST
    @Path("create")
    @RolesAllowed("Admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create (String jsonString) throws API_Exception, DatabaseException, SportCreationException {
        String name, description;
        
        name = getStringFromJson("name", jsonString);
        description = getStringFromJson("description", jsonString);
        
        SportDTO sportDTO = FACADE.createSport(name, description);
        return Response.ok(sportDTO).build();
    }
    
    @GET
    @Path ("Sports")
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSports() {
        List<SportDTO> sports = FACADE.getAllSports();
        return Response.ok(sports).build();
    }
    
    private String getStringFromJson(String keyword, String jsonString) throws API_Exception {
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            return (json.get(keyword).getAsString());
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
    }
    
}
