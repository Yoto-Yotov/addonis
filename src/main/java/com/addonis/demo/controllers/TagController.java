package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonChangeDTO;
import com.addonis.demo.models.AddonTagDto;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TagController
 * Show all tags of current addon
 * Add tag to addon. Authentication needed - user or admin.
 * Delete tag from addon. Authentication needed - user or admin.
 * Update tag. Authentication needed - user or admin.
 * Filter by tag.
 */
@Controller
public class TagController {

    private TagService tagService;
    private AddonService addonService;

    @Autowired
    public TagController(TagService tagService, AddonService addonService) {
        this.tagService = tagService;
        this.addonService = addonService;
    }

    @PostMapping("/addon/tag")
    public String addTagToAddon(@ModelAttribute("addonTagDto") AddonTagDto addonTagDto, Model model) {
        int addonId = addonTagDto.getAddonId();
        String tagName = addonTagDto.getTagName();
        String userName = addonTagDto.getUserName();
        try {
            tagService.addTagToAddon(addonId, tagName, userName);
        } catch (EntityNotFoundException | DuplicateEntityException | NotAuthorizedException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        Addon oldAddon = addonService.getById(addonId);
        AddonChangeDTO newAddon = new AddonChangeDTO();
        newAddon.setName(oldAddon.getName());
        newAddon.setDescription(oldAddon.getDescription());
        model.addAttribute("newAddon", newAddon);
        model.addAttribute("oldAddon", oldAddon);
        model.addAttribute("addonTagDto", new AddonTagDto());
        return  "edit-addon";
    }
}
