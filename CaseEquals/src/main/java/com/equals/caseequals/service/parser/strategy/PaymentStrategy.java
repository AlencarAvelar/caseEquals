package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;

public interface PaymentStrategy {
    boolean appliesTo(String rawBrandName);
    Transaction processLine(String line);
}