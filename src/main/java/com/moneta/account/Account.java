package com.moneta.account;

import com.moneta.operation.DepositOperation;
import com.moneta.operation.InsufficientBalanceException;
import com.moneta.operation.MinAmountException;
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

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new MinAmountException("Withdraw amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        balance = balance.subtract(amount);
    }
}
