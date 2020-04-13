package com.addonis.demo.utils;

import com.addonis.demo.models.User;
import com.addonis.demo.models.UserDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    private UserService userService;

    @Autowired
    public UserUtils(UserService userService) {
        this.userService = userService;
    }

    public static User mergeUser(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserInfo mergeUserInfo(UserDTO userDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userDto.getName());
        userInfo.setEmail(userDto.getEmail());
        return userInfo;
    }
}
