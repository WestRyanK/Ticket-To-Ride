package byu.codemonkeys.tickettoride.mdbprovider;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import org.bson.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import byu.codemonkeys.persistance.ISessionDAO;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by meganrich on 12/9/17.
 */

public class SessionDAO implements ISessionDAO {

    private MongoCollection<Document> collection;

    public SessionDAO(MongoDatabase database){
        collection = database.getCollection("Session");
    }

    @Override
    public void saveSessionData(String authToken, String sessionJson) {
        Document doc = collection.find(eq("authToken", authToken)).first();
        Document data = Document.parse(sessionJson);
        if(doc != null){
            Document newPair = new Document("data", data);
            collection.updateOne(eq("authToken", authToken), new Document("$set", newPair));

        } else {
            doc = new Document("authToken", authToken)
                    .append("data", data);
            collection.insertOne(doc);
        }

    }

    @Override
    public String getSessionData(String authToken) {
        Document doc = collection.find(eq("authToken", authToken)).projection(Projections.exclude("_id")).first();
        return doc.toJson();
    }

    @Override
    public Map<String, String> getAllSessionData() {
        Iterator<Document> iter = collection.find().projection(Projections.exclude("_id")).iterator();
        Map<String, String> docs = new HashMap<>();

        while(iter.hasNext()){
            Document doc = iter.next();
            String authToken = (String) doc.get("authToken");
            Document data = (Document) doc.get("data");
            docs.put(authToken, data.toJson());
        }
        return docs;
    }

    @Override
    public void clear() {
        collection.deleteMany(new Document());
    }
}
