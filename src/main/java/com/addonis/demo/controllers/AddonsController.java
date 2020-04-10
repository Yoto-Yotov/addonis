package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AddonsController {
    private AddonService addonService;

    @Autowired
    public AddonsController(AddonService addonService) {
        this.addonService = addonService;
    }
}
