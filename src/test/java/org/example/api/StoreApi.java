package org.example.api;

import io.restassured.response.Response;
import org.example.model.Order;

import static io.restassured.RestAssured.given;


public class StoreApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static Response createOrder(Order order) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(order)
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getOrder(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/store/order/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteOrder(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .delete("/store/order/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getInventory() {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/store/inventory")
                .then()
                .log().all()
                .extract().response();
    }
}