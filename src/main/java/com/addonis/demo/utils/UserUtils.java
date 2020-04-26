package com.addonis.demo.utils;

import com.addonis.demo.models.UserChangeDTO;
import com.addonis.demo.models.UserDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * UserUtils
 * Merging DTOs to UserInfo
 * Send method for sending email when registering.
 */
@Component
public class UserUtils {

    UserInfoService userInfoService;

    @Autowired
    public UserUtils(UserInfoService userInfoService) {
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

    public static boolean isValidEmailAddress(String email) {
        String emailRegex = "([a-zA-Z0-9/_/./-]+@[a-zA-Z]+.[a-z]+)";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return !pat.matcher(email).matches();
    }
}
