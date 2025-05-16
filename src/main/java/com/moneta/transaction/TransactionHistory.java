package com.moneta.transaction;

import java.util.List;

public interface TransactionHistory {
    void record(Transaction transaction);

    List<Transaction> history();
}
