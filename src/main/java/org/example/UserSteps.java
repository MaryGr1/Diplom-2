package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps {

    @Step("Send POST request to /api/auth/register")

    public ValidatableResponse createUser (User user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Send POST request to /api/auth/register создание без логина")

    public ValidatableResponse createUserNoEmail(UserNoEmail userNoEmail){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userNoEmail)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Send POST request to /api/auth/register создание без пароля")

    public ValidatableResponse createUserNoPassword(UserNoPassword userNoPassword){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userNoPassword)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Send POST request to /api/auth/register создание без имени")

    public ValidatableResponse createUserNoName(UserNoName userNoName){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userNoName)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Send POST request to /api/auth/login")

    public ValidatableResponse loginUser(User user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login")
                .then();
    }

}
