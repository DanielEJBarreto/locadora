package daniel.caixa.DTO;

import daniel.caixa.Entity.BookingStatus;

public class AlterBookingStatusRequest {
    private Long bookingId;
    private BookingStatus status;

    public BookingStatus getStatus() {
        return status;
    }
}

