package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Readme;
import com.addonis.demo.services.base.BaseServiceContract;

public interface ReadmeService extends BaseServiceContract<Readme, Integer> {

    String gerReadmeString(int readmeId);
}
