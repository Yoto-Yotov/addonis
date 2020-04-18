package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.services.contracts.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * RatingController
 * Read - get addon average rating
 * Create - rate an addon, authentication needed (user or admin)
 */
@RestController
@RequestMapping("api/rating")
public class RestRatingController {

    private RatingService ratingService;

    @Autowired
    public RestRatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rate/{addon_id}/{rating}")
    public void rateAddon(@PathVariable("addon_id") int addonId, @PathVariable("rating") int rating, @RequestHeader(name = "Authorization") String name) {
        try {
            ratingService.rateAddon(addonId, name, rating);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/{addon_id}")
    public double getUserRating(@PathVariable("addon_id") int addonId, @RequestHeader(name = "Authorization") String name) {
        try {
            return ratingService.getUserRating(addonId, name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{addonId}")
    public double getAddonRating(@PathVariable("addonId") int addonId) {
        try {
            return ratingService.getAddonRating(addonId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
