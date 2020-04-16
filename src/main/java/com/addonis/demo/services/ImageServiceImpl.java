package com.addonis.demo.services;

import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * ImageServiceImpl
 * Takes the uploaded file. Makes it to Byte[] and saves it in the data base.
 */
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

            if (file.getBytes().length == 0) {
                URL url = new URL("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png");
                BufferedImage myPicture = ImageIO.read(url);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( myPicture, "png", baos );
                byte[] imageInByte = baos.toByteArray();
                byteObjects = new Byte[imageInByte.length];
            }

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
