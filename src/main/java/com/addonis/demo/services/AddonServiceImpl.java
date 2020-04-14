package com.addonis.demo.services;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.commitresponse.LastCommitResponse;
import com.addonis.demo.models.enums.Status;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.GitHubService;
import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.addonis.demo.utils.LastCommitMapper.mapLastCommitResponseToLastCommit;

@Service
public class AddonServiceImpl implements AddonService {

    AddonRepository addonRepository;
    LastCommitService lastCommitService;
    GitHubService githubService;

    @Autowired
    public AddonServiceImpl(AddonRepository addonRepository, LastCommitService lastCommitService,
                            GitHubService githubService) {
        this.addonRepository = addonRepository;
        this.lastCommitService = lastCommitService;
        this.githubService = githubService;
    }

    @Override
    public List<Addon> getAll() {
        return addonRepository.findAll();
    }

    @Override
    public Addon getById(Integer integer) {
        return addonRepository.getOne(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        addonRepository.deleteById(integer);
    }

    @Override
    public void update(Addon addon) {
        addonRepository.save(addon);
    }

    @Override
    public Addon create(Addon addon) {
        String url = addon.getOriginLink();
        LastCommitResponse response = githubService.getLastCommit(url);
        LastCommit lastCommit = mapLastCommitResponseToLastCommit(response);
        lastCommitService.create(lastCommit);
        addon.setLastCommit(lastCommit);
        addon.setPullsCount(githubService.getPullsCount(url));
        addon.setStatus(Status.PENDING);
        addon.setIssuesCount(githubService.getIssuesCount(url));
        addonRepository.save(addon);
        return addon;
    }

    @Override
    public boolean checkAddonExistsById(int addonId) {
        return addonRepository.existsById(addonId);
    }
}
