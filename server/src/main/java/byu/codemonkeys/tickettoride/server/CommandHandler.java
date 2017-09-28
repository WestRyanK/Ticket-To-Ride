package byu.codemonkeys.tickettoride.server;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import byu.codemonkeys.tickettoride.server.commands.RegisterCommand;
import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

class CommandHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Serializer serializer = new Serializer();

            Result result = getCommand(exchange).execute();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            send(serializer.serialize(result), exchange);
        } catch (JsonSyntaxException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
     * @param exchange a HttpExchange.
     * @return a Command (an implementation of ICommand).
     */
    private ICommand getCommand(HttpExchange exchange) throws JsonSyntaxException {
        return new RegisterCommand("user", "password");
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
