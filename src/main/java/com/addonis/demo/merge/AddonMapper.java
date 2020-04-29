package com.addonis.demo.utils;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonDTO;
import com.addonis.demo.models.BinaryContent;
import com.addonis.demo.services.contracts.BinaryContentService;

import java.io.IOException;

public class AddonUtils {


    public static Addon mapDtoToAddon(AddonDTO addonDto, BinaryContentService binaryContentService) throws IOException {
        Addon addon = new Addon();
        addon.setName(addonDto.getName());
        addon.setDescription(addonDto.getDescription());
        addon.setOriginLink(addonDto.getLink());
        addon.setPicture(addonDto.getAddonPicture());
        addon.setUserInfo(addonDto.getCreator());

        BinaryContent binaryContent = new BinaryContent();
        binaryContent.setDocName(addonDto.getFile().getOriginalFilename());
        binaryContent.setType(addonDto.getFile().getContentType());
        binaryContent.setFile(addonDto.getFile().getBytes());
        binaryContentService.create(binaryContent);
        addon.setBinaryFile(binaryContent.getId());

        return addon;
    }

}
