package APIs;

import body.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Notes {

    RegisterBody registerBody;
    LoginBody loginBody;
    CreateNoteBody createNoteBody;
    PutNoteBody putNoteBody;
    PatchNoteBody patchNoteBody;
    public Response register(String name, String email,String password){
        registerBody = new RegisterBody();
        registerBody.setName(name);
        registerBody.setEmail(email);
        registerBody.setPassword(password);

        return RestAssured.given().log().all().header("Content-Type","application/json")
                .header("Accept","application/json").body(registerBody)
                .when().post("/users/register");
    }

    public Response login(String email, String password){
        loginBody = new LoginBody();
        loginBody.setEmail(email);
        loginBody.setPassword(password);

        return RestAssured.given().log().all().header("Content-Type","application/json")
                .header("Accept","application/json")
                .body(loginBody).when().post("/users/login");

    }

    public Response createNote(String title, String description,String category, String token){
        createNoteBody =  new CreateNoteBody();
        createNoteBody.setTitle(title);
        createNoteBody.setDescription(description);
        createNoteBody.setCategory(category);


        return RestAssured.given().log().all()
                .header("x-auth-token",token)
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .body(createNoteBody).when().post("/notes");
    }

    public Response getNoteCreatedById(String id,String token){

        return  RestAssured.given().log().all().pathParam("id",id)
                .header("Accept","application/json")
                .header("x-auth-token",token)
                .when().get("/notes/{id}");
    }

    public Response UpdateExistingNoteById(String id, String title,String description, boolean completed ,String category, String token){
        putNoteBody = new PutNoteBody();
        putNoteBody.setId(id);
        putNoteBody.setTitle(title);
        putNoteBody.setDescription(description);
        putNoteBody.setCompleted(completed);
        putNoteBody.setCategory(category);


        return RestAssured.given().log().all().pathParam("id",id)
                .header("x-auth-token",token)
                .header("Content-Type","application/json")
                .header("Accept","application/json").body(putNoteBody)
                .when().put("/notes/{id}");
    }

    public Response patchAnExistingNoteBId(String id,boolean completed,String token){
        patchNoteBody = new PatchNoteBody();
        patchNoteBody.setCompleted(completed);

        return RestAssured.given().log().all().pathParam("id",id)
                 .header("x-auth-token",token)
                .header("Content-Type","application/json")
                .header("Accept","application/json").body(patchNoteBody).patch("/notes/{id}");

    }

    public Response deleteAnExistingNoteById(String id,String token){
        return RestAssured.given().log().all().pathParam("id",id)
                .header("x-auth-token",token)
                .header("Content-Type","application/json")
                .header("Accept","application/json").when().delete("/notes/{id}");

    }
}
