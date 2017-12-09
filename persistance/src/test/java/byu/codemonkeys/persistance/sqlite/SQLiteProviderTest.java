package byu.codemonkeys.persistance.sqlite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import byu.codemonkeys.persistance.IUserDAO;

import static org.junit.Assert.*;

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
}