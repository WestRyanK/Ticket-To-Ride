package byu.codemonkeys.tickettoride.mdbprovider;

import com.mongodb.MongoClient;

import com.mongodb.client.MongoDatabase;

import byu.codemonkeys.persistance.*;

public class MDBProvider implements IPersistanceProvider{
    private MongoClient mongoClient = new MongoClient();
    private MongoDatabase database  = mongoClient.getDatabase("ticketToRide");

    @Override
    public void init(){
        database.createCollection("User");
        database.createCollection("Game");
        database.createCollection("Command");
        database.createCollection("Session");
    };

    @Override
    public void clear(){
        database.getCollection("User").drop();
        database.getCollection("Game").drop();
        database.getCollection("Command").drop();
        database.getCollection("Session").drop();
    };

    @Override
    public IActiveGameDAO newActiveGameDAO(){
        return new ActiveGameDAO(database);
    };

    @Override
    public ISessionDAO newSessionDAO(){
        return new SessionDAO(database);
    };

    @Override
    public IUserDAO newUserDAO(){
        return new UserDAO(database);
    };
}
