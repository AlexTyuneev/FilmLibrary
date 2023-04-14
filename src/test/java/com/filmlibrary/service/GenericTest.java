package com.filmlibrary.service;

import com.filmlibrary.dto.GenericDTO;
import com.filmlibrary.exception.MyDeleteException;
import com.filmlibrary.mapper.GenericMapper;
import com.filmlibrary.model.GenericModel;
import com.filmlibrary.repository.GenericRepository;
import com.filmlibrary.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

//TODO: https://habr.com/ru/post/444982/ - Mokito
public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {
    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;
    
    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(CustomUserDetails
                                                                                                           .builder()
                                                                                                           .username("USER"),
                                                                                                     null,
                                                                                                     null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    protected abstract void getAll();
    
    protected abstract void getOne();
    
    protected abstract void create();
    
    protected abstract void update();
    
    protected abstract void delete() throws MyDeleteException;
    
    protected abstract void restore();
    
    protected abstract void getAllNotDeleted();
}
