package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.UserInfoService;
import com.addonis.demo.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * AdminController
 * Show admin portal - admin information + option to edin information. Show all users + option to disable user.
 * Show all pending addons + option to approve them.
 */
@Controller
public class AdminController {

    private UserInfoService userInfoService;
    private UserService userService;
    private AddonService addonService;

    public AdminController(UserInfoService userInfoService, UserService userService, AddonService addonService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.addonService = addonService;
    }

    @GetMapping("/admin")
    public String showAdminPortal(Model model, Principal principal) {
        model.addAttribute("admin", userInfoService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userInfoService.getAll());
        model.addAttribute("addons", addonService.getAll());
        return "admin-portal";
    }

    @GetMapping("/admin/users")
    public String showUsersToPortal(Model model, Principal principal) {
        model.addAttribute("users", userInfoService.getAll());
        model.addAttribute("usersUser", userService);
        return "users";
    }

    @PostMapping("/admin/users/d/{username}")
    public String disableUser(@PathVariable String username, Model model) {
        try {
            UserInfo userToDel = userInfoService.getUserByUsername(username);
            userService.softDeleteUser(userToDel.getName());
            userInfoService.softDeleteUserInfo(userToDel.getName());
        }  catch (EntityNotFoundException e) {
            model.addAttribute("users", userInfoService.getAll());
            model.addAttribute("usersUser", userService);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/e/{username}")
    public String enableUser(@PathVariable String username, Model model) {
        try {
            UserInfo userToRestore = userInfoService.getUserByUsername(username);
            userService.restoreUser(userToRestore.getName());
            userInfoService.restoreUser(userToRestore.getName());
        } catch (EntityNotFoundException e) {
            model.addAttribute("users", userInfoService.getAll());
            model.addAttribute("usersUser", userService);
            model.addAttribute("error", e.getMessage());
            return "users";
        }
        return "redirect:/admin/users";
    }
}