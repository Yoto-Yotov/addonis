package com.addonis.demo.controllers.rest;

import com.addonis.demo.models.Tag;
import com.addonis.demo.services.contracts.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
