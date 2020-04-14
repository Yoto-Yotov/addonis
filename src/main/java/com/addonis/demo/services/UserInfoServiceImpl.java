package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo findByEmailAddress(String emailAddress) {
        return userInfoRepository.findByEmailAddress(emailAddress);
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
        userInfoRepository.deleteById(integer);
    }

    @Override
    public void update(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        if (checkIfUserExistByEmail(userInfo.getEmail())) {
            throw new DuplicateEntityException("User", "email", userInfo.getEmail());
        }
        userInfoRepository.save(userInfo);
        return null;
    }

    public boolean checkIfUserExistByEmail(String email) {
        return getAll()
                .stream()
                .map(UserInfo::getEmail)
                .anyMatch(b -> b.equalsIgnoreCase(email));
    }

    public boolean checkIfUserExistByName(String name) {
        return getAll()
                .stream()
                .map(UserInfo::getName)
                .anyMatch(b -> b.equalsIgnoreCase(name));
    }

    @Override
    public UserInfo gerUserByUsername(String name) {
        if (!checkIfUserExistByName(name)){
            throw new EntityNotFoundException("User", name);
        }
        return userInfoRepository.getByUserName(name);
    }
}
