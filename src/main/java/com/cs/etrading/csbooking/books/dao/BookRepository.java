package com.cs.etrading.csbooking.books.dao;

import com.cs.etrading.csbooking.books.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {
    public List findAllByBookName(String bookName) ;
}
