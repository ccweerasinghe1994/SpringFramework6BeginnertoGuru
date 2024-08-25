package com.wchamara.spring6restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException errors) {

        List errorsList = errors.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> errorMap = Map.of(
                    "field", fieldError.getField(),
                    "message", fieldError.getDefaultMessage());

            return errorMap;
        }).toList();

        return ResponseEntity.badRequest().body(errorsList);
    }

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity handleJPAErrors(Exception e) {
        return ResponseEntity.badRequest().build();
    }

}
