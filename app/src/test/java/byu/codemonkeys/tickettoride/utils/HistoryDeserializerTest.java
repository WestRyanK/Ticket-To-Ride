package byu.codemonkeys.tickettoride.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.commands.IClientCommand;
import byu.codemonkeys.tickettoride.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;

import static org.junit.Assert.*;


public class HistoryDeserializerTest {

    @Test
    public void TestDeserializer() {
        List<CommandData> commands = new ArrayList<>();
        SendMessageCommand message1 = new SendMessageCommand(new Message());
        SendMessageCommand message2 = new SendMessageCommand(new Message());

        commands.add(message1);
        commands.add(message2);

        HistoryResult result = new HistoryResult(commands);

        String json = new Serializer().serialize(result);

        HistoryResult actualResult = HistoryDeserializer.deserializeHistoryResult(json);

        assertNotNull(actualResult);
        assertTrue(actualResult.isSuccessful());
        assertTrue(actualResult.getHistory().size() == 2);

        for (CommandData command : actualResult.getHistory()) {
            assertTrue(command instanceof SendMessageCommandData);
            assertTrue(command instanceof IClientCommand);
        }
    }

    @Test
    public void TestDeserializer_Failure() {

        String error = "Crap Broke";

        HistoryResult result = new HistoryResult(error);

        String json = new Serializer().serialize(result);

        HistoryResult actualResult = HistoryDeserializer.deserializeHistoryResult(json);

        assertNotNull(actualResult);
        assertFalse(actualResult.isSuccessful());
        assertEquals(error, actualResult.getErrorMessage());
    }

}
