package com.addonis.demo.firstDB.repository.contracts;

import com.addonis.demo.firstDB.models.LastCommit;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastCommitRepository extends BaseRepository<LastCommit, Integer> {

    boolean existsById(int id);
}
