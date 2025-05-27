package org.example.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.api.UserApi;
import org.example.model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Pet Store API")
@Feature("User Management")
class UserTest {
    private User testUser;
    private static final String USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername(USERNAME);
        testUser.setEmail("test@example.com");
        UserApi.createUser(testUser).then().statusCode(200);
    }

    @AfterEach
    void tearDown() {
        UserApi.deleteUser(USERNAME).then().statusCode(200);
    }

    @Test
    @Story("CRUD Operations")
    @DisplayName("Create user")
    void testCreateUser() {
        Response response = UserApi.getUser(USERNAME);
        assertEquals(200, response.getStatusCode());
        assertEquals(USERNAME, response.jsonPath().getString("username"));
    }

    @Test
    @Story("CRUD Operations")
    @DisplayName("Update user")
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
    @Story("CRUD Operations")
    @DisplayName("Delete user")
    void testDeleteUser() {
        Response deleteResponse = UserApi.deleteUser(USERNAME);
        assertEquals(200, deleteResponse.getStatusCode());

        Response getResponse = UserApi.getUser(USERNAME);
        assertEquals(404, getResponse.getStatusCode());
    }
}