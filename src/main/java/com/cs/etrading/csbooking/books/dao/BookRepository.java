package com.cs.etrading.csbooking.books.dao;

import com.cs.etrading.csbooking.books.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
    public List findAllByBookName(String bookName) ;
}
