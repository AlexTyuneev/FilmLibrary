package com.filmlibrary;

import com.filmlibrary.dto.OrderDTO;
import com.filmlibrary.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderTestData {

    OrderDTO FILM_RENT_INFO_DTO = new OrderDTO(LocalDateTime.now(),
            14,
                                                             LocalDateTime.now(),
                                                             false,
            false,
                                                             1L,
                                                             1L,
                                                             null);
    
    List<OrderDTO> FILM_RENT_INFO_DTO_LIST = List.of(FILM_RENT_INFO_DTO);

    Order FILM_RENT_INFO = new Order(null,
                                                   null,
                                                   LocalDateTime.now(),
            14,
                                                   LocalDateTime.now(),
                                                   false, false
                                                   );
    
    List<Order> FILM_RENT_INFO_LIST = List.of(FILM_RENT_INFO);
}
