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
        return null;
    }

    @Override
    public LastCommit getById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void update(LastCommit lastCommit) {

    }

    @Override
    public void create(LastCommit lastCommit) {
        lastCommitRepository.save(lastCommit);
    }
}
