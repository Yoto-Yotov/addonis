package com.addonis.demo.secondDB.secondServices.base;

import java.util.List;


public interface BaseServiceContract<T, ID> {

    List<T> getAll();
    T getById(ID id);
    void deleteById(ID id);
    void update(T t);
    T create(T t);
}
