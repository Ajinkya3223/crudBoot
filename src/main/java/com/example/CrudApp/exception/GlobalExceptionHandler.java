package com.example.CrudApp.exception;

import com.example.CrudApp.dtos.ApiResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMsg> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        logger.info("Exception handler invoked !!");
        ApiResponseMsg message = ApiResponseMsg.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

    }
    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                map.put(error.getField(),error.getDefaultMessage())
        );
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);


    }

}