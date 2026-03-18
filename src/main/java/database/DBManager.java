package database;

import java.util.List;
import java.util.Map;

public class DBManager {
    public static DBClient getClient(String dbType){
        switch(dbType.toLowerCase()){
            case "postgres":
                return new PostgresClient();
            case "mongo":
                return new MongoDBClient();
            case "mysql":
                return new MySqlClient();
            default:
                throw new RuntimeException("Unsupported DB");
        }
    }
}
