package com.andrew.FinancialHelper.controller;


import com.andrew.FinancialHelper.dto.ErrorDetails;
import com.andrew.FinancialHelper.exception.InsufficientFundsException;
import com.andrew.FinancialHelper.exception.UserEmailAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    // TODO: Review the project's exception system to ensure it's well-designed.
    //  After the review, assess whether additional methods or information are needed in exception handling.
    //  Consider refactoring to reduce duplicated code in handling methods.
    @ExceptionHandler({EntityNotFoundException.class, InsufficientFundsException.class, UserEmailAlreadyTakenException.class})
    public ResponseEntity<ErrorDetails> handleCustomExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorDetails(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> exceptionMethodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorDetails(errorMessage));
    }

    private ErrorDetails createErrorDetails(String message) {
        return new ErrorDetails(message, LocalDateTime.now());
    }
}
