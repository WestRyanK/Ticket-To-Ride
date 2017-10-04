package byu.codemonkeys.tickettoride.models;

import java.util.List;

/**
 * Created by Ryan on 9/29/2017.
 */

public class PendingGame {
	public static final int MIN_ALLOWED_PLAYERS = 2;
	public static final int MAX_ALLOWED_PLAYERS = 5;
	
	private String gameName;
	private int minPlayers;
	private int maxPlayers;
	
	public PendingGame(String gameName, int minPlayers, int maxPlayers) {
		this.gameName = gameName;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	private List<Player> players;
	
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
