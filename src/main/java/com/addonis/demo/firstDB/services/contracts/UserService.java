package com.addonis.demo.firstDB.services.contracts;

import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.Authorities;
import com.addonis.demo.firstDB.models.User;
import com.addonis.demo.firstDB.services.base.BaseServiceContract;

import java.util.List;

public interface UserService extends BaseServiceContract<User, Integer> {

    void softDeleteUser(String name);
    User getUserByName(String userName);
    List<Authorities> getUserAuthorities(String userName);
    boolean isAdmin(String userName);
    void checkRights(String userName, Addon addon);
}
