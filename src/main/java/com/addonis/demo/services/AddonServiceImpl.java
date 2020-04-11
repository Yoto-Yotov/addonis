package com.addonis.demo.services;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.LastCommit;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddonServiceImpl implements AddonService {

    AddonRepository addonRepository;
    LastCommitService lastCommitService;

    @Autowired
    public AddonServiceImpl(AddonRepository addonRepository, LastCommitService lastCommitService) {
        this.addonRepository = addonRepository;
        this.lastCommitService = lastCommitService;
    }

    @Override
    public List<Addon> getAll() {
        return addonRepository.findAll();
    }

    @Override
    public Addon getById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void update(Addon addon) {
        addonRepository.save(addon);
    }

    @Override
    public void create(Addon addon) {
        addonRepository.save(addon);
        //LastCommit lastCommit //to create using the url
        //lastCommitService.create(lastCommit);
        //lastCommitService.create(addon.getOriginLink());
    }
}
