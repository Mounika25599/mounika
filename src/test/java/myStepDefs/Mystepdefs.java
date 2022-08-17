package myStepDefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import javafx.beans.binding.BooleanExpression;
import models.ErrorMessage;
import models.Student;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;

import java.util.logging.ErrorManager;

import static io.restassured.RestAssured.given;

import static utils.TestNGListener.userdata;


public class Mystepdefs {

    Student users;
    JSONObject jsonobject;

    JsonPath jsonPath;
    ObjectMapper objectMapper=new ObjectMapper();
    Response response;
    Student responseUser;

    @Given("create a student")
    public void userDetails() {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createStudent");
    }

    @When("updating the student")
    public void creatingAUser() throws JsonProcessingException {
        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(),Student.class);
    }
    @Then("the student is updated")
    public void theStudentIsUpdated() throws JsonProcessingException {

        Student responseUser =  objectMapper.readValue(response.asString(),Student.class);
        Assert.assertEquals(users.getEmail(),responseUser.getEmail());
    }

    @When("creating a student")
    public void creatingAStudent() throws JsonProcessingException {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createStudent");
        users =new Student(

                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(),Student.class);
    }

    @And("Updating the student")
    public void updatingTheStudent() throws JsonProcessingException {
        jsonobject = (JSONObject) TestNGListener.userdata.get("UpdateStudent");

        users =new Student(
                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().put(Endpoints.studentEndPoint+"/"+responseUser.getId())
                .then()
                .statusCode(200).extract().response();
        responseUser=objectMapper.readValue(response.asString(),Student.class);
    }

    @Then("student is updated")
    public void studentIsUpdated() throws JsonProcessingException {
        Student responseUser =  objectMapper.readValue(response.asString(),Student.class);
        Assert.assertEquals(users.getName(),responseUser.getName());
    }

    @Then("Getting Student data")
    public void gettingStudentData() {
        response=given()
                .body(users)
                .when().get(Endpoints.studentEndPoint)
                .then()
                .statusCode(200).extract().response();

    }

    @And("student without name")
    public void studentWithoutName() {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createStudent");

        users =new Student(
                (String) jsonobject.get(""),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));

    }


    @Then("Error message Name cannot be blank")
    public void errorMessageNameCannotBeBlank() {
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"),"Name is required");
    }

    @And("student details without age")
    public void studentDetailsWithoutAge() {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createStudent");

        users =new Student(
                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                0,
                (String) jsonobject.get("email"));
    }

    @Then("Error message Age cannot be blank")
    public void errorMessageAgeCannotBeBlank() {
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"),"Age is required");
    }

    @And("student details without email")
    public void studentDetailsWithoutEmail() {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createStudent");

        users =new Student(
                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get(""));
    }

    @Then("Error message Email cannot be blank")
    public void errorMessageEmailCannotBeBlank() {
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"),"Email is required");
    }

    @And("student details without id")
    public void studentDetailsWithoutId() {
        jsonobject = (JSONObject) TestNGListener.userdata.get("createStudent");

        users =new Student(
                (String) jsonobject.get("name"),
                0,
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().post(Endpoints.studentEndPoint)
                .then()
                .statusCode(500).extract().response();
        jsonPath = new JsonPath(response.asString());
    }

    @Then("Error message internal server error")
    public void errorMessageInternalServerError() throws JsonProcessingException {
        ErrorMessage errorMessage = objectMapper.readValue(response.asString(),ErrorMessage.class);
        Assert.assertEquals(errorMessage.getError(),"Internal Server Error");
    }

    @And("enter student without path")
    public void enterStudentWithoutPath() {
        jsonobject = (JSONObject) TestNGListener.userdata.get("PostStudent");

        users =new Student(
                (String) jsonobject.get("name"),
                (Long) jsonobject.get("id"),
                (Long) jsonobject.get("age"),
                (String) jsonobject.get("email"));
        response=given()
                .body(users)
                .when().put(Endpoints.studentEndPoint)
                .then()
                .statusCode(405).extract().response();

    }

    @Then("throw an method error")
    public void throwAnMethodError() {
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("error"),"Method Not Allowed");
    }

    @Then("Delete student details")
    public void deleteStudentDetails() {
        response=given()
                .when().delete(Endpoints.studentEndPoint +"/" +responseUser.getId())
                .then()
                .statusCode(200).extract().response();
    }

    @And("delete student without parm")
    public void deleteStudentWithoutParm() {
        response=given()
                .when().delete()
                .then()
                .statusCode(405).extract().response();

    }
}

