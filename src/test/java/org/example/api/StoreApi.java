package org.example.api;

import io.restassured.response.Response;
import org.example.model.Order;

import static io.restassured.RestAssured.given;


/**
 * API клиент для работы с заказами в Petstore.
 * Предоставляет методы для управления заказами и инвентарем.
 */
public class StoreApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    /**
     * Создает новый заказ на питомца.
     *
     * @param order объект заказа для создания
     * @return Ответ API с деталями созданного заказа или сообщением об ошибке
     */
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

    /**
     * Получает информацию о заказе по его ID.
     *
     * @param id идентификатор заказа
     * @return Ответ API с деталями заказа или сообщением об ошибке
     */
    public static Response getOrder(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/store/order/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Удаляет заказ по его ID.
     *
     * @param id идентификатор заказа для удаления
     * @return Ответ API с результатом операции
     */
    public static Response deleteOrder(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .delete("/store/order/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Получает текущий инвентарь магазина.
     *
     * @return Ответ API с данными инвентаря
     */
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