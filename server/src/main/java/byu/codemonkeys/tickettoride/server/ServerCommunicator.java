package byu.codemonkeys.tickettoride.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private static final int MIN_ARGUMENTS = 3;
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private static final String CLEAR_FLAG = "-clear";

    private HttpServer server;

    public static void main(String[] args) {
        if (args.length < MIN_ARGUMENTS) {
           System.out.println("USAGE: java ServerCommunicator <port> <db plugin path> <N - max persistence deltas> [-clear]");
            return;
        }

        int port, numCommands;
        String pluginPath = args[1];

        try {
            port = Integer.parseInt(args[0]);
            numCommands = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        PersistenceFacade.initPersistanceFacade(numCommands, pluginPath);

        if (args.length > MIN_ARGUMENTS && CLEAR_FLAG.equals(args[MIN_ARGUMENTS]))
            PersistenceFacade.getInstance().clear();
        else
            PersistenceFacade.getInstance().restoreServer();

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
