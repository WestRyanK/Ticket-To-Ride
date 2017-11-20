package byu.codemonkeys.tickettoride.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.*;
import byu.codemonkeys.tickettoride.shared.results.*;
import byu.codemonkeys.tickettoride.utils.HistoryDeserializer;

public class ClientCommunicator {
	private static ClientCommunicator instance;
	
	private Serializer serializer;
	
	private String host;
	private int port;
	private static final String DEFAULT_HOST =
//			"10.24.222.148";
			"104.155.184.125";
	private static final int DEFAULT_PORT = 8080;
	
	private ClientCommunicator() {
		host = DEFAULT_HOST;
		port = DEFAULT_PORT;
		serializer = new Serializer();
	}
	
	/**
	 * Returns the single ClientCommunicator instance.
	 *
	 * @return the ClientCommunicator instance.
	 */
	public static ClientCommunicator getInstance() {
		if (instance == null) {
			instance = new ClientCommunicator();
		}
		
		return instance;
	}
	
	public void changeConfiguration(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public LoginResult sendLogin(LoginCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.LOGIN), request),
										  LoginResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new LoginResult(e.getMessage());
		}
	}
	
	public Result sendLogout(LogoutCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.LOGOUT), request),
										  Result.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(e.getMessage());
		}
	}
	
	public LoginResult sendRegister(RegisterCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.REGISTER), request),
										  LoginResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new LoginResult(e.getMessage());
		}
	}
	
	public PendingGameResult sendCreateGame(CreateGameCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.CREATE_GAME), request),
										  PendingGameResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new PendingGameResult(e.getMessage());
		}
	}
	
	public PendingGameResult sendJoinPendingGame(JoinPendingGameCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.JOIN_PENDING_GAME), request),
										  PendingGameResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new PendingGameResult(e.getMessage());
		}
	}
	
	public PendingGamesResult sendLeavePendingGame(LeavePendingGameCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.LEAVE_PENDING_GAME),
													request), PendingGamesResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new PendingGamesResult(e.getMessage());
		}
	}
	
	public PendingGamesResult sendCancelGame(CancelGameCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.CANCEL_GAME), request),
										  PendingGamesResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new PendingGamesResult(e.getMessage());
		}
	}
	
	public PendingGamesResult sendGetPendingGames(GetPendingGamesCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.GET_PENDING_GAMES), request),
										  PendingGamesResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new PendingGamesResult(e.getMessage());
		}
	}
	
	public PendingGameResult sendGetPendingGame(GetPendingGameCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.GET_PENDING_GAME), request),
										  PendingGameResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new PendingGameResult(e.getMessage());
		}
	}
	
	public StartGameResult sendStartGame(StartGameCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.START_GAME), request),
										  StartGameResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new StartGameResult(e.getMessage());
		}
	}
	
	public HistoryResult sendUpdateHistory(UpdateHistoryCommandData request) {
		try {
			String json = getString(getURL(CommandType.UPDATE_HISTORY), request);
			return HistoryDeserializer.deserializeHistoryResult(json);
		} catch (IOException e) {
			e.printStackTrace();
			return new HistoryResult(e.getMessage());
		}
	}
	
	public DestinationCardResult sendDrawDestinationCards(DrawDestinationCardsCommandData request) {
		try {
			String string = getString(getURL(CommandType.DRAW_DESTINATION_CARDS), request);
			return serializer.deserialize(string, DestinationCardResult.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new DestinationCardResult(e.getMessage());
		}
	}
	
	public DestinationCardResult sendChooseInitialDestinationCards(ChooseDestinationCardsCommandData request) {
		try {
			String string = getString(getURL(CommandType.CHOOSE_DESTINATION_CARDS), request);
			DestinationCardResult result = serializer.deserialize(string,
																  DestinationCardResult.class);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return new DestinationCardResult(e.getMessage());
		}
	}
	
	public Result sendSendMessage(SendMessageCommandData request) {
		try {
			return serializer.deserialize(getString(getURL(CommandType.SEND_MESSAGE), request),
										  Result.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(e.getMessage());
		}
	}
	
	public DrawFaceUpTrainCardResult sendDrawFaceUpTrainCard(DrawFaceUpTrainCardCommandData request) {
		try {
			String string = getString(getURL(CommandType.DRAW_FACEUP_TRAIN_CARD), request);
			DrawFaceUpTrainCardResult result = serializer.deserialize(string,
																	  DrawFaceUpTrainCardResult.class);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return new DrawFaceUpTrainCardResult(e.getMessage());
		}
	}
	
	public DrawDeckTrainCardResult sendDrawDeckTrainCard(DrawDeckTrainCardCommandData request) {
		try {
			String string = getString(getURL(CommandType.DRAW_DECK_TRAIN_CARD), request);
			DrawDeckTrainCardResult result = serializer.deserialize(string,
																	DrawDeckTrainCardResult.class);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return new DrawDeckTrainCardResult(e.getMessage());
		}
	}
	
	/**
	 * Constructs a full HTTP URL String to the specified path.
	 *
	 * @param path a valid HTTP path.
	 * @return the constructed String.
	 */
	private String getURL(String path) {
		return String.format("http://%s:%d/%s", host, port, path);
	}
	
	/**
	 * Sends the specified request to the specified URL and returns the response as a String.
	 *
	 * @param url     a full HTTP URL.
	 * @param request the Object that will be sent as the request body.
	 * @return a String representation of the response.
	 * @throws IOException
	 */
	private String getString(String url, Object request) throws IOException {
		byte[] bytes = getBytes(url, request);
		return new String(bytes);
	}
	
	/**
	 * Sends the specified request to the specified URL and returns the reponse.
	 *
	 * @param url     a full HTTP URL.
	 * @param request the Object that will be sent as the request body.
	 * @return the response.
	 * @throws IOException
	 */
	private byte[] getBytes(String url, Object request) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		
		// TODO: Dynamically set these values.
		connection.setDoOutput(true);
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		
		try {
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			
			writer.write(serializer.serialize(request));
			writer.close();
			
			// TODO: We should probably move this reading login into a utility class
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();
			
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			
			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			
			out.close();
			
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw e;
			//			return serializer.serialize(Result.failed(e.getMessage())).getBytes();
		} finally {
			connection.disconnect();
		}
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public static boolean isValidHost(String hostString) {
		return (hostString != null && !hostString.isEmpty());
	}
	
	public static final int MIN_PORT_NUMBER = 0;
	public static final int MAX_PORT_NUMBER = 65535;
	
	public static boolean isValidPort(int portNumber) {
		return (portNumber >= MIN_PORT_NUMBER && portNumber <= MAX_PORT_NUMBER);
	}
	
	public static boolean isValidPort(String portString) {
		try {
			int portNumber = Integer.parseInt(portString);
			return isValidPort(portNumber);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
