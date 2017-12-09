package byu.codemonkeys.tickettoride.server.util;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import byu.codemonkeys.tickettoride.server.commands.CancelGameCommand;
import byu.codemonkeys.tickettoride.server.commands.ChooseDestinationCardsCommand;
import byu.codemonkeys.tickettoride.server.commands.ClaimRouteCommand;
import byu.codemonkeys.tickettoride.server.commands.CreateGameCommand;
import byu.codemonkeys.tickettoride.server.commands.DrawDeckTrainCardCommand;
import byu.codemonkeys.tickettoride.server.commands.DrawDestinationCardsCommand;
import byu.codemonkeys.tickettoride.server.commands.DrawFaceUpTrainCardCommand;
import byu.codemonkeys.tickettoride.server.commands.GetExistingGamesCommand;
import byu.codemonkeys.tickettoride.server.commands.GetPendingGameCommand;
import byu.codemonkeys.tickettoride.server.commands.GetPendingGamesCommand;
import byu.codemonkeys.tickettoride.server.commands.JoinExistingGameCommand;
import byu.codemonkeys.tickettoride.server.commands.JoinPendingGameCommand;
import byu.codemonkeys.tickettoride.server.commands.LeavePendingGameCommand;
import byu.codemonkeys.tickettoride.server.commands.LoginCommand;
import byu.codemonkeys.tickettoride.server.commands.LogoutCommand;
import byu.codemonkeys.tickettoride.server.commands.RegisterCommand;
import byu.codemonkeys.tickettoride.server.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.server.commands.StartGameCommand;
import byu.codemonkeys.tickettoride.server.commands.UpdateHistoryCommand;
import byu.codemonkeys.tickettoride.server.exceptions.InvalidCommandException;
import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;

public class CommandUtils {
    private static Serializer serializer = new Serializer();
    private static JsonParser parser = new JsonParser();

    public static ICommand buildCommand(String json) {
        JsonObject jsonNode = parser.parse(json).getAsJsonObject();
        String type = jsonNode.get("commandType").getAsString();

        switch (type) {
            case CommandType.CANCEL_GAME:
                return serializer.deserialize(json, CancelGameCommand.class);
            case CommandType.CREATE_GAME:
                return serializer.deserialize(json, CreateGameCommand.class);
            case CommandType.GET_PENDING_GAMES:
                return serializer.deserialize(json, GetPendingGamesCommand.class);
            case CommandType.GET_PENDING_GAME:
                return serializer.deserialize(json, GetPendingGameCommand.class);
            case CommandType.JOIN_PENDING_GAME:
                return serializer.deserialize(json, JoinPendingGameCommand.class);
            case CommandType.LEAVE_PENDING_GAME:
                return serializer.deserialize(json, LeavePendingGameCommand.class);
            case CommandType.LOGIN:
                return serializer.deserialize(json, LoginCommand.class);
            case CommandType.LOGOUT:
                return serializer.deserialize(json, LogoutCommand.class);
            case CommandType.REGISTER:
                return serializer.deserialize(json, RegisterCommand.class);
            case CommandType.START_GAME:
                return serializer.deserialize(json, StartGameCommand.class);
            case CommandType.SEND_MESSAGE:
                return serializer.deserialize(json, SendMessageCommand.class);
            case CommandType.UPDATE_HISTORY:
                return serializer.deserialize(json, UpdateHistoryCommand.class);
            case CommandType.CHOOSE_DESTINATION_CARDS:
                return serializer.deserialize(json, ChooseDestinationCardsCommand.class);
            case CommandType.DRAW_DESTINATION_CARDS:
                return serializer.deserialize(json, DrawDestinationCardsCommand.class);
            case CommandType.DRAW_DECK_TRAIN_CARD:
                return serializer.deserialize(json, DrawDeckTrainCardCommand.class);
            case CommandType.DRAW_FACEUP_TRAIN_CARD:
                return serializer.deserialize(json, DrawFaceUpTrainCardCommand.class);
            case CommandType.CLAIM_ROUTE:
                return serializer.deserialize(json, ClaimRouteCommand.class);
            case CommandType.GET_EXISTING_GAMES:
                return serializer.deserialize(json, GetExistingGamesCommand.class);
            case CommandType.JOIN_EXISTING_GAME:
                return serializer.deserialize(json, JoinExistingGameCommand.class);
            default:
                throw new InvalidCommandException(type);
        }
    }
}
