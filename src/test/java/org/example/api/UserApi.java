package org.example.api;

import io.restassured.response.Response;
import org.example.model.User;

import java.util.List;

import static io.restassured.RestAssured.given;


/**
 * API клиент для работы с пользователями в Petstore.
 * Предоставляет методы для создания, получения, обновления и удаления пользователей.
 */
public class UserApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    /**
     * Создает нового пользователя.
     *
     * @param user объект пользователя для создания
     * @return Ответ API с результатом операции
     */
    public static Response createUser(User user) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user)
                .post("/user")
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Получает информацию о пользователе по имени.
     *
     * @param username имя пользователя
     * @return Ответ API с деталями пользователя или сообщением об ошибке
     */
    public static Response getUser(String username) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/user/" + username)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param user объект пользователя с обновленными данными
     * @return Ответ API с результатом операции
     */
    public static Response updateUser(User user) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(user)
                .put("/user/" + user.getUsername())
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Удаляет пользователя по имени.
     *
     * @param username имя пользователя для удаления
     * @return Ответ API с результатом операции
     */
    public static Response deleteUser(String username) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .delete("/user/" + username)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Вход пользователя в систему
     * @param username имя пользователя
     * @param password пароль
     * @return Ответ API
     */
    public static Response loginUser(String username, String password) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .queryParam("username", username)
                .queryParam("password", password)
                .get("/user/login")
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Выход пользователя из системы
     * @return Ответ API
     */
    public static Response logoutUser() {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .get("/user/logout")
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Создание списка пользователей
     * @param users список пользователей
     * @return Ответ API
     */
    public static Response createUsersWithList(List<User> users) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(users)
                .post("/user/createWithList")
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Создание массива пользователей
     * @param users массив пользователей
     * @return Ответ API
     */
    public static Response createUsersWithArray(User[] users) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(users)
                .post("/user/createWithArray")
                .then()
                .log().all()
                .extract().response();
    }
}