package com.filmlibrary.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
}
