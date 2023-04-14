package com.filmlibrary.repository;

import com.filmlibrary.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository
      extends GenericRepository<Film> {
    @Query(nativeQuery = true,
            value = """
                 select distinct b.*
                 from films b
                 left join films_directors ba on b.id = ba.film_id
                 join directors a on a.id = ba.director_id
                 where b.title ilike '%' || btrim(coalesce(:title, b.title)) || '%'
                 and cast(b.genre as char) like coalesce(:genre,'%')
                 and a.director_fio ilike '%' || :director_fio || '%'
                 and b.is_deleted = false
                      """)
    Page<Film> searchFilms(@Param(value = "genre") String genre,
                           @Param(value = "title") String title,
                           @Param(value = "director_fio") String director_fio,
                           Pageable pageable);

    // Film findFilmByIdAndFilmRentInfosReturnedFalseAndIsDeletedFalse(final Long id);

    @Query("""
          select case when count(b) > 0 then false else true end
          from Film b join Order bri on b.id = bri.film.id
          where b.id = :id and bri.returned = false
          """)
    boolean checkFilmForDeletion(final Long id);

    Page<Film> findAllByIsDeletedFalse(Pageable pageable);
}
