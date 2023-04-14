package com.filmlibrary.MVC.controller;

import com.filmlibrary.annotation.MySecuredAnnotation;
import com.filmlibrary.dto.*;
import com.filmlibrary.exception.MyDeleteException;
import com.filmlibrary.mapper.DirectorMapper;
import com.filmlibrary.repository.DirectorRepository;
import com.filmlibrary.service.FilmService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.filmlibrary.constants.UserRolesConstants.ADMIN;

@Hidden
@Controller
@RequestMapping("films")
@Slf4j
public class MVCFilmController {
    private final FilmService filmService;
//    private final DirectorService directorService;
    private final DirectorMapper directorMapper;
    private final DirectorRepository directorRepository;

    public MVCFilmController(FilmService filmService, DirectorMapper directorMapper, DirectorRepository directorRepository) {
        this.filmService = filmService;
        this.directorMapper = directorMapper;

        this.directorRepository = directorRepository;
    }
    
//    @GetMapping("")
//    public String getAll(Model model) {
//        List<FilmWithDirectorsDTO> result = filmService.getAllFilmsWithDirectors();
//        model.addAttribute("films", result);
//        model.addAttribute("films", result);
//        return "films/viewAllFilms";
//    }
@GetMapping("")
public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                     @RequestParam(value = "size", defaultValue = "5") int pageSize,
                     @ModelAttribute(name = "exception") final String exception,
                     Model model) {
    PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "filmTitle"));
    Page<FilmWithDirectorsDTO> result;
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    if (ADMIN.equalsIgnoreCase(userName)) {
        result = filmService.getAllFilmsWithDirectors(pageRequest);
    }
    else {
        result = filmService.getAllNotDeletedFilmsWithDirectors(pageRequest);
    }
    model.addAttribute("films", result);
    model.addAttribute("exception", exception);
    return "films/viewAllFilms";
}
    @MySecuredAnnotation(value = "ROLE_ADMIN")
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("film", filmService.getFilmWithDirectors(id));
        return "films/viewFilm";
    }
    
    @GetMapping("/add")
    public String create() {
        return "films/addFilm";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("filmForm") FilmDTO filmDTO, @RequestParam MultipartFile file) {
        if (file != null && file.getSize() > 0) {
            filmService.create(filmDTO, file);
        }
        else {
            filmService.create(filmDTO);
        }
        // filmService.create(filmDTO);
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
    public String addFilm(@ModelAttribute("filmDirectorForm") AddDirector2FilmDTO addDirector2FilmDTO) {
        filmService.addDirector(addDirector2FilmDTO);
        return "redirect:/films";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("film", filmService.getOne(id));
        return "films/updateFilm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("filmForm") FilmDTO filmDTO,
                         @RequestParam MultipartFile file) {
        if (file != null && file.getSize() > 0) {
            filmService.update(filmDTO, file);
        }
        else {
            filmService.update(filmDTO);
        }
        return "redirect:/films";
    }

    @PostMapping("/search")
    public String searchFilms(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("filmSearchForm") FilmSearchDTO filmSearchDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("films", filmService.findFilms(filmSearchDTO, pageRequest));
        return "films/viewAllFilms";
    }

    @PostMapping("/search/director")
    public String searchFilms(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("directorSearchForm") DirectorDTO directorDTO,
                              Model model) {
        FilmSearchDTO filmSearchDTO = new FilmSearchDTO();
        filmSearchDTO.setDirectorFio(directorDTO.getDirectorFio());
        return searchFilms(page, pageSize, filmSearchDTO, model);
    }

    @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFilm(@Param(value = "filmId") Long filmId) throws IOException {
        FilmDTO filmDTO = filmService.getOne(filmId);
        Path path = Paths.get(filmDTO.getOnlineCopyPath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(this.headers(path.getFileName().toString()))
                .contentLength(path.toFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private HttpHeaders headers(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        filmService.deleteSoft(id);
        return "redirect:/films";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        filmService.restore(id);
        return "redirect:/films";
    }

    @ExceptionHandler({MyDeleteException.class, AccessDeniedException.class})
    public RedirectView handleError(HttpServletRequest req,
                                    Exception ex,
                                    RedirectAttributes redirectAttributes) {
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        redirectAttributes.addFlashAttribute("exception", ex.getMessage());
        return new RedirectView("/films", true);
    }




}
