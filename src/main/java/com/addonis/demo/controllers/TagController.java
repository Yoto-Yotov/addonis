package com.addonis.demo.controllers;

import com.addonis.demo.firstDB.services.contracts.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * TagController - set tags to addons, update tag and delete tag. Visualization of all tags.
 */
@Controller
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
}
