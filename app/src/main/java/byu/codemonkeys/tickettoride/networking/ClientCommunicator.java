package byu.codemonkeys.tickettoride.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.results.*;

public class ClientCommunicator {
    private static ClientCommunicator instance;

    private Serializer serializer;

    private String host;
    private int port;

    public static void main(String[] args) {
        System.out.println(new Serializer().serialize(ClientCommunicator.getInstance().send("", new Object())));
    }

    private ClientCommunicator() {
        host = "localhost";
        port = 8080;
        serializer = new Serializer();
    }

    /**
     * Returns the single ClientCommunicator instance.
     * @return the ClientCommunicator instance.
     */
    public static ClientCommunicator getInstance() {
        if (instance == null) {
            instance = new ClientCommunicator();
        }

        return instance;
    }

    /**
     * Sends the specified request to the specified path and returns the response as a Result.
     * @param path a valid HTTP path.
     * @param request the Object to be sent as the request body.
     * @return the Result of processing the request.
     */
    public Result send(String path, Object request) {
        try {
            switch(path){
                case Routes.LOGIN:
                    return serializer.deserialize(getString(getURL(path), request), LoginResult.class);
                case Routes.LOGOUT:
                    return serializer.deserialize(getString(getURL(path), request), Result.class);
                case Routes.REGISTER:
                    return serializer.deserialize(getString(getURL(path), request), LoginResult.class);
                case Routes.CREATE_GAME:
                    return serializer.deserialize(getString(getURL(path), request), PendingGamesResult.class);
                case Routes.JOIN_PENDING_GAME:
                    return serializer.deserialize(getString(getURL(path), request), PendingGamesResult.class);
                case Routes.LEAVE_PENDING_GAME:
                    return serializer.deserialize(getString(getURL(path), request), PendingGamesResult.class);
                case Routes.START_GAME:
                    return serializer.deserialize(getString(getURL(path), request), StartGameResult.class);
                case Routes.CANCEL_GAME:
                    return serializer.deserialize(getString(getURL(path), request), PendingGamesResult.class);
                case Routes.GET_PENDING_GAMES:
                    return serializer.deserialize(getString(getURL(path), request), PendingGamesResult.class);
                default:
                    // TODO(compy-386): Throw an error here?
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result();
        }
    }

    /**
     * Constructs a full HTTP URL String to the specified path.
     * @param path a valid HTTP path.
     * @return the constructed String.
     */
    private String getURL(String path) {
        return String.format("http://%s:%d/%s", host, port, path);
    }

    /**
     * Sends the specified request to the specified URL and returns the response as a String.
     * @param url a full HTTP URL.
     * @param request the Object that will be sent as the request body.
     * @return a String representation of the response.
     * @throws IOException
     */
    private String getString(String url, Object request) throws IOException {
        return new String(getBytes(url, request));
    }

    /**
     * Sends the specified request to the specified URL and returns the reponse.
     * @param url a full HTTP URL.
     * @param request the Object that will be sent as the request body.
     * @return the response.
     * @throws IOException
     */
    private byte[] getBytes(String url, Object request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // TODO: Dynamically set these values.
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

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
}
