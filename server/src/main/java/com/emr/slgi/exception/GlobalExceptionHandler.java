package com.emr.slgi.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException exception) {

        Map<String, Object> map = new HashMap<>();

        map.put(
            "message",
            Optional.ofNullable(exception.getReason()).orElse(((HttpStatus)exception.getStatusCode()).getReasonPhrase())
        );

        return ResponseEntity.status(exception.getStatusCode()).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidException(MethodArgumentNotValidException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Invalid Request");
        map.put("errors", e.getFieldErrors().stream().map((err) -> err.getField() + ": " + err.getDefaultMessage()).toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
