package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.*;
import com.addonis.demo.models.commitresponse.LastCommitResponse;
import com.addonis.demo.models.enums.Sortby;
import com.addonis.demo.models.enums.Status;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.repository.contracts.ReadmeRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.GitHubService;
import com.addonis.demo.services.contracts.LastCommitService;
import com.addonis.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.addonis.demo.api.LastCommitMapper.mapLastCommitResponseToLastCommit;
import static com.addonis.demo.constants.Constants.*;

/**
 * AddonServiceImpl - used for business logic of the addons. All CRUD operation and validations.
 */
@Service
public class AddonServiceImpl implements AddonService {

    AddonRepository addonRepository;
    LastCommitService lastCommitService;
    GitHubService githubService;
    ReadmeRepository readmeRepository;
    UserService userService;

    @Autowired
    public AddonServiceImpl(AddonRepository addonRepository, LastCommitService lastCommitService,
                            GitHubService githubService, ReadmeRepository readmeRepository,
                            UserService userService) {
        this.addonRepository = addonRepository;
        this.lastCommitService = lastCommitService;
        this.githubService = githubService;
        this.readmeRepository = readmeRepository;
        this.userService = userService;
    }

    @Override
    public List<Addon> getAll() {
        return addonRepository.findAll();
    }

    @Override
    public Addon getById(Integer id) {
        return addonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDON, id));
    }

    @Override
    public Addon getAddonByName(String name) {
        if (addonRepository.getByName(name) == null) {
            throw new EntityNotFoundException(USER_U, name);
        }
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
        return getById(addonId).getUserInfo().getName();
    }

    @Override
    public void changeDownloadCount(int addonId) {
        Addon addon = getById(addonId);
        int downloadCount = addon.getDownloadsCount() + 1;
        addon.setDownloadsCount(downloadCount);
        update(addon);
    }

    @Override
    public void softDeleteAddon(String name, UserInfo user) {
        try {
            addonRepository.existsByName(name);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(ADDON_A, name);
        }
        Addon addon = addonRepository.getByName(name);
        if (!userService.isAdmin(user.getName()) ||
                !userService.getUserByName(name).getUsername().equals(addon.getUserInfo().getName())) {
            throw new NotAuthorizedException(user.getName());
        }
        addonRepository.softDeleteAddonInfo(name);
    }

    @Override
    public List<Addon> getNewest() {
        return addonRepository.findTop6ByStatusOrderByIdDesc(Status.APPROVED);
    }

    @Override
    public List<Addon> getTopByDownloads() {
        return addonRepository.findTop6ByStatusOrderByDownloadsCountDesc(Status.APPROVED);
    }

    @Override
    public List<Addon> get6Random() {
        return addonRepository.get6Random();
    }

    @Override
    public void deleteById(Integer id) {
        try {
            addonRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(ADDON, id);
        }
    }

    public List<Addon> findByNameContaining(String name) {
        return addonRepository.findAllByStatusAndNameContaining(Status.APPROVED, name);
    }

    @Override
    public void update(Addon addon) {
        try {
            addonRepository.save(addon);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DuplicateEntityException(ADDON);
        }
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
        } catch (DataIntegrityViolationException | IOException ex) {
            throw new DuplicateEntityException(ADDON);
        }
    }

    @Override
    public boolean checkAddonExistsById(int addonId) {
        return addonRepository.existsById(addonId);
    }

    public List<Addon> getAllSortBy(String direction, Sortby sortBy) {
        return addonRepository.findAllByStatus(Status.APPROVED, Sort.by(Sort.Direction.valueOf(direction), sortBy.getParam()));
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
