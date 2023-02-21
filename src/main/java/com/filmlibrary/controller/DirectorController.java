package com.filmlibrary.controller;

import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.repository.DirectorRepository;
import com.filmlibrary.repository.FilmRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;


@RestController
@RequestMapping("/directors")
@Tag(name = "Режиссеры",
     description = "Контроллер для работы с режиссерами фильмотеки")
public class DirectorController
      extends GenericController<Director> {
    
    private final FilmRepository filmRepository;
    private final DirectorRepository directorRepository;
    
    public DirectorController(FilmRepository filmRepository,
                              DirectorRepository directorRepository) {
        super(directorRepository);
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
    }
    
    @Operation(description = "Добавить фильм к режиссеру", method = "addFilm")
    @RequestMapping(value = "/addFilm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Director> addAuthor(@RequestParam(value = "filmId") Long bookId,
                                            @RequestParam(value = "directorId") Long authorId) {
        Film book = filmRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Фильм с переданным ID не найден"));
        Director author = directorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("Режиссер с таким ID не найден"));
        author.getFilms().add(book);
        return ResponseEntity.status(HttpStatus.OK).body(directorRepository.save(author));
    }
}
