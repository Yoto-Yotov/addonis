package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.Tag;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/tag")
public class RestTagController {

    private TagService tagService;

    @Autowired
    public RestTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getAll();
    }

    @PostMapping("/{tagName}")
    public Tag createTag(@PathVariable("tagName") String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);

        try {
            tagService.create(tag);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return tag;
    }

    @GetMapping("/{tagId}")
    public Tag getTagById(@PathVariable int tagId) {
        try {
            return tagService.getById(tagId);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{tagName}")
    public String deleteTag(@PathVariable String tagName) {
        try {
            tagService.deleteTagByName(tagName);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        return String.format("Tag with name %s was successfully deleted", tagName);
    }

    @PutMapping("/{addonId}/{tagName}")
    public Tag addTagToAddon(@PathVariable int addonId, @PathVariable String tagName) {
        try {
            return tagService.addTagToAddon(addonId, tagName);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (DuplicateEntityException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @PutMapping("/{addonId}/remove/{tagName}")
    public String removeTagFromAddon(@PathVariable int addonId, @PathVariable String tagName) {
        try {
            tagService.removeTagFromAddon(addonId, tagName);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        return String.format("Tag %s successfully removed from addon", tagName);
    }

}
