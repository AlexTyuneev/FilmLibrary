package com.example.filmlibrary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "users_id_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")

public class User
      extends GenericModel {
    
    @Column(name = "login", nullable = false)
    private String login;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;
    
    @Column(name = "email", nullable = false)
    private String email;

    
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false,
                foreignKey = @ForeignKey(name = "FK_USER_ROLES"))
    private Role roles;
    
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
}
