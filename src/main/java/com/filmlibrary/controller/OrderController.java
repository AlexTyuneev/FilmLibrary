package com.filmlibrary.controller;

import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.model.Order;
import com.filmlibrary.model.User;
import com.filmlibrary.repository.DirectorRepository;
import com.filmlibrary.repository.FilmRepository;
import com.filmlibrary.repository.OrderRepository;
import com.filmlibrary.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/orders")
@Tag(name = "Заказы фильмов",
     description = "Контроллер для работы с заказами фильмов")
public class OrderController
      extends GenericController<Order> {

    private final OrderRepository orderRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository, FilmRepository filmRepository,
                           UserRepository userRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
    }

    @Operation(description = "Создать новый заказ", method = "create")
    @RequestMapping(value = "/newOrder", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Order> create(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "filmId") Long filmId,
                                        @RequestBody Order order) {
        // orderRepository.save(order);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь с переданным ID не найден"));
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new NotFoundException("Фильм с переданным ID не найден"));
        order.setUser(user);
        order.setFilm(film);
        // return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(order));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(order));



         // ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(order));

        // return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}

