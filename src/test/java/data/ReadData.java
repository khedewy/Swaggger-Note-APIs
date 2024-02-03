package data;

import io.restassured.path.json.JsonPath;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadData {

    private String jsonPath;
    private FileReader fileReader;

    public ReadData(String jsonPath) throws FileNotFoundException {
        this.jsonPath = jsonPath;
        fileReader = new FileReader(jsonPath);
    }

    public String getTestData(String path) throws FileNotFoundException {
        Object testData;

        fileReader = new FileReader(jsonPath);
        testData = JsonPath.from(fileReader).getString(path);
        return String.valueOf(testData);

    }
}
