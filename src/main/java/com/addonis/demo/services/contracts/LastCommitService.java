package com.addonis.demo.services.contracts;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.services.base.BaseServiceContract;

public interface LastCommitService extends BaseServiceContract<LastCommit, Integer> {
    boolean existsById(int id);
}
