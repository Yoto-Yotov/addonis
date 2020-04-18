package com.addonis.demo.services;

import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileServiceImpl
 * Service for uploading addon binary content
 */
@Service
public class FileServiceImpl implements FileService {

    AddonService addonService;

    @Autowired
    public FileServiceImpl(AddonService addonService) {
        this.addonService = addonService;
    }

    @Override
    public void saveAddonFile(int addonId, MultipartFile file) {

        try {
            Addon addon = addonService.getById(addonId);

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            addon.setContent(byteObjects);

            addonService.update(addon);

        } catch (IOException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
    }

}
