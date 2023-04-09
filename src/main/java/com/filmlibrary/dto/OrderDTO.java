package com.filmlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO
      extends GenericDTO {
    
//    private BookDTO book;
//    private UserDTO user;
    private LocalDateTime rentDate;
    private Integer rentPeriod;
    private LocalDateTime returnDate;
    private Boolean returned;
    private boolean isPurchase;
    private Long filmId;
    private Long userId;
    private FilmDTO filmDTO;
    
}
