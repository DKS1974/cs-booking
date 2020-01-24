package com.cs.etrading.csbooking.orders.dao;

import com.cs.etrading.csbooking.orders.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    public List<Order> findAllByBook_BookId(Long id) ;
}
