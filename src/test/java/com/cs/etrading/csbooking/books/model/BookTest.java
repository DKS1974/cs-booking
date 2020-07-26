package com.cs.etrading.csbooking.books.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    public void shouldBeAbleToCreateBook() {
        Book book = Book.builder()
                .bookId(1L)
                .bookName("testname")
                .allocated(true)
                .closedDt(new Date())
                .openedDt(new Date())
                .build();
        assert(book.getBookId()).equals(1L);
        assert(book.getBookName()).equals("testname");
        assertTrue(book.getAllocated());
        assertNotNull(book.getClosedDt());
        assertNotNull(book.getOpenedDt());
    }

    @Test
    public void shouldBeAbleToCreateBookAnsSetValues() {
        Book book = Book.builder()

                .build();
        book.setBookId(1L);
        book.setBookName("testname");
        book.setAllocated(true);
        book.setClosedDt(new Date());
        book.setOpenedDt(new Date());
        assert(book.getBookId()).equals(1L);
        assert(book.getBookName()).equals("testname");
        assertTrue(book.getAllocated());
        assertNotNull(book.getClosedDt());
        assertNotNull(book.getOpenedDt());
    }
}