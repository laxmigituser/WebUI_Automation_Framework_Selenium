package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class JsonUtils {
    /*
    with concurrant hashmap Multiple threads can safely access the cache.
    computeIfAbsent ensures JSON file is loaded only once, even if multiple threads request it simultaneously.
    ObjectMapper from Jackson is thread-safe after configuration.
    ThreadLocal is useful when each thread must have its own instance of something mutable.
    ConcurrentHashMap → Test data
    ThreadLocal → WebDriver / runtime objects
     */
    private static final String DATA_PATH = "src/test/resources/testdata/";
    private static final ObjectMapper mapper = new ObjectMapper();
    // Thread-safe cache
    private static final ConcurrentHashMap<String, JsonNode> cache = new ConcurrentHashMap<>();
    private static JsonNode loadJson(String fileName) {
        return cache.computeIfAbsent(fileName, f -> {
            try {
                File file = new File(DATA_PATH + f + ".json");
                return mapper.readTree(file);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load JSON file: " + f, e);
            }
        });
    }
    private static JsonNode getNode(String key) {
        try {
            String[] parts = key.split("\\.");
            String fileName = parts[0];
            JsonNode root = loadJson(fileName);
            JsonNode node = root;
            for (int i = 1; i < parts.length; i++) {
                node = node.get(parts[i]);
                if (node == null) {
                    return null;
                }
            }
            return node;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read key: " + key, e);
        }
    }
    public static String getValue(String key){
        JsonNode node = getNode(key);
        return node != null ? node.asText() : null;
    }
    public static List<String> getList(String key) {
        JsonNode node = getNode(key);
        List<String> values = new ArrayList<>();
        if (node != null && node.isArray()) {
            for (JsonNode element : node) {
                values.add(element.asText());
            }
        }
        return values;
    }

    public static Object[][] getDataProviderData(String key) {
        JsonNode node = getNode(key);
        if (!node.isArray()) {
            throw new RuntimeException("Expected JSON array for DataProvider");
        }
        Object[][] data = new Object[node.size()][1];
        for (int i = 0; i < node.size(); i++) {
            data[i][0] = node.get(i);
        }
        return data;
    }

}
