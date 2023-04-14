package com.filmlibrary.repository;

import com.filmlibrary.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository
      extends GenericRepository<Director> {
    Page<Director> findAllByIsDeletedFalse(Pageable pageable);

    Page<Director> findAllByDirectorFioContainsIgnoreCaseAndIsDeletedFalse (String directorFio,
                                                                       Pageable pageable);

    @Query(value = """
          select case when count(a) > 0 then false else true end
          from Director a join a.films b
                        join Order bri on b.id = bri.film.id
          where a.id = :directorId
          and bri.returned = false
          """)
    boolean checkDirectorForDeletion(final Long directorId);
}
