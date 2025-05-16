package com.moneta.transaction;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionHistory implements TransactionHistory {
    private final List<Transaction> history = new ArrayList<>();

    @Override
    public void record(Transaction transaction) {
        history.add(transaction);
    }

    @Override
    public List<Transaction> history() {
        return history;
    }
}
