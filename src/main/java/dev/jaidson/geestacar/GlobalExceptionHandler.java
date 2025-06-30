package dev.jaidson.geestacar;

import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class, DataAccessException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
        // Lógica para lidar com ResourceNotFoundException e DataAccessException
        ErrorResponse errorResponse = null;;
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
        // Lógica para lidar com todas as outras exceções
        ErrorResponse errorResponse = null;
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = null;
        return new ResponseEntity<>("Erro de desserialização de Json, confira os valores dos parâmetros possíveis", HttpStatus.BAD_REQUEST);
    }
}
