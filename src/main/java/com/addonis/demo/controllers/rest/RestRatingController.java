package com.addonis.demo.controllers.rest;

import com.addonis.demo.services.contracts.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rating")
public class RestRatingController {

    private RatingService ratingService;

    @Autowired
    public RestRatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
}
