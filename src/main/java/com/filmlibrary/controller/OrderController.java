package com.filmlibrary.controller;

import com.filmlibrary.model.Order;
import com.filmlibrary.repository.OrderRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Tag(name = "Заказы фильмов",
     description = "Контроллер для работы с заказами фильмов")
public class OrderController
      extends GenericController<Order> {

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
