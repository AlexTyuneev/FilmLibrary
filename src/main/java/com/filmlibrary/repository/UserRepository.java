package com.filmlibrary.repository;

import com.filmlibrary.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
      extends GenericRepository<User> {
}
