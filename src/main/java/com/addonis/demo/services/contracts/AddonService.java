package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Tag;
import com.addonis.demo.services.base.BaseServiceContract;

import java.util.List;

public interface AddonService extends BaseServiceContract<Addon, Integer> {
    Addon getAddonById(int addonId);
    List<Addon> getAllPendingAddons();
    List<Addon> getAllApprovedAddons();
    Addon getAddonByName(String name);
    boolean checkAddonExistsById(int addonId);
    boolean checkAddonExistsByName(String name);
}
