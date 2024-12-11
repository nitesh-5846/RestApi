package main.java.com.example.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/players")
public class PlayerResource {
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addPlayer(Player player)
//	{
//		try {
//			DatabaseUtil.insertPlayer(player.getId(), player.getPlayerName(), player.getAge(), player.getCity(),
//					player.getTeam(), player.getNumberOfMatches(), player.getHighestScore());
//			return Response.status(Response.Status.CREATED).entity(player).build();
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//		}
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPlayer(Player player) {
	    // Log the received player object
	    System.out.println("Received player: " + player);

	    if (player == null) {
	        return Response.status(Response.Status.BAD_REQUEST).entity("Invalid player data").build();
	    }

	    try {
	        // Log before calling the insert method
	        System.out.println("Inserting player: " + player.getPlayerName());

	        DatabaseUtil.insertPlayer(
	                player.getId(), player.getPlayerName(), player.getAge(),
	                player.getCity(), player.getTeam(), player.getNumberOfMatches(),
	                player.getHighestScore()
	        );

	        return Response.status(Response.Status.CREATED)
	                .entity(player)  // Return the player data
	                .build();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Error inserting player into the database").build();
	    }
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlayers(@QueryParam("id") int id, @QueryParam("playerName") String playerName)
	{
		try {
			ResultSet rs;
			if(id > 0)
				rs = DatabaseUtil.getPlayers(id);
			else if (playerName != null)
				rs = DatabaseUtil.getPlayers(playerName);
			else
				rs = DatabaseUtil.getPlayers();
			
			StringBuilder result = new StringBuilder("[");
			while(rs.next())
			{
				result.append("{")
					  .append("\"id\": ").append(rs.getInt("id")).append(",")
					  .append("\"playerName\": \"").append(rs.getString("playerName")).append("\",")
					  .append("\"age\": ").append(rs.getInt("age")).append(",")
					  .append("\"city\": \"").append(rs.getString("city")).append("\",")
					  .append("\"team\": \"").append(rs.getString("team")).append("\",")
					  .append("\"numberOfMatches\": ").append(rs.getInt("numberOfMatches")).append(",")
					  .append("\"highestScore\": ").append(rs.getInt("highestScore"))
					  .append("},");				
			}
			
			if(result.length() > 1)
			{
				result.deleteCharAt(result.length() - 1);
			}			

		    result.append("]");
			return Response.ok(result.toString()).build();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
//	@GET
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getPlayers(@PathParam("id") int id)
//	{
//		try {
//			ResultSet rs = DatabaseUtil.getPlayers(id);
//			StringBuilder result = new StringBuilder();
//			while(rs.next())
//			{
//				
//				result.append("{")
//					  .append("\"id\": ").append(rs.getInt("id")).append(",")
//					  .append("\"name\": ").append(rs.getString("playerName")).append(",")
//					  .append("\"age\": ").append(rs.getInt("age")).append(",")
//					  .append("\"city\": ").append(rs.getString("city")).append(",")
//					  .append("\"team\": ").append(rs.getString("team")).append(",")
//					  .append("\"numberOfMatches\": ").append(rs.getInt("numberOfMatches")).append(",")
//					  .append("\"highestScore\": ").append(rs.getInt("highestScore"))
//					  .append("}");				
//			}
//			return Response.ok(result.toString()).build();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//		}
//	}
//	
//	@GET
//	@Path("/{playerName}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getPlayers(@PathParam("playerName") String playerName)
//	{
//		try {
//			ResultSet rs = DatabaseUtil.getPlayers(playerName);
//			StringBuilder result = new StringBuilder();
//			while(rs.next())
//			{
//				
//				result.append("{")
//					  .append("\"id\": ").append(rs.getInt("id")).append(",")
//					  .append("\"name\": ").append(rs.getString("playerName")).append(",")
//					  .append("\"age\": ").append(rs.getInt("age")).append(",")
//					  .append("\"city\": ").append(rs.getString("city")).append(",")
//					  .append("\"team\": ").append(rs.getString("team")).append(",")
//					  .append("\"numberOfMatches\": ").append(rs.getInt("numberOfMatches")).append(",")
//					  .append("\"highestScore\": ").append(rs.getInt("highestScore"))
//					  .append("}");				
//			}
//			return Response.ok(result.toString()).build();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//		}
//	}
}
