package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Tag;
import com.addonis.demo.repository.contracts.TagRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.TagService;
import com.addonis.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.addonis.demo.constants.Constants.*;

/**
 * TagServiceImpl
 * Get all tags. No authentication needed
 * Get all tags of an addon. No authentication needed.
 * Create and add tag to addon. Authentication needed - user or admin.
 */
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private AddonService addonService;
    private UserService userService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, AddonService addonService, UserService userService) {
        this.tagRepository = tagRepository;
        this.addonService = addonService;
        this.userService = userService;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getById(Integer tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException(TAG, tagId));
    }

    @Override
    public void deleteById(Integer tagId) {
        if(!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException(TAG, tagId);
        }
        tagRepository.deleteTagFromAllAddons(tagId);
        tagRepository.deleteById(tagId);
    }

    @Override
    public void update(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public Tag create(Tag tag) {
        if(tagRepository.existsByTagName(tag.getTagName())) {
            throw new DuplicateEntityException(TAG, NAME, tag.getTagName());
        }
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTagByName(String name, String userName) {
        if(!userService.isAdmin(userName)) {
            throw new NotAuthorizedException(userName);
        }
        Tag tagToDelete = tagRepository.getTagByTagName(name);
        if(tagToDelete == null) {
            throw new EntityNotFoundException(TAG, name);
        }
        tagRepository.deleteTagFromAllAddons(tagToDelete.getTagId());
        tagRepository.deleteTagByName(name);
    }

    @Override
    public Tag addTagToAddon(int addonId, String tagName, String userName) {
        if(!userService.isAdmin(userName) && !addonService.getCreatorName(addonId).equals(userName)) {
            throw new NotAuthorizedException(userName);
        }

        Tag tagToAdd = tagRepository.getTagByTagName(tagName);

        if(tagToAdd == null) {
            tagToAdd = new Tag();
            tagToAdd.setTagName(tagName);
            create(tagToAdd);
        }

        if(!addonService.checkAddonExistsById(addonId)) {
            throw new EntityNotFoundException(ADDON_A, addonId);
        }

        try {
            tagRepository.addTagToAddon(addonId, tagToAdd.getTagId());
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DuplicateEntityException(ADDON, TAG);
        }

        return tagToAdd;
    }

    @Override
    public void removeTagFromAddon(int addonId, String tagName, String userName) {
        if(!userService.isAdmin(userName) && !addonService.getCreatorName(addonId).equals(userName)) {
            throw new NotAuthorizedException(userName);
        }
        Tag tagToRemove = tagRepository.getTagByTagName(tagName);
        tagRepository.removeTagFromAddon(tagToRemove.getTagId(), addonId);
    }

    @Override
    public void renameTag(int tagId, String tagName, String userName) {
        if(!userService.isAdmin(userName)) {
            throw new NotAuthorizedException(userName);
        }
        Tag tagToUpdate = tagRepository.getOne(tagId);
        tagToUpdate.setTagName(tagName);
        try {
            tagRepository.save(tagToUpdate);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DuplicateEntityException(TAG, NAME, tagName);
        }
    }

}
