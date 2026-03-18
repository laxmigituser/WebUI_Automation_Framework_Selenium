package database;

import config.ConfigReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlClient implements DBClient{
    public List<Map<String, Object>> executeQuery(String dbName, String query) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String url =  ConfigReader.getPropertyValueByKey("db_url_ms")+ dbName;
        String username = ConfigReader.getPropertyValueByKey("username");
        String password = ConfigReader.getPropertyValueByKey("password");
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = meta.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                resultList.add(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }


}
