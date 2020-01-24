package com.cs.etrading.csbooking.orders.service;

import com.cs.etrading.csbooking.books.exception.BookClosedException;
import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.books.service.BookService;
import com.cs.etrading.csbooking.common.exception.NotFoundException;
import com.cs.etrading.csbooking.orders.dao.OrderRepository;
import com.cs.etrading.csbooking.orders.model.Order;
import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager ;

    @Autowired
    private BookService bookService ;

    @Transactional
    public Order createOrder(Order order) throws BookNotFoundException, BookClosedException {
        validateBook(order.getBook().getBookId());
        try {
            Order savedOrder = orderRepository.save(order) ;
            entityManager.flush();
            entityManager.refresh(savedOrder);
            entityManager.refresh(savedOrder.getBook());
            return savedOrder ;
        } catch (DataIntegrityViolationException exception){
            throw new BookNotFoundException(order.getBook().getBookId()) ;
        }
    }

    private void validateBook(Long bookId )throws BookClosedException {
        if(bookService.isClosed(bookId))
            throw new BookClosedException(bookId) ;
    }

    public List<Order> fetchAllOrdersByBookId(Long bookId) {
        return orderRepository.findAllByBook_BookId(bookId) ;
    }

    public Order fetchOrderByOrderId(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order",id));
    }
}
