package nl.sogyo.restservice;
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;
import domain.Mancala;  

@Path("/MancalaService") 

public class MancalaService {
   //MancalaDao mancalaDao = MancalaDao.getMancalaDaoInstance(); 
   @GET 
   @Path("/Gamestate") 
   @Produces(MediaType.APPLICATION_XML) 
   public String getGameState(){ 
       // get mancala with matching id
	   // then get gamestate
	   return "hello";
   }  


}


