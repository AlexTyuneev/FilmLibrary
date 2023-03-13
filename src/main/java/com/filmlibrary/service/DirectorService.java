package com.filmlibrary.service;

import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.mapper.DirectorMapper;
import com.filmlibrary.model.Director;
import com.filmlibrary.repository.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorService
      extends GenericService<Director, DirectorDTO> {
    protected DirectorService(DirectorRepository directorRepository,
                              DirectorMapper directorMapper) {
        super(directorRepository, directorMapper);
    }
}
