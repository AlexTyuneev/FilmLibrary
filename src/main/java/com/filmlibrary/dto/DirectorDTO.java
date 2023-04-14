package com.filmlibrary.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DirectorDTO
      extends GenericDTO {
    private String directorFio;
//    private Integer position;
    private String birthDate;
    private String description;
    private Set<Long> filmsIds;
    private boolean isDeleted;

}
