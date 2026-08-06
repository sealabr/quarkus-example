package com.example.quarkus;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Endpoint REST com Quarkus REST (Jakarta REST / JAX-RS).
 *
 * Equivalente Spring: {@code GreetingController}
 */
@Path("/api/greetings")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @GET
    @Path("/{name}")
    public GreetingResponse greet(@PathParam("name") @NotBlank String name) {
        return greetingService.greet(name);
    }
}
