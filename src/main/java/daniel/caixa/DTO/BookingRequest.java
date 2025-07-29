package daniel.caixa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import daniel.caixa.Entity.BookingStatus;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@RegisterForReflection
public class BookingRequest {

    @JsonProperty
    private Long vehicleId;

    @JsonProperty
    @NotBlank
    private String customerName;

    @JsonProperty
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @JsonProperty
    @NotNull
    @Future
    private LocalDate endDate;

    @JsonProperty
    private BookingStatus status = BookingStatus.CREATED;

    public BookingRequest (){}
    public BookingRequest (Long vehicleId, String customerName, LocalDate startDate, LocalDate endDate){
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}

