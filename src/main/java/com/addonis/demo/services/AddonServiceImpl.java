package com.addonis.demo.services;

import com.addonis.demo.models.Addon;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.services.contracts.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddonServiceImpl implements AddonService {

    AddonRepository addonRepository;

    @Autowired
    public AddonServiceImpl(AddonRepository addonRepository) {
        this.addonRepository = addonRepository;
    }

    @Override
    public List<Addon> getAll() {
        return null;
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
    }
}
