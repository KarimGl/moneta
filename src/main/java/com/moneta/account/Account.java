package com.moneta.account;

import com.moneta.operation.*;
import com.moneta.transaction.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;

    private final List<Transaction> transactions = new ArrayList<>();

    public void deposit(BigDecimal amount) {
        Transaction transaction = new DepositOperation(amount, balance).execute();
        balance = transaction.balanceAfter();
        transactions.add(transaction);
    }

    public BigDecimal balance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        Operation operation = new WithdrawOperation(amount, balance);
        Transaction transaction = operation.execute();
        balance = transaction.balanceAfter();
        transactions.add(transaction);
    }

    public List<Transaction> history() {
        return transactions;
    }
}
