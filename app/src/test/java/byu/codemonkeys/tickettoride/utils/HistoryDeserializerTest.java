package byu.codemonkeys.tickettoride.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.commands.IClientCommand;
import byu.codemonkeys.tickettoride.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.commands.SetupGameCommand;
import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SetupGameCommandData;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.Self;
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
	public void TestSetupGameCommand() {
		List<CommandData> commands = new ArrayList<>();
		String json = "{\"history\":[{\"game\":{\"map\":{\"changed\":false,\"obs\":[]},\"turn\":0,\"players\":[{\"hand\":{\"Green\":1,\"Wild\":2,\"Red\":1},\"destinations\":[],\"selecting\":[{\"id\":0,\"destinationA\":6,\"destinationB\":21,\"pointValue\":21},{\"id\":2,\"destinationA\":18,\"destinationB\":30,\"pointValue\":8},{\"id\":1,\"destinationA\":25,\"destinationB\":8,\"pointValue\":8}],\"type\":\"self\",\"score\":0,\"color\":\"Blue\",\"numTrainCards\":0,\"numDestinationCards\":0,\"numTrains\":45,\"userName\":\"westryank\",\"changed\":false,\"obs\":[]}],\"deck\":{\"type\":\"deck\",\"revealed\":[{\"cardColor\":\"Wild\"},{\"cardColor\":\"Green\"},{\"cardColor\":\"Green\"},{\"cardColor\":\"Orange\"},{\"cardColor\":\"Purple\"}],\"numHidden\":101,\"numDestinationCards\":27},\"gameID\":\"3be079c0-667c-4d26-8825-bf4dec7bab60\",\"gameName\":\"Jhgfds\",\"gameOwner\":{\"password\":\"password\",\"userName\":\"westryank\",\"changed\":false,\"obs\":[]},\"gameUsers\":[{\"password\":\"password\",\"userName\":\"westryank\",\"changed\":false,\"obs\":[]}],\"started\":true,\"changed\":false,\"obs\":[]},\"numTrains\":0,\"commandType\":\"setupGame\",\"queuedPosition\":0}],\"successful\":true}";
//		String json = "{\"history\":[{\"game\":{\"map\":{},\"turn\":0,\"players\":[{\"hand\":{\"Red\":1,\"Green\":2,\"Blue\":1},\"destinations\":[],\"selecting\":[null],\"type\":\"self\",\"score\":0,\"color\":\"BLUE\",\"numTrainCards\":0,\"numDestinationCards\":0,\"numTrains\":45,\"userName\":\"Jacob\"},{\"type\":\"opponent\",\"score\":0,\"color\":\"RED\",\"numTrainCards\":4,\"numDestinationCards\":0,\"numTrains\":45,\"userName\":\"Ryan\"}],\"deck\":{\"numHidden\":102,\"numDestinationCards\":0},\"gameID\":\"48804c68-fb88-4df7-86b1-803edc1e53c8\",\"gameName\":\"My Game\",\"gameOwner\":{\"password\":\"password\",\"userName\":\"Jacob\"},\"gameUsers\":[{\"password\":\"password\",\"userName\":\"Jacob\"},{\"password\":\"password\",\"userName\":\"Ryan\"}],\"started\":true},\"numTrains\":0,\"commandType\":\"setupGame\",\"queuedPosition\":0}],\"successful\":true}";
		
		HistoryResult result = HistoryDeserializer.deserializeHistoryResult(json);
		
		assertNotNull(result);
		assertTrue(result.isSuccessful());
		assertEquals(1, result.getHistory().size());
		
		CommandData command = result.getHistory().get(0);
		
		assertTrue(command instanceof SetupGameCommandData);
		assertTrue(command instanceof IClientCommand);
		
		SetupGameCommandData setupGameCommand = (SetupGameCommandData) command;
		
		assertTrue(setupGameCommand.getGame().getSelf() instanceof Self);
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
