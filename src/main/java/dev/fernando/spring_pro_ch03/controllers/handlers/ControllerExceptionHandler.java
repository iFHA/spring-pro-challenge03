package dev.fernando.spring_pro_ch03.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.fernando.spring_pro_ch03.dto.CustomError;
import dev.fernando.spring_pro_ch03.dto.ValidationError;
import dev.fernando.spring_pro_ch03.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new CustomError(Instant.now(), status, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        var error = new ValidationError(Instant.now(), status, "Dados inválidos!", request.getRequestURI());
        e.getBindingResult()
        .getFieldErrors()
        .forEach(fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.status(status).body(error);
    }
}
