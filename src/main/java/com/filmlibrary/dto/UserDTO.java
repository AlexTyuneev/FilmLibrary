package com.filmlibrary.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO
      extends GenericDTO {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String email;
    // private RoleDTO roles;
    private Set<Long> userOrders;
    private RoleDTO role;
    private String changePasswordToken;
    private Set<Long> userBooksRent;
    private boolean isDeleted;
}
