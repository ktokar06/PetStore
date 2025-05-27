package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.Pet;

import static io.restassured.RestAssured.given;

/**
 * API-клиент для управления питомцами в PetStore.
 * Поддерживаются операции создания, получения, обновления, удаления,
 * частичного обновления и загрузки изображений.
 */
public class PetApi {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    /**
     * Создаёт нового питомца.
     *
     * @param pet объект Pet, содержащий данные нового питомца
     * @return ответ API с деталями созданного питомца
     */
    public static Response createPet(Pet pet) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pet)
                .post("/pet")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Получает информацию о питомце по ID.
     *
     * @param id идентификатор питомца
     * @return ответ API с данными питомца
     */
    public static Response getPetById(Long id) {
        return given()
                .log().all()
                .get("/pet/{petId}", id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Обновляет данные существующего питомца.
     *
     * @param pet объект Pet с обновлённой информацией
     * @return ответ API с результатом обновления
     */
    public static Response updatePet(Pet pet) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pet)
                .put("/pet")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Удаляет питомца по ID.
     *
     * @param id идентификатор питомца
     * @return ответ API с результатом удаления
     */
    public static Response deletePet(Long id) {
        return given()
                .log().all()
                .header("api_key", "special-key")
                .delete("/pet/{petId}", id)
                .then()
                .log().all()
                .extract()
                .response();
    }
}