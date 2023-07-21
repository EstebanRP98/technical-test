package com.erosero.bancontt.exception;

public class TypeMovementNotFoundException extends RuntimeException {

    public TypeMovementNotFoundException() {
        super(String.format("Type Movement not found"));
    }
}
