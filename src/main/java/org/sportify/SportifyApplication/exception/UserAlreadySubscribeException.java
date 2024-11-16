package org.sportify.SportifyApplication.exception;

public class UserAlreadySubscribeException extends RuntimeException{

    public UserAlreadySubscribeException() {
        super("usuario já está inscrito no evento");
    }

    public UserAlreadySubscribeException(String message) {
        super(message);
    }
}

