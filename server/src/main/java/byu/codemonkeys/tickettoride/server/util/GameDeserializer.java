package byu.codemonkeys.tickettoride.server.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import byu.codemonkeys.tickettoride.server.model.Deck;
import byu.codemonkeys.tickettoride.server.model.TrackedGame;
import byu.codemonkeys.tickettoride.shared.RuntimeTypeAdapterFactory;
import byu.codemonkeys.tickettoride.shared.commands.BeginGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.commands.DeckTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsChosenCommandData;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardsReshuffledCommandData;
import byu.codemonkeys.tickettoride.shared.commands.GameOverCommandData;
import byu.codemonkeys.tickettoride.shared.commands.LastTurnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.NextTurnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.RouteClaimedCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SetupGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SkipTurnCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.cards.IDeck;
import byu.codemonkeys.tickettoride.shared.model.turns.ActiveTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.OtherTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;

public class GameDeserializer {
    private static final RuntimeTypeAdapterFactory<CommandData> commandTypeFactory = RuntimeTypeAdapterFactory
            .of(CommandData.class, "commandType")
            .registerSubtype(SendMessageCommandData.class, CommandType.SEND_MESSAGE)
            .registerSubtype(SetupGameCommandData.class, CommandType.SETUP_GAME)
            .registerSubtype(BeginGameCommandData.class, CommandType.BEGIN_GAME)
            .registerSubtype(FaceUpTrainCardDrawnCommandData.class, CommandType.FACEUP_TRAIN_CARD_DRAWN)
            .registerSubtype(DeckTrainCardDrawnCommandData.class, CommandType.DECK_TRAIN_CARD_DRAWN)
            .registerSubtype(DestinationCardsChosenCommandData.class,
                    CommandType.DESTINATION_CARDS_CHOSEN)
            .registerSubtype(DestinationCardsDrawnCommandData.class,
                    CommandType.DESTINATION_CARDS_DRAWN)
            .registerSubtype(FaceUpTrainCardsReshuffledCommandData.class,
                    CommandType.FACEUP_TRAIN_CARDS_RESHUFFLED)
            .registerSubtype(SkipTurnCommandData.class, CommandType.SKIP_TURN)
            .registerSubtype(RouteClaimedCommandData.class, CommandType.ROUTE_CLAIMED)
            .registerSubtype(NextTurnCommandData.class, CommandType.NEXT_TURN)
            .registerSubtype(GameOverCommandData.class, CommandType.GAME_OVER)
            .registerSubtype(LastTurnCommandData.class, CommandType.LAST_TURN);

    private static final RuntimeTypeAdapterFactory<Player> playerTypeFactory = RuntimeTypeAdapterFactory
            .of(Player.class, "type")
            .registerSubtype(Self.class, Player.Type.Self)
            .registerSubtype(Opponent.class, Player.Type.Opponent);

    private static final RuntimeTypeAdapterFactory<IDeck> deckTypeFactory = RuntimeTypeAdapterFactory
            .of(IDeck.class, "type")
            .registerSubtype(Deck.class, "deck");

    private static final RuntimeTypeAdapterFactory<Turn> turnTypeFactory = RuntimeTypeAdapterFactory
            .of(Turn.class, "type")
            .registerSubtype(ActiveTurn.class, ActiveTurn.ACTIVE_TURN)
            .registerSubtype(OtherTurn.class, OtherTurn.OTHER_TURN);

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(commandTypeFactory)
            .registerTypeAdapterFactory(playerTypeFactory)
            .registerTypeAdapterFactory(deckTypeFactory)
            .registerTypeAdapterFactory(turnTypeFactory)
            .create();

    public static TrackedGame deserialize(String json) {
        return gson.fromJson(json, TrackedGame.class);
    }
}
