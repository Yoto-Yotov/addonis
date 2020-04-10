package com.addonis.demo.controllers.rest;

import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/addons")
public class RestAddonsController {

    private AddonService addonService;

    @Autowired
    public RestAddonsController(AddonService addonService) {
        this.addonService = addonService;
    }
}
