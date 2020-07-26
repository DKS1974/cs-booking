package com.cs.etrading.csbooking.books.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookCloseEventTest {

    @Test
    public void shouldBeAbleToCreateBookCloseEvent() {
        BookCloseEvent bookCloseEvent = BookCloseEvent.builder()
                .bookId(1L)
                .price(new BigDecimal(10))
                .quantity(10)
                .build();
        assert(bookCloseEvent.getBookId()).equals(1L);
        assert(bookCloseEvent.getPrice()).equals(new BigDecimal(10));
        assert (bookCloseEvent.getQuantity()).equals(10);
    }

    @Test
    public void shouldBeAbleToCreateBookCloseEventAnsSetValues() {
        BookCloseEvent bookCloseEvent = BookCloseEvent.builder()

                .build();
        bookCloseEvent.setBookId(1L);
        bookCloseEvent.setPrice(new BigDecimal(10));
        bookCloseEvent.setQuantity(10);
        assert(bookCloseEvent.getBookId()).equals(1L);
        assert(bookCloseEvent.getPrice()).equals(new BigDecimal(10));
        assert (bookCloseEvent.getQuantity()).equals(10);
    }

}