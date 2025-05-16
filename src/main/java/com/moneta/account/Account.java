package com.moneta.account;

import com.moneta.operation.MinAmountException;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new MinAmountException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
    }

    public BigDecimal balance() {
        return balance;
    }
}
