package org.sportify.SportifyApplication.handler;

import org.sportify.SportifyApplication.dto.ResponseErrorDTO;
import org.sportify.SportifyApplication.exception.EventAlreadyExistsException;
import org.sportify.SportifyApplication.exception.EventBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.exception.EventNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EventBodyWithIncorrectDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseErrorDTO> EventBodyWithIncorrectDataHandler(EventBodyWithIncorrectDataException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(EventAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseErrorDTO> EventAlreadyExistsExceptionHandler(EventAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseErrorDTO(HttpStatus.CONFLICT.value(), exception.getMessage()));
    }

    @ExceptionHandler(EventNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseErrorDTO> EventNotExistsExceptionHandler(EventNotExistsException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseErrorDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }
}
