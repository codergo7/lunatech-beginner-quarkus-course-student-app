package com.lunatech.training.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ProductsResourceTest {



    @Test
    void products() {

        given()
                .when().get("/products")
                .then()
              //  .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    void getProductsById() {

        given()
                .when().get("products/1")
                .jsonPath().getString("name")
                .equals("Chair");
    }
}