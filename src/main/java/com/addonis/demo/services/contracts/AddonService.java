package com.addonis.demo.services.contracts;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Tag;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.models.enums.Status;
import com.addonis.demo.services.base.BaseServiceContract;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddonService extends BaseServiceContract<Addon, Integer> {
    Addon getAddonById(int addonId);
    List<Addon> getAllPendingAddons();
    List<Addon> getAllApprovedAddons();
    Addon getAddonByName(String name);
    boolean checkAddonExistsById(int addonId);
    boolean checkAddonExistsByName(String name);
    List<Addon> getMyAddons(UserInfo user);
    Byte[] getContent(int id);
    void enableAddon(String name);
    String getCreatorName(int addonId);
    void changeDownloadCount(int addonId);

    void softDeleteAddon(String name);
}
