package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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


    @Step("Send DELETE request to /api/auth/user")
    public ValidatableResponse userDelete(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .delete("/api/auth/user")
                .then();
    }
    @Step("Delete user data after authorization")
    public ValidatableResponse userDeleteAfterLogin(User user) {
        ValidatableResponse loginResponse = loginUser(user);
        String accessToken = loginResponse.extract().path("accessToken");

        if (accessToken == null) {
            throw new IllegalStateException("Authorization failed, no access token received. Response: " +
                    loginResponse.extract().asString());
        }

        return userDelete(accessToken);
    }


}
