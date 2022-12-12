package com.wegotrip.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.*;
import static org.hamcrest.Matchers.notNullValue;

public class ResponseSpecs {
    public static ResponseSpecification registerSuccessResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("id", notNullValue())
            .expectBody("token", notNullValue())
            .build();

    public static ResponseSpecification registerUnSuccessResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .expectBody("error", notNullValue())
            .build();

    public static ResponseSpecification createSuccessResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .expectBody("name", notNullValue())
            .expectBody("job", notNullValue())
            .expectBody("id", notNullValue())
            .expectBody("createdAt", notNullValue())
            .build();

    public static ResponseSpecification createUnSuccessResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification deleteResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification listUsersResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();
}
