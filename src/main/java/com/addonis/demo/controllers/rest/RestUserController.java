package com.addonis.demo.controllers.rest;

import com.addonis.demo.models.User;
import com.addonis.demo.models.UserDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.services.contracts.UserService;
import com.addonis.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class RestUserController {

    private UserInfoService userInfoService;
    private UserService userService;
    private UserUtils userUtils;

    @Autowired
    public RestUserController(UserInfoService userInfoService, UserService userService, UserUtils userUtils) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.userUtils = userUtils;
    }

    @GetMapping
    public List<UserInfo> getAllsers() {
        return userInfoService.getAll();
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            user.setUsername(userDTO.getName());
            user.setPassword(userDTO.getPassword());
            userService.create(user);

            UserInfo userInfo = new UserInfo();
            userInfo.setName(userDTO.getName());
            userInfo.setEmail(userDTO.getEmail());
            userInfoService.create(userInfo);
        } catch (ParseException e) {
            throw new IllegalArgumentException("This is a parse exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("User was created");
    }

}
