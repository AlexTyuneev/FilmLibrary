package com.filmlibrary.service;

import com.filmlibrary.dto.AddDirector2FilmDTO;
import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.dto.FilmWithDirectorsDTO;
import com.filmlibrary.mapper.FilmMapper;
import com.filmlibrary.mapper.FilmWithDirectorsMapper;
import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.repository.DirectorRepository;
import com.filmlibrary.repository.FilmRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService
      extends GenericService<Film, FilmDTO> {
    //  Инжектим конкретный репозиторий для работы с таблицей books
    private final FilmRepository filmRepository;
    private final FilmWithDirectorsMapper filmWithDirectorsMapper;
    private final DirectorRepository directorRepository;


    protected FilmService(FilmRepository filmRepository,
                          FilmMapper filmMapper, FilmWithDirectorsMapper filmWithDirectorsMapper,
                          DirectorRepository directorRepository) {
        //Передаем этот репозиторй в абстрактный севрис,
        //чтобы он понимал с какой таблицей будут выполняться CRUD операции
        super(filmRepository, filmMapper);
        this.filmRepository = filmRepository;
        this.filmWithDirectorsMapper = filmWithDirectorsMapper;
        this.directorRepository = directorRepository;
    }
    public List<FilmWithDirectorsDTO> getAllFilmsWithDirectors() {
        return filmWithDirectorsMapper.toDTOs(filmRepository.findAll());
    }

//    public FilmDTO addDirectorToFilm(Long filmId, Long directorId) throws NotFoundException {
//        Film film = filmRepository.findById(filmId)
//                .orElseThrow(() -> new NotFoundException("Фильм с id " + filmId + " не найден"));
//        Director director = directorRepository.findById(directorId)
//                .orElseThrow(() -> new NotFoundException("Режиссер с id " + directorId + " не найден"));
//        film.getDirectors().add(director);
//        return mapper.toDto(filmRepository.save(film));
//    }

    public void addDirector(AddDirector2FilmDTO addDirector2FilmDTO) {
        FilmDTO film = getOne(addDirector2FilmDTO.getFilmId());
        film.getDirectorsIds().add(addDirector2FilmDTO.getDirectorId());
        update(film);
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
