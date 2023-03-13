package com.filmlibrary.service;

import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.mapper.FilmMapper;
import com.filmlibrary.model.Film;
import com.filmlibrary.repository.FilmRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmService
      extends GenericService<Film, FilmDTO> {
    //  Инжектим конкретный репозиторий для работы с таблицей books
    private final FilmRepository repository;
    
    protected FilmService(FilmRepository repository,
                          FilmMapper mapper) {
        //Передаем этот репозиторй в абстрактный севрис,
        //чтобы он понимал с какой таблицей будут выполняться CRUD операции
        super(repository, mapper);
        this.repository = repository;
    }
    
    //    public BookDTO getOne(Long id) {
//        return bookMapper.toDTO(bookRepository.findById(id).orElseThrow());
////        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных не существует по переданному id:" + id));
////        return new BookDTO(book);
//    }

//    public List<BookDTO> getAll() {
//        return new BookDTO(book);
//    }
}
