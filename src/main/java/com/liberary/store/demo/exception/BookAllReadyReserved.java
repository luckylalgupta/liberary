package com.liberary.store.demo.exception;

public class BookAllReadyReserved extends RuntimeException{
    public BookAllReadyReserved(String message) {
        super(message);
    }
}
