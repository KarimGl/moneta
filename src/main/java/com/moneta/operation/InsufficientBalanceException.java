package com.moneta.operation;

public class InsufficientBalanceException extends OperationException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
