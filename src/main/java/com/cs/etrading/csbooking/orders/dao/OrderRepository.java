package com.cs.etrading.csbooking.orders.dao;

import com.cs.etrading.csbooking.orders.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
    public List<Order> findAllByBook_BookId(Long id) ;
}
