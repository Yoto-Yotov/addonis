package com.addonis.demo.firstDB.repository.contracts;


import com.addonis.demo.firstDB.models.Readme;
import com.addonis.demo.firstDB.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadmeRepository extends BaseRepository<Readme, Integer> {
}
