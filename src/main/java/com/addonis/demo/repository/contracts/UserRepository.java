package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.User;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {
    boolean existsByUsername(String name);
    User findUserByUsername(String username);
}
