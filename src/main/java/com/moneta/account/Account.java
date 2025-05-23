package com.moneta.account;

import com.moneta.operation.DepositOperation;
import com.moneta.operation.Operation;
import com.moneta.operation.WithdrawOperation;
import com.moneta.transaction.Transaction;
import com.moneta.transaction.TransactionHistory;

import java.math.BigDecimal;
import java.util.List;

public class Account {

    private BigDecimal balance = BigDecimal.ZERO;

    private final TransactionHistory transactionsHistory;

    public Account(TransactionHistory transactionsHistory) {
        this.transactionsHistory = transactionsHistory;
    }

    public void deposit(BigDecimal amount) {
        Transaction transaction = new DepositOperation(amount, balance).execute();
        balance = transaction.balanceAfter();
        transactionsHistory.record(transaction);
    }

    public BigDecimal balance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        Operation operation = new WithdrawOperation(amount, balance);
        Transaction transaction = operation.execute();
        balance = transaction.balanceAfter();
        transactionsHistory.record(transaction);
    }

    public List<Transaction> history() {
        return transactionsHistory.history();
    }
}
