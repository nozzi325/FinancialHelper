package com.andrew.FinancialHelper.controller;


import com.andrew.FinancialHelper.exception.*;
import com.andrew.FinancialHelper.utils.ErrorDetails;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionNotFoundHandler(NotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorDetails> exceptionInsufficientFundsHandler(InsufficientFundsException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(UserEmailAlreadyTakenException.class)
    public ResponseEntity<ErrorDetails> exceptionUserEmailAlreadyTakenHandler(UserEmailAlreadyTakenException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> exceptionMethodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        StringBuilder errMessage = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errMessage.append(String.format("%s; ",fieldError.getDefaultMessage()));
        }
        errorDetails.setMessage(String.valueOf(errMessage).trim());
        errorDetails.setTimestamp(LocalDateTime.now());
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }
}
