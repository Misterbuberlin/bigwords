package com.cgm.life.resource;

import com.cgm.life.entity.WordEntity;
import com.cgm.life.service.WordService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class) // Use H2 test resource
public class PremiumWordsResourceTest {

    @Inject
    EntityManager entityManager;

    @AfterEach
    @Transactional
    public void cleanDatabase() {
        entityManager.createNativeQuery("DELETE FROM cgm.words").executeUpdate();

    }

    @Test
    @Transactional
    @TestSecurity(user = "username", roles = {"BIG_WORDS"})
    public void testAddPremiumWords() {
        WordService wordServiceMock = mock(WordService.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(wordServiceMock).persistWords(anyList());
        List<String> words = Arrays.asList("word1", "word2", "word3");
        given()
                .contentType(ContentType.JSON)
                .body(words)
                .when()
                .post("/words/premium")
                .then()
                .statusCode(201);
    }

    @Test
    @TestSecurity(user = "username", roles = {"BIG_WORDS"})
    public void testGetPremiumData() {
        WordService premiumWordServiceMock = mock(WordService.class);
        PanacheQuery<WordEntity> mockQuery = mock(PanacheQuery.class);
        when(premiumWordServiceMock.getPremiumWords()).thenReturn(mockQuery);
        given()
                .get("/words/premium")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$", hasSize(greaterThanOrEqualTo(0))); // Add assertions based on your expected response
    }
}
