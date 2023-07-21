package com.erosero.bancontt.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {

        super(String.format("Client with Id %d not found", id));
    }
}
