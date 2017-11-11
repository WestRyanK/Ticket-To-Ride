package byu.codemonkeys.tickettoride.server;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import byu.codemonkeys.tickettoride.server.commands.*;
import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.commands.DrawDeckTrainCardCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.server.exceptions.InvalidCommandException;
import byu.codemonkeys.tickettoride.shared.results.DrawDeckTrainCardResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

class CommandHandler implements HttpHandler {
    private Serializer serializer = new Serializer();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Result result = getCommand(exchange).execute();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            send(serializer.serialize(result), exchange);
        } catch (JsonSyntaxException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        } catch (InvalidCommandException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    /**
     * Uses the request URI and request body of the specified exchange to construct the command to
     * be executed.
     * @param exchange an HttpExchange.
     * @return a Command (an implementation of ICommand).
     */
    ICommand getCommand(HttpExchange exchange) throws JsonSyntaxException {

        String path = exchange.getRequestURI().getPath();
        String type = getCommandTypeFromPath(path);
        InputStream requestBody = exchange.getRequestBody();

        return buildCommand(type, requestBody);
    }

    private ICommand buildCommand(String type, InputStream requestBody) {
        switch (type) {
            case CommandType.CANCEL_GAME:
                return serializer.deserialize(requestBody, CancelGameCommand.class);
            case CommandType.CREATE_GAME:
                return serializer.deserialize(requestBody, CreateGameCommand.class);
            case CommandType.GET_PENDING_GAMES:
                return serializer.deserialize(requestBody, GetPendingGamesCommand.class);
            case CommandType.GET_PENDING_GAME:
                return serializer.deserialize(requestBody, GetPendingGameCommand.class);
            case CommandType.JOIN_PENDING_GAME:
                return serializer.deserialize(requestBody, JoinPendingGameCommand.class);
            case CommandType.LEAVE_PENDING_GAME:
                return serializer.deserialize(requestBody, LeavePendingGameCommand.class);
            case CommandType.LOGIN:
                return serializer.deserialize(requestBody, LoginCommand.class);
            case CommandType.LOGOUT:
                return serializer.deserialize(requestBody, LogoutCommand.class);
            case CommandType.REGISTER:
                return serializer.deserialize(requestBody, RegisterCommand.class);
            case CommandType.START_GAME:
                return serializer.deserialize(requestBody, StartGameCommand.class);
            case CommandType.SEND_MESSAGE:
                return serializer.deserialize(requestBody, SendMessageCommand.class);
            case CommandType.UPDATE_HISTORY:
                return serializer.deserialize(requestBody, UpdateHistoryCommand.class);
            case CommandType.CHOOSE_DESTINATION_CARDS:
                return serializer.deserialize(requestBody, ChooseDestinationCardsCommand.class);
            case CommandType.DRAW_DECK_TRAIN_CARD:
                return serializer.deserialize(requestBody, DrawDeckTrainCardCommand.class);
            default:
                throw new InvalidCommandException();
        }
    }

    private String getCommandTypeFromPath(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * Sends the specified String to the client as the response body of the specified exchange.
     * @param string a String.
     * @param exchange a HttpExchange with an open response body.
     * @throws IOException
     */
    private void send(String string, HttpExchange exchange) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());

        writer.write(string);
        writer.close();
    }
}
