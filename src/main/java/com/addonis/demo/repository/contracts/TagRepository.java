package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Tag;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, Integer> {

    @Query("delete from Tag t where t.tagName = :tagName")
    void deleteTagByName(@Param("tagName") String tagName);
}
