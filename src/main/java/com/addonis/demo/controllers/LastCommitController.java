package com.addonis.demo.controllers;

import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LastCommitController {

    private LastCommitService LastCommitService;

    @Autowired
    public LastCommitController(LastCommitService lastCommitService) {
        LastCommitService = lastCommitService;
    }
}