package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

//import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoDBClient implements DBClient{
    public List<Map<String, Object>> executeQuery(String dbName, String query) {
        List<Map<String, Object>> result = new ArrayList<>();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = database.getCollection(query);
        for (Document doc : collection.find()) {
            Map<String, Object> row = new HashMap<>(doc);
            result.add(row);
        }
        mongoClient.close();
        return result;
    }
}
