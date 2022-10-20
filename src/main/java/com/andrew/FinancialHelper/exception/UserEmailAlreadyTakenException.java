package com.andrew.FinancialHelper.exception;

public class UserEmailAlreadyTakenException extends RuntimeException {
    public UserEmailAlreadyTakenException(String message) {
        super(message);
    }
}
