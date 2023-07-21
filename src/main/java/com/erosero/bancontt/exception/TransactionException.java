package com.erosero.bancontt.exception;

public class TransactionException extends RuntimeException {

    public TransactionException() {

        super(String.format("The transaction cannot be less than 5 dollars."));
    }
}
