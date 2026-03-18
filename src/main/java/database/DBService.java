package database;

import java.util.List;
import java.util.Map;

public class DBService {
    public static List<Map<String, Object>> query(String dbType, String dbName, String query) throws Exception {
        DBClient client = DBManager.getClient(dbType);
        return client.executeQuery(dbName, query);
    }
}
