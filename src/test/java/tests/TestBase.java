package tests;

import APIs.Notes;
import data.ReadData;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import java.io.FileNotFoundException;

public class TestBase {

    Notes notes;
    ReadData registerData;
    ReadData createNoteData;
    ReadData putNoteData;
    ReadData patchData;
    @BeforeClass
    public void setData() throws FileNotFoundException {
         notes = new Notes();
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api";
        registerData = new ReadData("src/test/java/data/registerData.json");
        createNoteData = new ReadData("src/test/java/data/createNoteData.json");
        putNoteData = new ReadData("src/test/java/data/putNoteData.json");
        patchData = new ReadData("src/test/java/data/patchNoteData.json");
    }
}
