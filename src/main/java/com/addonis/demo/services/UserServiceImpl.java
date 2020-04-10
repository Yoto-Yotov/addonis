package com.addonis.demo.services;

import com.addonis.demo.models.User;
import com.addonis.demo.repository.contracts.UserRepository;
import com.addonis.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(String s) {
        return null;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }
}
