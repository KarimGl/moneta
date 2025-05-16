package com.moneta.account;

import com.moneta.operation.InsufficientBalanceException;
import com.moneta.operation.MinAmountException;
import com.moneta.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void new_account_balance_should_be_zero() {
        // When
        BigDecimal balance = account.balance();

        // Then
        assertEquals(BigDecimal.ZERO, account.balance());
    }

    @Test
    void balance_should_increase_after_a_deposit_operation() {
        // Given
        BigDecimal oneHundred = BigDecimal.valueOf(100);

        // When
        account.deposit(oneHundred);

        // Then
        assertEquals(oneHundred, account.balance());
    }

    @ParameterizedTest(name = "{1} amount")
    @MethodSource("invalidAmounts")
    @DisplayName("Should prohibit deposit of")
    void should_prohibit_deposit_of_invalid_amount(BigDecimal amount, String cause) {

        MinAmountException thrown = assertThrows(
                MinAmountException.class,
                // When
                () -> account.deposit(amount)
        );

        // Then
        assertTrue(thrown.getMessage().contains("Deposit amount must be positive"));
    }

    @Test
    void balance_should_decrease_after_a_withdraw_operation() {
        // Given
        BigDecimal oneHundred = BigDecimal.valueOf(100);
        account.deposit(oneHundred);
        assertEquals(oneHundred, account.balance());

        // When
        account.withdraw(BigDecimal.TEN);

        // Then
        assertEquals(BigDecimal.valueOf(90), account.balance());
    }

    @Test
    void should_prohibit_withdraw_when_insufficient_balance() {
        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                // When
                () -> account.withdraw(BigDecimal.TEN)
        );

        // Then
        assertTrue(thrown.getMessage().contains("Insufficient balance"));
    }



    @ParameterizedTest(name = "{1} amount")
    @MethodSource("invalidAmounts")
    @DisplayName("Should prohibit withdraw of")
    void should_prohibit_withdraw_of_invalid_amount(BigDecimal amount, String cause) {

        MinAmountException thrown = assertThrows(
                MinAmountException.class,
                // When
                () -> account.withdraw(amount)
        );

        // Then
        assertTrue(thrown.getMessage().contains("Withdraw amount must be positive"));
    }


    @Test
    void any_operation_must_be_historicized() {
        // Given
        account.deposit(BigDecimal.TEN);
        account.withdraw(BigDecimal.TEN);

        // When
        List<Transaction> history = account.history();

        // Then
        assertEquals(2, history.size());
        assertEquals("DEPOSIT", history.get(0).operation());
        assertEquals("WITHDRAW", history.get(1).operation());
    }



    private static Stream<Arguments> invalidAmounts() {
        return Stream.of(
                Arguments.of(BigDecimal.TEN.negate(), "negative"),
                Arguments.of(BigDecimal.ZERO, "zero"),
                Arguments.of((Object) null, "null")
        );
    }
}
