package uk.gov.dwp.uc.pairtest.exception;

public class InvalidTicketRequestException extends RuntimeException {
    //TODO: Create Error messages

    public InvalidTicketRequestException() {
    }

    public InvalidTicketRequestException(String message) {
        super(message);
    }

    public InvalidTicketRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTicketRequestException(Throwable cause) {
        super(cause);
    }

    public InvalidTicketRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
