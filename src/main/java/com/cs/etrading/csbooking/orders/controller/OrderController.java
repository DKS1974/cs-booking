package com.cs.etrading.csbooking.orders.controller;

import com.cs.etrading.csbooking.orders.model.Order;
import com.cs.etrading.csbooking.orders.service.OrderService;
import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import com.cs.etrading.csbooking.common.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class OrderController {

    @Autowired
    private OrderService bidService;

    @GetMapping("/order/{id}")
    @ResponseBody
    public Order getBidById(@PathVariable( "id") Long id) throws NotFoundException {
        return bidService.fetchOrderByOrderId(id) ;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<Order> createBid(@RequestBody @Valid Order order) throws BookNotFoundException {
       return  ResponseEntity.status(HttpStatus.CREATED).body(bidService.createOrder(order)) ;
    }

    @GetMapping ("/order")
    @ResponseBody
    public List<Order> getBidByBook(@RequestParam Long bookId) {
        return bidService.fetchAllOrdersByBookId(bookId) ;
    }
}
