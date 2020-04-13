package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Tag;
import com.addonis.demo.services.base.BaseServiceContract;

public interface AddonService extends BaseServiceContract<Addon, Integer> {
    boolean checkAddonExistsById(int addonId);
}
