package com.addonis.demo.validation;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.firstDB.models.AddonDTO;
import com.addonis.demo.firstDB.services.contracts.AddonService;

import static com.addonis.demo.constants.Constants.ADDON_A;

public class AddonValidator {

    public static void validateAddonDto(AddonDTO addonDTO, AddonService addonService) {
        if (addonService.checkAddonExistsByName(addonDTO.getName())) {
            throw new DuplicateEntityException(ADDON_A, addonDTO.getName());
        }
        if (addonDTO.getDescription() == null) {
            throw new InvalidDataException("description");
        }
//        if (addonDTO.getAddonPicture() == null) {
//            throw new InvalidDataException("picture");
//        }
//        if (addonDTO.getFile() == null) {
//            throw new InvalidDataException("binary file");
//        }
    }
}
