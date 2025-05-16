package com.moneta.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(LocalDateTime when, String operation, BigDecimal amount, BigDecimal balanceAfter) {
}
