package com.filmlibrary.service;

import com.filmlibrary.constants.MailConstants;
import com.filmlibrary.dto.RoleDTO;
import com.filmlibrary.dto.UserDTO;
import com.filmlibrary.mapper.UserMapper;
import com.filmlibrary.model.User;
import com.filmlibrary.repository.UserRepository;
import com.filmlibrary.utils.MailUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.filmlibrary.constants.UserRolesConstants.ADMIN;

@Service
public class UserService
      extends GenericService<User, UserDTO> {
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    protected UserService(UserRepository userRepository,
                          UserMapper userMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          JavaMailSender javaMailSender) {
        super(userRepository, userMapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }
    
    public UserDTO getUserByLogin(final String login) {
        return mapper.toDto(((UserRepository) repository).findUserByLogin(login));
    }
    
    public UserDTO getUserByEmail(final String email) {
        return mapper.toDto(((UserRepository) repository).findUserByEmail(email));
    }
    
    public Boolean checkPassword(String password, UserDetails userDetails) {
        return bCryptPasswordEncoder.matches(password, userDetails.getPassword());
    }
    
    @Override
    public UserDTO create(UserDTO object) {
        RoleDTO roleDTO = new RoleDTO();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ADMIN.equalsIgnoreCase(userName)) {
            roleDTO.setId(2L);//библиотекарь
        }
        else {
            roleDTO.setId(1L);//пользователь
        }
        object.setRole(roleDTO);
        // object.setCreatedBy("REGISTRATION FORM");
        // object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
    
    public void sendChangePasswordEmail(final UserDTO userDTO) {
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);
        SimpleMailMessage mailMessage = MailUtils.createEmailMessage(userDTO.getEmail(),
                                                                     MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                                                                     MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid);
        javaMailSender.send(mailMessage);
    }
    
    public void changePassword(final String uuid,
                               final String password) {
        UserDTO user = mapper.toDto(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        user.setChangePasswordToken(null);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        update(user);
    }
    
    public Page<UserDTO> findUsers(UserDTO userDTO,
                                   Pageable pageable) {
        Page<User> users = ((UserRepository) repository).searchUsers(userDTO.getFirstName(),
                                                                     userDTO.getLastName(),
                                                                     userDTO.getLogin(),
                                                                     pageable);
        List<UserDTO> result = mapper.toDTOs(users.getContent());
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }
    
     /*public List<String> getUserEmailsWithDelayedRentDate() {
        return ((UserRepository) repository).getDelayedEmails();
    }*/
}
