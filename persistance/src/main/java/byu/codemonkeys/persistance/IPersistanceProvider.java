package byu.codemonkeys.persistance;

public interface IPersistanceProvider {
    void init();
    void clear();
    IActiveGameDAO newActiveGameDAO();
    ISessionDAO newSessionDAO();
    IUserDAO newUserDAO();
}
