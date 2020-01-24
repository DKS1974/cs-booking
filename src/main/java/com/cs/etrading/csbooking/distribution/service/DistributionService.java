package com.cs.etrading.csbooking.distribution.service;

import com.cs.etrading.csbooking.books.exception.BookNotFoundException;
import com.cs.etrading.csbooking.books.model.BookCloseEvent;
import com.cs.etrading.csbooking.books.service.BookService;
import com.cs.etrading.csbooking.distribution.dao.DistributionRepository;
import com.cs.etrading.csbooking.distribution.model.Distribution;
import com.cs.etrading.csbooking.distribution.model.OrderStatus;
import com.cs.etrading.csbooking.orders.model.Order;
import com.cs.etrading.csbooking.orders.model.OrderType;
import com.cs.etrading.csbooking.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistributionService {

    @Autowired
    private DistributionRepository distributionRepository ;

    @Autowired
    private OrderService orderService ;

    @Autowired
    private BookService bookService;

    @Transactional
    public void distribute(BookCloseEvent bookCloseEvent) throws BookNotFoundException {
        Long bookId = bookCloseEvent.getBookId() ;
        if(bookService.bookExists(bookId) && bookService.isClosed(bookId) && !bookService.isAllocated(bookId) ) {
            List<Order> orders = orderService.fetchAllOrdersByBookId(bookId) ;
            processValidOrders(filterValidOrders(bookCloseEvent, orders), bookCloseEvent);
            processInvalidOrders(filterInvalidOrders(bookCloseEvent, orders), bookCloseEvent);
        } else {
            throw new BookNotFoundException(bookId);
        }
    }

    public List<Distribution> getDistributionsByBookId(Long bookId) {
        return distributionRepository.findAllByOrder_Book_BookId(bookId) ;
    }

    private Integer calutateTotalQuantityOfValidOrder(List<Order> validOrder ) {
       return  validOrder.stream().mapToInt(Order::getQuantity).sum() ;
    }

    private void processValidOrders( List<Order> validOrder, BookCloseEvent bookCloseEvent) {
        Double sumOfQuantity  =  calutateTotalQuantityOfValidOrder(validOrder).doubleValue() ;
        validOrder.stream().forEach(order -> {
            distributionRepository.save(
                    Distribution.builder()
                            .order(order)
                            .price(bookCloseEvent.getPrice())
                            .quantity(calculateAllocation(bookCloseEvent, sumOfQuantity, order))
                            .status(OrderStatus.executed)
                            .build()
            ) ;});
    }

    private Integer calculateAllocation(BookCloseEvent bookCloseEvent, Double sumOfQuantity, Order order) {
        Integer maxAllocation =  Long.valueOf(Math.round(( order.getQuantity()/ sumOfQuantity) * bookCloseEvent.getQuantity())).intValue();
        return order.getQuantity() >= maxAllocation ?  maxAllocation : order.getQuantity() ;
    }

    private void processInvalidOrders( List<Order> invalidOrder, BookCloseEvent bookCloseEvent) {
        invalidOrder.stream().forEach(order -> {
            distributionRepository.save(
                    Distribution.builder()
                            .order((Order)order)
                            .price(bookCloseEvent.getPrice())
                            .quantity(0)
                            .status(OrderStatus.invalid)
                            .build()
            ) ;});
    }
    private List<Order>  filterValidOrders(BookCloseEvent bookCloseEvent, List<Order> orders) {
         return orders.stream()
                 .filter(order -> ( OrderType.limit.equals(order.getType()) && order.getPrice().compareTo(bookCloseEvent.getPrice()) >= 0
                    || OrderType.market.equals(order.getType())))
                 .collect(Collectors.toList() );
    }

    private List<Order> filterInvalidOrders(BookCloseEvent bookCloseEvent, List<Order> orders) {
        return orders.stream()
                .filter(order -> OrderType.limit.equals(order.getType()) && order.getPrice().compareTo(bookCloseEvent.getPrice()) < 0 )
                .collect(Collectors.toList() );
    }

}
