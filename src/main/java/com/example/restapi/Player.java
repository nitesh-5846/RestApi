package main.java.com.example.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	
	private int id;
	private String playerName;
	private int age;
	private String city;
	private String team;
	private int numberOfMatches;
	private int highestScore;	
	
	@JsonProperty("id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty("playerName")
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@JsonProperty("age")
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@JsonProperty("city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@JsonProperty("team")
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	@JsonProperty("numberOfMatches")
	public int getNumberOfMatches() {
		return numberOfMatches;
	}
	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}
	
	@JsonProperty("highestScore")
	public int getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}
	
}
