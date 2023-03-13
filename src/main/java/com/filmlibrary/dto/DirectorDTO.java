package com.filmlibrary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO
      extends GenericDTO {
    private String directorFio;
    private Integer position;
    private Set<Long> filmsIds;
}
