package database;

import java.util.List;
import java.util.Map;

public interface DBClient {
    List<Map<String, Object>> executeQuery(String dbName, String query) throws Exception;
}
