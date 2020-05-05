package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.base.BaseServiceContract;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseServiceContract<User, Integer> {

    void softDeleteUser(String name);
    void restoreUser(String name);
    User getUserByName(String userName);
    List<Authorities> getUserAuthorities(String userName);
    boolean isAdmin(String userName);
    void checkRights(String userName, Addon addon);
}
