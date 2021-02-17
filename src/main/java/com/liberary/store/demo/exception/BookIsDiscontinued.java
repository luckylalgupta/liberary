package com.liberary.store.demo.exception;

public class BookIsDiscontinued extends RuntimeException{
    public BookIsDiscontinued(String message) {
        super(message);
    }
}
