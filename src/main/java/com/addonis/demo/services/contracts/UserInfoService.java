package com.addonis.demo.services.contracts;

import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.base.BaseServiceContract;

public interface UserInfoService extends BaseServiceContract<UserInfo, Integer> {

    UserInfo gerUserByUsername(String name);
}
