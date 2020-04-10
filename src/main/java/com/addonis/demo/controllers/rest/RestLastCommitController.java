package com.addonis.demo.controllers.rest;

import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/lastcommit")
public class RestLastCommitController {

    private LastCommitService lastCommitService;

    public RestLastCommitController(LastCommitService lastCommitService) {
        this.lastCommitService = lastCommitService;
    }
}
