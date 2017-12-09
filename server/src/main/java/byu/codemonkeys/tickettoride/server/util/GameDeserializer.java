package byu.codemonkeys.tickettoride.server.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import byu.codemonkeys.tickettoride.server.model.Deck;
import byu.codemonkeys.tickettoride.server.model.TrackedGame;
import byu.codemonkeys.tickettoride.shared.RuntimeTypeAdapterFactory;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.cards.IDeck;

public class GameDeserializer {
    private static final RuntimeTypeAdapterFactory<Player> playerTypeFactory = RuntimeTypeAdapterFactory
            .of(Player.class, "type")
            .registerSubtype(Self.class, Player.Type.Self)
            .registerSubtype(Opponent.class, Player.Type.Opponent);

    private static final RuntimeTypeAdapterFactory<IDeck> deckTypeFactory = RuntimeTypeAdapterFactory
            .of(IDeck.class, "type")
            .registerSubtype(Deck.class, "deck");

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(playerTypeFactory)
            .registerTypeAdapterFactory(deckTypeFactory)
            .create();

    public static TrackedGame deserialize(String json) {
        return gson.fromJson(json, TrackedGame.class);
    }
}
