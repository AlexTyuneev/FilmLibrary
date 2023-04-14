package com.filmlibrary.service;

import com.filmlibrary.constants.Errors;
import com.filmlibrary.dto.AddDirector2FilmDTO;
import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.dto.FilmSearchDTO;

import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.dto.FilmWithDirectorsDTO;
import com.filmlibrary.exception.MyDeleteException;
import com.filmlibrary.mapper.FilmMapper;
import com.filmlibrary.mapper.FilmWithDirectorsMapper;
import com.filmlibrary.utils.FileHelper;

import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FilmService
      extends GenericService<Film, FilmDTO> {
    //  Инжектим конкретный репозиторий для работы с таблицей books
    private final FilmRepository filmRepository;
    private final FilmWithDirectorsMapper filmWithDirectorsMapper;
    // private final DirectorRepository directorRepository;


    protected FilmService(FilmRepository filmRepository,
                          FilmMapper filmMapper, FilmWithDirectorsMapper filmWithDirectorsMapper/*,
                          DirectorRepository directorRepository*/) {
        //Передаем этот репозиторй в абстрактный севрис,
        //чтобы он понимал с какой таблицей будут выполняться CRUD операции
        super(filmRepository, filmMapper);
        this.filmRepository = filmRepository;
        this.filmWithDirectorsMapper = filmWithDirectorsMapper;
        //this.directorRepository = directorRepository;
    }
    public Page<FilmWithDirectorsDTO> getAllFilmsWithDirectors(Pageable pageable) {
        Page<Film> filmsPaginated = filmRepository.findAll(pageable);
        //List<FilmWithDirectorsDTO> result = filmWithDirectorsMapper.toDTOs(filmsPaginated.getContent());
        List<FilmWithDirectorsDTO> result = filmWithDirectorsMapper.toDTOs(filmsPaginated.getContent());
        return new PageImpl<>(result, pageable, filmsPaginated.getTotalElements());
        //return filmWithDirectorsMapper.toDTOs(filmRepository.findAll());
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

    public Page<FilmWithDirectorsDTO> getAllNotDeletedFilmsWithDirectors(Pageable pageable) {
        Page<Film> filmsPaginated = repository.findAllByIsDeletedFalse(pageable);
        List<FilmWithDirectorsDTO> result = filmWithDirectorsMapper.toDTOs(filmsPaginated.getContent());
        return new PageImpl<>(result, pageable, filmsPaginated.getTotalElements());
    }

    public FilmWithDirectorsDTO getFilmWithDirectors(Long id) {
        return filmWithDirectorsMapper.toDto(mapper.toEntity(super.getOne(id)));
    }

    public Page<FilmWithDirectorsDTO> findFilms(FilmSearchDTO filmSearchDTO,
                                              Pageable pageable) {
        String genre = filmSearchDTO.getGenre() != null ? String.valueOf(filmSearchDTO.getGenre().ordinal()) : null;
        Page<Film> filmsPaginated = filmRepository.searchFilms(genre,
                filmSearchDTO.getFilmTitle(),
                filmSearchDTO.getDirectorFio(),
                pageable
        );
        List<FilmWithDirectorsDTO> result = filmWithDirectorsMapper.toDTOs(filmsPaginated.getContent());
        return new PageImpl<>(result, pageable, filmsPaginated.getTotalElements());
    }

    // files/books/year/month/day/file_name_{id}_{created_when}.txt
    // files/книга_id.pdf
    public FilmDTO create(final FilmDTO object,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        object.setOnlineCopyPath(fileName);
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    public FilmDTO update(final FilmDTO object,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        object.setOnlineCopyPath(fileName);
        return mapper.toDto(filmRepository.save(mapper.toEntity(object)));
    }

    @Override
    public void deleteSoft(Long id) throws MyDeleteException {
        Film film = filmRepository.findById(id).orElseThrow(
                () -> new org.webjars.NotFoundException("Фильма с заданным ID=" + id + " не существует"));
//        boolean bookCanBeDeleted = repository.findBookByIdAndBookRentInfosReturnedFalseAndIsDeletedFalse(id) == null;
        boolean filmCanBeDeleted = filmRepository.checkFilmForDeletion(id);
        if (filmCanBeDeleted) {
            if (film.getOnlineCopyPath() != null && !film.getOnlineCopyPath().isEmpty()) {
                FileHelper.deleteFile(film.getOnlineCopyPath());
            }
            markAsDeleted(film);
            filmRepository.save(film);
        }
        else {
            throw new MyDeleteException(Errors.Films.FILM_DELETE_ERROR);
        }
    }

    public void restore(Long objectId) {
        Film film = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Фильма с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(film);
        repository.save(film);
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
