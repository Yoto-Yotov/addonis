package com.addonis.demo.secondDB.secondRepository.contracts;

import com.addonis.demo.firstDB.repository.base.BaseRepository;
import com.addonis.demo.secondDB.secondModels.BinaryContent;
import org.springframework.stereotype.Repository;

@Repository
public interface BinaryContentRepository extends BaseRepository<BinaryContent, Integer> {

    BinaryContent findByDocName(String name);
}
