package com.filmlibrary.service;

import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.dto.OrderDTO;
import com.filmlibrary.mapper.OrderMapper;
import com.filmlibrary.model.Order;
import com.filmlibrary.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService
      extends GenericService<Order, OrderDTO> {
    private OrderRepository orderRepository;
    private FilmService filmService;
    // private final OrderMapper orderMapper;


    protected OrderService(OrderRepository orderRepository,
                           OrderMapper orderMapper, FilmService filmService, OrderMapper orderMapper1) {
        super(orderRepository, orderMapper);
        this.filmService = filmService;
        this.orderRepository = orderRepository;
    }

    public OrderDTO rentFilm(OrderDTO orderDTO) {
        FilmDTO filmDTO = filmService.getOne(orderDTO.getFilmId());
        filmService.update(filmDTO);
        long rentPeriod = orderDTO.getRentPeriod() != null ? orderDTO.getRentPeriod() : 14L;
        orderDTO.setRentDate(LocalDateTime.now());
        orderDTO.setReturned(false);
        orderDTO.setRentPeriod((int) rentPeriod);
        orderDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));

        return mapper.toDto(repository.save(mapper.toEntity(orderDTO)));
    }

    public void returnBook(final Long id) {
        OrderDTO orderDTO = getOne(id);
        orderDTO.setReturned(true);
        orderDTO.setReturnDate(LocalDateTime.now());
        FilmDTO filmDTO = orderDTO.getFilmDTO();
        update(orderDTO);
        filmService.update(filmDTO);
    }
}
