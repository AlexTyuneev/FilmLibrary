package com.filmlibrary.repository;

import com.filmlibrary.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
      extends GenericRepository<Order> {
    Page<Order> getOrderByUserId(Long userId,
                                               Pageable pageable);
}
