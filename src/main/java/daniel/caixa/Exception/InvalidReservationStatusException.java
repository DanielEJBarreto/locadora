package daniel.caixa.Exception;

public class InvalidReservationStatusException extends RuntimeException{
    public InvalidReservationStatusException(String message) {
        super(message);
    }
}
