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
        return null;
    }

    @Override
    public Tag getById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void update(Tag tag) {

    }

    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTagByName(String name) {
        tagRepository.deleteTagByName(name);
    }

    @Override
    public Tag addTagToAddon(int addonId, String tagName, String userName) {
        Tag tagToAdd = tagRepository.getTagByName(tagName);

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

}
