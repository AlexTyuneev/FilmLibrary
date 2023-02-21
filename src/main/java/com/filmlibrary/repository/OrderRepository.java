package com.filmlibrary.repository;

import com.filmlibrary.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
      extends GenericRepository<Order> {
}
