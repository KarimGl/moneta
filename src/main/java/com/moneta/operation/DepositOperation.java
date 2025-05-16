package com.moneta.operation;

import com.moneta.transaction.Transaction;

import java.math.BigDecimal;

import static com.moneta.operation.OperationType.DEPOSIT;
import static java.time.LocalDateTime.now;

public class DepositOperation implements Operation {

    private final BigDecimal amount;
    private final BigDecimal balance;

    public DepositOperation(BigDecimal amount, BigDecimal balance) {
        this.amount = amount;
        this.balance = balance;
    }


    @Override
    public Transaction execute() {
        checkMinAmount();
        BigDecimal balanceAfter = balance.add(amount);
        return new Transaction(now(), DEPOSIT, amount, balanceAfter);
    }

    private void checkMinAmount() {
        if (amount == null || amount.signum() <= 0) {
            throw new MinAmountException("Deposit amount must be positive");
        }
    }
}
