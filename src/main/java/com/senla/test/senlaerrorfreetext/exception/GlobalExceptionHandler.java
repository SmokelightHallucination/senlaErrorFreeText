package com.senla.test.senlaerrorfreetext.exception;

import com.senla.test.senlaerrorfreetext.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(
            TaskNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Task not found: path={}, message={}",
                request.getRequestURI(), ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                40401,
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(InvalidTextException.class)
    public ResponseEntity<ErrorResponse> handleInvalidText(
            InvalidTextException ex,
            HttpServletRequest request
    ) {
        log.warn("Invalid text: path={}, message={}",
                request.getRequestURI(), ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                40001,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation failed");

        log.warn("Request validation failed: path={}, message={}",
                request.getRequestURI(), message);

        return buildResponse(
                message,
                40002,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableRequest(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        log.warn("Malformed request body: path={}",
                request.getRequestURI());

        return buildResponse(
                "Malformed JSON or invalid request field value",
                40003,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        log.warn("Invalid path parameter: path={}, parameter={}",
                request.getRequestURI(), ex.getName());

        return buildResponse(
                "Invalid request parameter: " + ex.getName(),
                40004,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unexpected error: path={}",
                request.getRequestURI(), ex);

        return buildResponse(
                "Internal server error",
                50001,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            String message,
            int errorCode,
            HttpStatus status,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                message,
                errorCode,
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(response);
    }
}
