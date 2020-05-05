package com.addonis.demo.services;

import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.addonis.demo.constants.Constants.*;

/**
 * ImageServiceImpl takes the uploaded file. Makes it to Byte[] and saves it in the data base.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private UserInfoRepository userInfoRepository;
    private AddonRepository addonRepository;

    @Autowired
    public ImageServiceImpl(UserInfoRepository userInfoRepository, AddonRepository addonRepository) {
        this.userInfoRepository = userInfoRepository;
        this.addonRepository = addonRepository;
    }

    @Override
    public void saveImageFile(int userId, MultipartFile file) {
        try {
            UserInfo user = userInfoRepository.getOne(userId);

            Byte[] byteObjects = getBytesFromFile(file);

            user.setProfileImage(byteObjects);
            userInfoRepository.save(user);

        } catch (IOException ex) {
            throw new IllegalStateException(FAIL_TO_UPLOAD_IMAGE);
        }
    }

    @Override
    public void saveImageFileToAddon(int addonId, MultipartFile file) {
        try {
            Addon addon = addonRepository.getOne(addonId);

            Byte[] byteObjects = getBytesFromFile(file);

            addon.setPicture(byteObjects);
            addonRepository.save(addon);

        } catch (IOException ex) {
            throw new IllegalStateException(FAIL_TO_UPLOAD_IMAGE);
        }
    }

    private Byte[] getBytesFromFile(MultipartFile file) throws IOException {
        Byte[] byteObjects = new Byte[file.getBytes().length];

        int i = 0;

        for (byte b : file.getBytes()) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }

    @Override
    public void checkIfImageExists(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidDataException(PICTURE, FILE_NOT_BLANK);
        }
    }
}
