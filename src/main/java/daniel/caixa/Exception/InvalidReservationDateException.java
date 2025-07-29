package daniel.caixa.Exception;

public class InvalidReservationDateException extends RuntimeException{
    public InvalidReservationDateException(String message) {
        super(message);
    }
}
