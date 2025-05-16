package com.moneta.operation;

import com.moneta.transaction.Transaction;

public interface Operation {

    Transaction execute();

    String type();
}
