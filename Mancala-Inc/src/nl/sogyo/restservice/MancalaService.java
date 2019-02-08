package nl.sogyo.restservice;
import java.io.IOException;
import java.util.HashMap;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import domain.Mancala;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/mancalaservice")
public class MancalaService {
	private MancalaDao mancalaDao = MancalaDao.getMancalaDaoInstance();
	private ObjectMapper mapper = new ObjectMapper();

	@GET
	@Path("/game/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getGameState(@PathParam("id") int id) {
		Mancala mancala = mancalaDao.getGame(id);
		StringBuilder jsonResponse = null;
		if (mancala == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("no mancala game has been found with id: " + id)
					.build();
		} else {
			jsonResponse = parseToJSON(mancala.getGameState());
			return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
		}
	}

	@GET
	@Path("/game")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getGames() {
		HashMap<Integer, Mancala> mancalaGamesMap = mancalaDao.getGames();
		StringBuilder jsonResponse = new StringBuilder("");
		for (int i : mancalaGamesMap.keySet()) {
			jsonResponse.append("id: " + parseToJSON(i) + " - gamestate:"
					+ parseToJSON(mancalaGamesMap.get(i).getGameState()) + "\n");
		}
		return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
	}

	@POST
	@Path("/game")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createNewGame() {
		Mancala mancala = new Mancala();
		int id = mancalaDao.setGame(mancala);
		StringBuilder jsonResponse = parseToJSON(id);
		return Response.status(Response.Status.CREATED).entity(jsonResponse.toString()).build();
	}

	@PUT
	@Path("/game/{id}/cup/{cupId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response takeTurn(@PathParam("id") int id, @PathParam("cupId") int cupId) {
		Mancala mancala = mancalaDao.getGame(id);

		if (mancala == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("no mancala game exists for the id of: " + id)
					.build();
		}
		mancala.makeMoveFacade(cupId);
		return Response.status(Response.Status.OK).entity("move has been made").build();
	}

	private StringBuilder parseToJSON(Object object) {
		StringBuilder json = null;
		try {
			json = new StringBuilder(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	@DELETE
	@Path("game/{id}")
	public Response deleteGame(@PathParam("id") int id) {
		if (mancalaDao.deleteGame(id)) {
			return Response.status(Response.Status.OK).entity("game has been deleted").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("game is not found").build();
		}
	}

}
