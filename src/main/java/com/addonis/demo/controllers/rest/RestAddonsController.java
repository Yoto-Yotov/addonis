package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonDTO;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.*;
import com.addonis.demo.utils.AddonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

/**
 * Rest controller for addons
 * Get all addons with details - name, creater, last commit information, addon tags. No authentication needed
 * Create addon - authentication needed (user or admin)
 * Update addon - authentication needed (user or addon)
 * Upload content - upload binary file
 * Add tag to addon - authentication needed (user or admin)
 * Delete addon - authentication needed (user or admin)
 */
@RestController
@RequestMapping("/api/addons")
public class RestAddonsController {

    private AddonService addonService;
    private FileService fileService;
    private UserInfoService userInfoService;
    private UserService userService;
    private BinaryContentService binaryContentService;

    @Autowired
    public RestAddonsController(AddonService addonService, FileService fileService, UserInfoService userInfoService, BinaryContentService binaryContentService) {
        this.addonService = addonService;
        this.fileService = fileService;
        this.userInfoService = userInfoService;
        this.binaryContentService = binaryContentService;
    }

    @GetMapping("/all")
    public List<Addon> getAll() {
        return addonService.getAll();
    }
    
    @PostMapping(value = "/create")
    public Addon createAddon(@RequestBody AddonDTO addonDto,
                             @RequestHeader(name = "Authorization") String username) {
        try {
            UserInfo userInfo = userInfoService.gerUserByUsername(username);
            addonDto.setCreator(userInfo);
            Addon addonToCreate = AddonUtils.mapDtoToAddon(addonDto, binaryContentService);
            addonService.create(addonToCreate);
            return addonService.getById(addonToCreate.getId());
        } catch (DuplicateEntityException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/{addonId}")
    public Addon getAddonById(@PathVariable int addonId) {
        return addonService.getAddonById(addonId);
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

    @GetMapping("/pending")
    public List<Addon> getPendingAddons(@RequestHeader(name = "Authorization") String username) {

//        if (!userService.isAdmin(username)) {
//            throw new NotAuthorizedException(username);
//        }
        return addonService.getAllPendingAddons();
    }

    @GetMapping("/approved")
    public List<Addon> getApprovedAddons(@RequestHeader(name = "Authorization") String username) {
//        if (!userService.isAdmin(username)) {
//            throw new NotAuthorizedException(username);
//        }
        return addonService.getAllApprovedAddons();
    }

    @GetMapping("/name/{name}")
    public Addon getByName(@PathVariable String name) {
        return addonService.getAddonByName(name);
    }

    @GetMapping("my-addons")
    public List<Addon> getMyAddons(@RequestHeader(name = "Authorization") String username) {
        UserInfo user = userInfoService.gerUserByUsername(username);
        return addonService.getMyAddons(user);
    }
}
