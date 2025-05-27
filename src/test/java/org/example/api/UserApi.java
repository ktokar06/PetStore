package org.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.model.User;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * API клиент для работы с пользователями в Petstore.
 * Предоставляет методы для создания, получения, обновления и удаления пользователей.
 */
public class UserApi {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    /**
     * Создает нового пользователя.
     *
     * @param user объект пользователя для создания
     * @return Ответ API с результатом операции
     */
    public static Response createUser(User user) {
        return given()
                .log().all()
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
                .delete("/user/" + username)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Создает несколько пользователей из списка.
     *
     * @param users список объектов User для создания
     * @return Ответ API с результатом операции
     */
    public static Response createUsersWithList(List<User> users) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(users)
                .post("/user/createWithList")
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Создает несколько пользователей из массива.
     *
     * @param users массив объектов User для создания
     * @return Ответ API с результатом операции
     */
    public static Response createUsersWithArray(User[] users) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(users)
                .post("/user/createWithArray")
                .then()
                .log().all()
                .extract().response();
    }
}