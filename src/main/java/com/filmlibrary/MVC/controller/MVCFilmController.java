package com.filmlibrary.MVC.controller;

import com.filmlibrary.dto.AddDirector2FilmDTO;
import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.dto.FilmWithDirectorsDTO;
import com.filmlibrary.mapper.DirectorMapper;
import com.filmlibrary.repository.DirectorRepository;
import com.filmlibrary.service.DirectorService;
import com.filmlibrary.service.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("films")
public class MVCFilmController {
    private final FilmService filmService;
    private final DirectorService directorService;
    private final DirectorMapper directorMapper;
    private final DirectorRepository directorRepository;

    public MVCFilmController(FilmService filmService, DirectorService directorService,
                             DirectorMapper directorMapper, DirectorRepository directorRepository) {
        this.filmService = filmService;
        this.directorService = directorService;
        this.directorMapper = directorMapper;
        this.directorRepository = directorRepository;
    }
    
    @GetMapping("")
    public String getAll(Model model) {
        List<FilmWithDirectorsDTO> result = filmService.getAllFilmsWithDirectors();
        model.addAttribute("films", result);
        model.addAttribute("films", result);
        return "films/viewAllFilms";
    }
    
    @GetMapping("/add")
    public String create() {
        return "films/addFilm";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("filmForm") FilmDTO filmDTO) {
        filmService.create(filmDTO);
        return "redirect:/films";
    }

    @GetMapping("/addDirector2Film/{id}")
    public String addDirector(@PathVariable Long id,
                              Model model) {
        List<DirectorDTO> directorsDTOs = directorMapper.toDTOs(directorRepository.findAll());
        FilmDTO filmDTO = filmService.getOne(id);
        model.addAttribute("filmDTO", filmDTO);
        model.addAttribute("director", directorsDTOs);
        model.addAttribute("film", filmService.getOne(filmDTO.getId()).getFilmTitle());
        return "/films/addDirector2Film";
    }

//    @PostMapping("/addDirector2Film")
//    public String addDirector(@ModelAttribute("id") Long filmId,
//                              @ModelAttribute("directorsFio") Long directorId) throws NotFoundException {
//        filmService.addDirectorToFilm(filmId, directorId);
//        return "redirect:/films";
//    }

    @PostMapping("/addDirector2Film")
    public String addBook(@ModelAttribute("filmDirectorForm") AddDirector2FilmDTO addDirector2FilmDTO) {
        filmService.addDirector(addDirector2FilmDTO);
        return "redirect:/films";
    }




}
