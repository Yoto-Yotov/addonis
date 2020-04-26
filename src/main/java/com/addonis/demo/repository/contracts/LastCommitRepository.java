package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastCommitRepository extends BaseRepository<LastCommit, Integer> {

    boolean existsById(int id);
}
