package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Send POST request to /api/orders")

    public ValidatableResponse creatingAnOrder (Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }
}
