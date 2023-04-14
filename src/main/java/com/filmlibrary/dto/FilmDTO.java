package com.filmlibrary.dto;

import com.filmlibrary.model.Director;
import com.filmlibrary.model.Film;
import com.filmlibrary.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO
      extends GenericDTO {
  
  private String filmTitle;
  private String premierYear;
  private String country;
  private Genre genre;
  private Integer amount;
  private Set<Long> directorsIds;
  private String storagePlace;
  private String onlineCopyPath;
  private String description;
  private boolean isDeleted;



      public FilmDTO(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        //из entity делаем DTO
        filmDTO.setFilmTitle(film.getFilmTitle());
        filmDTO.setGenre(film.getGenre());
        filmDTO.setCountry(film.getCountry());
        filmDTO.setPremierYear(film.getPremierYear());

        filmDTO.setDescription(film.getDescription());
        filmDTO.setId(film.getId());
        // filmDTO.setPageCount(film.getPageCount());
        Set<Director> directors = film.getDirectors();
        Set<Long> directorIds = new HashSet<>();
        if (directors != null && directors.size() > 0) {
          directors.forEach(a -> directorIds.add(a.getId()));
        }
        filmDTO.setDirectorsIds(directorIds);
    }

//    public List<BookDTO> getBookDTOs(List<Book> books) {
//        List<BookDTO> bookDTOS = new ArrayList<>();
//        for (Book book : books) {
//            bookDTOS.add(new BookDTO(book));
//        }
//        return bookDTOS;
//    }
}
