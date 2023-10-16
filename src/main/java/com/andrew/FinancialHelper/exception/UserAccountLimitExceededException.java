package com.andrew.FinancialHelper.exception;

public class UserAccountLimitExceededException extends RuntimeException {

    public UserAccountLimitExceededException(String message) {
        super(message);
    }
}