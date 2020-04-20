package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;

import com.addonis.demo.models.UserInfo;

import com.addonis.demo.repository.contracts.AuthorityRepository;

import com.addonis.demo.repository.contracts.UserRepository;
import com.addonis.demo.services.contracts.UserService;
import com.addonis.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserServiceImpl
 * CRUD operation for user.
 */
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
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
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateEntityException("user");
        }
        return userRepository.save(user);
    }

    @Override
    public void softDeleteUser(String username) {
        User user = userRepository.getByName(username);
        user.setEnabled(0);
    }

    @Override
    public User getUserByName(String userName) {
        User user =  userRepository.findUserByUsername(userName);
        if(user == null) {
            throw new EntityNotFoundException("user", userName);
        }
        return user;
    }

    @Override
    public List<Authorities> getUserAuthorities(String userName) {
        if (!userRepository.existsByUsername(userName)) {
            throw new EntityNotFoundException("user", userName);
        }
        return authorityRepository.getByUsername(userName);
    }

    @Override
    public boolean isAdmin(String userName) {
        return getUserAuthorities(userName)
                .stream()
                .map(Authorities::getAuthority)
                .anyMatch(authority -> authority.equalsIgnoreCase("ROLE_ADMIN"));
    }

}
