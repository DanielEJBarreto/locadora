package daniel.caixa.Mapper;

import daniel.caixa.DTO.BookingRequest;
import daniel.caixa.DTO.BookingResponse;
import daniel.caixa.Entity.Booking;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookingMapper {

    public Booking toEntity(BookingRequest dto) {
        Booking b = new Booking();
        b.setVehicleId(dto.getVehicleId());
        b.setCustomerName(dto.getCustomerName());
        b.setStartDate(dto.getStartDate());
        b.setEndDate(dto.getEndDate());
        b.setStatus(dto.getStatus());
        return b;
    }

    public BookingResponse toResponse(Booking b) {
        BookingResponse dto = new BookingResponse();
        dto.setId(b.getId());
        dto.setVehicleId(b.getVehicleId());
        dto.setCustomerName(b.getCustomerName());
        dto.setStartDate(b.getStartDate());
        dto.setEndDate(b.getEndDate());
        dto.setStatus(b.getStatus());
        return dto;
    }
}

