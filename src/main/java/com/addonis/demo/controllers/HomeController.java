package com.addonis.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 * Show home page
 * Show about page (information about Addonis)
 * Show contact information
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(Model model) {
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
