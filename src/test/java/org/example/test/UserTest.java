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
@Feature("Управление пользователями")
class UserTest {
    private User testUser;
    private static final String USERNAME = "testuser";
    private static final String USERNAME2 = "testuser2";
    private static final String USERNAME3 = "testuser3";

    @BeforeEach
    @Step("Подготовка тестовых данных - создание пользователя")
    void setUp() {
        testUser = new User();
        testUser.setUsername(USERNAME);
        testUser.setEmail("test@example.com");
        UserApi.createUser(testUser).then().statusCode(200);
    }

    @AfterEach
    @Step("Очистка тестовых данных - удаление пользователя")
    void tearDown() {
        UserApi.deleteUser(USERNAME).then().statusCode(200);
        UserApi.deleteUser(USERNAME2).then();
        UserApi.deleteUser(USERNAME3).then();
    }

    @Test
    @Story("Создание пользователя")
    @DisplayName("Успешное создание пользователя")
    @Description("Проверка корректного создания новой записи пользователя")
    @Severity(SeverityLevel.BLOCKER)
    void testCreateUser() {
        Response response = UserApi.getUser(USERNAME);
        assertEquals(200, response.getStatusCode());
        assertEquals(USERNAME, response.jsonPath().getString("username"));
    }

    @Test
    @Story("Редактирование пользователя")
    @DisplayName("Обновление данных пользователя")
    @Description("Проверка возможности редактирования информации о пользователе")
    @Severity(SeverityLevel.CRITICAL)
    void testUpdateUser() {
        testUser.setEmail("new@example.com");
        testUser.setPhone("1234567890");

        Response updateResponse = UserApi.updateUser(testUser);
        assertEquals(200, updateResponse.getStatusCode());

        Response getResponse = UserApi.getUser(USERNAME);
        assertEquals("new@example.com", getResponse.jsonPath().getString("email"));
        assertEquals("1234567890", getResponse.jsonPath().getString("phone"));
    }

    @Test
    @Story("Удаление пользователя")
    @DisplayName("Удаление пользователя")
    @Description("Проверка корректного удаления записи пользователя")
    @Severity(SeverityLevel.CRITICAL)
    void testDeleteUser() {
        Response deleteResponse = UserApi.deleteUser(USERNAME);
        assertEquals(200, deleteResponse.getStatusCode());

        Response getResponse = UserApi.getUser(USERNAME);
        assertEquals(404, getResponse.getStatusCode());
    }

    @Test
    @Story("Авторизация пользователя")
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверка входа пользователя в систему")
    @Severity(SeverityLevel.CRITICAL)
    void testLoginUser() {
        testUser.setPassword("testpass");
        UserApi.updateUser(testUser).then().statusCode(200);

        Response response = UserApi.loginUser(USERNAME, "testpass");
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("logged in user session"));
    }

    @Test
    @Story("Авторизация пользователя")
    @DisplayName("Неудачная авторизация с неверными данными")
    @Description("Проверка обработки неверных учетных данных")
    @Severity(SeverityLevel.NORMAL)
    void testLoginUserWithInvalidCredentials() {
        Response response = UserApi.loginUser(USERNAME, "wrongpassword");
        assertEquals(400, response.getStatusCode());
    }

    @Test
    @Story("Выход пользователя")
    @DisplayName("Выход пользователя из системы")
    @Description("Проверка завершения сессии пользователя")
    @Severity(SeverityLevel.MINOR)
    void testLogoutUser() {
        Response response = UserApi.logoutUser();
        assertEquals(200, response.getStatusCode());
        assertEquals("ok", response.getBody().asString());
    }

    @Test
    @Story("Создание пользователей")
    @DisplayName("Создание списка пользователей")
    @Description("Проверка массового создания пользователей")
    @Severity(SeverityLevel.BLOCKER)
    void testCreateUsersWithList() {
        User user1 = new User();
        user1.setUsername(USERNAME2);
        user1.setEmail("user2@example.com");

        User user2 = new User();
        user2.setUsername(USERNAME3);
        user2.setEmail("user3@example.com");

        List<User> users = Arrays.asList(user1, user2);

        Response response = UserApi.createUsersWithList(users);
        assertEquals(200, response.getStatusCode());

        Response getResponse1 = UserApi.getUser(USERNAME2);
        assertEquals(200, getResponse1.getStatusCode());

        Response getResponse2 = UserApi.getUser(USERNAME3);
        assertEquals(200, getResponse2.getStatusCode());
    }

    @Test
    @Story("Создание пользователей")
    @DisplayName("Создание массива пользователей")
    @Description("Проверка массового создания пользователей через массив")
    @Severity(SeverityLevel.BLOCKER)
    void testCreateUsersWithArray() {
        User user1 = new User();
        user1.setUsername(USERNAME2);
        user1.setEmail("user2@example.com");

        User user2 = new User();
        user2.setUsername(USERNAME3);
        user2.setEmail("user3@example.com");

        User[] users = new User[]{user1, user2};

        Response response = UserApi.createUsersWithArray(users);
        assertEquals(200, response.getStatusCode());

        Response getResponse1 = UserApi.getUser(USERNAME2);
        assertEquals(200, getResponse1.getStatusCode());

        Response getResponse2 = UserApi.getUser(USERNAME3);
        assertEquals(200, getResponse2.getStatusCode());
    }
}


