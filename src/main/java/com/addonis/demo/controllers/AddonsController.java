package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * AddonController
 * Visualization - see all ACTIVE addons.
 * Create addon. Authentication needed - user
 * Update addon. Authentication needed - user or admin
 * Show all pending addons. Authentication need - admin.
 * Show all addons of user (active and pending). Authentication needed - user.
 * Addon page - visualization of addon details. No authentication needed.
 * Button to add tag. Authentication needed - user or admin.
 * Button to edin addon. Authentication needed - user or admin.
 */
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
