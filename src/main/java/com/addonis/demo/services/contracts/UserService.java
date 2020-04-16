package com.addonis.demo.services.contracts;

import com.addonis.demo.models.User;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.base.BaseServiceContract;

public interface UserService extends BaseServiceContract<User, String> {

    void softDeleteUser(String name);
}
