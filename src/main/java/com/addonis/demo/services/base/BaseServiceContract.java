package com.addonis.demo.services.base;

import com.addonis.demo.models.Authorities;

import java.util.List;

public interface BaseServiceContract<T, ID> {

    List<T> getAll();
    T getById(ID id);
    void deleteById(ID id);
    void update(T t);
    Authorities create(T t);
}
