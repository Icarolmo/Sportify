package org.sportify.SportifyApplication.exception;

public class EventAlreadyExistsException extends RuntimeException {

    public EventAlreadyExistsException() {
        super("Este evento já existe ou um evento existe já existe com este nome.");
    }

    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
