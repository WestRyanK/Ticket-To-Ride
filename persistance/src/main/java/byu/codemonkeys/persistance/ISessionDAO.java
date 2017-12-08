package byu.codemonkeys.persistance;


import java.util.Map;

public interface ISessionDAO {
    void saveSessionData(String authToken, String sessionJson);
    String getSessionData(String authToken);
    Map<String, String> getAllSessionData();
    void deleteSessionData(String authToken);

    void clear();
}
