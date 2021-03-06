package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.AddonChangeDTO;
import com.addonis.demo.firstDB.models.AddonDTO;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.services.contracts.*;
import com.addonis.demo.models.enums.Sortby;
import com.addonis.demo.secondDB.secondServices.contracts.BinaryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.addonis.demo.constants.Constants.DELETE_CONFIRMATION;
import static com.addonis.demo.merge.AddonMapper.mapDtoToAddon;
import static com.addonis.demo.merge.AddonMerger.mergeTwoAddons;

/**
 * Rest controller for addons - CRUD operations
 */
@RestController
@RequestMapping("/api/addons")
public class RestAddonsController {

    private AddonService addonService;
    private FileService fileService;
    private UserInfoService userInfoService;
    private UserService userService;
    private BinaryContentService binaryContentService;
    private ReadmeService readmeService;

    @Autowired
    public RestAddonsController(AddonService addonService, FileService fileService, UserInfoService userInfoService,
                                BinaryContentService binaryContentService, UserService userService, ReadmeService readmeService) {
        this.addonService = addonService;
        this.fileService = fileService;
        this.userInfoService = userInfoService;
        this.binaryContentService = binaryContentService;
        this.userService = userService;
        this.readmeService = readmeService;
    }

    @GetMapping("/all")
    public List<Addon> getAll() {
        return addonService.getAll();
    }
    
    @PostMapping(value = "/create")
    public Addon createAddon(@RequestBody @Valid AddonDTO addonDto,
                             @RequestHeader(name = "Authorization") String username) {
        try {
            UserInfo userInfo = userInfoService.getUserByUsername(username);
            addonDto.setCreator(userInfo);
            Addon addonToCreate = mapDtoToAddon(addonDto, binaryContentService);
            addonService.create(addonToCreate);
            return addonService.getById(addonToCreate.getId());
        } catch (DuplicateEntityException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping(value = "/update/{addonId}")
    public Addon updateAddon(@PathVariable int addonId, @RequestBody @Valid AddonChangeDTO addonDto,
                             @RequestHeader(name = "Authorization") String username) {
        try {
            Addon addonToUpdate = addonService.getById(addonId);
            mergeTwoAddons(addonToUpdate, addonDto, binaryContentService);
            userService.checkRights(username, addonToUpdate);
            addonService.update(addonToUpdate);
            return addonService.getById(addonToUpdate.getId());
        } catch (DuplicateEntityException | EntityNotFoundException | NotAuthorizedException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{addonId}")
    public String deleteAddon(@PathVariable int addonId,
                              @RequestHeader(name = "Authorization") String username) {
        try {
            Addon addonToDelete = addonService.getById(addonId);
            UserInfo user = userInfoService.getUserByUsername(username);
            addonService.softDeleteAddon(addonToDelete.getName(), user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotAuthorizedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        return DELETE_CONFIRMATION;
    }

    @GetMapping("/{addonId}")
    public Addon getAddonById(@PathVariable int addonId) {
        try {
            return addonService.getById(addonId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //ToDo
    @PostMapping(value = "/upload/{id}", consumes = "multipart/form-data")
    public Addon uploadAddon(@PathVariable int id, @RequestParam MultipartFile file,
                             @RequestHeader(name = "Authorization") String authorization) {
        try {
            Addon addon = addonService.getById(id);
            userService.checkRights(authorization, addon);
            if(file.isEmpty()) {
                fileService.saveAddonFile(id, file);
            }
            return addonService.getById(id);
        } catch (InvalidDataException | EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotAuthorizedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/pending")
    public List<Addon> getPendingAddons(@RequestHeader(name = "Authorization") String username) {
        if (!userService.isAdmin(username)) {
            throw new NotAuthorizedException(username);
        }
        return addonService.getAllPendingAddons();
    }

    @GetMapping("")
    public List<Addon> getAllSortBy(@RequestParam("v") String sortBy) {
        return addonService.getAllSortBy("ASC",Sortby.getByParam(sortBy));
    }

    @GetMapping("/featured")
    public List<Addon> getFeaturedAddons() {
        return addonService.get6Random();
    }

    @GetMapping("/newest")
    public List<Addon> getNewestAddons() {
        return addonService.getNewest();
    }

    @GetMapping("/popular")
    public List<Addon> getPopularAddons() {
        return addonService.getTopByDownloads();
    }

    @GetMapping("/approved")
    public List<Addon> getApprovedAddons(@RequestHeader(name = "Authorization") String username) {
        return addonService.getAllApprovedAddons();
    }

    @GetMapping("/name/{name}")
    public Addon getByName(@PathVariable String name) {
        try {
            return addonService.getAddonByName(name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("my-addons")
    public List<Addon> getMyAddons(@RequestHeader(name = "Authorization") String username) {
        try {
            UserInfo user = userInfoService.getUserByUsername(username);
            return addonService.getMyAddons(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("readme/{id}")
    public String getReadme(@PathVariable int id) {
        return readmeService.gerReadmeString(id);
    }

}
