package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddonRepository extends BaseRepository<Addon, Integer> {
}
