package com.erosero.bancontt.exception;

public class AccountDeactivatedException extends RuntimeException {

    public AccountDeactivatedException() {

        super(String.format("Account is Deactivated"));
    }
}
