package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.services.base.BaseServiceContract;

import java.util.List;

public interface AuthorityService extends BaseServiceContract<Authorities, String> {
        List<Authorities> getUserAuthorities(String userName);
}

