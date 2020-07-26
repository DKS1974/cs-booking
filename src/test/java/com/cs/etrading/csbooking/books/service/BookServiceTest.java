package com.cs.etrading.csbooking.books.service;

import com.cs.etrading.csbooking.books.dao.BookRepository;
import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.books.model.BookCloseEvent;
import com.cs.etrading.csbooking.distribution.service.DistributionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookService bookService;
    private BookRepository bookRepository ;
    private EntityManager entityManager;
    private DistributionService distributionService;

    @BeforeEach
    public void init() {
        bookRepository = mock(BookRepository.class) ;
        entityManager = mock(EntityManager.class);
        distributionService = mock(DistributionService.class);
        doNothing().when(entityManager).flush();
        doNothing().when(entityManager).refresh(any(Book.class));

        when(bookRepository.findAllByBookName(any())).thenReturn(Arrays.asList(Book.builder().bookId(1L).bookName("test").build()));
        when(bookRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(Book.builder().bookId(1L).bookName("test").allocated(false).build()));
        when(bookRepository.save(any(Book.class))).thenReturn(Book.builder().bookId(2L).bookName("test").build());

        doNothing().when( distributionService).distribute(any(BookCloseEvent.class));

        bookService = new BookService();
        bookService.setBookRepository(bookRepository);
        bookService.setEntityManager(entityManager);
        bookService.setDistributionService(distributionService);

    }
    @Test
    public void shouldBeAbleToFetchAllBooks() {
        List<Book> books = bookService.fetchBooksByName("test");
       assertEquals(1, books.size());
       assertEquals(1L, books.get(0).getBookId());
       assertEquals("test", books.get(0).getBookName());
    }

    @Test
    public void shouldBeAbleToCreateBook() {
        Book book =  bookService.createBook(Book.builder().bookName("test").openedDt(new Date()).build()) ;
        assertEquals("test",book.getBookName());
        assertEquals(2L,book.getBookId());
    }

    @Test
    public void shouldBeAbleToCloseBookAndProcessAllocations() {
        bookService.closeBookAndProcessAllocations(BookCloseEvent.builder().bookId(1L).price(new BigDecimal(10)).quantity(100).build()) ;
        verify(distributionService,times(1)).distribute(any(BookCloseEvent.class));
    }

}