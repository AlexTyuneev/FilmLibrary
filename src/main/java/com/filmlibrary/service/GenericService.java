package com.filmlibrary.service;

import com.filmlibrary.dto.GenericDTO;
import com.filmlibrary.mapper.GenericMapper;
import com.filmlibrary.model.GenericModel;
import com.filmlibrary.repository.GenericRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

/**
 * Абстрактный сервис который хранит в себе реализацию CRUD операций по умолчанию
 * Если реализация отличная от того что представлено в этом классе,
 * то она переопределяется в сервисе для конкретной сущности
 *
 * @param <T> - Сущность с которой мы работаем
 * @param <N> - DTO, которую мы будем отдавать/принимать дальше
 */
@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {
    
    //Инжектим абстрактный репозиторий для работы с базой данных
    protected final GenericRepository<T> repository;
    protected final GenericMapper<T, N> mapper;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<T> repository,
                             GenericMapper<T, N> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    public List<N> listAll() {
        return mapper.toDTOs(repository.findAll());
    }
    
    public N getOne(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Данных по заданному id: " + id + " не найдены")));
    }
    
    public N create(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
    
    public N update(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
