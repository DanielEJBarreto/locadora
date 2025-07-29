package daniel.caixa.Service;

import daniel.caixa.DTO.BookingRequest;
import daniel.caixa.DTO.BookingResponse;
import daniel.caixa.Entity.Booking;
import daniel.caixa.Entity.BookingStatus;
import daniel.caixa.Entity.Vehicle;
import daniel.caixa.Entity.VehicleStatus;
import daniel.caixa.Exception.*;
import daniel.caixa.Mapper.BookingMapper;
import daniel.caixa.Repository.BookingRepository;
import daniel.caixa.Repository.VehicleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookingService {

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    BookingRepository repository;

    @Inject
    BookingMapper mapper;

    public List<BookingResponse> listAll() {
        return repository.listAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse findById(Long id) {
        return repository.findByIdOptional(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional
    public BookingResponse create(BookingRequest dto) {

        // Regra 1: verificar se o veículo existe e está disponível
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId());
        if (vehicle == null) {
            throw new VehicleNotFoundException("Veículo não encontrado");
        }

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new VehicleUnavailableException("Veículo indisponível para reserva");
        }

        // Regra 2: startDate deve ser hoje ou futuro
        if (dto.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("A data de início da reserva deve ser hoje ou no futuro");
        }

        // Regra 3: endDate ≥ startDate
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidReservationDateException("A data de término da reserva deve ser igual ou posterior à data de início");
        }

        //Alterando Status do Veiculo para RENTED
        vehicle.setStatus(VehicleStatus.RENTED);

        // Mapeamento e persistência
        Booking entity = mapper.toEntity(dto);
        repository.persist(entity);
        return mapper.toResponse(entity);

    }

    @Transactional
    public void alter(Long id, BookingStatus newStatus) {
        Booking booking = repository.findByIdOptional(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        BookingStatus currentStatus = booking.getStatus();

        // Regra 1: Só cancelar se Criada
        if (booking.getStatus() != BookingStatus.CREATED) {
            throw new InvalidReservationStatusException("Booking already " + booking.getStatus());
        }

        //Alterando Status do Veiculo para AVAILABLE
        Vehicle vehicle = vehicleRepository.findById(booking.getVehicleId());
        vehicle.setStatus(VehicleStatus.AVAILABLE);

        //Alterando Status do Booking para novo Status
        booking.setStatus(newStatus);
    }
}

