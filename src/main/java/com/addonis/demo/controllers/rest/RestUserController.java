package com.addonis.demo.controllers.rest;

import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.services.contracts.UserService;
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

    @Autowired
    public RestUserController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @GetMapping
    public List<UserInfo> getAllsers() {
        return userInfoService.getAll();
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserInfo user) {
        try {
            userInfoService.create(user);
        } catch (ParseException e) {
            throw new IllegalArgumentException("This is a parse exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("User was created");
    }

}
