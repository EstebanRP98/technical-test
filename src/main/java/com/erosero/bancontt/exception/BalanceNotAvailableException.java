package com.erosero.bancontt.exception;

public class BalanceNotAvailableException extends RuntimeException {

    public BalanceNotAvailableException() {

        super(String.format("Balance not available"));
    }
}
