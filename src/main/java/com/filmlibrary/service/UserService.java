package com.filmlibrary.service;

import com.filmlibrary.dto.RoleDTO;
import com.filmlibrary.dto.UserDTO;
import com.filmlibrary.mapper.UserMapper;
import com.filmlibrary.model.User;
import com.filmlibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService
      extends GenericService<User, UserDTO> {
    
    protected UserService(UserRepository userRepository,
                          UserMapper userMapper) {
        super(userRepository, userMapper);
    }
    
    @Override
    public UserDTO create(UserDTO object) {
        // RoleDTO roleDTO = new RoleDTO();
        // roleDTO.setId(1L);
        // object.setRoles(roleDTO);
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
}
