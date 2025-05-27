package org.example.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.api.PetApi;
import org.example.model.Pet;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

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
        testPet.setName("Fluffy");
        testPet.setStatus("available");
        testPet.setPhotoUrls(new ArrayList<>(Arrays.asList("http://photo1.com", "http://photo2.com")));

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
    @Story("Операции с карточкой питомца")
    @DisplayName("Создание нового питомца с валидными данными")
    @Description("Проверка успешного создания карточки питомца с обязательными полями")
    @Severity(SeverityLevel.BLOCKER)
    void testCreatePet() {
        Response response = PetApi.createPet(testPet);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getLong("id"));
    }

    @Test
    @Story("Операции с карточкой питомца")
    @DisplayName("Получение информации о существующем питомце")
    @Description("Проверка корректного возврата данных при запросе существующего питомца")
    @Severity(SeverityLevel.CRITICAL)
    void testGetPetById() {
        Response response = PetApi.getPetById(petId);
        assertEquals(200, response.getStatusCode());
        assertEquals(petId, response.jsonPath().getLong("id"));
        assertEquals("Fluffy", response.jsonPath().getString("name"));
    }

    @Test
    @Story("Операции с карточкой питомца")
    @DisplayName("Полное обновление данных питомца")
    @Description("Проверка корректного обновления всех полей карточки питомца")
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
    @Story("Операции с карточкой питомца")
    @DisplayName("Удаление существующего питомца")
    @Description("Проверка успешного удаления карточки питомца")
    @Severity(SeverityLevel.CRITICAL)
    void testDeletePet() {
        Response deleteResponse = PetApi.deletePet(petId);
        assertEquals(200, deleteResponse.getStatusCode());

        Response getResponse = PetApi.getPetById(petId);
        assertEquals(404, getResponse.getStatusCode());

        petId = null;
    }

    @Test
    @Story("Поиск и фильтрация питомцев")
    @DisplayName("Поиск питомцев по статусу 'available'")
    @Description("Проверка возврата непустого списка питомцев со статусом 'available'")
    @Severity(SeverityLevel.NORMAL)
    void testFindPetsByStatus() {
        Response response = PetApi.findPetsByStatus("available");
        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("id").size() > 0);
    }

    @Test
    @Story("Дополнительные операции с питомцами")
    @DisplayName("Частичное обновление данных через форму")
    @Description("Проверка обновления имени и статуса питомца через form-data")
    @Severity(SeverityLevel.MINOR)
    void testUpdatePetWithFormData() {
        String newName = "FluffyUpdated";
        String newStatus = "pending";

        Response response = PetApi.updatePetWithFormData(petId, newName, newStatus);
        assertEquals(200, response.getStatusCode());

        Response getResponse = PetApi.getPetById(petId);
        assertEquals(newName, getResponse.jsonPath().getString("name"));
        assertEquals(newStatus, getResponse.jsonPath().getString("status"));
    }

    @Test
    @Story("Дополнительные операции с питомцами")
    @DisplayName("Загрузка дополнительного изображения для питомца")
    @Description("Проверка успешной загрузки изображения с метаданными")
    @Severity(SeverityLevel.MINOR)
    void testUploadPetImage() {
        String additionalMetadata = "Test image";
        String fileContent = "Test file content";

        Response response = PetApi.uploadPetImage(petId, additionalMetadata, fileContent);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("message"));
    }

    @Test
    @Story("Обработка ошибок")
    @DisplayName("Запрос несуществующего питомца")
    @Description("Проверка корректной обработки запроса несуществующего ID питомца")
    @Severity(SeverityLevel.NORMAL)
    void testGetNonExistentPet() {
        long nonExistentId = 999999999L;
        Response response = PetApi.getPetById(nonExistentId);
        assertEquals(404, response.getStatusCode());
    }
}