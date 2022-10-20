package com.andrew.FinancialHelper.exception;

public class TransactionNotFoundException extends NotFoundException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
