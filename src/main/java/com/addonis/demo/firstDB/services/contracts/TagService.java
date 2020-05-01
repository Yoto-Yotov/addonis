package com.addonis.demo.firstDB.services.contracts;


import com.addonis.demo.firstDB.models.Tag;
import com.addonis.demo.firstDB.services.base.BaseServiceContract;

public interface TagService extends BaseServiceContract<Tag, Integer> {

    void deleteTagByName(String name, String userName);
    Tag addTagToAddon(int addonId, String tagName, String userName);
    void removeTagFromAddon(int addonId, String tagName, String userName);
    void renameTag(int tagId, String tagName, String userName);
}
