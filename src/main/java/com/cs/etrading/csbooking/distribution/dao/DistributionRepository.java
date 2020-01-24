package com.cs.etrading.csbooking.distribution.dao;

import com.cs.etrading.csbooking.distribution.model.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRepository extends CrudRepository<Distribution,Long> {
    List<Distribution> findAllByOrder_Book_BookId(Long bookId) ;
}
