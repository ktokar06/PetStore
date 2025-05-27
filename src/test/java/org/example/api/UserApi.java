package org.example.api;

import io.restassured.response.Response;
import org.example.model.User;

import static io.restassured.RestAssured.given;


public class UserApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static Response createUser(User user) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user)
                .post("/user")
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getUser(String username) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/user/" + username)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateUser(User user) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user)
                .put("/user/" + user.getUsername())
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteUser(String username) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .delete("/user/" + username)
                .then()
                .log().all()
                .extract().response();
    }
}