package com.addonis.demo.controllers.rest;

import com.addonis.demo.services.contracts.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tag")
public class RestTagController {

    private TagService tagService;

    public RestTagController(TagService tagService) {
        this.tagService = tagService;
    }
}
