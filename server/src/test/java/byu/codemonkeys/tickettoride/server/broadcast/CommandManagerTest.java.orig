package byu.codemonkeys.tickettoride.server.broadcast;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

import byu.codemonkeys.tickettoride.server.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.server.commands.UpdateHistoryCommand;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.model.Message;

public class CommandManagerTest {

    @Test
    public void TestCommandManager() {
        CommandManager manager = new CommandManager();
        manager.addClient("testClient1");
        manager.addClient("testClient2");

        SendMessageCommand command1 = new SendMessageCommand(new Message());
        SendMessageCommand command2 = new SendMessageCommand(new Message());

        manager.queueCommand(command1);
        manager.queueCommandSingleClient(command2, "testClient2");

        assertTrue(manager.getCommands("testClient1", UpdateHistoryCommand.NO_COMMANDS_SEEN_INDEX).size() == 1);
        assertTrue(manager.getCommands("testClient2", UpdateHistoryCommand.NO_COMMANDS_SEEN_INDEX).size() == 2);

        List<CommandData> commands = manager.getCommands("testClient2", 0);
        assertTrue(commands.size() == 1);

        CommandData expectedCommand = commands.get(0);
        assertEquals(command2, expectedCommand);
        assertEquals(1, expectedCommand.getQueuedPosition());
    }
}
