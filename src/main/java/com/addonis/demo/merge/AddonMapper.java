package com.addonis.demo.merge;

import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.AddonDTO;
import com.addonis.demo.models.BinaryContent;
import com.addonis.demo.services.contracts.BinaryContentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.addonis.demo.constants.Constants.BINARY;
import static com.addonis.demo.constants.Constants.FILE_NOT_BLANK;

/**
 * AddonMapper maps existing addon with the new fields
 */
public class AddonMapper {

    public static Addon mapDtoToAddon(AddonDTO addonDto, MultipartFile file, BinaryContentService binaryContentService) throws IOException {
        Addon addon = mapAddonDtoToAddon(addonDto);

        addBinaryContent(file, binaryContentService, addon);

        return addon;
    }

    public static Addon mapAddonDtoToAddon(AddonDTO addonDto) {
        Addon addon = new Addon();
        addon.setName(addonDto.getName());
        addon.setDescription(addonDto.getDescription());
        addon.setOriginLink(addonDto.getLink());
        addon.setPicture(addonDto.getAddonPicture());
        addon.setUserInfo(addonDto.getCreator());
        return addon;
    }

    public static void addBinaryContent(MultipartFile file, BinaryContentService binaryContentService, Addon addon) throws IOException {
        if (file.isEmpty()) {
            throw new InvalidDataException(BINARY, FILE_NOT_BLANK);
        }
        BinaryContent binaryContent = new BinaryContent();
        binaryContent.setDocName(file.getOriginalFilename());
        binaryContent.setType(file.getContentType());
        binaryContent.setFile(file.getBytes());
        binaryContentService.create(binaryContent);
        addon.setBinaryFile(binaryContent.getId());
    }

}
