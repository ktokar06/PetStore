package org.example.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.api.PetApi;
import org.example.model.Pet;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Магазин домашних животных")
@Feature("CRUD операции с питомцами")
class PetTest {

    private Pet testPet;
    private Long petId;

    @BeforeEach
    @Step("Подготовка тестовых данных - создание питомца")
    void setUp() {
        testPet = new Pet();
        long uniqueId = System.currentTimeMillis();
        testPet.setId(uniqueId);
        testPet.setName("Fluffy_" + uniqueId);
        testPet.setStatus("available");
        testPet.setPhotoUrls(new ArrayList<>(Arrays.asList("photo1", "photo2")));

        Response response = PetApi.createPet(testPet);
        assertEquals(200, response.getStatusCode(), "Не удалось создать питомца.");

        petId = response.jsonPath().getLong("id");
        assertNotNull(petId, "Идентификатор питомца не должен быть нулевым");
    }

    @Test
    @Story("Создание питомца")
    @DisplayName("Создание нового питомца")
    @Severity(SeverityLevel.BLOCKER)
    void testCreatePet() {
        Response response = PetApi.createPet(testPet);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getLong("id"));
    }

    @Test
    @Story("Получение информации о питомце")
    @DisplayName("Получение данных по ID")
    @Severity(SeverityLevel.CRITICAL)
    void testGetPetById() {
        Response response = PetApi.getPetById(petId);
        assertEquals(200, response.getStatusCode());
        assertEquals(petId, response.jsonPath().getLong("id"));
        assertNotNull(response.jsonPath().getString("name"));
    }

    @Test
    @Story("Редактирование питомца")
    @DisplayName("Обновление данных питомца")
    @Severity(SeverityLevel.NORMAL)
    void testUpdatePet() {
        testPet.setName("UpdatedName");
        testPet.setStatus("sold");

        if (testPet.getPhotoUrls() == null || testPet.getPhotoUrls().isEmpty()) {
            testPet.setPhotoUrls(List.of("default_photo_url"));
        }

        Response updateResponse = PetApi.updatePet(testPet);
        assertEquals(200, updateResponse.getStatusCode());

        Response getResponse = PetApi.getPetById(petId);
        assertEquals("UpdatedName", getResponse.jsonPath().getString("name"));
        assertEquals("sold", getResponse.jsonPath().getString("status"));
    }

    @Test
    @Story("Удаление питомца")
    @DisplayName("Удаление существующего питомца")
    @Severity(SeverityLevel.CRITICAL)
    void testDeletePet() {
        Response deleteResponse = PetApi.deletePet(petId);
        assertEquals(200, deleteResponse.getStatusCode());

        Response getResponse = PetApi.getPetById(petId);
        assertEquals(404, getResponse.getStatusCode(), "Питомец должен быть удален");

        petId = null;
    }
}