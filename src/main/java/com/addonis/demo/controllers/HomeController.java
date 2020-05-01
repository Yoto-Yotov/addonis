package com.addonis.demo.controllers;

import com.addonis.demo.firstDB.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController - visualization of home page, about page and contact information
 */
@Controller
public class HomeController {

    AddonService addonService;

    @Autowired
    public HomeController(AddonService addonService) {
        this.addonService = addonService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("newest", addonService.getNewest());
        model.addAttribute("featured", addonService.get6Random());
        model.addAttribute("popular", addonService.getTopByDownloads());
        return "index";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContactsPage() {
        return "contact";
    }
}
