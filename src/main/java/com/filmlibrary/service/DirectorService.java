package com.filmlibrary.service;

import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.dto.DirectorWithFilmsDTO;
import com.filmlibrary.dto.FilmWithDirectorsDTO;
import com.filmlibrary.mapper.DirectorMapper;
import com.filmlibrary.mapper.DirectorWithFilmsMapper;
import com.filmlibrary.mapper.FilmWithDirectorsMapper;
import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.repository.DirectorRepository;
import com.filmlibrary.repository.FilmRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class DirectorService
      extends GenericService<Director, DirectorDTO> {

    private final DirectorRepository directorRepository;
    private final DirectorWithFilmsMapper directorWithFilmsMapper;
    private final FilmRepository filmRepository;

    protected DirectorService(DirectorRepository directorRepository,
                              DirectorMapper directorMapper, FilmRepository filmRepository,
                              DirectorWithFilmsMapper directorWithFilmsMapper) {
        super(directorRepository, directorMapper);
        this.directorRepository = directorRepository;
        this.filmRepository = filmRepository;
        this.directorWithFilmsMapper = directorWithFilmsMapper;

    }

    public List<DirectorWithFilmsDTO> getDirectorsByFilmId(Long filmId) throws NotFoundException {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильма с таким ID не найдено"));
        List<Director> directorList = new ArrayList<>(film.getDirectors());
        return directorWithFilmsMapper.toDTOs(directorList);
    }

    public List<DirectorWithFilmsDTO> getAllDirectorsWithFilms() {
        return directorWithFilmsMapper.toDTOs(directorRepository.findAll());
    }
}
