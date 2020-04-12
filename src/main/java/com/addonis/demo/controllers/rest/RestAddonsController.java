package com.addonis.demo.controllers.rest;

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

    //ToDo Exceprion
    //ToDo CheckIfURLExist
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public Addon createBeer(/*@RequestBody Addon addon,*/ @RequestParam MultipartFile file) {
        try {
            //addonService.create(addon);
            if(file != null) {
                fileService.saveAddonFile(8, file);
            }
            return new Addon();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Addon> getAll() {
        return addonService.getAll();
    }
}
