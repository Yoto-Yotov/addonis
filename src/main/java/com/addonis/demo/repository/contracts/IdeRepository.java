package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.IDE;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeRepository extends BaseRepository<IDE, Integer> {
    boolean existsByIdeNameIgnoreCase(String name);

    IDE findByIdeNameIgnoreCase(String name);
}
