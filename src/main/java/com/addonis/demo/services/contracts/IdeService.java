package com.addonis.demo.services.contracts;

import com.addonis.demo.models.IDE;

import java.util.List;

public interface IdeService {
    IDE getByName(String ideName);
    void createIde(String name);
    List<IDE> getAll();
}
