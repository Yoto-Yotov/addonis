package com.addonis.demo.services;

import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public ImageServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
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
}
