package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AdminController {

    private UserInfoService userInfoService;
    private AddonService addonService;

    public AdminController(UserInfoService userInfoService, AddonService addonService) {
        this.userInfoService = userInfoService;
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
}
