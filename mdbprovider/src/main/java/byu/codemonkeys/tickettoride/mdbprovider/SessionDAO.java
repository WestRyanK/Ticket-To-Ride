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

public class SessionDAO implements ISessionDAO {

    private MongoCollection<Document> collection;

    public SessionDAO(MongoDatabase database){
        collection = database.getCollection("Session");
    }

    @Override
    public void saveSessionData(String authToken, String sessionJson) {
        Document doc = collection.find(eq("authToken", authToken)).first();
        String data = sessionJson;
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
        return doc.getString("data");
    }

    @Override
    public Map<String, String> getAllSessionData() {
        Iterator<Document> iter = collection.find().projection(Projections.exclude("_id")).iterator();
        Map<String, String> docs = new HashMap<>();

        while(iter.hasNext()){
            Document doc = iter.next();
            String authToken = (String) doc.get("authToken");
            String data = doc.getString("data");
            docs.put(authToken, data);
        }
        return docs;
    }

    @Override
    public void deleteSessionData(String authToken){
        collection.deleteOne(eq("authToken", authToken));
    };

    @Override
    public void clear() {
        collection.deleteMany(new Document());
    }
}
