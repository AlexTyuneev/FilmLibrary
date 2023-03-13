package com.filmlibrary.mapper;

import com.filmlibrary.dto.OrderDTO;
import com.filmlibrary.model.Order;
import com.filmlibrary.repository.FilmRepository;
import com.filmlibrary.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.Set;

@Component
public class OrderMapper
      extends GenericMapper<Order, OrderDTO> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    
    protected OrderMapper(ModelMapper mapper,
                          FilmRepository filmRepository,
                          UserRepository userRepository) {
        super(mapper, Order.class, OrderDTO.class);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }
    
    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(Order.class, OrderDTO.class)
              .addMappings(m -> m.skip(OrderDTO::setUserId)).setPostConverter(toDtoConverter())
              .addMappings(m -> m.skip(OrderDTO::setFilmId)).setPostConverter(toDtoConverter());
        
        super.modelMapper.createTypeMap(OrderDTO.class, Order.class)
              .addMappings(m -> m.skip(Order::setUser)).setPostConverter(toEntityConverter())
              .addMappings(m -> m.skip(Order::setFilm)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(OrderDTO source, Order destination) {
        destination.setFilm(filmRepository.findById(source.getFilmId()).orElseThrow(() -> new NotFoundException("Книги не найдено")));
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователя не найдено")));
    }
    
    @Override
    protected void mapSpecificFields(Order source, OrderDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setFilmId(source.getFilm().getId());
    }
    
    @Override
    protected Set<Long> getIds(Order entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
