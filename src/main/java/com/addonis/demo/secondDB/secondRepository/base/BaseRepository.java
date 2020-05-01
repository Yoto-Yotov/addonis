package com.addonis.demo.secondDB.secondRepository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface BaseRepository<T, K> extends JpaRepository<T, K> {
}