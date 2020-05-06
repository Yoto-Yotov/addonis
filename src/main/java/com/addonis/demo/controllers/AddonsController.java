package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.logger.Logger;
import com.addonis.demo.models.*;
import com.addonis.demo.exceptions.NotAuthorizedException;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonDTO;
import com.addonis.demo.models.BinaryContent;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.models.enums.Sortby;
import com.addonis.demo.services.contracts.*;
import com.github.rjeschke.txtmark.Processor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

import static com.addonis.demo.constants.Constants.ROLE_ADMIN;
import static com.addonis.demo.merge.AddonMapper.mapDtoToAddon;
import static com.addonis.demo.merge.AddonMerge.mergeTwoAddons;

/**
 * AddonController is responsible for all visualized operations with the addons. For some of them is authentication is needed.
 */
@Controller
public class AddonsController {
    private AddonService addonService;
    private UserInfoService userInfoService;
    private ImageService imageService;
    private BinaryContentService binaryContentService;
    private ReadmeService readmeService;
    private UserService userService;
    private TagService tagService;
    private RatingService ratingService;
    private IdeService ideService;

    @Autowired
    public AddonsController(AddonService addonService, UserInfoService userInfoService, ImageService imageService,
                            BinaryContentService binaryContentService, UserService userService, ReadmeService readmeService,
                            TagService tagService, RatingService ratingService, IdeService ideService) {

        this.addonService = addonService;
        this.userInfoService = userInfoService;
        this.imageService = imageService;
        this.binaryContentService = binaryContentService;
        this.readmeService = readmeService;
        this.userService = userService;
        this.tagService = tagService;
        this.ratingService = ratingService;
        this.ideService = ideService;
    }

    @GetMapping("/addons/search")
    public String showAddons(@RequestParam(required = false, value = "sr", defaultValue = "") String search, Model model) {
        model.addAttribute("addons", addonService.findByNameContaining(search));
        model.addAttribute("tags", tagService.getAll());
        model.addAttribute("ides", ideService.getAll());

        return "addons";
    }

    @GetMapping("/addon-create")
    public String showNewAddonForm(Model model) {
        model.addAttribute("addonDto", new AddonDTO());
        return "addon";
    }

    @PostMapping("/addon-create")
    public String createAddon(@Valid @ModelAttribute("addonDto") AddonDTO addonDto, BindingResult errors, Model model, Principal user,
                              @RequestParam("imagefile") MultipartFile imagefile,
                              @RequestParam("binaryFile") MultipartFile binaryFile) {

        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getAllErrors().get(0).getDefaultMessage());
            Logger.getLogger().severe(errors.getAllErrors().get(0).getDefaultMessage());
            return "addon";
        }

        Addon addonToCreate;
        try {
            UserInfo creator = userInfoService.getUserByUsername(user.getName());
            addonDto.setCreator(creator);
            addonToCreate = mapDtoToAddon(addonDto, binaryFile, binaryContentService);
            addonToCreate.setIdeId(ideService.getByName(addonDto.getAddonIde()));
            imageService.checkIfImageExists(imagefile);
            addonService.create(addonToCreate);
            imageService.saveImageFileToAddon(addonToCreate.getId(), imagefile);
        } catch (DuplicateEntityException | IOException | InvalidDataException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("addon", new AddonDTO());
            Logger.getLogger().severe(e.getMessage());
            return "addon";
        }

        return "redirect:/addons";
    }


    @GetMapping("/addon/{addonName}/image")
    public void renderAddonImageFromDb(@PathVariable String addonName, HttpServletResponse response) throws IOException {
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
    public String getPending(Model model, HttpServletRequest request) {
        if (!request.isUserInRole(ROLE_ADMIN)) {
            model.addAttribute("error", "You are not authorized");
            return "error";
        }
        model.addAttribute("addons", addonService.getAllPendingAddons());
        return "pending";
    }

    @GetMapping("/addons/details/{addonName}")
    public String showAddonDetails(@PathVariable String addonName,
                                   Model model, Principal user) {
        Addon addon = null;
        try {
            addon = addonService.getAddonByName(addonName);
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        int readmeId = addon.getReadmeId();
        String result = Processor.process(readmeService.gerReadmeString(readmeId));
        model.addAttribute("readmeFile", result);
        model.addAttribute("addon", addon);
        model.addAttribute("ratingDto", new RatingDTO());
        model.addAttribute("addonRating", ratingService.getAddonRating(addon.getId()));
        return "addon-details";
    }

    @GetMapping("/addons/my-addons")
    public String getMyAddons(Model model, Principal user) {
        UserInfo userInfo = null;
        try {
            userInfo = userInfoService.getUserByUsername(user.getName());
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        model.addAttribute("myAddons", addonService.getMyAddons(userInfo));
        return "my-addons";
    }

    @PostMapping("/addons/enable/{addonName}")
    public String enableAddon(@PathVariable String addonName, HttpServletRequest request, Model model) {
        if (!request.isUserInRole(ROLE_ADMIN)) {
            model.addAttribute("error", "You are not authorized");
            return "error";
        }
        addonService.enableAddon(addonName);
        return "redirect:/addons/pending";
    }

    @PostMapping("addon/delete")
    public String deleteAddon(@RequestParam("addonName") String addonName, Model model, Authentication authentication,
                              HttpServletRequest request, Principal user) {
        try {
            Addon addon = addonService.getAddonByName(addonName);
            String name = addonService.getCreatorName(addon.getId());
            if (!request.isUserInRole(ROLE_ADMIN) && !user.getName().equals(name)) {
                model.addAttribute("error", "You are not authorized");
                return "error";
            }
            addon.setEnabled(0);
            addonService.update(addon);
            return "redirect:/addons";
        } catch (DuplicateEntityException | EntityNotFoundException | NotAuthorizedException e) {
            model.addAttribute("error", e.getMessage());
            Logger.getLogger().severe(e.getMessage());
            return "addon-details";
        }
    }

    @GetMapping("/addons/download/{addonId}")
    public ResponseEntity<Resource> downloadFileFromLocal(@PathVariable int addonId) {
        Addon addon = addonService.getById(addonId);
        BinaryContent fileToDownload = binaryContentService.getById(addon.getBinaryFile());
        addonService.changeDownloadCount(addonId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileToDownload.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getDocName() + "\"")
                .body(new ByteArrayResource(fileToDownload.getFile()));
    }

    @GetMapping("/addon/{addonId}/readme")
    public String getReadme(@PathVariable int addonId, Model model) {
        Addon addon = addonService.getById(addonId);
        int readmeId = addon.getReadmeId();
        String result = Processor.process(readmeService.gerReadmeString(readmeId));
        model.addAttribute("readme", result);
        return "readme";
    }

    @GetMapping("/addon/edit/{addonName}")
    public String editAccountEdit(@PathVariable String addonName, Model model, Principal principal) {
        Addon oldAddon = addonService.getAddonByName(addonName);
        try {
            UserInfo userInfo = userInfoService.getUserByUsername(principal.getName());
            String creatorName = addonService.getCreatorName(oldAddon.getId());

            if (!userInfo.getName().equals(creatorName) && !userService.isAdmin(principal.getName())) {
                model.addAttribute("error", "You are not authorized!");
                return "error";
            }

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        AddonChangeDTO newAddon = new AddonChangeDTO();
        newAddon.setName(oldAddon.getName());
        newAddon.setDescription(oldAddon.getDescription());
        model.addAttribute("newAddon", newAddon);
        model.addAttribute("oldAddon", oldAddon);
        model.addAttribute("addonTagDto", new AddonTagDto());
        return "edit-addon";
    }

    @PostMapping("/addon/edit/{addonName}")
    public String updateAddon(@PathVariable String addonName, @Valid @ModelAttribute("newAddon") AddonChangeDTO newAddon,
                              BindingResult errors, Model model,
                              @RequestParam("imagefile") MultipartFile imagefile,
                              @RequestParam("binaryFile") MultipartFile binaryFile) throws IOException {

        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getAllErrors().get(0));
            return "edit-addon";
        }

        newAddon.setFile(binaryFile);
        Addon oldAddon = addonService.getAddonByName(addonName);

        mergeTwoAddons(oldAddon, newAddon, binaryContentService);

        if (imagefile.getSize() > 2) {
            try {
                imageService.saveImageFileToAddon(oldAddon.getId(), imagefile);
            } catch (IllegalStateException | IllegalArgumentException ex) {
                model.addAttribute("error", ex.getMessage());
                return "edit-addon";
            }
        }

        try {
            addonService.update(oldAddon);
        } catch (DuplicateEntityException | InvalidDataException ex) {
            model.addAttribute("error", ex.getMessage());
            return "edit-addon";
        }

        return "redirect:/addon/edit/" + oldAddon.getName();
    }

    @GetMapping("/addons")
    public String getAllSortBy(@RequestParam(required = false, value = "sort", defaultValue = "name") String sortBy,
                               @RequestParam(required = false, value = "order", defaultValue = "ASC") String direction, Model model) {
        model.addAttribute("addons", addonService.getAllSortBy(direction, Sortby.getByParam(sortBy)));
        model.addAttribute("selsort", sortBy);
        model.addAttribute("ordersort", direction);
        model.addAttribute("tags", tagService.getAll());
        model.addAttribute("ides", ideService.getAll());

        return "addons";
    }

    @GetMapping("/addons/ide/{ideName}")
    public String getAllFilterByIde(@PathVariable("ideName") String ideName, Model model) {
        model.addAttribute("addons", addonService.getAllFilterByIdeName(ideName));
        model.addAttribute("tags", tagService.getAll());
        model.addAttribute("ides", ideService.getAll());

        return "addons";
    }

    @GetMapping("/addons/tag/{tagName}")
    public String getAllFilterByTag(@PathVariable("tagName") String tagName, Model model) {
        model.addAttribute("addons", addonService.getAllFilterByTagName(tagName));
        model.addAttribute("tags", tagService.getAll());
        model.addAttribute("ides", ideService.getAll());

        return "addons";
    }
}