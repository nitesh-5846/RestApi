package main.java.com.example.restapi;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import javax.ws.rs.core.Response;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace(); // Log stack trace
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Error: " + exception.getMessage())
                       .build();
    }
}

