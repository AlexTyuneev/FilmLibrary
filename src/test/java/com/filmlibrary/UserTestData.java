package com.filmlibrary;

import com.filmlibrary.dto.RoleDTO;
import com.filmlibrary.dto.UserDTO;
import com.filmlibrary.model.Role;
import com.filmlibrary.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserTestData {
    
    UserDTO USER_DTO = new UserDTO("login",
                                   "password",
                                   "email",
                                   "birthDate",
                                   "firstName",
                                   "lastName",
                                   "middleName",
                                   "phone",
                                   "address",
                                   null,
                                   new RoleDTO(),
                                   "changePasswordToken",
                                   new HashSet<>(),
                                   false
    );
    
    List<UserDTO> USER_DTO_LIST = List.of(USER_DTO);
    
//    User USER = new User("login",
//                         "password",
//            "firstName",
//            "lastName",
//            "middleName",
//            LocalDate.now(),
//                         "email",
//                          "phone",
//                         "address",
//                         "changePasswordToken",
//                         new Role(),
//            new HashSet<>()
//    );
//
//    List<User> USER_LIST = List.of(USER);
}
