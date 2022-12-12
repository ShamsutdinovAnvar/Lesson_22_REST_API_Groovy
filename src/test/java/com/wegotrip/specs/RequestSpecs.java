package com.wegotrip.specs;


import io.restassured.specification.RequestSpecification;

import static com.wegotrip.helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class RequestSpecs {

    public static RequestSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .basePath("/api/register")
            .log().all()
            .contentType(JSON);

    public static RequestSpecification usersRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/users?page=2")
            .log().all()
            .contentType(JSON);
}
