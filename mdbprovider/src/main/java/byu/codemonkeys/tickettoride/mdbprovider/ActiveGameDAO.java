package byu.codemonkeys.tickettoride.mdbprovider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import byu.codemonkeys.persistance.IActiveGameDAO;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class ActiveGameDAO implements IActiveGameDAO {

    MongoCollection<Document> gameCollection;
    MongoCollection<Document> commandCollection;

    public ActiveGameDAO(MongoDatabase database){
        gameCollection = database.getCollection("Game");
        commandCollection = database.getCollection("Command");

    }

    @Override
    public void saveCommandData(String gameID, String commandJson) {
            Document doc = new Document("gameID", gameID)
                    .append("data", commandJson);
            commandCollection.insertOne(doc);
    }

    @Override
    public List<String> getAllCommandData(String gameID) {
        Iterator<Document> iter = commandCollection.find(eq("gameID", gameID)).projection(Projections.exclude("_id")).iterator();
        List<String> commands = new ArrayList<String>();

        while(iter.hasNext()){
            Document doc = iter.next();
            String data = doc.getString("data");
            commands.add(data);
        }
        return commands;
    }

    @Override
    public void saveGameData(String gameID, String gameJson) {
        Document doc = gameCollection.find(eq("gameID", gameID)).first();
        if(doc != null){
            Document newPair = new Document("data", gameJson);
            gameCollection.updateOne(eq("gameID", gameID), new Document("$set", newPair));
            commandCollection.deleteMany(eq("gameID", gameID));
        } else {
            doc = new Document("gameID", gameID)
                    .append("data", gameJson);
            gameCollection.insertOne(doc);
        }
    }

    @Override
    public String getGameData(String gameID) {
        Document doc = gameCollection.find(eq("gameID", gameID)).projection(Projections.exclude("_id")).first();
        return doc.getString("data");
    }

    @Override
    public Map<String, String> getAllGameData() {
        Iterator<Document> iter = gameCollection.find().projection(Projections.exclude("_id")).iterator();
        Map<String, String> docs = new HashMap<>();

        while(iter.hasNext()){
            Document doc = iter.next();
            String gameID = doc.getString("gameID");
            String data = doc.getString("data");
            docs.put(gameID, data);
        }
        return docs;
    }

    @Override
    public void deleteGameData(String gameID){
        gameCollection.deleteOne(eq("gameID", gameID));
        commandCollection.deleteMany(eq("gameID", gameID));
    }

    @Override
    public void clear() {
        gameCollection.deleteMany(new Document());
        commandCollection.deleteMany(new Document());
    }
}
