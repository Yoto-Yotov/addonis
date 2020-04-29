package com.addonis.demo.controllers.rest;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.models.LastCommit;
import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * LastCommit RestController for getting last commit information
 */
@RestController
@RequestMapping("api/lastcommit")
public class RestLastCommitController {

    private LastCommitService lastCommitService;

    @Autowired
    public RestLastCommitController(LastCommitService lastCommitService) {
        this.lastCommitService = lastCommitService;
    }

    @GetMapping("/{lastCommintId}")
    public LastCommit getLastCommit(@PathVariable int lastCommintId) {
        try {
            return lastCommitService.getById(lastCommintId);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
