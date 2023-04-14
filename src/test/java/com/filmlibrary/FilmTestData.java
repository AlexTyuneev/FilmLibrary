package com.filmlibrary;

import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.dto.FilmDTO;
import com.filmlibrary.dto.FilmWithDirectorsDTO;
import com.filmlibrary.model.Film;
import com.filmlibrary.model.Genre;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface FilmTestData {
    FilmDTO FILM_DTO_1 = new FilmDTO("filmTitle1",
                                     "publishDate1",
                                     "country1",
                                    Genre.DRAMA,
                                     1,
                                        new HashSet<>(),
                                     "storagePlace1",
                                     "onlineCopyPath1",

                                     "description1",


                                     false);
    
    FilmDTO FILM_DTO_2 = new FilmDTO("filmTitle2",
            "publishDate2",
            "country2",
            Genre.HORROR,
            1,
            new HashSet<>(),
            "storagePlace2",
            "onlineCopyPath2",

            "description2",


            false);
    
    List<FilmDTO> FILM_DTO_LIST = Arrays.asList(FILM_DTO_1, FILM_DTO_2);
    
    Film FILM_1 = new Film("filmTitle1",
                           "year",

                           "country1",
            Genre.DRAMA,
                           "storagePlace1",
                           "onlineCopyPath1",
                           "description",

                           new HashSet<>(),
                           new HashSet<>());
    Film FILM_2 = new Film("filmTitle2",
            "year",

            "country2",
            Genre.SCI_FI,
            "storagePlace2",
            "onlineCopyPath2",
            "description",

            new HashSet<>(),
            new HashSet<>());
    
    List<Film> FILM_LIST = Arrays.asList(FILM_1, FILM_2);
    
    Set<DirectorDTO> DIRECTORS = new HashSet<>(DirectorTestData.DIRECTOR_DTO_LIST);
    FilmWithDirectorsDTO FILM_WITH_DIRECTORS_DTO_1 = new FilmWithDirectorsDTO(FILM_1, DIRECTORS);
    FilmWithDirectorsDTO FILM_WITH_DIRECTORS_DTO_2 = new FilmWithDirectorsDTO(FILM_2, DIRECTORS);
    
    List<FilmWithDirectorsDTO> FILM_WITH_DIRECTORS_DTO_LIST = Arrays.asList(FILM_WITH_DIRECTORS_DTO_1, FILM_WITH_DIRECTORS_DTO_2);
}
