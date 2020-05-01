package com.addonis.demo.firstDB.services;

import com.addonis.demo.firstDB.models.Addon;
import com.addonis.demo.firstDB.models.UserInfo;
import com.addonis.demo.firstDB.repository.contracts.AddonRepository;
import com.addonis.demo.firstDB.repository.contracts.UserInfoRepository;
import com.addonis.demo.firstDB.services.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            user.setProfileImage(byteObjects);
            userInfoRepository.save(user);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveImageFileToAddon(int addonId, MultipartFile file) {
        try {
            Addon addon = addonRepository.getOne(addonId);

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            addon.setPicture(byteObjects);
            addonRepository.save(addon);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
