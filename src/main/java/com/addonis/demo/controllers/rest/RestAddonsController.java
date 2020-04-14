package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.FileService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/addons")
public class RestAddonsController {

    private AddonService addonService;
    private FileService fileService;

    @Autowired
    public RestAddonsController(AddonService addonService, FileService fileService) {
        this.addonService = addonService;
        this.fileService = fileService;
    }
    
    @PostMapping(value = "/create")
    public Addon createAddon(@RequestBody Addon addon) {
        try {
            addonService.create(addon);
            return addonService.getById(addon.getId());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(value = "/upload/{id}", consumes = "multipart/form-data")
    public Addon uploadAddon(@PathVariable int id, @RequestParam MultipartFile file) {
        try {
            if(file != null) {
                fileService.saveAddonFile(id, file);
            }
            return addonService.getById(id);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Addon> getAll() {
        return addonService.getAll();
    }
}
