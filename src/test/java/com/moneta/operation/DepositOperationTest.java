package com.moneta.operation;

import com.moneta.transaction.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.moneta.operation.OperationType.DEPOSIT;
import static org.junit.jupiter.api.Assertions.*;

class DepositOperationTest {

    @Test
    void should_return_a_valid_deposit_transaction() {
        Operation operation = new DepositOperation(BigDecimal.TEN, BigDecimal.TWO);

        Transaction depositTransaction = operation.execute();

        assertEquals(DEPOSIT, depositTransaction.operation());
        assertEquals(BigDecimal.TEN, depositTransaction.amount());
        assertEquals(BigDecimal.valueOf(12), depositTransaction.balanceAfter());
    }

    @ParameterizedTest(name = "{1} amount")
    @MethodSource("invalidAmounts")
    @DisplayName("Should not execute deposit of")
    void should_prohibit_deposit_of_invalid_amount(BigDecimal amount, String cause) {
        Operation operation = new DepositOperation(amount, BigDecimal.ZERO);

        MinAmountException thrown = assertThrows(
                MinAmountException.class,
                // When
                operation::execute
        );

        // Then
        assertTrue(thrown.getMessage().contains("Deposit amount must be positive"));
    }

    private static Stream<Arguments> invalidAmounts() {
        return Stream.of(
                Arguments.of(BigDecimal.TEN.negate(), "negative"),
                Arguments.of(BigDecimal.ZERO, "zero"),
                Arguments.of((Object) null, "null")
        );
    }
}
