package com.app.quantitymeasurement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UnsupportedOperationException.class, IllegalArgumentException.class})
    public ResponseEntity<Void> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({ArithmeticException.class})
    public ResponseEntity<Void> handleArithmetic(ArithmeticException ex) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleOther(Exception ex) {
        return ResponseEntity.status(500).build();
    }
}
