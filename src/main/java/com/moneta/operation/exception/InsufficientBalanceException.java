package com.moneta.operation.exception;

public class InsufficientBalanceException extends OperationException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
