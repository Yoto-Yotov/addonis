package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
}
