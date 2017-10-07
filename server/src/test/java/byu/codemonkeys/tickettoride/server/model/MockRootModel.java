package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;

public class MockRootModel implements IRootModel {
    @Override
    public boolean verifyLogin(String username, String password) {
        if (username.equals("username") && password.equals("password")) {
            return true;
        }

        return false;
    }

    @Override
    public boolean authorize(String authToken) {
        if (authToken.equals("auth-token")) {
            return true;
        }

        return false;
    }

    @Override
    public void removeSession(String authToken) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void registerNewUser(String username, String password) {
        if (username.equals("username")) {
            throw new AlreadyExistsException();
        }
    }

    @Override
    public String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addSession(ServerSession session) {

    }

    @Override
    public ServerSession getSession(String authToken) {
        if (authToken.equals("auth-token")) {
            User user = new User("username", "password");
            return new ServerSession(user, "auth-token");
        }

        if (authToken.equals("auth-token-0")) {
            User user = new User("user0", "password");
            return new ServerSession(user, "auth-token-0");
        }

        return null;
    }

    @Override
    public List<PendingGame> getPendingGames() {
        List<PendingGame> games = new ArrayList<>();

        User user = new User("username", "password");

        games.add(new PendingGame("game-1-id", "game-1-name", user));

        return games;
    }

    @Override
    public PendingGame getPendingGame(String gameID) {
        if (gameID.equals("game-1-id")) {
            User user = new User("username", "password");

            return new PendingGame("game-1-id", "game-1-name", user);
        }

        if (gameID.equals("game-2-id")) {
            User user = new User("user", "secret");

            return new PendingGame("game-2-id", "game-2-name", user);
        }

        if (gameID.equals("full-game-id")) {
            User user = new User("user0", "password");

            PendingGame game = new PendingGame("full-game-id", "full-game-name", user);

            for (int i = 1; i < 5; ++i) {
                game.addUser(new User("user" + i, "secret"));
            }

            return game;
        }

        return null;
    }

    @Override
    public void addPendingGame(PendingGame game) {

    }

    @Override
    public void removePendingGame(String gameID) {

    }

    @Override
    public ActiveGame activateGame(String gameID) throws NoSuchElementException {
        if (gameID.equals("game-1-id")) {
            User user = new User("username", "password");
            PendingGame game = new PendingGame("game-1-id", "game-1-name", user);

            return new ActiveGame(game);
        }

        return null;
    }
}
