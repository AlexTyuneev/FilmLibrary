package com.filmlibrary.service;

import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.dto.OrderDTO;
import com.filmlibrary.mapper.OrderMapper;
import com.filmlibrary.model.Order;
import com.filmlibrary.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService
      extends GenericService<Order, OrderDTO> {
    private OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private FilmService filmService;
    // private final OrderMapper orderMapper;


    protected OrderService(OrderRepository orderRepository,
                           OrderMapper orderMapper, FilmService filmService) {
        super(orderRepository, orderMapper);
        this.filmService = filmService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Page<OrderDTO> listUserRentFilms(final Long id,
                                                   final Pageable pageable) {
        Page<Order> objects = orderRepository.getOrderByUserId(id, pageable);
        List<OrderDTO> results = orderMapper.toDTOs(objects.getContent());
        return new PageImpl<>(results, pageable, objects.getTotalElements());
    }

    public OrderDTO rentFilm(OrderDTO orderDTO) {
        FilmDTO filmDTO = filmService.getOne(orderDTO.getFilmId());
        // filmDTO.setAmount(filmDTO.getAmount() - 1);

        filmService.update(filmDTO);
        long rentPeriod = orderDTO.getRentPeriod() != null ? orderDTO.getRentPeriod() : 14L;
        orderDTO.setRentDate(LocalDateTime.now());
        orderDTO.setReturned(false);
        orderDTO.setRentPeriod((int) rentPeriod);
        orderDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        orderDTO.setCreatedWhen(LocalDateTime.now());
        orderDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        return mapper.toDto(repository.save(mapper.toEntity(orderDTO)));
    }

    public void returnBook(final Long id) {
        OrderDTO orderDTO = getOne(id);
        orderDTO.setReturned(true);
        orderDTO.setReturnDate(LocalDateTime.now());
        FilmDTO filmDTO = orderDTO.getFilmDTO();
//        filmDTO.setAmount(filmDTO.getAmount() + 1);

         update(orderDTO);
        filmService.update(filmDTO);
    }
}
