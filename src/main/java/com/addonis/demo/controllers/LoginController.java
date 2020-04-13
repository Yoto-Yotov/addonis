package com.addonis.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
