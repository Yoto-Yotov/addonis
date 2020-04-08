package com.addonis.demo.services;

import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddonServiceImpl implements AddonService {

    AddonRepository addonRepository;

    @Autowired
    public AddonServiceImpl(AddonRepository addonRepository) {
        this.addonRepository = addonRepository;
    }
}
