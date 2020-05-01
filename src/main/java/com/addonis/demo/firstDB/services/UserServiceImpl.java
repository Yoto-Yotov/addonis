package com.addonis.demo.firstDB.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.Authorities;
import com.addonis.demo.firstDB.models.User;
import com.addonis.demo.firstDB.repository.contracts.AuthorityRepository;
import com.addonis.demo.firstDB.repository.contracts.UserRepository;
import com.addonis.demo.firstDB.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.addonis.demo.constants.Constants.*;

/**
 * UserServiceImpl - CRUD operation for user.
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
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER, id));
    }

    @Override
    public void deleteById(Integer id) {
        getById(id);
        userRepository.deleteById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateEntityException(USER);
        }
        return userRepository.save(user);
    }

    @Override
    public void softDeleteUser(String username) {
        User user = userRepository.getByName(username);
        user.setEnabled(0);
        userRepository.save(user);
    }

    @Override
    public User getUserByName(String userName) {
        User user = userRepository.findUserByUsername(userName);
        if (user == null) {
            throw new EntityNotFoundException(USER, userName);
        }
        return user;
    }

    @Override
    public List<Authorities> getUserAuthorities(String userName) {
        if (!userRepository.existsByUsername(userName)) {
            throw new EntityNotFoundException(USER, userName);
        }
        return authorityRepository.getByUsername(userName);
    }

    @Override
    public boolean isAdmin(String userName) {
        return getUserAuthorities(userName).stream().map(Authorities::getAuthority).anyMatch(authority -> authority.equals(ROLE_ADMIN));
    }

    public void checkRights(String userName, Addon addon) {
        if (!isAdmin(userName) && !addon.getUserInfo().getName().equals(userName)){
            throw new NotAuthorizedException(USER_U);
        }
    }

}
