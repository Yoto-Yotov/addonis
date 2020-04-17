package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.Tag;
import com.addonis.demo.repository.contracts.TagRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private AddonService addonService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, AddonService addonService) {
        this.tagRepository = tagRepository;
        this.addonService = addonService;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getById(Integer tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("tag", tagId));
    }

    @Override
    public void deleteById(Integer tagId) {
        if(!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("tag", tagId);
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
            throw new DuplicateEntityException("tag", "name", tag.getTagName());
        }
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTagByName(String name) {
        Tag tagToDelete = tagRepository.getTagByTagName(name);
        if(tagToDelete == null) {
            throw new EntityNotFoundException("tag", name);
        }
        tagRepository.deleteTagFromAllAddons(tagToDelete.getTagId());
        tagRepository.deleteTagByName(name);
    }

    @Override
    public Tag addTagToAddon(int addonId, String tagName) {
        Tag tagToAdd = tagRepository.getTagByTagName(tagName);

        if(tagToAdd == null) {
            tagToAdd = new Tag();
            tagToAdd.setTagName(tagName);
            create(tagToAdd);
        }

        if(!addonService.checkAddonExistsById(addonId)) {
            throw new EntityNotFoundException("Addon", addonId);
        }

        try {
            tagRepository.addTagToAddon(addonId, tagToAdd.getTagId());
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DuplicateEntityException("addon", "tag");
        }

        return tagToAdd;
    }

    @Override
    public void removeTagFromAddon(int addonId, String tagName) {
        Tag tagToRemove = tagRepository.getTagByTagName(tagName);
        tagRepository.removeTagFromAddon(tagToRemove.getTagId(), addonId);
    }

}
