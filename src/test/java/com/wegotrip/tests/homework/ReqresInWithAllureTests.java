package com.wegotrip.tests.homework;

import com.wegotrip.models.lombok.LoginBodyLombokModel;
import com.wegotrip.models.lombok.LoginResponseLombokModel;
import com.wegotrip.models.lombok.LoginUnsuccessfulBodyLombokModel;
import com.wegotrip.models.lombok.LoginUnsuccessfulResponseLombokModel;
import com.wegotrip.models.pojo.LoginBodyPojoModel;
import com.wegotrip.models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wegotrip.helpers.CustomApiListener.withCustomTemplates;
import static com.wegotrip.specs.LoginSpecs.loginRequestSpec;
import static com.wegotrip.specs.LoginSpecs.loginResponseSpec;
import static com.wegotrip.specs.RequestSpecs.usersRequestSpec;
import static com.wegotrip.specs.ResponseSpecs.listUsersResponseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class ReqresInWithAllureTests {

    @Test
    @DisplayName("Проверка создания пользователя")
    void createWithPojoModelTest() {
        LoginBodyPojoModel body = new LoginBodyPojoModel();
        body.setName("Anvar");
        body.setJob("leader");

        LoginResponsePojoModel response = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(response.getName()).isEqualTo("Anvar");
    }

    @Test
    @DisplayName("Проверка регистрации ")
    void registerSuccessfulLombokModelTest() {
        LoginBodyLombokModel body = new LoginBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("com/wegotrip");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(response.getToken()).isNotEmpty();
        assertThat(response.getId()).isEqualTo(4);
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации")
    void loginUnsuccessfulWithAllureTest() {
        LoginUnsuccessfulBodyLombokModel body = new LoginUnsuccessfulBodyLombokModel();
        body.setEmail("peter@klaven");

        LoginUnsuccessfulResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(LoginUnsuccessfulResponseLombokModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");


    }

    @Test
    @DisplayName("Проверка изменения пользователя")
    void updateWithCustomAllureTest() {
        LoginBodyPojoModel body = new LoginBodyPojoModel();
        body.setName("Nikita");
        body.setJob("developer");

        LoginResponsePojoModel response = given()
                .filter(withCustomTemplates())
                .log().all()
                .body(body)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(response.getUpdatedAt()).isNotEmpty();
    }

    @Test
    @DisplayName("Проверка авторизации")
    void loginWithSpecsTest() {
        LoginBodyLombokModel body = new LoginBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponseLombokModel response = given() // given(loginRequestSpec)
                .spec(loginRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Проверка списка пользователей ")
    void listUsersSuccessTest() {
        given()
                .spec(usersRequestSpec)
                .when()
                .get()
                .then()
                .spec(listUsersResponseSpec)
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("charles.morris@reqres.in"))
                .body("data.first_name.flatten()",
                        hasItem("Janet"))
                .body("data.findAll{it.avatar =~/.*?.jpg/}.avatar.flatten()",
                        hasItem("https://reqres.in/img/faces/3-image.jpg"));
    }

}
