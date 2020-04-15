package com.addonis.demo.services;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.repository.contracts.LastCommitRepository;
import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LastCommitServiceImpl implements LastCommitService {

    private LastCommitRepository lastCommitRepository;

    @Autowired
    public LastCommitServiceImpl(LastCommitRepository lastCommitRepository) {
        this.lastCommitRepository = lastCommitRepository;
    }

    @Override
    public List<LastCommit> getAll() {
        return lastCommitRepository.findAll();
    }

    @Override
    public LastCommit getById(Integer integer) {
        return lastCommitRepository.getOne(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        lastCommitRepository.deleteById(integer);
    }

    @Override
    public void update(LastCommit lastCommit) {
        lastCommitRepository.save(lastCommit);
    }

    @Override
    public LastCommit create(LastCommit lastCommit) {
        return lastCommitRepository.save(lastCommit);
    }
}
