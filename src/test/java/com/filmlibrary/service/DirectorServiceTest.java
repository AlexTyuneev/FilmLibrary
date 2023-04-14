package com.filmlibrary.service;


import com.filmlibrary.DirectorTestData;
import com.filmlibrary.dto.AddFilmDTO;
import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.exception.MyDeleteException;
import com.filmlibrary.mapper.DirectorMapper;
import com.filmlibrary.model.Director;
import com.filmlibrary.repository.DirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DirectorServiceTest
      extends GenericTest<Director, DirectorDTO> {
    
    public DirectorServiceTest() {
        super();
        FilmService filmService = Mockito.mock(FilmService.class);
        repository = Mockito.mock(DirectorRepository.class);
        mapper = Mockito.mock(DirectorMapper.class);
        service = new DirectorService((DirectorRepository) repository, (DirectorMapper) mapper, filmService);
    }
    
    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(DirectorTestData.DIRECTOR_LIST);
        Mockito.when(mapper.toDTOs(DirectorTestData.DIRECTOR_LIST)).thenReturn(DirectorTestData.DIRECTOR_DTO_LIST);
        List<DirectorDTO> directorDTOS = service.listAll();
        log.info("Testing getAll(): " + directorDTOS);
        assertEquals(DirectorTestData.DIRECTOR_LIST.size(), directorDTOS.size());
    }
    
    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(DirectorTestData.DIRECTOR_1));
        Mockito.when(mapper.toDto(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
        DirectorDTO directorDTO = service.getOne(1L);
        log.info("Testing getOne(): " + directorDTO);
        assertEquals(DirectorTestData.DIRECTOR_DTO_1, directorDTO);
    }
    
    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(DirectorTestData.DIRECTOR_DTO_1)).thenReturn(DirectorTestData.DIRECTOR_1);
        Mockito.when(mapper.toDto(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
        Mockito.when(repository.save(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_1);
        DirectorDTO directorDTO = service.create(DirectorTestData.DIRECTOR_DTO_1);
        log.info("Testing create(): " + directorDTO);
        assertEquals(DirectorTestData.DIRECTOR_DTO_1, directorDTO);
    }
    
    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(DirectorTestData.DIRECTOR_DTO_1)).thenReturn(DirectorTestData.DIRECTOR_1);
        Mockito.when(mapper.toDto(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
        Mockito.when(repository.save(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_1);
        DirectorDTO directorDTO = service.update(DirectorTestData.DIRECTOR_DTO_1);
        log.info("Testing update(): " + directorDTO);
        assertEquals(DirectorTestData.DIRECTOR_DTO_1, directorDTO);
    }
    
    @Order(5)
    @Test
    @Override
    protected void delete() throws MyDeleteException {
        Mockito.when(((DirectorRepository) repository).checkDirectorForDeletion(1L)).thenReturn(true);
//        Mockito.when(directorRepository.checkDirectorForDeletion(2L)).thenReturn(false);
        Mockito.when(repository.save(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_1);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(DirectorTestData.DIRECTOR_1));
        log.info("Testing delete() before: " + DirectorTestData.DIRECTOR_1.isDeleted());
        service.deleteSoft(1L);
        log.info("Testing delete() after: " + DirectorTestData.DIRECTOR_1.isDeleted());
        assertTrue(DirectorTestData.DIRECTOR_1.isDeleted());
    }
    
    @Order(6)
    @Test
    @Override
    protected void restore() {
        DirectorTestData.DIRECTOR_3.setDeleted(true);
        Mockito.when(repository.save(DirectorTestData.DIRECTOR_3)).thenReturn(DirectorTestData.DIRECTOR_3);
        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(DirectorTestData.DIRECTOR_3));
        log.info("Testing restore() before: " + DirectorTestData.DIRECTOR_3.isDeleted());
        ((DirectorService) service).restore(3L);
        log.info("Testing restore() after: " + DirectorTestData.DIRECTOR_3.isDeleted());
        assertFalse(DirectorTestData.DIRECTOR_3.isDeleted());
    }
    
    @Order(7)
    @Test
    void searchDirectors() {
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "directorFio"));
        Mockito.when(((DirectorRepository) repository).findAllByDirectorFioContainsIgnoreCaseAndIsDeletedFalse("directorFio1", pageRequest))
              .thenReturn(new PageImpl<>(DirectorTestData.DIRECTOR_LIST));
        Mockito.when(mapper.toDTOs(DirectorTestData.DIRECTOR_LIST)).thenReturn(DirectorTestData.DIRECTOR_DTO_LIST);
        Page<DirectorDTO> directorDTOList = ((DirectorService) service).searchDirectors("directorFio1", pageRequest);
        log.info("Testing searchDirectors(): " + directorDTOList);
        assertEquals(DirectorTestData.DIRECTOR_DTO_LIST, directorDTOList.getContent());
    }
    
    @Order(8)
    @Test
    void addFilm() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(DirectorTestData.DIRECTOR_1));
        Mockito.when(service.getOne(1L)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
        Mockito.when(repository.save(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_1);
        ((DirectorService) service).addFilm(new AddFilmDTO(1L, 1L));
        log.info("Testing addFilm(): " + DirectorTestData.DIRECTOR_DTO_1.getFilmsIds());
        assertTrue(DirectorTestData.DIRECTOR_DTO_1.getFilmsIds().size() >= 1);
    }
    
    @Order(9)
    @Test
    protected void getAllNotDeleted() {
        DirectorTestData.DIRECTOR_3.setDeleted(true);
        List<Director> directors = DirectorTestData.DIRECTOR_LIST.stream().filter(Predicate.not(Director::isDeleted)).toList();
        Mockito.when(repository.findAllByIsDeletedFalse()).thenReturn(directors);
        Mockito.when(mapper.toDTOs(directors)).thenReturn(
              DirectorTestData.DIRECTOR_DTO_LIST.stream().filter(Predicate.not(DirectorDTO::isDeleted)).toList());
        List<DirectorDTO> directorDTOS = service.listAllNotDeleted();
        log.info("Testing getAllNotDeleted(): " + directorDTOS);
        assertEquals(directors.size(), directorDTOS.size());
    }
    
}
