package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.utils.UserUtils;
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
            throw new EntityNotFoundException("User", integer);
        }
        userInfoRepository.deleteById(integer);
    }

    @Override
    public void update(UserInfo userInfo) {
        if (!userInfoRepository.existsByEmail(userInfo.getEmail())) {
            throw new EntityNotFoundException("User", userInfo.getEmail());
        }
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        if (userInfoRepository.existsByEmail(userInfo.getEmail())) {
            throw new DuplicateEntityException("User", "email", userInfo.getEmail());
        }
        UserUtils.send_2(userInfo.getEmail(), userInfo.getName());
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo gerUserByUsername(String name) {
        if (!userInfoRepository.existsByName(name)){
            throw new EntityNotFoundException("User", name);
        }
        return userInfoRepository.getByUserName(name);
    }
}
