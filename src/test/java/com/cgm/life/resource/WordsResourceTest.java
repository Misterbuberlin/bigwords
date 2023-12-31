package com.cgm.life.resource;

import com.cgm.life.service.WordService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
