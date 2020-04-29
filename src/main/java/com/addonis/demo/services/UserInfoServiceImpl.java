package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.send.EmailSend;
import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.addonis.demo.constants.Constants.*;
import static com.addonis.demo.validation.EmailValidator.isValidEmailAddress;

/**
 * UserInfoServiceImpl
 * Takes information about a user. Authentication needed - user
 * Create user. No authentication needed.
 * Get user by id. No authentication needed.
 * Delete user. (Hard delete + soft delete) Authentication needed - admin.
 * Update user. Authentication needed - user
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<UserInfo> getAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo getById(Integer integer) {
        return userInfoRepository.getOne(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        if (!userInfoRepository.existsById(integer)) {
            throw new EntityNotFoundException(USER_U, integer);
        }
        userInfoRepository.deleteById(integer);
    }

    @Override
    public void update(UserInfo userInfo) {
        if (!userInfoRepository.existsByEmail(userInfo.getEmail())) {
            throw new EntityNotFoundException(USER_U, userInfo.getEmail());
        }
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        if (userInfoRepository.existsByName(userInfo.getName())) {
            throw new DuplicateEntityException(USER);
        }
        if(isValidEmailAddress(userInfo.getEmail())){
            throw new InvalidDataException(EMAIL);
        }
        if (userInfoRepository.existsByEmail(userInfo.getEmail())) {
            throw new DuplicateEntityException(USER_U, EMAIL, userInfo.getEmail());
        }
        EmailSend.send_2(userInfo.getEmail(), userInfo.getName());
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserByUsername(String name) {
        if (!userInfoRepository.existsByName(name)){
            throw new EntityNotFoundException(USER_U, name);
        }
        return userInfoRepository.getByUserName(name);
    }

    @Override
    public void softDeleteUserInfo(String name) {
        UserInfo userInfo = userInfoRepository.getByUserName(name);
        userInfo.setEnabled(0);
//                userInfoRepository.softDeleteUserInfo(name);
    }

    public boolean checkUserExistByName(String name) {
        return userInfoRepository.existsByName(name);
    }

    public boolean checkUserExistByEmail(String email) {
        return userInfoRepository.existsByEmail(email);
    }
}
