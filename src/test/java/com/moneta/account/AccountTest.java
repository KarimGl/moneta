package com.moneta.account;

import com.moneta.operation.MinAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assert balance.equals(BigDecimal.ZERO);
    }

    @Test
    void balance_should_increase_after_a_deposit_operation() {
        // Given
        BigDecimal oneHundred = BigDecimal.valueOf(100);

        // When
        account.deposit(oneHundred);

        // Then
        assert account.balance().equals(oneHundred);
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

    private static Stream<Arguments> invalidAmounts() {
        return Stream.of(
                Arguments.of(BigDecimal.TEN.negate(), "negative"),
                Arguments.of(BigDecimal.ZERO, "zero"),
                Arguments.of((Object) null, "null")
        );
    }
}
