package errorhandling.mappers;

import DTOs.ExceptionDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.exceptions.SportCreationException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nikolaj Larsen
 */
@Provider
public class SportCreationExceptionMapper implements ExceptionMapper<SportCreationException>{

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final int ERROR_CODE = 400;
    @Context
    ServletContext context;
    
    @Override
    public Response toResponse(SportCreationException exception) {
        ExceptionDTO err = new ExceptionDTO(ERROR_CODE, exception.getMessage());
        return Response.status(ERROR_CODE).entity(gson.toJson(err)).type(MediaType.APPLICATION_JSON).build();
    }
    
}
