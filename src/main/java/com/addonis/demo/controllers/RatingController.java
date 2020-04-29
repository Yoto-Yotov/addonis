package com.addonis.demo.controllers;

import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.RatingDTO;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * RatingController
 * Show addon average rating.
 * Rate an addon. Authentication needed - user or admin
 */
@Controller
public class RatingController {

    private RatingService ratingService;
    private AddonService addonService;

    @Autowired
    public RatingController(RatingService ratingService, AddonService addonService) {
        this.ratingService = ratingService;
        this.addonService = addonService;
    }

    @PostMapping("/addons/r")
    public String rateAddon(@ModelAttribute("ratingDto") RatingDTO ratingDTO, Model model) {
         String userName = ratingDTO.getUsername();
         int rating = ratingDTO.getRating();
         int addonId = ratingDTO.getAddonID();

         try {
             ratingService.rateAddon(addonId, userName, rating);
         } catch (EntityNotFoundException ex) {
             model.addAttribute("error", ex.getMessage());
         }

         return "redirect:/addons/details/" + addonService.getById(addonId).getName();
    }
}
