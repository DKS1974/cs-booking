package com.cs.etrading.csbooking.books.service;

import com.cs.etrading.csbooking.books.dao.BookRepository;
import com.cs.etrading.csbooking.books.exception.BookClosedException;
import com.cs.etrading.csbooking.books.exception.BookNameExistsException;
import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.books.model.BookCloseEvent;
import com.cs.etrading.csbooking.distribution.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository ;

    @PersistenceContext
    private EntityManager entityManager ;

    @Autowired
    private DistributionService distributionService ;

    public  Book fetchBook(Long bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId)) ;
    }

    public List<Book> fetchAllBooks()  {
        return (List<Book>) bookRepository.findAll() ;
    }

    public List<Book> fetchBooksByName(String bookName)  {
        return (List<Book>) bookRepository.findAllByBookName(bookName) ;
    }

    @Transactional
    public  Book createBook(@Valid Book book) throws BookNameExistsException, BookNotFoundException {
        try {
            Book savedBook =  bookRepository.save(book);
            entityManager.flush();
            entityManager.refresh(savedBook);
            return savedBook ;
        }catch (PersistenceException exception) {
                throw  new BookNameExistsException(book.getBookName()) ;
        }

    }

    @Transactional
    public void closeBookAndProcessAllocations(BookCloseEvent bookCloseEvent) throws BookNotFoundException {
        Long bookId = bookCloseEvent.getBookId();
        if(!isClosed(bookId))
            closeBook( bookId) ;
        if(!isAllocated(bookId))
            processAllocations(bookCloseEvent);
    }

    @Transactional
    private void processAllocations(BookCloseEvent bookCloseEvent) {
        distributionService.distribute(bookCloseEvent);
        Book book = fetchBook(bookCloseEvent.getBookId()) ;
        book.setAllocated(Boolean.TRUE);
        bookRepository.save(book) ;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void closeBook(Long bookId) {
        Book book = fetchBook(bookId) ;
        book.setClosedDt(new Date());
        bookRepository.save(book) ;
    }
    public boolean bookExists(Long bookId) {
        return bookRepository.existsById(bookId) ;
    }
    public boolean isAllocated(Long bookId) throws BookNotFoundException {
        return fetchBook(bookId).getAllocated() ;
    }
    public boolean isClosed(Long bookId) throws BookNotFoundException {
        return fetchBook(bookId).getClosedDt() != null  ;
    }
}
