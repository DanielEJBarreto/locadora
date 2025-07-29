package daniel.caixa.Exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        ErrorResponse error;

        if (e instanceof VehicleNotFoundException) {
            error = new ErrorResponse("VEHICLE NOT FOUND", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (e instanceof VehicleUnavailableException) {
            error = new ErrorResponse("VEHICLE UNAVAILABLE", e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }

        if (e instanceof InvalidReservationDateException) {
            error = new ErrorResponse("INVALID DATE", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        if (e instanceof InvalidReservationStatusException){
            error = new ErrorResponse("INVALID STATUS", e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }

        // fallback genérico
        error = new ErrorResponse(LocalDateTime.now(), "UNKNOWN ERROR", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}