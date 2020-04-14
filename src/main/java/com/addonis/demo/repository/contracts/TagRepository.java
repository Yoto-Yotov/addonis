package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Tag;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TagRepository extends BaseRepository<Tag, Integer> {

    @Modifying
    @Query("delete from Tag t where t.tagName = :tagName")
    void deleteTagByName(@Param("tagName") String tagName);

    @Modifying
    @Query(value = "DELETE FROM addons_tags WHERE tags_tag_id = :tagId", nativeQuery = true)
    void deleteTagFromAllAddons(@Param("tagId") int tagId);

    @Modifying
    @Query(value = "DELETE FROM addons_tags WHERE tags_tag_id = :tagId and addon_addon_id = :addonId", nativeQuery = true)
    void removeTagFromAddon(@Param("tagId") int tagId, @Param("addonId") int addonId);

    @Modifying
    @Query(value = "INSERT INTO addons_tags (addon_addon_id, tags_tag_id) VALUES (:addonId, :tagId)", nativeQuery = true)
    void addTagToAddon(@Param("addonId") int addonId, @Param("tagId") int tagId);

    Tag getTagByTagName(@Param("tagName") String tagName);

    boolean existsByTagName(String tagName);
}
