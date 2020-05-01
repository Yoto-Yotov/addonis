package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.firstDB.models.Authorities;
import com.addonis.demo.firstDB.models.User;
import com.addonis.demo.firstDB.models.UserDTO;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.services.contracts.AuthorityService;
import com.addonis.demo.firstDB.services.contracts.UserInfoService;
import com.addonis.demo.firstDB.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

/**
 * UserRestController is responsible for CRUD operations with users
 */
@RestController
@RequestMapping("api/user")
public class RestUserController {

    private UserInfoService userInfoService;
    private UserService userService;
    private AuthorityService authorityService;

    @Autowired
    public RestUserController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @GetMapping
    public List<UserInfo> getAllsers(@RequestHeader(name = "Authorization") String authorization) {
        return userInfoService.getAll();
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        try {
            userService.create(user);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setName(userDTO.getName());
        userInfo.setEmail(userDTO.getEmail());
        try {
            userInfoService.create(userInfo);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        System.out.println("User was created");
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userInfoService.deleteById(id);
        return String.format("User with id %d successfully deleted", id);
    }

    @GetMapping("/{username}")
    public UserInfo getByUserName(@PathVariable String username) {
        try {
            return userInfoService.getUserByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("id/{id}")
    public UserInfo getByUserId(@PathVariable int id) {
        try {
            return userInfoService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{name}/roles")
    public Collection<Authorities> getUserAuthorities(@PathVariable String name) {
        try {
            return userService.getUserAuthorities(name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
