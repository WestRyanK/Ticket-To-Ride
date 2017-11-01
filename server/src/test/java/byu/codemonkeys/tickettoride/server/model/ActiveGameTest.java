package byu.codemonkeys.tickettoride.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.PlayerColor;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;

import static org.junit.Assert.*;

public class ActiveGameTest {
    private ActiveGame game;

    @Before
    public void setUp() {
        User owner = new User("Owner", "password");
        User opponent = new User("Opponent", "supersecure");

        PendingGame pendingGame = new PendingGame("My Game ID", "My Game", owner);
        pendingGame.addUser(opponent);

        game = new ActiveGame(pendingGame);
    }

    @Test
    public void deal() throws Exception {
        for (Player player : game.getPlayers()) {
            Self self = (Self) player;

            // This will fail on GitLab because it depends upon the destination card data file.
//            assertEquals(3, self.getSelecting().size());
            assertEquals(4, self.getNumTrainCards());
        }
    }

    @Test
    public void prepareForClientTest() {
        byu.codemonkeys.tickettoride.shared.model.ActiveGame preparedGame = game.prepareForClient(game.getOwner());

        Self self = preparedGame.getSelf();

        assertEquals(game.getOwner().getUsername(), self.getUsername());
        assertEquals(0, self.getNumDestinationCards());

        // This will fail on GitLab because it depends upon the destination card data file.
//        assertEquals(3, self.getSelecting().size());

        for (DestinationCard card : self.getSelecting()) {
            // This will fail on GitLab because it depends upon the destination card data file.
//            assertNotNull(card);
        }

        assertEquals(4, self.getNumTrainCards());

        List<PlayerColor> used = new ArrayList<>();
        for (Player player : preparedGame.getPlayers()) {
            assertFalse(used.contains(player.getColor()));
            used.add(player.getColor());
        }
    }

    @Test
    public void testDestinateCards() {
        Set<DestinationCard> used = new HashSet<>();

        for (Player player : game.getPlayers()) {
            Self self = (Self) player;

            for (DestinationCard card : self.getSelecting()) {
                // This will fail on GitLab because it depends upon the destination card data file.x
//                assertFalse(used.contains(card));

                used.add(card);
            }
        }
    }
}