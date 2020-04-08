package com.addonis.demo.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BaseRepository<T, K> extends JpaRepository<T, K> {
}
