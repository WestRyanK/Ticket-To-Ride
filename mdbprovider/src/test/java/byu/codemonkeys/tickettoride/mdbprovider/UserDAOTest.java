package byu.codemonkeys.tickettoride.mdbprovider;

import org.junit.Test;

import byu.codemonkeys.persistance.IUserDAO;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by meganrich on 12/11/17.
 */

public class UserDAOTest {
    @Test
    public void save_user_data_test_simple_success(){
        MDBProvider provider = new MDBProvider();
        provider.init();
        IUserDAO dao = new MDBProvider().newUserDAO();
        dao.saveUserData("megameg", "{'hello':'blahblahblahblah'}");
        assertEquals(dao.getUserData("megameg"),
                "{ \"username\" : \"megameg\", \"data\" : { \"hello\" : \"blahblahblahblah\" } }");
        provider.clear();
    }

    @Test
    public void save_user_data_test_update(){
        MDBProvider provider = new MDBProvider();
        provider.init();
        IUserDAO dao = new MDBProvider().newUserDAO();
        dao.saveUserData("megameg", "{'hello':'blahblahblahblah'}");
        dao.saveUserData("megameg", "{'hello':'newvalue'}");
        assertEquals(dao.getUserData("megameg"),
                "{ \"username\" : \"megameg\", \"data\" : { \"hello\" : \"newvalue\" } }");
        provider.clear();
    }

    @Test
    public void get_all_test(){
        MDBProvider provider = new MDBProvider();
        provider.init();
        IUserDAO dao = new MDBProvider().newUserDAO();
        dao.saveUserData("megameg", "{'hello':'blahblahblahblah'}");
        dao.saveUserData("megameg2", "{'hello':'newvalue'}");

        Map<String, String> compare = new HashMap();
        compare.put("megameg", "{ \"hello\" : \"blahblahblahblah\" }");
        compare.put("megameg2", "{ \"hello\" : \"newvalue\" }");
        assertEquals(compare, dao.getAllUserData());
        provider.clear();
    }

    @Test
    public void clear_test(){
        MDBProvider provider = new MDBProvider();
        provider.init();
        IUserDAO dao = new MDBProvider().newUserDAO();
        dao.saveUserData("megameg", "{'hello':'blahblahblahblah'}");
        dao.saveUserData("megameg2", "{'hello':'newvalue'}");

        dao.clear();
        assertEquals(new HashMap<String, String>(), dao.getAllUserData());

        provider.clear();
    }

}
