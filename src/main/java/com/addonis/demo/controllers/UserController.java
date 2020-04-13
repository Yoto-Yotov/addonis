package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    private UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //ToDo Get all user addons
    @GetMapping("/my-account")
    public String showUserAccount(Model model, Principal principal) {
        model.addAttribute("userinfo", userInfoService.gerUserByUsername(principal.getName()));
        return "my-account";
    }

}
