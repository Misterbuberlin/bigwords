package com.cgm.life.resource;

import com.cgm.life.service.WordService;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordAccessTests {
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
}
