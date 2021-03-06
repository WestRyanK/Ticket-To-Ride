package byu.codemonkeys.tickettoride.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.commands.BeginGameCommand;
import byu.codemonkeys.tickettoride.commands.DeckTrainCardDrawnCommand;
import byu.codemonkeys.tickettoride.commands.DestinationCardsChosenCommand;
import byu.codemonkeys.tickettoride.commands.DestinationCardsDrawnCommand;
import byu.codemonkeys.tickettoride.commands.FaceUpTrainCardDrawnCommand;
import byu.codemonkeys.tickettoride.commands.GameOverCommand;
import byu.codemonkeys.tickettoride.commands.FaceUpTrainCardsReshuffledCommand;
import byu.codemonkeys.tickettoride.commands.LastTurnCommand;
import byu.codemonkeys.tickettoride.commands.RouteClaimedCommand;
import byu.codemonkeys.tickettoride.commands.NextTurnCommand;
import byu.codemonkeys.tickettoride.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.commands.SetupGameCommand;
import byu.codemonkeys.tickettoride.commands.SkipTurnCommand;
import byu.codemonkeys.tickettoride.shared.RuntimeTypeAdapterFactory;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.model.cards.Deck;
import byu.codemonkeys.tickettoride.shared.model.cards.IDeck;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.Self;
import byu.codemonkeys.tickettoride.shared.model.turns.ActiveTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.OtherTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;

public class HistoryDeserializer {
	private static final RuntimeTypeAdapterFactory<CommandData> commandTypeFactory = RuntimeTypeAdapterFactory
			.of(CommandData.class, "commandType")
			.registerSubtype(SendMessageCommand.class, CommandType.SEND_MESSAGE)
			.registerSubtype(SetupGameCommand.class, CommandType.SETUP_GAME)
			.registerSubtype(BeginGameCommand.class, CommandType.BEGIN_GAME)
			.registerSubtype(FaceUpTrainCardDrawnCommand.class, CommandType.FACEUP_TRAIN_CARD_DRAWN)
			.registerSubtype(DeckTrainCardDrawnCommand.class, CommandType.DECK_TRAIN_CARD_DRAWN)
			.registerSubtype(DestinationCardsChosenCommand.class,
							 CommandType.DESTINATION_CARDS_CHOSEN)
			.registerSubtype(DestinationCardsDrawnCommand.class,
							 CommandType.DESTINATION_CARDS_DRAWN)
			.registerSubtype(FaceUpTrainCardsReshuffledCommand.class,
							 CommandType.FACEUP_TRAIN_CARDS_RESHUFFLED)
			.registerSubtype(SkipTurnCommand.class, CommandType.SKIP_TURN)
			.registerSubtype(RouteClaimedCommand.class, CommandType.ROUTE_CLAIMED)
			.registerSubtype(NextTurnCommand.class, CommandType.NEXT_TURN)
			.registerSubtype(GameOverCommand.class, CommandType.GAME_OVER)
			.registerSubtype(LastTurnCommand.class, CommandType.LAST_TURN);
	
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
	
	private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(commandTypeFactory)
													  .registerTypeAdapterFactory(playerTypeFactory)
													  .registerTypeAdapterFactory(deckTypeFactory)
													  .registerTypeAdapterFactory(turnTypeFactory)
													  .create();
	
	public static List<CommandData> deserialize(String json) {
		Type listType = new TypeToken<List<CommandData>>() {
		}.getType();
		
		return gson.fromJson(json, listType);
	}
	
	public static <T> T deserializeObject(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	
	public static List<CommandData> deserialize(InputStream stream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		Type listType = new TypeToken<List<CommandData>>() {
		}.getType();
		
		return gson.fromJson(reader, listType);
	}
	
	public static HistoryResult deserializeHistoryResult(String json) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(json);
		List<CommandData> history = new ArrayList<>();
		if (!obj.get("successful").getAsBoolean()) {
			return new HistoryResult(obj.get("errorMessage").getAsString());
		}
		if (obj.has("history")) {
			JsonElement element = obj.get("history");
			history = HistoryDeserializer.deserialize(obj.get("history").toString());
		}
		
		return new HistoryResult(history);
	}
}
