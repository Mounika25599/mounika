package utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class JsonInputParser {

    public static JSONObject userdata;

    public static JSONObject inputDataInit() {
        JSONParser parser = new JSONParser();
        try {
            userdata =(JSONObject) parser.parse(new FileReader("src/test/resources/TestData.json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userdata;
    }

}
