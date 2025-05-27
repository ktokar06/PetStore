package org.example.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.api.PetApi;
import org.example.model.Pet;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@Epic("Магазин домашних животных")
@Feature("Управление карточками питомцев")
class PetTest {
    private Pet testPet;
    private Long petId;

    @BeforeEach
    @Step("Подготовка тестовых данных - создание питомца")
    void setUp() {
        testPet = new Pet();
        testPet.setId(petId);
        testPet.setName("Fluffy");
        testPet.setStatus("available");

        Response response = PetApi.createPet(testPet);
        petId = response.jsonPath().getLong("id");
        testPet.setId(petId);
    }

    @AfterEach
    @Step("Очистка тестовых данных - удаление питомца")
    void tearDown() {
        if (petId != null) {
            PetApi.deletePet(petId).then().statusCode(200);
        }
    }

    @Test
    @Story("Добавление нового питомца в каталог")
    @DisplayName("Успешное создание карточки питомца")
    @Description("Проверка что система корректно создает новую карточку питомца")
    @Severity(SeverityLevel.BLOCKER)
    void testCreatePet() {
        Response response = PetApi.createPet(testPet);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getLong("id"));
    }

    @Test
    @Story("Просмотр информации о питомце")
    @DisplayName("Получение данных существующего питомца")
    @Description("Проверка корректности отображения информации о питомце")
    @Severity(SeverityLevel.CRITICAL)
    void testGetPetById() {
        Response response = PetApi.getPetById(petId);
        assertEquals(200, response.getStatusCode());
        assertEquals(petId, response.jsonPath().getLong("id"));
    }

    @Test
    @Story("Редактирование карточки питомца")
    @DisplayName("Обновление информации о питомце")
    @Description("Проверка возможности изменения данных в карточке питомца")
    @Severity(SeverityLevel.NORMAL)
    void testUpdatePet() {
        testPet.setName("NewName");
        testPet.setStatus("sold");

        Response updateResponse = PetApi.updatePet(testPet);
        assertEquals(200, updateResponse.getStatusCode());

        Response getResponse = PetApi.getPetById(petId);
        assertEquals("NewName", getResponse.jsonPath().getString("name"));
        assertEquals("sold", getResponse.jsonPath().getString("status"));
    }

    @Test
    @Story("Удаление питомца из системы")
    @DisplayName("Удаление карточки питомца")
    @Description("Проверка что система корректно удаляет карточку питомца")
    @Severity(SeverityLevel.CRITICAL)
    void testDeletePet() {
        Response deleteResponse = PetApi.deletePet(petId);
        assertEquals(200, deleteResponse.getStatusCode());

        Response getResponse = PetApi.getPetById(petId);
        assertEquals(404, getResponse.getStatusCode());

        petId = null;
    }
}