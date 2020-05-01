package com.addonis.demo.controllers;

import com.addonis.demo.firstDB.services.contracts.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * RatingController - visualization of average rating and option to rate addon (authentication)
 */
@Controller
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
}
