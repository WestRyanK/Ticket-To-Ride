package byu.codemonkeys.persistance;


import java.util.Map;

public interface IUserDAO {
    void saveUserData(String username, String userJson);
    String getUserData(String username);
    Map<String, String> getAllUserData();

    void clear();
}
