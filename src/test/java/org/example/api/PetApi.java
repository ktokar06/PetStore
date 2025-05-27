package org.example.api;

import io.restassured.response.Response;
import org.example.model.Pet;

import static io.restassured.RestAssured.given;


/**
 * API клиент для работы с питомцами в PetStore.
 * Предоставляет методы для создания, получения, обновления и удаления питомцев.
 */
public class PetApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    /**
     * Создает нового питомца в магазине.
     *
     * @param pet объект питомца для создания
     * @return ответ API с деталями созданного питомца
     */
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

    /**
     * Получает информацию о питомце по его ID.
     *
     * @param id идентификатор питомца
     * @return ответ API с деталями питомца
     */
    public static Response getPetById(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/pet/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Обновляет информацию о существующем питомце.
     *
     * @param pet объект питомца с обновленными данными
     * @return ответ API с обновленными деталями питомца
     */
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

    /**
     * Удаляет питомца из магазина.
     *
     * @param id идентификатор питомца для удаления
     * @return ответ API с результатом операции
     */
    public static Response deletePet(Long id) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .delete("/pet/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Находит питомцев по статусу.
     *
     * @param status статус для поиска (available, pending, sold)
     * @return ответ API со списком питомцев
     */
    public static Response findPetsByStatus(String status) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .queryParam("status", status)
                .get("/pet/findByStatus")
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Обновляет данные питомца через форму.
     *
     * @param petId ID питомца для обновления
     * @param name новое имя питомца
     * @param status новый статус питомца
     * @return ответ API с результатом операции
     */
    public static Response updatePetWithFormData(Long petId, String name, String status) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status)
                .post("/pet/" + petId)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Загружает изображение для питомца.
     *
     * @param petId ID питомца
     * @param additionalMetadata дополнительные метаданные
     * @param fileContent содержимое файла для загрузки
     * @return ответ API с результатом загрузки
     */
    public static Response uploadPetImage(Long petId, String additionalMetadata, String fileContent) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("multipart/form-data")
                .multiPart("additionalMetadata", additionalMetadata)
                .multiPart("file", "file.txt", fileContent.getBytes())
                .post("/pet/" + petId + "/uploadImage")
                .then()
                .log().all()
                .extract().response();
    }
}