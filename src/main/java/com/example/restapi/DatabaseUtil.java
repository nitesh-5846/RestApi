package main.java.com.example.restapi;

import redis.clients.jedis.Jedis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtil {

	private static final String URL = "jdbc:mysql://db:3306/testDB";
	private static final String USER = "test1";
	private static final String PASSWORD = "test1";
	private static final String REDIS_HOST = "redis";
	private static final int REDIS_PORT = 6379;


	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void insertPlayer(int id, String playerName, int age, String city, String team, int numberOfMatches, int highestScore) throws SQLException
	{
		String query = "INSERT INTO Cricket_Players (id, playerName, age, city, team, numberOfMatches, highestScore) VALUES (?,?,?,?,?,?,?)";
		try(Connection con = getConnection())
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.setString(2, playerName);
			ps.setInt(3, age);
			ps.setString(4, city);
			ps.setString(5, team);
			ps.setInt(6, numberOfMatches);
			ps.setInt(7,  highestScore);
			ps.executeUpdate();

			// Invalidate the Redis cache for all players
			try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)){
				jedis.del("all_players");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error while entering data in the database.");
		}
	}

	public static ResultSet getPlayers() throws SQLException
	{
		System.out.println("Getting all the players");

		//Check if data exists in Redis
		try(Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)){
			if(jedis.exists("all_players")){
				System.out.println("Cache hit for all players");
				String cachedData = jedis.get("all_players");
				return ResultSetUtils.fromJson(cachedData);
			}

			//Cache miss: Fetch from MySQL
			String query = "SELECT * FROM Cricket_Players";
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs =  ps.executeQuery();

			//Convert ResultSet into Json and store it in  Redis
			String jsonData = ResultSetUtils.toJson(rs);
			jedis.setex("all_players", 3600, jsonData); //Cache for 1 hour

			// Return the simulated ResultSet from the JSON
	                return ResultSetUtils.fromJson(jsonData);

		}
	}

	public static ResultSet getPlayers(int id) throws SQLException
	{
		System.out.println("Getting player with id - " + id);
		String query = "Select * FROM Cricket_Players WHERE id = " + id;
		Connection con = getConnection();
		PreparedStatement ps= con.prepareStatement(query);
		return ps.executeQuery();
	}

	public static ResultSet getPlayers(String playerName) throws SQLException
	{

		//Check if data exists in Redis
                try(Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)){ 
                        if(jedis.exists("player_" + playerName)){ 
                                System.out.println("Cache hit for all players");
                                String cachedData = jedis.get("player_" + playerName);
                                return ResultSetUtils.fromJson(cachedData);
                        }


			System.out.println("Getting players with name - " + playerName);
			String query = "Select * FROM Cricket_Players WHERE playerName = '" + playerName + "'";
			Connection con = getConnection();
			PreparedStatement ps= con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			// Convert ResultSet to JSON and store in Redis
	           	 String jsonData = ResultSetUtils.toJson(rs);
	    		jedis.setex("player_" + playerName, 3600, jsonData); // Cache for 1 hour

			// Return the simulated ResultSet from the JSON
                        return ResultSetUtils.fromJson(jsonData);

		}
	}
}
