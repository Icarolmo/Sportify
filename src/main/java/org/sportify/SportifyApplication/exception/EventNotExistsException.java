package org.sportify.SportifyApplication.exception;

public class EventNotExistsException extends RuntimeException{
    public EventNotExistsException() {
        super("Este evento não existe");
    }

    public EventNotExistsException(String message) {
        super(message);
    }
}
