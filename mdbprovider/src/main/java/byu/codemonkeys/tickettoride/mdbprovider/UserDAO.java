package byu.codemonkeys.tickettoride.mdbprovider;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

import byu.codemonkeys.persistance.IUserDAO;

public class UserDAO implements IUserDAO {

    private MongoCollection<org.bson.Document> collection;

    public UserDAO(MongoDatabase database) {
        collection = database.getCollection("User");
    }

    @Override
    public void saveUserData(String username, String userJson) {
        Document doc = collection.find(eq("username", username)).first();
        Document data = Document.parse(userJson);
        if(doc != null){
            //If the user already exists, update their data
            Document newPair = new Document("data", data);
            collection.updateOne(eq("username", username), new Document("$set", newPair));

        } else {
            doc = new Document("username", username)
                    .append("data", data);
            collection.insertOne(doc);
        }
    }

    @Override
    public String getUserData(String username) {
        Document doc = collection.find(eq("username", username)).projection(Projections.exclude("_id")).first();
        return doc.toJson();
    }

    @Override
    public Map<String, String> getAllUserData() {
        Iterator<Document> iter = collection.find().projection(Projections.exclude("_id")).iterator();
        Map<String, String> docs = new HashMap<>();

        while(iter.hasNext()){
            Document doc = iter.next();
            String username = (String) doc.get("username");
            Document data = (Document) doc.get("data");
            docs.put(username, data.toJson());
        }
        return docs;
    }

    @Override
    public void clear() {
        collection.deleteMany(new Document());
    }
}
