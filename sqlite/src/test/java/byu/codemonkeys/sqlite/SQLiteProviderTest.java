package byu.codemonkeys.sqlite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import byu.codemonkeys.persistance.IActiveGameDAO;
import byu.codemonkeys.persistance.IUserDAO;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class SQLiteProviderTest {
    private SQLiteProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new SQLiteProvider();
        provider.init();
    }

    @After
    public void tearDown() {
        provider.clear();
    }

    @Test
    public void testUserDAO() {
        IUserDAO dao = provider.newUserDAO();

        String username = UUID.randomUUID().toString();
        String expectedData = "{\"username\":\"" + username + "\"}";
        dao.saveUserData(username, expectedData);
        String actualData = dao.getUserData(username);
        assertEquals(expectedData, actualData);
    }

    @Test
    public void testActiveGameDAO() {
        IActiveGameDAO dao = provider.newActiveGameDAO();

        String gameID = UUID.randomUUID().toString();
        String gameData = "{\"id\":\"" + gameID + "\"}";
        dao.saveGameData(gameID, gameData);

        String retrievedGameData = dao.getGameData(gameID);
        assertEquals(gameData, retrievedGameData);

        dao.saveGameData(gameID, gameData);

        String commandData = "{\"type\":\"command\"}";
        dao.saveCommandData(gameID, commandData);

        List<String> retrievedCommandData = dao.getAllCommandData(gameID);
        assertEquals(1, retrievedCommandData.size());
        assertEquals(commandData, retrievedCommandData.get(0));

        String gameID2 = UUID.randomUUID().toString();
        dao.saveGameData(gameID2, gameData);
        dao.saveCommandData(gameID2, commandData);
        dao.saveGameData(gameID, gameData);
        assertEquals(0, dao.getAllCommandData(gameID).size());
        assertEquals(1, dao.getAllCommandData(gameID2).size());

        dao.saveCommandData(gameID, commandData);
        dao.deleteGameData(gameID);

        assertNull(dao.getGameData(gameID));
        assertEquals(0, dao.getAllCommandData(gameID).size());
    }
}