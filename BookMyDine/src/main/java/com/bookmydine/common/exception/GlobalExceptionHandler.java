package com.bookmydine.common.exception;

import com.bookmydine.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errors = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error ->
                error.getField()
                    + ": "
                    + error.getDefaultMessage())
            .toList();

        ErrorResponse response =
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .errors(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
            .badRequest()
            .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEnum(HttpMessageNotReadableException ex, HttpServletRequest request) {

        ErrorResponse response =
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .errors(List.of(ex.getMostSpecificCause().getMessage()))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
            .badRequest()
            .body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(ResourceAlreadyExistsException ex, HttpServletRequest request) {

        ErrorResponse response =
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .errors(List.of(
                    "Resource already exists"
                ))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse response =
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .errors(List.of("Resource not found"))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(ResourceAlreadyUpdatedException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyUpdatedException(ResourceAlreadyUpdatedException ex, HttpServletRequest request) {
        ErrorResponse response =
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message(ex.getMessage())
                .errors(List.of("Resource already updated"))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
            .status(HttpStatus.OK.value())
            .body(response);
    }

}
