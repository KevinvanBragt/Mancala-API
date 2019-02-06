package nl.sogyo.restservice;
import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import domain.Mancala;  
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/mancalaservice") 
public class MancalaService {
   private MancalaDao mancalaDao = MancalaDao.getMancalaDaoInstance(); 
   private ObjectMapper mapper = new ObjectMapper();

   
   @GET 
   @Path("/verify") 
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
   //for testing connection
   public String verify(){ 
       return "hello";
   }  
   
   @POST
   @Path("/session")
   @Produces({MediaType.APPLICATION_JSON})
   public Response createNewSession() {
	   String jsonResponse = null;
	   return Response.status(Response.Status.NOT_IMPLEMENTED).entity(jsonResponse).build();
   }
   
   @GET 
   @Path("/game/{id}") 
   @Produces({MediaType.APPLICATION_JSON}) 
   public Response getGameState(@PathParam("id") int id){ 
       Mancala mancala = mancalaDao.loadGame(id);
       String jsonResponse = null;
	   if (mancala == null) {
		   return Response.status(Response.Status.NOT_FOUND).entity("no mancala game has been found with id: " + id).build();
	   }
	   else {
		   jsonResponse = parseToJSON(mancala.getGameState());
		   return Response.status(Response.Status.OK).entity(jsonResponse).build();
	   }
	}  
   
   @POST 
   @Path("/game") 
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
   public Response createNewGame() {
	   Mancala mancala = new Mancala();
	   int id = mancalaDao.saveGame(mancala);
	   String jsonResponse = parseToJSON(id);
	   return Response.status(Response.Status.CREATED).entity(jsonResponse).build();
   }
   
   @PUT 
   @Path("/game/{id}/cup/{cupId}") 
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN}) 
   public Response takeTurn(@PathParam("id") int id, @PathParam("cupId") int cupId) {
	   Mancala mancala = mancalaDao.loadGame(id);
	   
	   if (mancala == null) {
		   return Response.status(Response.Status.NOT_FOUND).entity("no mancala game exists for the id of: " + id).build();
	   } 	   
	   mancala.makeMoveFacade(cupId);
	   return Response.status(Response.Status.OK).entity("move has been made").build();
   }
   
   private String parseToJSON(Object object) {
	   String json = null;
	   try {
		   json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		   } catch (JsonGenerationException e) { e.printStackTrace();
		   } catch (JsonMappingException e) { e.printStackTrace();
		   } catch (IOException e) { e.printStackTrace();
		   }
	   return json;
   }
   
   
}


