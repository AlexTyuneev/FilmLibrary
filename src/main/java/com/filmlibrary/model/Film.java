package com.filmlibrary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "films_id_seq", allocationSize = 1)
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")

public class Film
      extends GenericModel {
    
    @Column(name = "title", nullable = false)
    private String filmTitle;
    
    @Column(name = "premier_year", nullable = false)
    private String premierYear;

    @Column(name = "country")
    private String country;
    
    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;
    @Column(name = "storage_place"/*, nullable = false*/)
    private String storagePlace;

    @Column(name = "online_copy_path")
    private String onlineCopyPath;

    @Column(name = "description")
    private String description;
    
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "films_directors",
               joinColumns = @JoinColumn(name = "film_id"), foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"),
               inverseJoinColumns = @JoinColumn(name = "director_id"), inverseForeignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS"))
    //@JsonBackReference
    private Set<Director> directors;
    
    @OneToMany(mappedBy = "film", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Order> orders;
}
