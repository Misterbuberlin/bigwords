package com.cgm.life.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
@QuarkusTest
public class WordPremiumIntegrationTest {

    @Inject
    EntityManager entityManager;

    @AfterEach
    @Transactional
    public void cleanDatabase() {
        entityManager.createNativeQuery("DELETE FROM cgm.words").executeUpdate();

    }

    @Test
    @TestSecurity(user = "username", roles = {"BIG_WORDS"})
    @Transactional
    public void testAddPremiumWords() {
        List<String> premiumWords = Arrays.asList("prem_word1", "prem_word2", "prem_word3");
        List<String> words = Arrays.asList("word1", "word2", "word3");

        given()
                .contentType(ContentType.JSON)
                .body(premiumWords)
                .when()
                .post("/words/premium")
                .then()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .body(words)
                .when()
                .post("/words")
                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/words/premium")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("[0]", equalTo("prem_word1"))
                .body("[1]", equalTo("prem_word2"))
                .body("[2]", equalTo("prem_word3"));
    }

    @Test
    @TestSecurity(user = "username", roles = {"BIG_WORDS"})
    @Transactional
    public void testAddWords() {
        List<String> premiumWords = Arrays.asList("prem_word1", "prem_word2", "prem_word3");
        List<String> words = Arrays.asList("word1", "word2", "word3");

        given()
                .contentType(ContentType.JSON)
                .body(premiumWords)
                .when()
                .post("/words/premium")
                .then()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .body(words)
                .when()
                .post("/words")
                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/words")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("[0]", equalTo("word1"))
                .body("[1]", equalTo("word2"))
                .body("[2]", equalTo("word3"));
    }
}
