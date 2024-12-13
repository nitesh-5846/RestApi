package main.java.com.example.restapi;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Handle pre-flight OPTIONS request (CORS check)
        if ("OPTIONS".equals(requestContext.getMethod())) {
            return;  // No need to process further for OPTIONS request
        }

        // Set CORS headers for each incoming request
        requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");  // You can replace "*" with a specific domain for production
        requestContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        requestContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        requestContext.getHeaders().add("Content-Type", "application/json");
        requestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Set CORS headers for the response
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");  // Replace "*" with your frontend URL for security
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        responseContext.getHeaders().add("Content-Type", "application/json");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}