package org.sportify.SportifyApplication.exception;

public class RequestBodyWithIncorrectDataException extends RuntimeException{

    public RequestBodyWithIncorrectDataException() {
        super("Corpo da requisição com dados incorretos.");
    }

    public RequestBodyWithIncorrectDataException(String message) {
        super(message);
    }
}
