package com.cs.etrading.csbooking.books.exception;

public class BookClosedException extends RuntimeException {
    public BookClosedException(Long bookId) {
        super(String.format("Book with ID=%s is closed", bookId));
    }
}
