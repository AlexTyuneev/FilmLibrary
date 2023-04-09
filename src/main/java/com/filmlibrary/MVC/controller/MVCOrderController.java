package com.filmlibrary.MVC.controller;

import com.filmlibrary.service.FilmService;
import com.filmlibrary.dto.OrderDTO;
import com.filmlibrary.service.OrderService;
import com.filmlibrary.service.FilmService;
import com.filmlibrary.service.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@Hidden
@RequestMapping("/rent")
public class MVCOrderController {
    private final OrderService orderService;
    private final FilmService filmService;
    
    public MVCOrderController(OrderService orderService, FilmService filmService) {
        this.orderService = orderService;
        this.filmService = filmService;
    }
    
    @GetMapping("/film/{filmId}")
    public String rentFilm(@PathVariable Long filmId,
                           Model model) {
        model.addAttribute("film", filmService.getOne(filmId));
        return "userFilms/rentFilm";
    }
    
    @PostMapping("/film")
    public String rentFilm(@ModelAttribute("orderForm") OrderDTO orderDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderDTO.setUserId(Long.valueOf(customUserDetails.getUserId()));
        orderService.rentFilm(orderDTO);
        return "redirect:/rent/user-films/" + customUserDetails.getUserId();
    }
    
    @GetMapping("/user-films/{id}")
    public String userBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "size", defaultValue = "5") int pageSize,
                            @PathVariable Long id,
                            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<OrderDTO> rentInfoDTOPage = orderService.listAll(pageRequest);
        model.addAttribute("orders", rentInfoDTOPage);
        return "userBooks/viewAllUserBooks";
    }
    
    @GetMapping("/return-book/{id}")
    public String returnBook(@PathVariable Long id) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           orderService.returnBook(id);
        return "redirect:/rent/user-books/" + customUserDetails.getUserId();
    }
}
