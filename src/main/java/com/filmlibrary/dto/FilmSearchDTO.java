package com.filmlibrary.dto;

import com.filmlibrary.model.Genre;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilmSearchDTO {
    private String filmTitle;
    private String directorFio;
    private Genre genre;
}
