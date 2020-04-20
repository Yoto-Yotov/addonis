package com.addonis.demo.utils;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonDTO;

public class AddonUtils {

    public static Addon mapDtoToAddon(AddonDTO addonDto) {
        Addon addon = new Addon();
        addon.setName(addonDto.getName());
        addon.setDescription(addonDto.getDescription());
        addon.setOriginLink(addonDto.getLink());
        addon.setPicture(addonDto.getAddonPicture());
        addon.setUserInfo(addonDto.getCreator());
        return addon;
    }

}
