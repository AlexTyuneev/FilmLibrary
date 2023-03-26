package com.filmlibrary.dto;

import com.filmlibrary.model.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FilmDTO
      extends GenericDTO {
  
  private String filmTitle;
  private String premierYear;
  private String country;
  private Genre genre;
  private Set<Long> directorsIds;



  //    public BookDTO(Book book) {
//        BookDTO bookDTO = new BookDTO();
//        //из entity делаем DTO
//        bookDTO.setBookTitle(book.getBookTitle());
//        bookDTO.setGenre(book.getGenre());
//        bookDTO.setDescription(book.getDescription());
//        bookDTO.setId(book.getId());
//        bookDTO.setPageCount(book.getPageCount());
//        bookDTO.setPublishDate(book.getPublishDate());
//        Set<Author> authors = book.getAuthors();
//        Set<Long> authorIds = new HashSet<>();
//        authors.forEach(a -> authorIds.add(a.getId()));
//        bookDTO.setAuthorsIds(authorIds);
//    }
//
//    public List<BookDTO> getBookDTOs(List<Book> books) {
//        List<BookDTO> bookDTOS = new ArrayList<>();
//        for (Book book : books) {
//            bookDTOS.add(new BookDTO(book));
//        }
//        return bookDTOS;
//    }
}
