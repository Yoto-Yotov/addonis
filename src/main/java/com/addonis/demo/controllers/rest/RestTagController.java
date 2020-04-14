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

    @DeleteMapping("/delete/{tagName}")
    public String deleteTag( @PathVariable String tagName) {
        tagService.deleteTagByName(tagName);
        return String.format("Tag with name %s was successfully deleted", tagName);
    }

    @PutMapping("/{addonId}/{tagName}")
    public Tag addTagToAddon(@PathVariable int addonId, @PathVariable String tagName, @RequestHeader(name = "Authorization") String authorization) {
        try {
            return tagService.addTagToAddon(addonId, tagName, authorization);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (DuplicateEntityException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }
}
