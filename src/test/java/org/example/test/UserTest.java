package org.example.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.api.UserApi;
import org.example.model.User;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Магазин домашних животных")
@Feature("CRUD операции с пользователями")
class UserTest {

    private static final String USERNAME = "testuser";
    private static final String USERNAME2 = "testuser2";
    private static final String USERNAME3 = "testuser3";

    private User testUser;

    @BeforeEach
    @Step("Создание пользователя перед тестом")
    void setUp() {
        testUser = new User();
        testUser.setUsername(USERNAME);
        testUser.setEmail("test@example.com");
        testUser.setPassword("pass123");
        testUser.setPhone("111222333");
        UserApi.createUser(testUser).then().statusCode(200);
    }

    @Test
    @Story("Создание пользователя")
    @DisplayName("Создание нового пользователя")
    @Severity(SeverityLevel.BLOCKER)
    void testCreateUser() {
        Response response = UserApi.getUser(USERNAME);
        assertEquals(200, response.getStatusCode());
        assertEquals(USERNAME, response.jsonPath().getString("username"));
    }

    @Test
    @Story("Получение пользователя")
    @DisplayName("Получение данных пользователя")
    @Severity(SeverityLevel.CRITICAL)
    void testGetUser() {
        Response response = UserApi.getUser(USERNAME);
        assertEquals(200, response.getStatusCode());
        assertEquals(USERNAME, response.jsonPath().getString("username"));
    }

    @Test
    @Story("Редактирование пользователя")
    @DisplayName("Обновление информации пользователя")
    @Severity(SeverityLevel.NORMAL)
    void testEditUser() {

        User existingUser = UserApi.getUser(USERNAME).as(User.class);

        existingUser.setEmail("updated@example.com");
        existingUser.setPhone("999888777");

        Response updateResponse = UserApi.updateUser(existingUser);
        assertEquals(200, updateResponse.getStatusCode());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Response getResponse = UserApi.getUser(USERNAME);
        assertEquals("updated@example.com", getResponse.jsonPath().getString("email"));
        assertEquals("999888777", getResponse.jsonPath().getString("phone"));
    }

    @Test
    @Story("Удаление пользователя")
    @DisplayName("Удаление существующего пользователя")
    @Severity(SeverityLevel.CRITICAL)
    void testDeleteUser() {
        Response deleteResponse = UserApi.deleteUser(USERNAME);
        assertEquals(200, deleteResponse.getStatusCode());

        Response getResponse = UserApi.getUser(USERNAME);

        assertEquals(404, getResponse.getStatusCode(), "Пользователь должен быть удалён и недоступен");
    }

    @Test
    @Story("Массовое создание пользователей")
    @DisplayName("Создание пользователей из массива")
    @Severity(SeverityLevel.NORMAL)
    void testCreateUserArray() {
        User user1 = new User();
        user1.setUsername(USERNAME2);
        user1.setEmail("user2@example.com");

        User user2 = new User();
        user2.setUsername(USERNAME3);
        user2.setEmail("user3@example.com");

        User[] users = new User[]{user1, user2};

        Response response = UserApi.createUsersWithArray(users);
        assertEquals(200, response.getStatusCode());

        assertEquals(200, UserApi.getUser(USERNAME2).getStatusCode());
        assertEquals(200, UserApi.getUser(USERNAME3).getStatusCode());
    }

    @Test
    @Story("Массовое создание пользователей")
    @DisplayName("Создание пользователей из списка")
    @Severity(SeverityLevel.NORMAL)
    void testCreateUserList() {
        User user1 = new User();
        user1.setUsername(USERNAME2);
        user1.setEmail("user2@example.com");

        User user2 = new User();
        user2.setUsername(USERNAME3);
        user2.setEmail("user3@example.com");

        List<User> users = Arrays.asList(user1, user2);

        Response response = UserApi.createUsersWithList(users);
        assertEquals(200, response.getStatusCode());

        assertEquals(200, UserApi.getUser(USERNAME2).getStatusCode());
        assertEquals(200, UserApi.getUser(USERNAME3).getStatusCode());
    }
}