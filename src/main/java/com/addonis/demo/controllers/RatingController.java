package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * RatingController
 * Show addon average rating.
 * Rate an addon. Authentication needed - user or admin
 */
@Controller
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
}
