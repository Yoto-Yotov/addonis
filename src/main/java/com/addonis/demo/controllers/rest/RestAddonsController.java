package com.addonis.demo.controllers.rest;

import com.addonis.demo.models.Addon;
import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/addons")
public class RestAddonsController {

    private AddonService addonService;

    @Autowired
    public RestAddonsController(AddonService addonService) {
        this.addonService = addonService;
    }

    //ToDo Exceprion
    //ToDo CheckIfURLExist
    @PostMapping("/create")
    public Addon createBeer(@RequestBody Addon addon) {
        try {
            addonService.create(addon);
            return addon;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Addon> getAll() {
        return addonService.getAll();
    }
}
