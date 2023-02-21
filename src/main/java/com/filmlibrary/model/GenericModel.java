package com.filmlibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class GenericModel implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1;
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private Long id;
    
}
