package com.addonis.demo.firstDB.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * BaseRepository
 * @param <T> model type
 * @param <K> primary key
 */
@NoRepositoryBean
public interface BaseRepository<T, K> extends JpaRepository<T, K> {
}
