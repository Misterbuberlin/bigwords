package com.cgm.life.exception;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionMapperTest {

    @Test
    public void testToResponse() {
        // Create an instance of the CustomExceptionMapper
        CustomExceptionMapper exceptionMapper = new CustomExceptionMapper();

        // Simulate an exception
        Exception exception = new Exception("Test exception");

        // Invoke the toResponse() method
        Response response = exceptionMapper.toResponse(exception);

        // Verify the response status
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        // Verify the response entity
        String expectedEntity = "An error occurred in BigWords app :-): " + exception;
        assertEquals(expectedEntity, response.getEntity());
    }
}

