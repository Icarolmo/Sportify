package org.sportify.SportifyApplication.exception;

public class EventBodyWithIncorrectDataException extends RuntimeException{

    public EventBodyWithIncorrectDataException() {
        super("Corpo da requisição com dados incorretos.");
    }

    public EventBodyWithIncorrectDataException(String message) {
        super(message);
    }
}
