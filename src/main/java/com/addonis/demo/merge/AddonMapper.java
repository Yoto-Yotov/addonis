package com.addonis.demo.merge;

import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.AddonDTO;
import com.addonis.demo.secondDB.secondModels.BinaryContent;
import com.addonis.demo.secondDB.secondServices.contracts.BinaryContentService;

import java.io.IOException;

/**
 * AddonMapper maps existing addon with the new fields
 */
public class AddonMapper {


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
