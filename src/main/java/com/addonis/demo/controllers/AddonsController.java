package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddonsController {
    private AddonService addonService;

    @Autowired
    public AddonsController(AddonService addonService) {
        this.addonService = addonService;
    }

    @GetMapping("/addons")
    public String showBeers(Model model) {
        model.addAttribute("addon", addonService.getAll());
        return "addon";
    }
}
