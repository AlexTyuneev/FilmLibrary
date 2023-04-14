package com.filmlibrary.service;

import com.filmlibrary.constants.Errors;
import com.filmlibrary.dto.*;
import com.filmlibrary.exception.MyDeleteException;
import com.filmlibrary.mapper.DirectorMapper;
import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.repository.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Set;


@Service
public class DirectorService
      extends GenericService<Director, DirectorDTO> {

    private final DirectorRepository directorRepository;
    private final FilmService filmService;
//    private final FilmRepository filmRepository;

//    protected DirectorService(DirectorRepository directorRepository,
//                              DirectorMapper directorMapper, FilmRepository filmRepository,
//                              DirectorWithFilmsMapper directorWithFilmsMapper) {
//        super(directorRepository, directorMapper);
//        this.directorRepository = directorRepository;
//        this.filmRepository = filmRepository;
//        this.directorWithFilmsMapper = directorWithFilmsMapper;
//    }



    protected DirectorService(DirectorRepository directorRepository,
                            DirectorMapper directorMapper,
                            FilmService filmService) {
        super(directorRepository, directorMapper);
        this.directorRepository = directorRepository;
        this.filmService = filmService;
    }

//    public List<DirectorWithFilmsDTO> getDirectorsByFilmId(Long filmId) throws NotFoundException {
//        Film film = filmRepository.findById(filmId)
//                .orElseThrow(() -> new NotFoundException("Фильма с таким ID не найдено"));
//        List<Director> directorList = new ArrayList<>(film.getDirectors());
//        return directorWithFilmsMapper.toDTOs(directorList);
//    }

    public Page<DirectorDTO> searchDirectors(final String directorFio,
                                         Pageable pageable) {
        Page<Director> directors = directorRepository.findAllByDirectorFioContainsIgnoreCaseAndIsDeletedFalse(directorFio, pageable);
        List<DirectorDTO> result = mapper.toDTOs(directors.getContent());
        return new PageImpl<>(result, pageable, directors.getTotalElements());
    }

    public void addFilm (AddFilmDTO addDirector2FilmDTO) {
        DirectorDTO director = getOne(addDirector2FilmDTO.getDirectorId());
        filmService.getOne(addDirector2FilmDTO.getFilmId());
        director.getFilmsIds().add(addDirector2FilmDTO.getFilmId());
        update(director);
    }
    @Override
    public void deleteSoft(Long objectId) throws MyDeleteException {
        Director director = directorRepository.findById(objectId).orElseThrow(
                () -> new org.webjars.NotFoundException("Режиссера с заданным id=" + objectId + " не существует."));
        boolean directorCanBeDeleted = directorRepository.checkDirectorForDeletion(objectId);
        if (directorCanBeDeleted) {
            markAsDeleted(director);
            Set<Film> films = director.getFilms();
            if (films != null && films.size() > 0) {
                films.forEach(this::markAsDeleted);
            }
            directorRepository.save(director);
        }
        else {
            throw new MyDeleteException(Errors.Directors.DIRECTOR_DELETE_ERROR);
        }
    }

    public void restore(Long objectId) {
        Director director = directorRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Режиссера с заданным id=" + objectId + " не существует."));
        unMarkAsDeleted(director);
        Set<Film> films = director.getFilms();
        if (films != null && films.size() > 0) {
            films.forEach(this::unMarkAsDeleted);
        }
        directorRepository.save(director);
    }

//    public List<DirectorWithFilmsDTO> getAllDirectorsWithFilms() {
//        return directorWithFilmsMapper.toDTOs(directorRepository.findAll());
//    }
}
