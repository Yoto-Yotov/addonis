package com.addonis.demo.services;

import com.addonis.demo.models.Tag;
import com.addonis.demo.services.contracts.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
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
    public void create(Tag tag) {

    }
}
