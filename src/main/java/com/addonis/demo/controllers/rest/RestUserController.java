package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.models.UserDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.AuthorityService;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.services.contracts.UserService;
import com.addonis.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

/**
 * UserRestController
 * CRUD operations
 * Create - create user. No authentication needed.
 * Read - see all users. Authentication needed - admin.
 * Update - update user profile details. Authentication needed - user.
 * Delete - delete user / disable user. Authentication needed - admin.
 * See all addons created by the user. Authentication needed - user.
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
//        if (!authorityService.getAuthority(authorization).equals("ROLE_ADMIN")) {
//            throw new NotAuthorizedException("You should be admin to see all users");
//        }
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
            return userInfoService.gerUserByUsername(username);
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
