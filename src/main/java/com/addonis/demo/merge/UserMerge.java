package com.addonis.demo.merge;

import com.addonis.demo.firstDB.models.UserChangeDTO;
import com.addonis.demo.firstDB.models.UserDTO;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserUtils- Merging DTOs to UserInfo in order a user to be created or updated
 */
@Component
public class UserMerge {

    UserInfoService userInfoService;

    @Autowired
    public UserMerge(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public static UserInfo mergeTwoUsers(UserInfo oldUser, UserChangeDTO newUser) {
        oldUser.setEmail(getNotNullValue(oldUser.getEmail(), newUser.getEmail()));
        if (!newUser.getFirstName().equals("")) {
            oldUser.setFirstName(getNotNullValue(oldUser.getFirstName(), newUser.getFirstName()));
        }
        if (!newUser.getFirstName().equals("")) {
            oldUser.setLastName(getNotNullValue(oldUser.getLastName(), newUser.getLastName()));
        }
        return oldUser;
    }

    public static <T> T getNotNullValue(T a, T b) {
        return b != null && a != null && !b.equals(a) ? b : a;
    }

    public static UserInfo mergeUserInfo(UserDTO userDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userDto.getName());
        userInfo.setEmail(userDto.getEmail());
        userInfo.setFirstName(userDto.getFirstName());
        userInfo.setLastName(userDto.getLastName());
        return userInfo;
    }

}
