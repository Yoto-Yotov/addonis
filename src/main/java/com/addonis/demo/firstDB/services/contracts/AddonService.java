package com.addonis.demo.firstDB.services.contracts;

import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.services.base.BaseServiceContract;
import com.addonis.demo.models.enums.Sortby;

import java.util.List;

public interface AddonService extends BaseServiceContract<Addon, Integer> {
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
    void softDeleteAddon(String name, UserInfo user);
    List<Addon> getAllSortBy(String direction, Sortby sortby);
    List<Addon> getNewest();
    List<Addon> getTopByDownloads();
    List<Addon> get6Random();
    List<Addon> findByNameContaining(String name);
}
