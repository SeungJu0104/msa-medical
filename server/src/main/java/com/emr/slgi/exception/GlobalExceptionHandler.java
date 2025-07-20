package com.emr.slgi.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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
    
    // 첨부파일 크기가 클경우 에러 처리
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxSizeException(MaxUploadSizeExceededException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "첨부파일크기가 너무 큽니다. 최대 허용 용량을 확인해 주세요");    
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateKeyException(MethodArgumentNotValidException e) {
        Map<String, Object> map = Map.of("message", "이미 존재하는 리소스입니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<Map<String, Object>> handleCannotDeleteException(DataAccessResourceFailureException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "데이터베이스 연결에 실패했습니다. 잠시 후 다시 시도해 주세요.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

}
