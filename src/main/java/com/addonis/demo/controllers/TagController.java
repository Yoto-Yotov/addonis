package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.TagService;
import org.springframework.stereotype.Controller;

@Controller
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
}
