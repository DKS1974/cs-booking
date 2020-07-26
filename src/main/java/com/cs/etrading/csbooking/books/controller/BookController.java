package com.cs.etrading.csbooking.books.controller;

import com.cs.etrading.csbooking.books.exception.BookClosedException;
import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.books.model.BookCloseEvent;
import com.cs.etrading.csbooking.books.service.BookService;
import com.cs.etrading.csbooking.common.exception.EntityExistsException;
import com.cs.etrading.csbooking.common.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService ;

    @GetMapping ("/book/{id}")
    @ResponseBody
    public Book getBookById(@PathVariable( "id") Long id) throws NotFoundException {
        return bookService.fetchBook(id) ;
    }

    @GetMapping ("/book/name/{bookName}")
    @ResponseBody
    public List<Book> getBookByName(@RequestParam( "bookName") String bookName) throws NotFoundException {
        return bookService.fetchBooksByName(bookName) ;
    }

    @GetMapping
    @ResponseBody
    public List<Book> getAllBooks()  {
        return bookService.fetchAllBooks() ;
    }


    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) throws EntityExistsException, BookNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book)) ;
    }

    @PutMapping("/book")
    @ResponseBody
    public void closeBook(@RequestBody @Valid BookCloseEvent bookCloseEvent) throws BookNotFoundException, BookClosedException {
        bookService.closeBookAndProcessAllocations(bookCloseEvent);
    }
}
