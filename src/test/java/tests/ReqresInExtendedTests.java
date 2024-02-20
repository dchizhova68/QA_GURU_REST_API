package tests;

import io.restassured.RestAssured;
import models.lombok.createUserModel.CreateUserRequestModel;
import models.lombok.createUserModel.CreateUserResponseModel;
import models.lombok.errorResponseModel.ErrorResponseModel;
import models.lombok.loginModel.LoginBodyLombokModel;
import models.lombok.usersModel.UsersResponseModel;
import models.lombok.usersModel.UserslistModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.reqresInSpec.*;

public class ReqresInExtendedTests {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void checkUserEmailByUserIdTest() {
        UsersResponseModel response = step("Получаем информацию о пользователе по его ID", () ->
                given(userEmailSpec)
                        .get()
                        .then()
                        .spec(successResponseSpec)
                        .extract().as(UsersResponseModel.class));

        step("Проверяем, что поста пользователя совпадает с  emma.wong@reqres.in", () ->
                assertEquals("emma.wong@reqres.in", response.getData().getEmail()));
    }

    @Test
    void succsessfulCreateUserTest() {
        CreateUserRequestModel authData = new CreateUserRequestModel();
        authData.setJob("Chemist");
        authData.setName("Diana Rose");

        CreateUserResponseModel response = step("Создаем нового пользовтеля", () ->
                given(createUserSpec)
                        .body(authData)

                        .when()
                        .post()

                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем, что в ответе пришли введенные данные пользователя ", () ->
                assertEquals("Diana Rose", response.getName()));
        assertEquals("Chemist", response.getJob());
    }

    @Test
    void unsuccsessfulRegisterTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("Diana Rose");
        authData.setPassword("passW0rd");

        ErrorResponseModel response = step("Отправляем запрос на регистрацию", () ->
                given(RegisterSpec)
                        .body(authData)

                        .when()
                        .post()

                        .then()
                        .spec(erorr400ResponseSpec)
                        .extract().as(ErrorResponseModel.class));

        step("Проверяем текст ошибки в ответе", () ->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }

    @Test
    void missingPasswordRegisterTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("Diana Rose");

        ErrorResponseModel response = step("Отправляем запрос на регистрацию без указания пароля", () ->
                given(RegisterSpec)
                        .body(authData)

                        .when()
                        .post()

                        .then()
                        .spec(erorr400ResponseSpec)
                        .extract().as(ErrorResponseModel.class));

        step("Проверяем текст ошибки в ответе", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void findUserInlistTest() {

        UserslistModel response = step("Make request", () ->
                given(findUserInlistSpec)
                        .get()
                        .then()
                        .spec(successResponseSpec)
                        .extract().as(UserslistModel.class));

        step("Check response", () -> {

           assertEquals("emma.wong@reqres.in", response.getData()[2].getEmail());

            //body("data.findAll {it.id = 3}.email", hasItem("emma.wong@reqres.in"));
        });
    }
}
