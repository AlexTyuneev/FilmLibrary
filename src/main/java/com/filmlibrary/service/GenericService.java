package com.filmlibrary.service;

import com.filmlibrary.dto.GenericDTO;
import com.filmlibrary.exception.MyDeleteException;
import com.filmlibrary.mapper.GenericMapper;
import com.filmlibrary.model.GenericModel;
import com.filmlibrary.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Абстрактный сервис, который хранит в себе реализацию CRUD операций по-умолчанию.
 * Если реализация отличная от того, что представлено в этом классе,
 * то она переопределяется в реализации конкретного сервиса.
 *
 * @param <T> - Сущность, с которой мы работаем.
 * @param <N> - DTO, которую мы будем отдавать/принимать дальше.
 */
@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {
    
    //Инжектим абстрактный репозиторий для работы с базой данных
    protected final GenericRepository<T> repository;
    //Инжектим абстрактный маппер для преобразований из DTO -> Entity, и обратно.
    protected final GenericMapper<T, N> mapper;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<T> repository,
                             GenericMapper<T, N> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    /**
     * Метод, возвращающий полный список всех сущностей.
     *
     * @return Список сконвертированных сущностей в DTO
     */
    public List<N> listAll() {
        return mapper.toDTOs(repository.findAll());
    }
    
    public Page<N> listAll(Pageable pageable) {
        Page<T> objects = repository.findAll(pageable);
        List<N> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }
    
    /***
     * Получить информацию о конкретном объекте/сущности по ID.
     *
     * @param id - идентификатор сущности для поиска.
     * @return - конкретная сущность в формате DTO
     */
    public N getOne(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Данных по заданному id: " + id + " не найдены")));
    }
    
    /***
     * Создание сущности в БД.
     *
     * @param object - информация о сущности/объекте.
     * @return - сохраненная в БД сущность в формате DTO.
     */
    public N create(N object) {
        /*
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now()); */
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
    
    /***
     * Обновление сущности в БД.
     *
     * @param object - информация о сущности/объекте.
     * @return - обновленная в БД сущность в формате DTO.
     */
    public N update(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
    
    /***
     * Удаление сущности из БД.
     *
     * @param id - идентификатор сущности, которая должна быть удалена.
     */
    public void delete(Long id) throws MyDeleteException {
        repository.deleteById(id);
    }
}
