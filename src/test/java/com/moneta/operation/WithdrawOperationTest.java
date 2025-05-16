package com.moneta.operation;

import com.moneta.transaction.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.moneta.operation.OperationType.WITHDRAW;
import static org.junit.jupiter.api.Assertions.*;

class WithdrawOperationTest {

    @Test
    void should_return_a_valid_withdraw_transaction() {
        Operation operation = new WithdrawOperation(BigDecimal.ONE, BigDecimal.TEN);

        Transaction depositTransaction = operation.execute();

        assertEquals(WITHDRAW, depositTransaction.operation());
        assertEquals(BigDecimal.ONE, depositTransaction.amount());
        assertEquals(BigDecimal.valueOf(9), depositTransaction.balanceAfter());
    }

    @ParameterizedTest(name = "{1} amount")
    @MethodSource("invalidAmounts")
    @DisplayName("Should not execute withdraw of")
    void should_prohibit_withdraw_of_invalid_amount(BigDecimal amount, String cause) {
        Operation operation = new WithdrawOperation(amount, BigDecimal.ZERO);

        MinAmountException thrown = assertThrows(
                MinAmountException.class,
                // When
                operation::execute
        );

        // Then
        assertTrue(thrown.getMessage().contains("Withdraw amount must be positive"));
    }

    @Test
    void should_prohibit_withdraw_when_insufficient_balance() {
        Operation operation = new WithdrawOperation(BigDecimal.TEN, BigDecimal.ONE);

        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                // When
                operation::execute
        );

        // Then
        assertTrue(thrown.getMessage().contains("Insufficient balance"));
    }

    private static Stream<Arguments> invalidAmounts() {
        return Stream.of(
                Arguments.of(BigDecimal.TEN.negate(), "negative"),
                Arguments.of(BigDecimal.ZERO, "zero"),
                Arguments.of((Object) null, "null")
        );
    }
}
