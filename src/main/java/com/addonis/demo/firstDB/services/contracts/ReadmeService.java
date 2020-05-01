package com.addonis.demo.firstDB.services.contracts;

import com.addonis.demo.firstDB.models.Readme;
import com.addonis.demo.firstDB.services.base.BaseServiceContract;

public interface ReadmeService extends BaseServiceContract<Readme, Integer> {

    String gerReadmeString(int readmeId);
}
