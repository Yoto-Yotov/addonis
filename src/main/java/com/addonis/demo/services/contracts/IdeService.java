package com.addonis.demo.services.contracts;

import com.addonis.demo.models.IDE;

public interface IdeService {
    IDE getByName(String ideName);
    void createIde(String name);
}
