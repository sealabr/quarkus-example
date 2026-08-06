package com.example.quarkus.hr;

import java.net.URI;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * API REST de RH — CRUD de funcionários + relatório simples.
 *
 * Equivalente Spring: {@code EmployeeController}
 */
@Path("/api/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeService service;

    @GET
    public List<EmployeeResponse> list(
            @QueryParam("department") String department,
            @QueryParam("active") Boolean active) {
        return service.list(department, active);
    }

    @GET
    @Path("/stats/by-department")
    public List<DepartmentCount> byDepartment() {
        return service.activeByDepartment();
    }

    @GET
    @Path("/{id}")
    public EmployeeResponse get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @POST
    public Response hire(@Valid EmployeeRequest request) {
        EmployeeResponse created = service.hire(request);
        return Response.created(URI.create("/api/employees/" + created.id()))
                .entity(created)
                .build();
    }

    @PUT
    @Path("/{id}")
    public EmployeeResponse update(@PathParam("id") Long id, @Valid EmployeeRequest request) {
        return service.update(id, request);
    }

    @POST
    @Path("/{id}/deactivate")
    public EmployeeResponse deactivate(@PathParam("id") Long id) {
        return service.deactivate(id);
    }
}
