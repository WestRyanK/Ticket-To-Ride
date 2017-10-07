package byu.codemonkeys.tickettoride.shared.model;


import java.util.List;

public class GameBase {
	protected String gameID, gameName;
	protected UserBase gameOwner;
	protected List<UserBase> gameUsers;
	
	public static int MIN_PLAYERS = 2;
	public static int MAX_PLAYERS = 5;
	public static int MIN_GAME_NAME_LENGTH = 6;
	public static int MAX_GAME_NAME_LENGTH = 12;
	
	
	public String getID() {
		return gameID;
	}
	
	public String getName() {
		return gameName;
	}
	
	public UserBase getOwner() {
		return gameOwner;
	}
	
	public List<UserBase> getUsers() {
		return gameUsers;
	}
	
	public boolean hasUser(UserBase user) {
		return gameUsers.contains(user);
	}
	
	public static boolean isValidGameName(String gameName) {
		return (gameName != null &&
				gameName.length() >= MIN_GAME_NAME_LENGTH &&
				gameName.length() <= MAX_GAME_NAME_LENGTH);
	}
	
	public static boolean canStartGame(GameBase game) {
		return game != null &&
				game.getUsers().size() >= MIN_PLAYERS &&
				game.getUsers().size() <= MAX_PLAYERS;
	}
}
