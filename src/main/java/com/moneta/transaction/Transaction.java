package com.moneta.transaction;

import com.moneta.operation.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(LocalDateTime when, OperationType operation, BigDecimal amount, BigDecimal balanceAfter) {
}
