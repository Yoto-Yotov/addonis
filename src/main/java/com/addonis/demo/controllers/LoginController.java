package com.addonis.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LoginController
 * Show login page + log the user if exist.
 * Option to go to registration page.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
