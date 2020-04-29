package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.*;
import com.addonis.demo.models.commitresponse.LastCommitResponse;
import com.addonis.demo.models.enums.Status;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.repository.contracts.ReadmeRepository;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.GitHubService;
import com.addonis.demo.services.contracts.LastCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.addonis.demo.utils.LastCommitMapper.mapLastCommitResponseToLastCommit;

/**
 * AddonServiceImpl
 * Service for Addon
 * Takes information from LastCommitService, AddonRepository, GitHubService
 * Provides methods for getting all Addons. No authentication needed,
 * Get addon by id. No authentication needed.
 * Delete addon. Authentication needed - user or admin.
 * Update addon. Authentication needed - user or admin.
 * Create addon + create last commit. Authentication needed - user or admin.
 */
@Service
public class AddonServiceImpl implements AddonService {

    AddonRepository addonRepository;
    LastCommitService lastCommitService;
    GitHubService githubService;
    ReadmeRepository readmeRepository;

    @Autowired
    public AddonServiceImpl(AddonRepository addonRepository, LastCommitService lastCommitService,
                            GitHubService githubService, ReadmeRepository readmeRepository) {
        this.addonRepository = addonRepository;
        this.lastCommitService = lastCommitService;
        this.githubService = githubService;
        this.readmeRepository =readmeRepository;
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
    public Addon getAddonById(int addonId) {
        return addonRepository.findById(addonId).orElseThrow(() -> new EntityNotFoundException("addon", addonId));
    }

    @Override
    public Addon getAddonByName(String name) {
        return addonRepository.getByName(name);
    }

    @Override
    public List<Addon> getAllPendingAddons() {
        return addonRepository.getAddonByStatus(Status.PENDING);
    }

    @Override
    public List<Addon> getAllApprovedAddons() {
        return addonRepository.getAddonByStatus(Status.APPROVED);
    }


    @Override
    public String getCreatorName(int addonId) {
        return getAddonById(addonId).getUserInfo().getName();
    }

    @Override
    public void changeDownloadCount(int addonId) {
        Addon addon = getAddonById(addonId);
        int downloadCount = addon.getDownloadsCount() + 1;
        addon.setDownloadsCount(downloadCount);
        update(addon);
    }

    @Override
    public void softDeleteAddon(String name) {
        addonRepository.softDeleteAddonInfo(name);
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
        if (checkAddonExistsByName(addon.getName())) {
            throw new DuplicateEntityException("Addon", "name", addon.getName());
        }
        String url = addon.getOriginLink();
        try {
            LastCommitResponse response = githubService.getLastCommit(url);
            LastCommit lastCommit = mapLastCommitResponseToLastCommit(response);
            lastCommitService.create(lastCommit);
            addon.setLastCommit(lastCommit);
            addon.setPullsCount(githubService.getPullsCount(url));
            addon.setStatus(Status.PENDING);
            addon.setIssuesCount(githubService.getIssuesCount(url));
            Readme readme = githubService.getReadme(url);
            readmeRepository.save(readme);
            addon.setReadmeId(readme.getReadmeId());
            return addonRepository.save(addon);
        }  catch (DataIntegrityViolationException | IOException ex) {
            throw new DuplicateEntityException("addon");
        }
    }

    @Override
    public boolean checkAddonExistsById(int addonId) {
        return addonRepository.existsById(addonId);
    }

    @Override
    public boolean checkAddonExistsByName(String name) {
        return addonRepository.existsByName(name);
    }

    @Override
    public List<Addon> getMyAddons(UserInfo user) {
        return addonRepository.getMyAddons(user);
    }

    @Override
    public Byte[] getContent(int id) {
//        return addonRepository.getFile(id);
        return null;
    }

    @Override
    public void enableAddon(String name) {
        Addon addon = addonRepository.getByName(name);
        addon.setStatus(Status.APPROVED);
        addonRepository.save(addon);
    }




}
