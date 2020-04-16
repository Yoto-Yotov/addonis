package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.services.base.BaseServiceContract;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseServiceContract<User, String> {
    User getUserByName(String userName);
    List<Authorities> getUserAuthorities(String userName);
}
