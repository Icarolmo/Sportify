package org.sportify.SportifyApplication.exception;

public class EventAlreadyExistsException extends RuntimeException {

    public EventAlreadyExistsException() {
        super("Corpo da requisição mal formada.");
    }

    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
