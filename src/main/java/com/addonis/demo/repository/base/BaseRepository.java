package com.addonis.demo.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * BaseRepository
 * @param <T> model type
 * @param <K> primary key
 */
@NoRepositoryBean
public interface BaseRepository<T, K> extends JpaRepository<T, K> {
}
