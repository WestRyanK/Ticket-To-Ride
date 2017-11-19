package byu.codemonkeys.tickettoride.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private static final int MIN_ARGUMENTS = 1;
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    public static void main(String[] args) {
        if (args.length < MIN_ARGUMENTS) {
//            System.out.println("USAGE: java ServerCommunicator <port>");
//            return;
			args = new String[] { "8080"};
        }

        int port;

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        new ServerCommunicator().runServer(port);
    }

    private void runServer(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        server.createContext("/", new CommandHandler());

        server.start();

        System.out.printf("Server started on port %d\n", port);
    }
}
