package com.cgm.life.resource;

import com.cgm.life.entity.WordEntity;
import com.cgm.life.service.WordService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@QuarkusTest
public class WordsResourceTest {


    @Test
    @TestSecurity(user = "username", roles = {"END_USER"})
    public void testGetWordsAsEndUser() {
        WordService wordServiceMock = mock(WordService.class);
        when(wordServiceMock.getWords()).thenReturn(List.of());
        given()
                .when().get("/words")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @TestSecurity(user = "username", roles = {"BIG_WORDS"})
    public void testGetWordsAsBigWordsRole() {
        WordService wordServiceMock = mock(WordService.class);
        when(wordServiceMock.getWords()).thenReturn(List.of());
        given()
                .when().get("/words")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetWordsWithoutProvidingRoles() {
        WordService wordServiceMock = mock(WordService.class);
        when(wordServiceMock.getWords()).thenReturn(List.of());
        given()
                .when().get("/words")
                .then()
                .statusCode(401); // Unauthorized, as no authentication provided
    }

    @Test
    @TestSecurity(user = "username", roles = {"FALSE_END_USER", "FALSE_BIG_WORDS"})
    public void testGetWordsWithWrongRoles() {
        WordService wordServiceMock = mock(WordService.class);
        when(wordServiceMock.getWords()).thenReturn(List.of());
        given()
                .when().get("/words")
                .then()
                .statusCode(403); // Unauthorized, as no authentication provided
    }

    @Test
    @TestSecurity(user = "username", roles = {"END_USER", "BIG_WORDS"})
    public void testAddWords() {
        List<String> words = Arrays.asList("word1", "word2", "word3");

        given()
                .contentType(ContentType.JSON)
                .body(words)
                .when()
                .post("/words")
                .then()
                .statusCode(201)
                .body("size()", equalTo(words.size()))
                .body("findAll { it.word == 'word1' }", hasSize(1))
                .body("findAll { it.word == 'word2' }", hasSize(1))
                .body("findAll { it.word == 'word3' }", hasSize(1));
    }
}
