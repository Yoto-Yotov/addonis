package com.addonis.demo.services;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LastCommitServiceImpl implements LastCommitService {
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

    }
}
