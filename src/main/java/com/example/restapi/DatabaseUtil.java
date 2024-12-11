package main.java.com.example.restapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtil {
	
	private static final String URL = "jdbc:mysql://0.0.0.0:3306/testDB";
	private static final String USER = "test1";
	private static final String PASSWORD = "test1";
	
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error while entering data in the database.");
		}
	}
	
	public static ResultSet getPlayers() throws SQLException
	{
		Connection con = null;
		try {
			System.out.println("Getting all the players");
			String query = "SELECT * FROM Cricket_Players";
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			return ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
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
		System.out.println("Getting players with name - " + playerName);
		String query = "Select * FROM Cricket_Players WHERE playerName = '" + playerName + "'";
		Connection con = getConnection();
		PreparedStatement ps= con.prepareStatement(query);
		return ps.executeQuery();		
	}
}
