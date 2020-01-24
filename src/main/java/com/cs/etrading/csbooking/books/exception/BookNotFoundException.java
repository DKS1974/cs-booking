package com.cs.etrading.csbooking.books.exception;

import com.cs.etrading.csbooking.common.exception.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(Long id) {
        super("Book", id);
    }
}
