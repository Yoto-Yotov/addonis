package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Tag;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, Integer> {

}
