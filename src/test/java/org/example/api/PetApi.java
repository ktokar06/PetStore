package org.example.api;

import io.restassured.response.Response;
import org.example.model.Pet;

import static io.restassured.RestAssured.given;

public class PetApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static Response createPet(Pet pet) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(pet)
                .post("/pet")
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getPetById(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/pet/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updatePet(Pet pet) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(pet)
                .put("/pet")
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deletePet(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .delete("/pet/" + id)
                .then()
                .log().all()
                .extract().response();
    }
}