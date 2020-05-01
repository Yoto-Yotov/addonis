package com.addonis.demo.firstDB.services.contracts;


import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.services.base.BaseServiceContract;

public interface UserInfoService extends BaseServiceContract<UserInfo, Integer> {

    UserInfo getUserByUsername(String name);
    void softDeleteUserInfo(String username);
    boolean checkUserExistByName(String name);
    boolean checkUserExistByEmail(String email);
}
