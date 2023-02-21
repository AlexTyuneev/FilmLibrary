package com.filmlibrary.repository;

import com.filmlibrary.model.Film;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository
      extends GenericRepository<Film> {
}
