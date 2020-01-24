package com.cs.etrading.csbooking.books.exception;

import com.cs.etrading.csbooking.common.exception.EntityExistsException;

public class BookNameExistsException extends EntityExistsException {
    public BookNameExistsException(String bookName) {
        super("Book", bookName);
    }
}
