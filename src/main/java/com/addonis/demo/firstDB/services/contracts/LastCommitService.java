package com.addonis.demo.firstDB.services.contracts;


import com.addonis.demo.firstDB.models.LastCommit;
import com.addonis.demo.firstDB.services.base.BaseServiceContract;

public interface LastCommitService extends BaseServiceContract<LastCommit, Integer> {
    boolean existsById(int id);
}
