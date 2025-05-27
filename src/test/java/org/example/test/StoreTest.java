package org.example.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.api.StoreApi;
import org.example.model.Order;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Магазин домашних животных")
@Feature("CRUD операции с заказами")
class StoreTest {
    private Order testOrder;
    private Long orderId;

    @BeforeEach
    @Step("Подготовка тестовых данных - создание заказа")
    void setUp() {
        testOrder = new Order();
        testOrder.setPetId(123L);
        testOrder.setQuantity(1);
        testOrder.setStatus("placed");

        Response response = StoreApi.createOrder(testOrder);
        assertEquals(200, response.getStatusCode(), "Не удалось создать заказ");
        orderId = response.jsonPath().getLong("id");
        assertNotNull(orderId, "ID заказа не получен");
    }

    @Test
    @Story("Создание заказа")
    @DisplayName("Создание нового заказа")
    @Severity(SeverityLevel.BLOCKER)
    void testCreateOrder() {
        assertNotNull(orderId, "ID заказа не должен быть null");
        Response response = StoreApi.getOrder(orderId);
        assertEquals(200, response.getStatusCode(), "Заказ не найден");
        assertEquals("placed", response.jsonPath().getString("status"),
                "Статус заказа не соответствует 'placed'");
    }

    @Test
    @Story("Получение заказа")
    @DisplayName("Получение данных по ID заказа")
    @Severity(SeverityLevel.CRITICAL)
    void testGetOrder() {
        Response response = StoreApi.getOrder(orderId);
        assertEquals(200, response.getStatusCode(), "Заказ не найден");
        assertEquals(orderId, response.jsonPath().getLong("id"),
                "ID полученного заказа не совпадает");
    }

    @Test
    @Story("Удаление заказа")
    @DisplayName("Удаление существующего заказа")
    @Severity(SeverityLevel.CRITICAL)
    void testDeleteOrder() {
        Response deleteResponse = StoreApi.deleteOrder(orderId);
        assertEquals(200, deleteResponse.getStatusCode(),
                "Не удалось удалить заказ");

        Response getResponse = StoreApi.getOrder(orderId);
        assertEquals(404, getResponse.getStatusCode(),
                "Заказ всё ещё существует");

        orderId = null;
    }

    @Test
    @Story("Получение инвентаря")
    @DisplayName("Просмотр текущего инвентаря питомцев")
    @Severity(SeverityLevel.NORMAL)
    void testGetInventory() {
        Response response = StoreApi.getInventory();
        assertEquals(200, response.getStatusCode(),
                "Не удалось получить инвентарь");
        assertTrue(response.jsonPath().getMap("").size() > 0,
                "Инвентарь пуст");
    }
}