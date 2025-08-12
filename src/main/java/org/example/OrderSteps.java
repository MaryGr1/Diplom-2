package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    private final RequestSpecification reqSpec;

    public OrderSteps(RequestSpecification reqSpec) {
        this.reqSpec = reqSpec;
    }

    @Step("Send POST request to /api/orders")

    public ValidatableResponse creatingAnOrder (Order order){
        return given()
                .spec(reqSpec)
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }
}
