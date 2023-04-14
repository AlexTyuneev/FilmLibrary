package com.filmlibrary.mapper;

import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.model.Director;
import com.filmlibrary.model.GenericModel;
import com.filmlibrary.repository.FilmRepository;
import com.filmlibrary.utils.DateFormatter;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DirectorMapper
      extends GenericMapper<Director, DirectorDTO> {
    private final FilmRepository filmRepository;
    
    protected DirectorMapper(ModelMapper modelMapper,
                             FilmRepository filmRepository) {
        super(modelMapper, Director.class, DirectorDTO.class);
        this.filmRepository = filmRepository;
    }
    
    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Director.class, DirectorDTO.class)
              .addMappings(m -> m.skip(DirectorDTO::setFilmsIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(DirectorDTO.class, Director.class)
              .addMappings(m -> m.skip(Director::setFilms)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Director::setBirthDate)).setPostConverter(toEntityConverter());

    }
    
    @Override
    protected void mapSpecificFields(DirectorDTO source, Director destination) {
        if (!Objects.isNull(source.getFilmsIds())) {
            destination.setFilms(new HashSet<>(filmRepository.findAllById(source.getFilmsIds())));
        }
        else {
            destination.setFilms(Collections.emptySet());
        }
        destination.setBirthDate(DateFormatter.formatStringToDate(source.getBirthDate()));

    }
    
    @Override
    protected void mapSpecificFields(Director source, DirectorDTO destination) {
        destination.setFilmsIds(getIds(source));
    }
    
    protected Set<Long> getIds(Director director) {
        return Objects.isNull(director) || Objects.isNull(director.getFilms())
               ? null
               : director.getFilms().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
