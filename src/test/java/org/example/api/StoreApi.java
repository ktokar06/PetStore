package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.Order;

import static io.restassured.RestAssured.given;

/**
 * API-клиент для управления заказами и инвентарём в PetStore.
 * Поддерживает операции создания, получения, удаления заказов и просмотра инвентаря.
 */
public class StoreApi {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    /**
     * Создаёт новый заказ на питомца.
     *
     * @param order объект заказа
     * @return ответ API с деталями созданного заказа
     */
    public static Response createOrder(Order order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Получает заказ по идентификатору.
     *
     * @param id ID заказа
     * @return ответ API с данными заказа или ошибкой
     */
    public static Response getOrder(Long id) {
        return given()
                .log().all()
                .get("/store/order/{orderId}", id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id ID заказа
     * @return ответ API с результатом удаления
     */
    public static Response deleteOrder(Long id) {
        return given()
                .log().all()
                .delete("/store/order/{orderId}", id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Получает текущее состояние инвентаря.
     *
     * @return ответ API с картой {статус: количество}
     */
    public static Response getInventory() {
        return given()
                .log().all()
                .get("/store/inventory")
                .then()
                .log().all()
                .extract()
                .response();
    }
}