package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Tag;
import com.addonis.demo.services.base.BaseServiceContract;

import java.util.List;

public interface TagService extends BaseServiceContract<Tag, Integer> {

    void deleteTagByName(String name);

    Tag addTagToAddon(int addonId, String tagName, String userName);
}
