package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Megan on 10/3/2017.
 */

public class ModelRoot implements ModelFacade {
	
	private static ModelRoot instance;
	
	public static ModelRoot getInstance() {
		if (instance == null)
			instance = new ModelRoot();
		return instance;
	}
	
	private ModelRoot() {
		this.currentUser = new User();
		this.pendingGames = new ArrayList<>();
		this.currentSession = new ClientSession();
	}
	
	private User currentUser;
	private ClientSession currentSession;
	private List<PendingGame> pendingGames;
	
	
	@Override
	public User getUser() {
		return null;
	}
	
	@Override
	public boolean loginUser(String username, String password) {
		return false;
	}
	
	@Override
	public boolean logoutUser() {
		return false;
	}
	
	@Override
	public boolean registerUser(String username, String password) {
		return false;
	}
	
	@Override
	public ClientSession getSession() {
		return null;
	}
	
	@Override
	public void setSession(ClientSession session) {
		
	}
	
	@Override
	public List<PendingGame> getPendingGames() {
		return null;
	}
	
	@Override
	public PendingGame createPendingGame(PendingGame game) {
		return null;
	}
	
	@Override
	public boolean joinPendingGame(PendingGame game) {
		return false;
	}
	
	@Override
	public boolean leavePendingGame() {
		return false;
	}
	
	@Override
	public Game startPendingGame(PendingGame game) {
		return null;
	}
}
