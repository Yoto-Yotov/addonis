package com.addonis.demo.repository.contracts;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.repository.base.BaseRepository;

import java.util.List;

public interface AuthorityRepository extends BaseRepository<Authorities, String> {
    List<Authorities> getByUsername(String username);
}
