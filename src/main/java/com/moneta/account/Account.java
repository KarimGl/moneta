package com.moneta.account;

import com.moneta.operation.DepositOperation;
import com.moneta.transaction.Transaction;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;

    public void deposit(BigDecimal amount) {
        Transaction transaction = new DepositOperation(amount, balance).execute();
        balance = transaction.balanceAfter();
    }

    public BigDecimal balance() {
        return balance;
    }

    public void withdraw(BigDecimal oneHundred) {
    }
}
