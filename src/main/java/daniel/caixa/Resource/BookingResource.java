package daniel.caixa.Resource;

import daniel.caixa.DTO.AlterBookingStatusRequest;
import daniel.caixa.DTO.BookingRequest;
import daniel.caixa.DTO.BookingResponse;
import daniel.caixa.Service.BookingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookingResource {

    BookingService bookingService;

    public BookingResource (BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GET
    public List<BookingResponse> listAll() {
        return bookingService.listAll();
    }

    @GET
    @Path("/{id}")
    public BookingResponse findById(@PathParam("id") Long id) {
        return bookingService.findById(id);
    }

    @POST
    public Response create(@Valid BookingRequest dto) {
        BookingResponse created = bookingService.create(dto);
        return Response.status(Response.Status.CREATED).entity("Booking n# " + created.getId() + " created.").build();
    }

    @PATCH
    @Path("/{id}/alter")
    public Response alter(@PathParam("id") Long id, AlterBookingStatusRequest dto) {
        bookingService.alter(id, dto.getStatus());
        return Response.ok().build();
    }

}

