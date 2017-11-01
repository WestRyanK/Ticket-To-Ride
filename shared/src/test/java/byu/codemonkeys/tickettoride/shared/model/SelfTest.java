package byu.codemonkeys.tickettoride.shared.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCardLoader;

import static org.junit.Assert.*;

public class SelfTest {
    @Test
    public void testSelect() {
        Self self = new Self("username", PlayerColor.Black);

        assertEquals(0, self.getSelecting().size());

        List<DestinationCard> destinations = DestinationCardLoader.getInstance().loadDestinationCardsFromResources();

        Set<DestinationCard> drawn = new HashSet<>();
        for (int i = 0; i < 3; ++i) {
            drawn.add(destinations.get(i));
        }

        self.giveDestinationCards(drawn);

        assertEquals(3, self.getSelecting().size());

        self.select(destinations.get(0));

        assertEquals(2, self.getSelecting().size());
        assertEquals(1, self.getNumDestinationCards());
    }
}