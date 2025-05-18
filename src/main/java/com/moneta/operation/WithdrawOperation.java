package com.moneta.operation;

import com.moneta.operation.exception.InsufficientBalanceException;
import com.moneta.operation.exception.MinAmountException;
import com.moneta.transaction.Transaction;

import java.math.BigDecimal;

import static com.moneta.operation.OperationType.WITHDRAW;
import static java.time.LocalDateTime.now;

public class WithdrawOperation implements Operation {

    private final BigDecimal amount;
    private final BigDecimal balance;

    public WithdrawOperation(BigDecimal amount, BigDecimal balance) {
        this.amount = amount;
        this.balance = balance;
    }


    @Override
    public Transaction execute() {
        checkMinAmount();
        checkBalance();
        BigDecimal balanceAfter = balance.subtract(amount);
        return new Transaction(now(), WITHDRAW, amount, balanceAfter);
    }

    private void checkMinAmount() {
        if (amount == null || amount.signum() <= 0) {
            throw new MinAmountException("Withdraw amount must be positive");
        }
    }

    private void checkBalance() {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }
}
