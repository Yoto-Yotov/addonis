package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
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
        return userRepository.findAll();
    }

    @Override
    public User getById(String s) {
        return userRepository.getOne(s);
    }

    @Override
    public void deleteById(String s) {
        userRepository.deleteById(s);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void create(User user) {
        if (checkIfUserExistByName(user.getUsername())) {
            throw new DuplicateEntityException("user");
        }
        userRepository.save(user);
    }

    public boolean checkIfUserExistByName(String username) {
        return getAll()
                .stream()
                .map(User::getUsername)
                .anyMatch(b -> b.equalsIgnoreCase(username));
    }

}
