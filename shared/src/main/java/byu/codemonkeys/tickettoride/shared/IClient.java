package byu.codemonkeys.tickettoride.shared;

public interface IClient {
	void updatePendingGames() throws Exception;
	
	void updatePendingGame() throws Exception;

	void updateGame() throws Exception;
}
