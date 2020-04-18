package com.addonis.demo.controllers;

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
        model.addAttribute("admin", userInfoService.gerUserByUsername(principal.getName()));
        model.addAttribute("users", userInfoService.getAll());
        model.addAttribute("addons", addonService.getAll());
        return "admin-portal";
    }

    @GetMapping("/admin/users")
    public String showUsersToPortal(Model model, Principal principal) {
        model.addAttribute("users", userInfoService.getAll());
        return "users";
    }

    @PostMapping("/admin/users/d/{username}")
    public String disableUser(@PathVariable String username) {
        UserInfo userToDel = userInfoService.gerUserByUsername(username);
        userInfoService.softDeleteUserInfo(userToDel.getName());
        userService.softDeleteUser(userToDel.getName());
        return "redirect:/admin/users";
    }
}
