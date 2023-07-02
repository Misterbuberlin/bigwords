package com.cgm.life.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        // Handle the exception and create a Response object
        // You can customize the response based on the exception

        // Example response: HTTP 500 Internal Server Error
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An error occurred in BigWords app :-): " + exception)
                .build();
    }
}

