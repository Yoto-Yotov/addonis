package com.addonis.demo.firstDB.services;

import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.firstDB.models.LastCommit;
import com.addonis.demo.firstDB.repository.contracts.LastCommitRepository;
import com.addonis.demo.firstDB.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * LastCommitServiceImpl takes the information about the last commit of addon
 */
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
        if (!existsById(integer)) {
            throw new EntityNotFoundException("Last commit", String.valueOf(integer));
        }
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

    @Override
    public boolean existsById(int id) {
        return lastCommitRepository.existsById(id);
    }
}
