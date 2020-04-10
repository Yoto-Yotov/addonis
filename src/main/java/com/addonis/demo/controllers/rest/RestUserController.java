package com.addonis.demo.controllers.rest;

import com.addonis.demo.services.contracts.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class RestUserController {

    private UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }
}
