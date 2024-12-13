package main.java.com.example.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	
	@JsonProperty("id")
	private int id;
	@JsonProperty("playerName")
	private String playerName;
	@JsonProperty("age")
	private int age;
	@JsonProperty("city")
	private String city;
	@JsonProperty("team")
	private String team;
	@JsonProperty("numberOfMatches")
	private int numberOfMatches;
	@JsonProperty("highestScore")
	private int highestScore;	
	
	public Player() { }
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}	
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}	
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}	
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getTeam() {
		return team;
	}
	
	public void setTeam(String team) {
		this.team = team;
	}
	
	public int getNumberOfMatches() {
		return numberOfMatches;
	}
	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}
	
	public int getHighestScore() {
		return highestScore;
	}
	
	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}
	
}
