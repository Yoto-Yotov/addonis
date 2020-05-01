package com.addonis.demo.services;

import com.addonis.demo.models.IDE;
import com.addonis.demo.repository.contracts.IdeRepository;
import com.addonis.demo.services.contracts.IdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeServiceImpl implements IdeService {

    private IdeRepository ideRepository;

    @Autowired
    public IdeServiceImpl(IdeRepository ideRepository) {
        this.ideRepository = ideRepository;
    }

    @Override
    public IDE getByName(String ideName) {
        if (!ideRepository.existsByIdeNameIgnoreCase(ideName)) {
            createIde(ideName);
        }
        return ideRepository.findByIdeNameIgnoreCase(ideName);
    }

    @Override
    public void createIde(String name) {
        IDE ide = IDE.builder().ideName(name.toLowerCase()).build();
        ideRepository.save(ide);
    }

    @Override
    public List<IDE> getAll() {
        return ideRepository.findAll();
    }
}
