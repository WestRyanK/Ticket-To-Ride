package byu.codemonkeys.tickettoride.server;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.net.URI;

import byu.codemonkeys.tickettoride.server.commands.*;
import byu.codemonkeys.tickettoride.server.exceptions.InvalidCommandException;
import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;

public class CommandHandlerTest {
    private MockExchange exchange;

    @Test
    public void testGetLoginCommand() {
        String requestBody = String.format("{\"commandType\": \"%s\"}", CommandType.LOGIN);
        URI uri = URI.create(String.format("http://localhost:8080/%s", CommandType.LOGIN));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof LoginCommand);
    }

    @Test
    public void testGetRegisterCommand() {
        String requestBody = String.format("{\"commandType\": \"%s\"}", CommandType.REGISTER);
        URI uri = URI.create(String.format("http://localhost:8080/%s", CommandType.REGISTER));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof RegisterCommand);
    }

    @Test
    public void testGetCreateGameCommand() {
        String commandType = CommandType.CREATE_GAME;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof CreateGameCommand);
    }

    @Test
    public void testGetJoinGameCommand() {
        String commandType = CommandType.JOIN_PENDING_GAME;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof JoinPendingGameCommand);
    }

    @Test
    public void testGetStartGameCommand() {
        String commandType = CommandType.START_GAME;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof StartGameCommand);
    }

    @Test
    public void testGetCancelGameCommand() {
        String commandType = CommandType.CANCEL_GAME;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof CancelGameCommand);
    }

    @Test
    public void testGetPendingGamesCommand() {
        String commandType = CommandType.GET_PENDING_GAMES;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof GetPendingGamesCommand);
    }

    @Test
    public void testGetLeaveGameCommand() {
        String commandType = CommandType.LEAVE_PENDING_GAME;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof LeavePendingGameCommand);
    }

    @Test
    public void testGetLogoutCommand() {
        String commandType = CommandType.LOGOUT;
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        ICommand command = new CommandHandler().getCommand(exchange);
        assertTrue(command instanceof LogoutCommand);
    }

    @Test(expected = InvalidCommandException.class)
    public void testInvalidCommand() {
        String commandType = "NO_SUCH_COMMAND";
        String requestBody = String.format("{\"commandType\": \"%s\"}", commandType);
        URI uri = URI.create(String.format("http://localhost:8080/%s", commandType));

        exchange = new MockExchange();
        exchange.setRequestBody(new ByteArrayInputStream(requestBody.getBytes()));
        exchange.setRequestURI(uri);

        new CommandHandler().getCommand(exchange);
    }
}
