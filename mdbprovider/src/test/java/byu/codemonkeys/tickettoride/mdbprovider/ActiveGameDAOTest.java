package byu.codemonkeys.tickettoride.mdbprovider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import byu.codemonkeys.persistance.IActiveGameDAO;

import static org.junit.Assert.*;

public class ActiveGameDAOTest {
    private MDBProvider provider;

    @Before
    public void setUp() {
        provider = new MDBProvider();
    }

    @After
    public void tearDown() {
        provider.clear();
    }

    @Test
    public void testSaveGameData() {
        IActiveGameDAO dao = provider.newActiveGameDAO();

        String game1ID = UUID.randomUUID().toString();
        String game1Data = UUID.randomUUID().toString();
        dao.saveGameData(game1ID, game1Data);

        assertEquals(game1Data, dao.getGameData(game1ID));

        String game2ID = UUID.randomUUID().toString();
        String game2Data = UUID.randomUUID().toString();
        dao.saveGameData(game2ID, game2Data);
        String commandData = UUID.randomUUID().toString();
        dao.saveCommandData(game1ID, commandData);
        dao.saveCommandData(game2ID, commandData);

        assertEquals(1, dao.getAllCommandData(game1ID).size());
        assertEquals(1, dao.getAllCommandData(game2ID).size());

        dao.saveGameData(game1ID, game1Data);

        assertEquals(0, dao.getAllCommandData(game1ID).size());
        assertEquals(1, dao.getAllCommandData(game2ID).size());
    }
}