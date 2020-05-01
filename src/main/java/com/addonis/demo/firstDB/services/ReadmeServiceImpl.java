package com.addonis.demo.firstDB.services;

import com.addonis.demo.firstDB.models.Readme;
import com.addonis.demo.firstDB.repository.contracts.ReadmeRepository;
import com.addonis.demo.firstDB.services.contracts.ReadmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * ReadmeServiceImpl takes information from GitHub and represents it.
 */
@Service
public class ReadmeServiceImpl implements ReadmeService {

    private ReadmeRepository readmeRepository;

    @Autowired
    public ReadmeServiceImpl(ReadmeRepository readmeRepository) {
        this.readmeRepository = readmeRepository;
    }

    @Override
    public List<Readme> getAll() {
        return readmeRepository.findAll();
    }

    @Override
    public Readme getById(Integer integer) {
        Readme readme = readmeRepository.getOne(integer);

        return readmeRepository.getOne(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        readmeRepository.deleteById(integer);
    }

    @Override
    public void update(Readme readme) {
        readmeRepository.save(readme);
    }

    @Override
    public Readme create(Readme readme) {
        return readmeRepository.save(readme);
    }

    @Override
    public String gerReadmeString(int readmeId) {
        Readme readme = getById(readmeId);
        byte[] code = Base64.getMimeDecoder().decode(readme.getText());
        String encoded =  new String(code);
        return encoded;
    }
}
