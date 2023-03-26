package com.filmlibrary.mapper;

import com.filmlibrary.dto.FilmWithDirectorsDTO;
import com.filmlibrary.model.Film;
import com.filmlibrary.model.GenericModel;
import com.filmlibrary.repository.DirectorRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmWithDirectorsMapper
      extends GenericMapper<Film, FilmWithDirectorsDTO> {
    
    private final DirectorRepository directorRepository;
    
    protected FilmWithDirectorsMapper(ModelMapper mapper,
                                      DirectorRepository directorRepository
                                   ) {
        super(mapper, Film.class, FilmWithDirectorsDTO.class);
        this.directorRepository = directorRepository;
    }
    
    @Override
    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Film.class, FilmWithDirectorsDTO.class)
              .addMappings(m -> m.skip(FilmWithDirectorsDTO::setDirectorsIds)).setPostConverter(toDtoConverter());
        
        modelMapper.createTypeMap(FilmWithDirectorsDTO.class, Film.class)
              .addMappings(m -> m.skip(Film::setDirectors)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(FilmWithDirectorsDTO source, Film destination) {
        destination.setDirectors(new HashSet<>(directorRepository.findAllById(source.getDirectorsIds())));
    }
    
    @Override
    protected void mapSpecificFields(Film source, FilmWithDirectorsDTO destination) {
        destination.setDirectorsIds(getIds(source));
    }
    
    @Override
    protected Set<Long> getIds(Film entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getId())
               ? null
               : entity.getDirectors().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
