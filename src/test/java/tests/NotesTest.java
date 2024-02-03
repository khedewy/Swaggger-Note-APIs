package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class NotesTest extends TestBase{

    String currentTime = String.valueOf(System.currentTimeMillis());
    String token;
    JsonPath jsonPath;
    String id;

    @Test
    public void Register() throws FileNotFoundException {
        String response = notes.register(registerData.getTestData("name"),
                registerData.getTestData("email")+currentTime+"@gmail.com",
                registerData.getTestData("password")).then()
                .statusCode(201).extract().response().asString();

        System.out.println(response);
    }

    @Test(priority = 1)
    public void login() throws FileNotFoundException {
        String response = notes.login(registerData.getTestData("email")+currentTime+"@gmail.com"
                ,registerData.getTestData("password")).then().statusCode(200).extract().response().asString();


        jsonPath = new JsonPath(response);
        token = jsonPath.getString("data.token");
        id = jsonPath.getString("data.id");
        System.out.println(token);
    }

    @Test(priority = 2)
    public void createNote() throws FileNotFoundException {
        String response = notes.createNote(createNoteData.getTestData("title"),
                createNoteData.getTestData("description"), createNoteData.getTestData("category"),token)
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
        jsonPath = new JsonPath(response);
        id = jsonPath.getString("data.id");
        System.out.println(id);
    }

    @Test(priority = 3)
    public void getNoteCreatedById() throws FileNotFoundException {
        String response = notes.getNoteCreatedById(id,token)
                .then().extract().response().asString();

        String title = jsonPath.getString("data.title");
        Assert.assertEquals(title,createNoteData.getTestData("title"));
        System.out.println(response);

    }

    @Test(priority = 4)
    public void updateAnExistingNoteById() throws FileNotFoundException {

        String response = notes.UpdateExistingNoteById(id,
                putNoteData.getTestData("title"),putNoteData.getTestData("description"),
                        Boolean.parseBoolean(putNoteData.getTestData("completed")), putNoteData.getTestData("category"),token)
                .then().extract().response().asString();


        jsonPath = new JsonPath(response);
        String assertionMessage = jsonPath.getString("message");
        Assert.assertEquals(assertionMessage,"Note successfully Updated");
        System.out.println(response);
    }

    @Test(priority = 5)
    public void patchAnExistingNote() throws FileNotFoundException {
        String response = notes.patchAnExistingNoteBId(id, Boolean.parseBoolean(patchData.getTestData("completed"))
                ,token).then().extract().response().asString();

        jsonPath = new JsonPath(response);
        String actualResult = jsonPath.getString("data.completed");
        String expectedResult = String.valueOf(Boolean.parseBoolean(patchData.getTestData("completed")));
        Assert.assertEquals(expectedResult,actualResult);

        System.out.println(response);
    }

    @Test(priority = 6)
    public void deleteAnExistingNote(){
        String response = notes.deleteAnExistingNoteById(id,token).then().extract().response().asString();

        jsonPath = new JsonPath(response);
        String assertionMessage = jsonPath.getString("message");
        Assert.assertEquals(assertionMessage,"Note successfully deleted");
        System.out.println(response);
    }


}

