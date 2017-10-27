package byu.codemonkeys.tickettoride.server.broadcast;


import byu.codemonkeys.tickettoride.shared.IClient;

public class ClientProxy implements IClient {
    private CommandManager manager;

    public ClientProxy(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void updatePendingGames() throws Exception {
        // no-op
    }

    @Override
    public void updatePendingGame() throws Exception {
        // no-op
    }
}
