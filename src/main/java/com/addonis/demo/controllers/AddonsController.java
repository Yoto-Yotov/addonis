package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonDTO;
import com.addonis.demo.models.BinaryContent;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.services.contracts.*;
import com.addonis.demo.utils.AddonUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

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
    private UserInfoService userInfoService;
    private ImageService imageService;
    private BinaryContentService binaryContentService;
    private ReadmeService readmeService;

    @Autowired
    public AddonsController(AddonService addonService, UserInfoService userInfoService, ImageService imageService, BinaryContentService binaryContentService, ReadmeService readmeService) {
        this.addonService = addonService;
        this.userInfoService = userInfoService;
        this.imageService = imageService;
        this.binaryContentService = binaryContentService;
        this.readmeService = readmeService;
    }

    @GetMapping("/addons")
    public String showBeers(Model model) {
        model.addAttribute("addons", addonService.getAllApprovedAddons());
        return "addons";
    }

    @GetMapping("/addon-create")
    public String showNewBeerForm(Model model) {
        model.addAttribute("addonDto", new AddonDTO());
        return "addon";
    }

    @PostMapping("/addon-create")
    public String createAddon(@Valid @ModelAttribute("addon") AddonDTO addonDto, BindingResult errors, Model model, Principal user,
                              @RequestParam("imagefile") MultipartFile imagefile, @RequestParam("binaryFile") MultipartFile binaryFile) {

        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getAllErrors().get(0));
            return "addons";
        }
        addonDto.setFile(binaryFile);

        Addon addonToCreate;
        try {
            UserInfo creator = userInfoService.gerUserByUsername(user.getName());
            addonDto.setCreator(creator);
            addonToCreate = AddonUtils.mapDtoToAddon(addonDto, binaryContentService);
            addonService.create(addonToCreate);
        } catch (DuplicateEntityException | IOException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("addon", new AddonDTO());
            return "addons";
        }

        try {
            imageService.saveImageFileToAddon(addonToCreate.getId(), imagefile);
        } catch (IllegalStateException ex) {
            model.addAttribute("error", "Image cant't be uploaded. Please try again!");
            return "addons";
        }
        return "redirect:/addons";
    }


    @GetMapping("/addon/{addonName}/image")
    public void renderBeerImageFromDb(@PathVariable String addonName, HttpServletResponse response) throws IOException {
        Addon addon = addonService.getAddonByName(addonName);
        if (addon.getPicture() != null) {
            byte[] byteArray = new byte[addon.getPicture().length];
            int i = 0;

            for (Byte addonByte : addon.getPicture()) {
                byteArray[i++] = addonByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @GetMapping("/addons/pending")
    public String getPending(Model model) {
        model.addAttribute("addons", addonService.getAllPendingAddons());
        return "pending";
    }

    @GetMapping("/addons/details/{addonName}")
    public String showAddinDetails(@PathVariable String addonName,
                                   Model model) {
        Addon addon = addonService.getAddonByName(addonName);
        model.addAttribute("addon", addon);
        return "addon-details";
    }

    @GetMapping("/addons/my-addons")
    public String getMyAddons(Model model, Principal user) {
        UserInfo userInfo = userInfoService.gerUserByUsername(user.getName());
        model.addAttribute("myAddons", addonService.getMyAddons(userInfo));
        return "my-addons";
    }

    @PostMapping("/addons/enable/{addonName}")
    public String enableAddon(@PathVariable String addonName) {
        addonService.enableAddon(addonName);
        return "redirect:/addons/pending";
    }

    //ToDo update download count
    @GetMapping("addons/download/{addonId}")
    public ResponseEntity<Resource> downloadFileFromLocal(@PathVariable int addonId) {
        Addon addon = addonService.getAddonById(addonId);
        BinaryContent fileToDownload = binaryContentService.getById(addon.getBinaryFile());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileToDownload.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getDocName() + "\"")
                .body(new ByteArrayResource(fileToDownload.getFile()));
    }

    @GetMapping("/addon/{addonId}/readme")
    public String getReadme(@PathVariable int addonId, Model model) {
        Addon addon = addonService.getAddonById(addonId);
        int readmeId = addon.getReadmeId();
//        String readmeInfo = readmeService.gerReadmeString()
        model.addAttribute("readme", readmeService.gerReadmeString(readmeId));
        return "readme";
    }
}
