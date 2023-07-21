package com.erosero.bancontt.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {

        super(String.format("Account not found"));
    }
}
