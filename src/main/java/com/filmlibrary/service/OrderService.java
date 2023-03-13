package com.filmlibrary.service;

import com.filmlibrary.dto.OrderDTO;
import com.filmlibrary.mapper.OrderMapper;
import com.filmlibrary.model.Order;
import com.filmlibrary.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService
      extends GenericService<Order, OrderDTO> {
    private OrderRepository orderRepository;
    
    protected OrderService(OrderRepository orderRepository,
                           OrderMapper orderMapper) {
        super(orderRepository, orderMapper);
        this.orderRepository = orderRepository;
    }
}
