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
                    .append("data", Document.parse(commandJson));
            commandCollection.insertOne(doc);
    }

    @Override
    public List<String> getAllCommandData(String gameID) {
        Iterator<Document> iter = commandCollection.find(eq("gameID", gameID)).projection(Projections.exclude("_id")).iterator();
        List<String> commands = new ArrayList<String>();

        while(iter.hasNext()){
            Document doc = iter.next();
            Document data = (Document) doc.get("data");
            commands.add(data.toJson());
        }
        return commands;
    }

    @Override
    public void saveGameData(String gameID, String gameJson) {
        Document doc = gameCollection.find(eq("gameID", gameID)).first();
        Document data = Document.parse(gameJson);
        if(doc != null){
            Document newPair = new Document("data", data);
            gameCollection.updateOne(eq("gameID", gameID), new Document("$set", newPair));

        } else {
            doc = new Document("gameID", gameID)
                    .append("data", data);
            gameCollection.insertOne(doc);
        }
    }

    @Override
    public String getGameData(String gameID) {
        Document doc = gameCollection.find(eq("gameID", gameID)).projection(Projections.exclude("_id")).first();
        return doc.toJson();
    }

    @Override
    public Map<String, String> getAllGameData() {
        Iterator<Document> iter = gameCollection.find().projection(Projections.exclude("_id")).iterator();
        Map<String, String> docs = new HashMap<>();

        while(iter.hasNext()){
            Document doc = iter.next();
            String gameID = (String) doc.get("gameID");
            Document data = (Document) doc.get("data");
            docs.put(gameID, data.toJson());
        }
        return docs;
    }

    @Override
    public void clear() {
        gameCollection.deleteMany(new Document());
        commandCollection.deleteMany(new Document());
    }
}
