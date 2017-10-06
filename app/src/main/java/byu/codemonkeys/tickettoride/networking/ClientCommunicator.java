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

public class ClientCommunicator {
	private static ClientCommunicator instance;
	
	private Serializer serializer;
	
	private String host;
	private int port;
	
																		.send("", new Object())));
	private ClientCommunicator() {
		host = "localhost";
		port = 8080;
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
            return serializer.deserialize(getString(getURL(CommandType.LOGIN), request), LoginResult.class);
		} catch (IOException e) {
			e.printStackTrace();
            return null;
        }
    }

    public Result sendLogout(LogoutCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.LOGOUT), request), Result.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LoginResult sendRegister(RegisterCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.REGISTER), request), LoginResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingGameResult sendCreateGame(CreateGameCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.CREATE_GAME), request), PendingGameResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingGameResult sendJoinPendingGame(JoinPendingGameCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.JOIN_PENDING_GAME), request), PendingGameResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingGamesResult sendLeavePendingGame(LeavePendingGameCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.LEAVE_PENDING_GAME), request), PendingGamesResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingGamesResult sendCancelGame(CancelGameCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.CANCEL_GAME), request), PendingGamesResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingGamesResult sendGetPendingGames(GetPendingGamesCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.GET_PENDING_GAMES), request), PendingGamesResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
		}
	}
	
    public StartGameResult sendStartGame(StartGameCommandData request) {
        try {
            return serializer.deserialize(getString(getURL(CommandType.START_GAME), request), StartGameResult.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
		return new String(getBytes(url, request));
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
		} finally {
			connection.disconnect();
		}
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
}
