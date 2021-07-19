package com.example.library.controller.exception;

import com.example.library.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage()
                        .setMessage("Oops something went wrong")
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setTimestamp(Instant.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage()
                        .setMessage("Wrong data")
                        .setStatus(HttpStatus.BAD_REQUEST.value())
                        .setTimestamp(Instant.now())
                        .setValidationErrors(
                                ex.getBindingResult().getAllErrors().stream()
                                        .map(error -> new ErrorMessage.ValidationErrorMessage(
                                                ((FieldError) error).getField(),
                                                error.getDefaultMessage()
                                        ))
                                        .collect(Collectors.toList())
                        )
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage()
                        .setMessage(ex.getMessage())
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setTimestamp(Instant.now()));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExist(UserAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorMessage()
                        .setMessage(ex.getMessage())
                        .setStatus(HttpStatus.CONFLICT.value())
                        .setTimestamp(Instant.now()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage()
                        .setMessage(ex.getMessage())
                        .setStatus(HttpStatus.FORBIDDEN.value())
                        .setTimestamp(Instant.now()));
    }
}