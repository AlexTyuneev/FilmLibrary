package com.filmlibrary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "director_id_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")

public class Director
      extends GenericModel {
    @Column(name = "director_fio", nullable = false)
    private String directorFio;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "description")
    private String description;

//    @Column(name = "position")
//    private Integer position;

//    @ManyToMany(mappedBy = "authors")
//    private Set<Book> books;
    
    //чтобы не было главной/не главной таблицы
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    //@JsonIgnore //убирает рекурсию
    //@JsonManagedReference //убирает рекурсию в связке с JsonBackReference, но не будет работать десериализация
    @JoinTable(
          name = "films_directors",
          joinColumns = @JoinColumn(name = "director_id"), foreignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS"),
          inverseJoinColumns = @JoinColumn(name = "film_id"), inverseForeignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"))
    private Set<Film> films;
}
