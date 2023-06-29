package com.cgm.life;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(WordsResource.class)
class WordsResourceTest {

    @Test
    void getWords() {
        given()
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(Matchers.is("[Community, Word, List]"));
    }
}