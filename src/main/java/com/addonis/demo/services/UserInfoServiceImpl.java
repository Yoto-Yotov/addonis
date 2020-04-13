package com.addonis.demo.services;

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
        userInfoRepository.getOne(integer);
    }

    @Override
    public void update(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public void create(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
