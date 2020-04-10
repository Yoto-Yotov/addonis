package com.addonis.demo.services.base;

import java.util.List;

public interface BaseServiceContract<T, ID> {

    List<T> getAll();
    T getById(ID id);
    void deleteById(ID id);
    void update(T t);
    void create(T t);
}
