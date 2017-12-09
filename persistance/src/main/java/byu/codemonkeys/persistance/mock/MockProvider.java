package byu.codemonkeys.persistance.mock;

import byu.codemonkeys.persistance.IActiveGameDAO;
import byu.codemonkeys.persistance.IPersistanceProvider;
import byu.codemonkeys.persistance.ISessionDAO;
import byu.codemonkeys.persistance.IUserDAO;


public class MockProvider implements IPersistanceProvider {
    @Override
    public void init() {

    }

    @Override
    public void clear() {

    }

    @Override
    public IActiveGameDAO newActiveGameDAO() {
        return new MockGameDAO();
    }

    @Override
    public ISessionDAO newSessionDAO() {
        return new MockSessionDAO();
    }

    @Override
    public IUserDAO newUserDAO() {
        return new MockUserDAO();
    }
}
