package com.filmlibrary.dto;

import com.filmlibrary.model.Director;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectorWithFilmsDTO
      extends DirectorDTO {
    private Set<FilmDTO> films;
}
