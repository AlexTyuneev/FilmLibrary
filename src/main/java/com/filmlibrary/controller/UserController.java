package com.filmlibrary.controller;

import com.filmlibrary.model.Film;
import com.filmlibrary.model.Order;
import com.filmlibrary.model.User;
import com.filmlibrary.repository.FilmRepository;
import com.filmlibrary.repository.OrderRepository;
import com.filmlibrary.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
     description = "Контроллер для работы с пользователями библиотеки")
public class UserController
      extends GenericController<User> {
    
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FilmRepository filmRepository;

    public UserController(UserRepository userRepository, OrderRepository orderRepository, FilmRepository filmRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.filmRepository = filmRepository;
    }

    @Operation(description = "Получить список всех фильмов пользователя", method = "getFilms")
    @RequestMapping(value = "/getUserFilms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Film>> addUserFilms(@RequestParam(value = "id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с переданным ID не найден"));
        Set<Order> userOrders = user.getOrders();
        Set<Film> films = userOrders.stream()
                .map(Order::getFilm)
                .collect(Collectors.toSet());

        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

}
