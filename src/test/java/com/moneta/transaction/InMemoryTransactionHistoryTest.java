package com.moneta.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTransactionHistoryTest {

    @Test
    void should_records_transaction() {
        TransactionHistory transactionHistory = new InMemoryTransactionHistory();
        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                "DEPOSIT",
                BigDecimal.TEN,
                BigDecimal.TEN
        );

        transactionHistory.record(transaction);

        List<Transaction> history = transactionHistory.history();
        assertEquals(1, history.size());
        assertEquals(transaction, history.getFirst());
    }
}
